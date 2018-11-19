package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;
import java.util.Set;

public class CasaDeBurritoImpl implements OOP.Provided.CasaDeBurrito {

    class CasaDeBurritoAlreadyInSystemException    extends Exception {}
    class CasaDeBurritoNotInSystemException        extends Exception {}
    class RateRangeException                       extends Exception {}

    public CasaDeBurritoImpl(int id, String name, int dist, Set<String> menu){

    }

    /**
     * @return the id of the casa de burrito.
     * */
    @override public int getId();

    /**
     * @return the name of the casa de burrito.
     * */
    @override public String getName();

    /**
     * @return the distance from the Technion.*/
    @override public int distance();

    /**
     * @return true iff the profesor rated this CasaDeBurrito
     * @param p - a profesor
     * */
    @override public  boolean isRatedBy(Profesor p);

    /**
     * rate the CasaDeBurrito by a profesor
     * @return the object to allow concatenation of function calls.
     * @param p - the profesor rating the CasaDeBurrito
     * @param r - the rating
     * */
    @override public OOP.Provided.CasaDeBurrito rate(Profesor p, int r)
            throws OOP.Provided.CasaDeBurrito.RateRangeException;

    /**
     * @return the number of rating the CasaDeBurrito has received
     * */
    @override public int numberOfRates();

    /**
     * @return the CasaDeBurrito's average rating
     * */
    @override public double averageRating();

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
    @override public String toString();

    @override public int compareTo(CasaDeBurrito casaDeBurrito);

}
