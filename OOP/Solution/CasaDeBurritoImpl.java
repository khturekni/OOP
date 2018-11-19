package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import java.util.Set;

/*
    REMEMBER: if you override "equals" you must also override hashcode!!!
 */


public class CasaDeBurritoImpl implements OOP.Provided.CasaDeBurrito {
    public CasaDeBurritoImpl(int id, String name, int dist, Set<String> menu){

    }

    /**
     * @return the id of the casa de burrito.
     * */
    @Override public int getId(){

    }

    /**
     * @return the name of the casa de burrito.
     * */
    @Override public String getName(){

    }

    /**
     * @return the distance from the Technion.*/
    @Override public int distance(){

    }

    /**
     * @return true iff the profesor rated this CasaDeBurrito
     * @param p - a profesor
     * */
    @Override public  boolean isRatedBy(Profesor p){

    }

    /**
     * rate the CasaDeBurrito by a profesor
     * @return the object to allow concatenation of function calls.
     * @param p - the profesor rating the CasaDeBurrito
     * @param r - the rating
     * */
    @Override public OOP.Provided.CasaDeBurrito rate(Profesor p, int r)
            throws OOP.Provided.CasaDeBurrito.RateRangeException{

    }

    /**
     * @return the number of rating the CasaDeBurrito has received
     * */
    @Override public int numberOfRates(){

    }

    /**
     * @return the CasaDeBurrito's average rating
     * */
    @Override public double averageRating(){

    }

    /**
     * @return the CasaDeBurrito's description as a string in the following format:
     * <format>
     * CasaDeBurrito: <name>.
     * Id: <id>.
     * Distance: <dist>.
     * Menu: <menuItem1, menuItem2, menuItem3...>.
     * </format>
     * Note: Menu items are ordered by lexicographical order, asc.
     *
     * Example:
     *
     * CasaDeBurrito: BBB.
     * Id: 1.
     * Distance: 5.
     * Menu: Cola, French Fries, Steak.
     *
     * */
    @Override public String toString(){

    }

    @Override public int compareTo(CasaDeBurrito casaDeBurrito){

    }

}
