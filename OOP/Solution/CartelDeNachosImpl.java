package OOP.Solution;

import OOP.Provided.CartelDeNachos;
import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CartelDeNachosImpl implements CartelDeNachos{

    public CartelDeNachosImpl(){

    }

    /**
     * add a profesor to the cartel.
     *
     * @param id - the id of the profesor
     * @param name - the name of the profesor
     * @return the Profesor added
     * */
    @Override public Profesor joinCartel(int id, String name)
            throws Profesor.ProfesorAlreadyInSystemException{

    }

    /**
     * add a casa de burrito to the cartel
     * @param id - the id of the casa de burrito
     * @param name - the name of the casa de burrito
     * @param dist - the distance of the casa de burrito from the Technion
     * @param menu - the set of menu items of the casa de burrito
     * @return the CasaDeBurrito added
     * */
    @Override public CasaDeBurrito addCasaDeBurrito(int id, String name, int dist, Set<String> menu)
            throws CasaDeBurrito.CasaDeBurritoAlreadyInSystemException{

    }

    /**
     * @return a collection of all profesores in the cartel
     * */
    @Override public  Collection<Profesor> registeredProfesores(){

    }

    /**
     * @return a collection of all casas de burrito in the cartel
     * */
    @Override public Collection<CasaDeBurrito> registeredCasasDeBurrito(){

    }

    /**
     * @return the profesor in the cartel by the id given
     * @param id - the id of the profesor to look for in the cartel
     * */
    @Override public Profesor getProfesor(int id)
            throws Profesor.ProfesorNotInSystemException{

    }

    /**
     * @return the casa de burrito in the cartel by the id given
     * @param id - the id of the casa de burrito to look for in the cartel
     * */
    @Override public CasaDeBurrito getCasaDeBurrito(int id)
            throws CasaDeBurrito.CasaDeBurritoNotInSystemException{

    }

    /**
     * add a connection of friendship between the two profesores received.
     * friendship is a symmetric relation!
     *
     * @return the object to allow concatenation of function calls.
     * @param p1 - the first profesor
     * @param p2 - the second profesor
     * */
    @Override public CartelDeNachos addConnection(Profesor p1, Profesor p2)
            throws Profesor.ProfesorNotInSystemException, Profesor.ConnectionAlreadyExistsException, Profesor.SameProfesorException{

    }

    /**
     * returns a collection of casas de burrito favored by the friends of the received profesor,
     * ordered by rating
     *
     * @param p - the profesor whom in relation to him, favored casas de burrito by his friends are considered
     * */
    @Override public Collection<CasaDeBurrito> favoritesByRating(Profesor p)
            throws Profesor.ProfesorNotInSystemException{

    }

    /**
     * returns a collection of casas de burrito favored by the friends of the received profesor,
     * ordered by distance from the Technion
     *
     * @param p - the profesor whom in relation to him, favored casas de burrito by his friends are considered
     * */
    @Override public  Collection<CasaDeBurrito> favoritesByDist(Profesor p)
            throws Profesor.ProfesorNotInSystemException{

    }

    /**
     * check whether the casa de burrito received is t-recommended by the received profesor (definition in the PDF)
     *
     * @param p - the profesor (dis)recommending the casa de burrito
     * @param c - the casa de burrito being (dis)recommended
     * @param t - the limit in the t-recommendation
     *
     * @return whether s t-recommends r
     * */
    @Override public  boolean getRecommendation(Profesor p, CasaDeBurrito c, int t)
            throws Profesor.ProfesorNotInSystemException, CasaDeBurrito.CasaDeBurritoNotInSystemException, CartelDeNachos.ImpossibleConnectionException{

    }

    /**
     * @return a list of the most popular casas-de-burrito's ids in the cartel.
     * */
    @Override public List<Integer> getMostPopularRestaurantsIds(){

    }

    /**
     * @return the cartel's description as a string in the following format:
     * <format>
     * Registered profesores: <profesorId1, profesorId2, profesorId3...>.
     * Registered casas de burrito: <casaId1, casaId2, casaId3...>.
     * Profesores:
     * <profesor1Id> -> [<friend1Id, friend2Id, friend3Id...>].
     * <profesor2Id> -> [<friend1Id, friend2Id, friend3Id...>].
     * ...
     * End profesores.
     * </format>
     * Note: profesores, casas de burrito and friends' ids are ordered by natural integer order, asc.*
     * Example:
     *
     * Registered profesores: 1, 236703, 555555.
     * Registered casas de burrito: 12, 13.
     * Profesores:
     * 1 -> [236703, 555555555].
     * 236703 -> [1].
     * 555555 -> [1].
     * End profesores.
     * */
    @Override public String toString(){

    }

}
