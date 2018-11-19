package OOP.Solution;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

/*
    REMEMBER: if you override "equals" you must also override hashcode!!!
 */


public class ProfesorImpl implements Profesor{
    private int id;
    private String name;
    private TreeSet<Profesor> Friends;
    private TreeSet<CasaDeBurrito> Favorites;


    public ProfesorImpl(int id_t, String name_t){
        id = id_t;
        name = name_t;
        Friends = new TreeSet<>();
        Favorites = new TreeSet<>();
    }

    /**
     * @return the id of the profesor.
     * */
    @Override public int getId(){
        return id;
    }

    /**
     * the profesor favorites a casa de burrito
     *
     * @return the object to allow concatenation of function calls.
     * @param c - the casa de burrito being favored by the profesor
     * */
    @Override public Profesor favorite(CasaDeBurrito c)
            throws UnratedFavoriteCasaDeBurritoException{
        if(Favorites.isEmpty()){
            throw new UnratedFavoriteCasaDeBurritoException();
        }
        Favorites.add(c);
        return this;
    }

    /**
     * @return the profesor's favorite casas de burrito
     * */
    @Override public Collection<CasaDeBurrito> favorites(){

    }

    /**
     * adding a profesor as a friend
     * @return the object to allow concatenation of function calls.
     * @param p - the profesor being "friend-ed"
     * */
    @Override public Profesor addFriend(Profesor p)
            throws SameProfesorException, ConnectionAlreadyExistsException{
        if(this.compareTo(p) == 0){
            throw new SameProfesorException();
        }
        if (Friends.contains(p)){
            throw new ConnectionAlreadyExistsException();
        }
        Friends.add(p);
        return this;

    }

    /**
     * @return the profesor's set of friends
     * */
    @Override public Set<Profesor> getFriends(){

    }

    /**
     * @return the profesor's set of friends, filtered by a predicate
     * @param p - the predicate for filtering
     * */
    @Override public Set<Profesor> filteredFriends(Predicate<Profesor> p){

    }

    /**
     * @return the profesor's favorite casas de burrito, ordered by a Comparator, and filtered by a predicate.
     * @param comp - a comparator for ordering
     * @param p - a predicate for filtering
     * */
    @Override public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p){

    }

    /**
     * @return the profesor's favorite casas de burrito, ordered by rating.
     * @param rLimit - the limit of rating under which casas de burrito will not be included.
     * */
    @Override public Collection<CasaDeBurrito> favoritesByRating(int rLimit){

    }

    /**
     * @return the profesor's favorite casas de burrito, ordered by distance and then rating.
     * @param dLimit - the limit of distance above which casas de burrito will not be included.
     * */
    @Override public Collection<CasaDeBurrito> favoritesByDist(int dLimit){

    }

    /**
     * @return the profesors's description as a string in the following format:
     * <format>
     * Profesor: <name>.
     * Id: <id>.
     * Favorites: <casaName1, casaName2, casaName3...>
     * </format>
     * Note: favorite casas de burrito are ordered by lexicographical order, asc.
     *
     * Example:
     *
     * Profesor: Oren.
     * Id: 236703.
     * Favorites: BBB, Burger salon.
     *
     * */

    @Override public String toString(){

    }


    @Override public int compareTo(Profesor profesor){

    }


}
