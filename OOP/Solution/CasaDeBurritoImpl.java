package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.stream.Collectors;

public class CasaDeBurritoImpl implements OOP.Provided.CasaDeBurrito {
    private Integer id;
    private String name;
    private Integer dist;
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

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int distance() {
        return this.dist;
    }

    @Override
    public boolean isRatedBy(Profesor p) {
        return this.ratings.containsKey(p.getId());
    }

    @Override
    public OOP.Provided.CasaDeBurrito rate(Profesor p, int r)
            throws OOP.Provided.CasaDeBurrito.RateRangeException {
        if (r < 0 || r > 5) {
            throw new RateRangeException();
        }
        this.ratings.put(p.getId(), r);
        return this;
    }

    @Override
    public int numberOfRates() {
        return this.ratings.size();
    }

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

    protected boolean eq(Object o) {
        if (!(o instanceof CasaDeBurritoImpl))
            return false;
        return ((CasaDeBurritoImpl)o).getId() == this.getId();
    }

    @Override
    public boolean equals(Object o) {
        return (this.eq(o) && ((CasaDeBurritoImpl)o).eq(this));
    }

    @Override
    public String toString() {
        LinkedList<String> menuList = menu.stream().collect(Collectors.toCollection(LinkedList::new));
        return ("CasaDeBurrito: " + name + ".\nId: " + id + ".\nDistance: " + dist + ".\nMenu: " +
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