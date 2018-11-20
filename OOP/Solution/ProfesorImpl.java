package OOP.Solution;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
    REMEMBER: if you override "equals" you must also override hashcode!!!
 */


public class ProfesorImpl implements Profesor{
    private int id;
    private String name;
    private LinkedList<Profesor> friends;
    private LinkedList<CasaDeBurrito> favorites;


    public ProfesorImpl(int id_t, String name_t){
        id = id_t;
        name = name_t;
        friends = new LinkedList<>();
        favorites = new LinkedList<>();
    }

    /**
     * @return the id of the profesor.
     * */
    @Override
    public int getId(){
        return id;
    }

    /**
     * the profesor favorites a casa de burrito
     *
     * @return the object to allow concatenation of function calls.
     * @param c - the casa de burrito being favored by the profesor
     * */
    @Override
    public Profesor favorite(CasaDeBurrito c)
            throws UnratedFavoriteCasaDeBurritoException{
        if(!c.isRatedBy(this)){
            throw new UnratedFavoriteCasaDeBurritoException();
        }
        favorites.add(c);
        return this;
    }

    /**
     * @return the profesor's favorite casas de burrito
     * */
    @Override
    public Collection<CasaDeBurrito> favorites(){
        return (new HashSet<CasaDeBurrito>(favorites));
    }

    /**
     * adding a profesor as a friend
     * @return the object to allow concatenation of function calls.
     * @param p - the profesor being "friend-ed"
     * */
    @Override
    public Profesor addFriend(Profesor p)
            throws SameProfesorException, ConnectionAlreadyExistsException{
        if(this.compareTo(p) == 0){
            throw new SameProfesorException();
        }
        if (friends.contains(p)){
            throw new ConnectionAlreadyExistsException();
        }
        friends.add(p);
        return this;

    }

    /**
     * @return the profesor's set of friends
     * */
    @Override
    public Set<Profesor> getFriends(){
        return (new HashSet<Profesor>(friends));
    }

    /**
     * @return the profesor's set of friends, filtered by a predicate
     * @param p - the predicate for filtering
     * */
    @Override
    public Set<Profesor> filteredFriends(Predicate<Profesor> p){
        return friends.stream()
                .filter(p)
                .collect(Collectors.toSet());
    }

    /**
     * @return the profesor's favorite casas de burrito, ordered by a Comparator, and filtered by a predicate.
     * @param comp - a comparator for ordering
     * @param p - a predicate for filtering
     * */
    @Override
    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p){
        return favorites.stream()
                .sorted(comp)
                .filter(p)
                .collect(Collectors.toList());
    }

    /**
     * @return the profesor's favorite casas de burrito, ordered by rating.
     * @param rLimit - the limit of rating under which casas de burrito will not be included.
     * */
    @Override
    public Collection<CasaDeBurrito> favoritesByRating(int rLimit){

    }

    /**
     * @return the profesor's favorite casas de burrito, ordered by distance and then rating.
     * @param dLimit - the limit of distance above which casas de burrito will not be included.
     * */
    @Override
    public Collection<CasaDeBurrito> favoritesByDist(int dLimit){

    }

    /**
     * @return the profesors's description as a string in the following format:
     * <format>
     * Profesor: <name>.
     * Id: <id>.
     * favorites: <casaName1, casaName2, casaName3...>
     * </format>
     * Note: favorite casas de burrito are ordered by lexicographical order, asc.
     *
     * Example:
     *
     * Profesor: Oren.
     * Id: 236703.
     * favorites: BBB, Burger salon.
     *
     * */

    @Override
    public String toString(){

    }


    @Override
    public int compareTo(Profesor profesor){
        return (id - profesor.getId());
    }

    protected boolean eq(Object o){
        if(!(o instanceof Profesor))
            return false;
        return (compareTo((Profesor) o) == 0);
    }

    @Override
    public boolean equals(Object o){
        return (eq(o) && ((Profesor)o).eq(this));
    }

    @Override
    public int hashCode(){
        return (int)((((1+Math.sqrt(5))/2)*id) % 503);
    }

    public class compareByRating implements Comparator<CasaDeBurrito>{
        @Override
        public int compare(CasaDeBurrito cdb1, CasaDeBurrito cdb2){
            return (int)(cdb1.averageRating() - cdb2.averageRating());

        }
    }

    public class compareByDistance implements Comparator<CasaDeBurrito> {
        @Override
        public int compare(CasaDeBurrito cdb1, CasaDeBurrito cdb2) {
            return (cdb2.distance() - cdb1.distance());
        }
    }

    public class compareByRateOrDist implements Comparator<CasaDeBurrito> {
        boolean by_rate;
        compareByRating cmp_rate;
        compareByDistance cmp_dist;
        /*  by_rate = true ==> compares first by rate, then by distance, then by ID.
            by_rate = false ==> compares first by diostance, then by rate, then by ID
        */
        public compareByRateOrDist(boolean by_rate) {
            this.by_rate = by_rate;
            cmp_dist = new compareByDistance();
            cmp_rate = new compareByRating();
        }

        @Override
        public int compare(CasaDeBurrito cdb1, CasaDeBurrito cdb2) {
            int res_by_rate = (cmp_rate.compare(cdb1, cdb2));
            if (by_rate) {
                if (res_by_rate != 0)
                    return res_by_rate;
            }
            if (cmp_dist.compare(cdb1, cdb2) != 0)
                return cmp_dist.compare(cdb1, cdb2);
            if (!by_rate)  {
                if (res_by_rate != 0)
                    return res_by_rate;
            }
            return cdb1.compareTo(cdb2);
        }

    }

}
