package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.stream.Collectors;

public class CasaDeBurritoImpl implements OOP.Provided.CasaDeBurrito {
    private int id;
    private String name;
    private int dist;
    private TreeSet<String> menu;
    private TreeMap<Integer, Integer> ratings;

    public CasaDeBurritoImpl(int id_t, String name_t, int dist_t, Set<String> menu_t) {
        id = id_t;
        name = name_t;
        dist = dist_t;
        menu = new TreeSet<String>();
        menu.addAll(menu_t);
        ratings = new TreeMap<Integer, Integer>();
    }

    /**
     * @return the id of the casa de burrito.
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * @return the name of the casa de burrito.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * @return the distance from the Technion.
     */
    @Override
    public int distance() {
        return this.dist;
    }

    /**
     * @param p - a profesor
     * @return true iff the profesor rated this CasaDeBurrito
     */
    @Override
    public boolean isRatedBy(Profesor p) {
        return this.ratings.containsKey(p.getId());
    }

    /**
     * rate the CasaDeBurrito by a profesor
     *
     * @param p - the profesor rating the CasaDeBurrito
     * @param r - the rating
     * @return the object to allow concatenation of function calls.
     */
    @Override
    public OOP.Provided.CasaDeBurrito rate(Profesor p, int r)
            throws OOP.Provided.CasaDeBurrito.RateRangeException {
        if (r < 0 || r > 5) {
            throw new RateRangeException();
        }
        this.ratings.put(p.getId(), r);
        return this;
    }

    /**
     * @return the number of rating the CasaDeBurrito has received
     */
    @Override
    public int numberOfRates() {
        return this.ratings.size();
    }

    /**
     * @return the CasaDeBurrito's average rating
     */
    @Override
    public double averageRating() {
        if (this.ratings.isEmpty()) {
            return 0;
        }
        List<Integer> rates = new ArrayList<Integer>(ratings.values());
        int sum = 0;
        for (int i : rates) {
            sum += i;
        }
        return (double) sum / (double) (this.numberOfRates());
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
     * <p>
     * Example:
     * <p>
     * CasaDeBurrito: BBB.
     * Id: 1.
     * Distance: 5.
     * Menu: Cola, French Fries, Steak.
     */
    @Override
    public String toString() {
        LinkedList<String> menuList = menu.stream().collect(Collectors.toCollection(LinkedList::new));
        return ("CasaDeBurrito: " + name + ".\nId: " + id + ".\nDistance: " + dist + ".\nMenu: "+
        menuList.stream()
                .sorted((s1, s2) -> s1.compareTo(s2))
                .collect(Collectors.toList())
                .toString()
                .replace("[", "")
                .replace("]", "."));
    }

    @Override
    public int compareTo(CasaDeBurrito casaDeBurrito) {
        return this.getId() - casaDeBurrito.getId();
    }

}