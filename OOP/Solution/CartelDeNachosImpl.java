package OOP.Solution;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.stream.Collectors;

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
            throw Profesor.ConnectionAlreadyExistsException();
        p1.addFriend(p2);
        p2.addFriend(p1);
        return this;

    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(Profesor p)
            throws Profesor.ProfesorNotInSystemException{
        if(!profesors.contains(p))
            throw new Profesor.ProfesorNotInSystemException();
        // TODO: finish!

    }

    @Override
    public  Collection<CasaDeBurrito> favoritesByDist(Profesor p)
            throws Profesor.ProfesorNotInSystemException{
        if(!profesors.contains(p))
            throw new Profesor.ProfesorNotInSystemException();
        // TODO: finish!

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
            pFriends.addAll(tempFriends);
        }
        for(Profesor prof : pFriends){
            if(prof.favorites().contains(c))
                return true;
        }
        return false;
    }

    @Override
    public List<Integer> getMostPopularRestaurantsIds(){

    }

    @Override
    public String toString(){

    }

}
