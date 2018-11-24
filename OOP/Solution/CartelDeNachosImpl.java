package OOP.Solution;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CartelDeNachosImpl implements CartelDeNachos{

    private HashSet<Profesor> profesors;
    private HashSet<CasaDeBurrito> casas;

    public CartelDeNachosImpl(){
        profesors = new HashSet<>();
        casas = new HashSet<>();
    }

    @Override
    public Profesor joinCartel(int id, String name)
            throws Profesor.ProfesorAlreadyInSystemException{
        Profesor p = new ProfesorImpl(id, name);
        if(profesors.contains(p))
            throw new Profesor.ProfesorAlreadyInSystemException();
        profesors.add(p);
        return p;
    }

    @Override
    public CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu)
            throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException{
        CasaDeBurrito c = new CasaDeBurritoImpl(id, name, dist, menu);
        if(casas.contains(c))
            throw new CasaDeBurrito.CasaDeBurritoAlreadyInSystemException();
        casas.add(c);
        return c;
    }

    @Override
    public  Collection<Profesor> registeredProfesores(){
        return (new HashSet<Profesor>(profesors));
    }

    @Override
    public Collection<CasaDeBurrito> registeredCasasDeBurrito(){
        return (new HashSet<CasaDeBurrito>(casas));
    }

    @Override
    public Profesor getProfesor(int id)
            throws Profesor.ProfesorNotInSystemException{
        LinkedList<Profesor> single_profesor =  profesors.stream()
                                                    .filter(p -> p.getId() == id)
                                                    .collect(Collectors.toCollection(LinkedList::new));
        if(single_profesor.isEmpty())
            throw new Profesor.ProfesorNotInSystemException();
        return single_profesor.getFirst();
    }

    @Override
    public CasaDeBurrito getCasaDeBurrito(int id)
            throws CasaDeBurrito.CasaDeBurritoNotInSystemException{
        LinkedList<CasaDeBurrito> single_casa = casas.stream()
                                                        .filter(c -> c.getId() == id)
                                                        .collect(Collectors.toCollection(LinkedList::new));
        if(single_casa.isEmpty())
            throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        return single_casa.getFirst();
    }

    @Override
    public CartelDeNachos addConnection(Profesor p1, Profesor p2)
            throws Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException{
        if((!profesors.contains(p1)) || !profesors.contains(p2))
            throw new Profesor.ProfesorNotInSystemException();
        if(p1.equals(p2))
            throw new Profesor.SameProfesorException();
        if(p1.getFriends().contains(p2) || p2.getFriends().contains(p1))
            throw new Profesor.ConnectionAlreadyExistsException();
        p1.addFriend(p2);
        p2.addFriend(p1);
        return this;

    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(Profesor p)
            throws Profesor.ProfesorNotInSystemException{
        return favoriteByMap(p, true);
    }

    @Override
    public  Collection<CasaDeBurrito> favoritesByDist(Profesor p)
            throws Profesor.ProfesorNotInSystemException{
        return favoriteByMap(p, false);
    }

    private Collection<CasaDeBurrito> favoriteByMap(Profesor p, boolean by_rate)
            throws Profesor.ProfesorNotInSystemException{
        if(!profesors.contains(p))
            throw new Profesor.ProfesorNotInSystemException();
        LinkedList<Collection<CasaDeBurrito>> tmp =  (p.getFriends().stream()
                .sorted()
                .map(by_rate ?
                        profesor -> profesor.favoritesByRating(0) :
                        profesor -> profesor.favoritesByDist(Integer.MAX_VALUE)
                )
                .collect(Collectors.toCollection(LinkedList::new)));
        Collection<CasaDeBurrito> ret = tmp.getFirst();
        for (Collection<CasaDeBurrito> c: tmp) {
            ret.addAll(c);
        }
        return ret.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public  boolean getRecommendation(Profesor p, CasaDeBurrito c, int t)
            throws Profesor.ProfesorNotInSystemException,
                    CasaDeBurrito.CasaDeBurritoNotInSystemException,
                    CartelDeNachos.ImpossibleConnectionException{
        if(!profesors.contains(p))
            throw new Profesor.ProfesorNotInSystemException();
        if(!casas.contains(c))
            throw new CasaDeBurrito.CasaDeBurritoNotInSystemException();
        if(t<0)
            throw new CartelDeNachos.ImpossibleConnectionException();
        if(p.favorites().contains(c))
            return true;
        HashSet<Profesor> pFriends = new HashSet<Profesor>();
        pFriends.add(p);
        for (int i = 0; i < t; i++){
            HashSet<Profesor> tempFriends = new HashSet<Profesor>();
            for(Profesor prof : pFriends){
                tempFriends.addAll(prof.getFriends());
            }
            if(!pFriends.addAll(tempFriends))   //returns true if modified and false otherwise
                break;
        }
        for(Profesor prof : pFriends){
            if(prof.favorites().contains(c))
                return true;
        }
        return false;
    }

    @Override
    public List<Integer> getMostPopularRestaurantsIds(){
        if(casas.isEmpty()){
            return new LinkedList<>();
        }
        HashMap<Integer,Integer> allCasas = new HashMap<>();
        for(CasaDeBurrito c : casas){
            allCasas.put(c.getId(),0);
        }
        for(Profesor prof : profesors){
            LinkedList<Profesor> pFriends = prof.getFriends();
            for(Profesor pF : pFriends){
                HashSet<CasaDeBurrito> favs = pF.favorites();
                for(CasaDeBurrito c : favs){
                    Integer curRank = allCasas.get(c.getId()) + 1;
                    allCasas.put(c.getId(),curRank);
                }
            }
        }
        Integer max = 0;
        Collection<Integer> allValues = allCasas.values();
        for(Integer v : allValues){
            if (v > max){
                max = v;
            }
        }
        LinkedList<Integer> popularCasas = new LinkedList<>();
        Set<Integer> allKeys = allCasas.keySet();
        for(Integer i : allKeys){
            if(allCasas.get(i) == max){
                popularCasas.add(i);
            }
        }
        Collections.sort(popularCasas);
    }

    @Override
    public String toString(){
        String registered_profesor = "Registered profesores: " +
                                        getIdsString(profesors, profesor ->( (Profesor)profesor).getId());

        String registered_cassas = "Registered casas de burrito: " +
                                        getIdsString(casas, casa -> ((CasaDeBurrito)casa).getId());

        String profesors_string = "Profesores:\n";
        LinkedList<Profesor> sorted_profs = profesors.stream()
                                                    .sorted()
                                                    .collect(Collectors.toCollection(LinkedList::new));
        for (Profesor p: sorted_profs) {
            profesors_string += Integer.toString(p.getId()) + " -> [" +
                    getIdsString(p.getFriends(), profesor ->( (Profesor)profesor).getId())
                    .replace(".\n", "].\n");
        }
        profesors_string +="End profesores.";
        return registered_profesor + registered_cassas + profesors_string;
    }
    private String getIdsString(Collection<?> collection, Function<? super Object , ? super Integer> mapper){
        return collection.stream()
                .map(mapper)
                .sorted()
                .collect(Collectors.toList())
                .toString()
                .replace("[", "")
                .replace("]", ".\n");
    }

}
