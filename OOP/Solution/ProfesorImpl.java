package OOP.Solution;

import OOP.Provided.CasaDeBurrito;
import OOP.Provided.Profesor;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProfesorImpl implements Profesor {
    private int id;
    private String name;
    private LinkedList<Profesor> friends;
    private LinkedList<CasaDeBurrito> favorites;


    public ProfesorImpl(int id_t, String name_t) {
        id = id_t;
        name = name_t;
        friends = new LinkedList<>();
        favorites = new LinkedList<>();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Profesor favorite(CasaDeBurrito c)
            throws UnratedFavoriteCasaDeBurritoException {
        if (!c.isRatedBy(this)) {
            throw new UnratedFavoriteCasaDeBurritoException();
        }
        favorites.add(c);
        return this;
    }

    @Override
    public Collection<CasaDeBurrito> favorites() {
        return (new HashSet<CasaDeBurrito>(favorites));
    }

    @Override
    public Profesor addFriend(Profesor p)
            throws SameProfesorException, ConnectionAlreadyExistsException {
        if (this.compareTo(p) == 0) {
            throw new SameProfesorException();
        }
        if (friends.contains(p)) {
            throw new ConnectionAlreadyExistsException();
        }
        friends.add(p);
        return this;
    }

    @Override
    public Set<Profesor> getFriends() {
        return (new HashSet<Profesor>(friends));
    }

    @Override
    public Set<Profesor> filteredFriends(Predicate<Profesor> p) {
        return friends.stream()
                .filter(p)
                .collect(Collectors.toSet());
    }

    @Override
    public Collection<CasaDeBurrito> filterAndSortFavorites(Comparator<CasaDeBurrito> comp, Predicate<CasaDeBurrito> p) {
        return favorites.stream()
                .filter(p)
                .sorted(comp)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByRating(int rLimit) {
        return favoritesByPred(cdb -> cdb.averageRating() >= rLimit, true);
    }

    @Override
    public Collection<CasaDeBurrito> favoritesByDist(int dLimit) {
        return favoritesByPred(cdb -> cdb.distance() <= dLimit, false);
    }

    private Collection<CasaDeBurrito> favoritesByPred(Predicate<CasaDeBurrito> p, boolean by_rate) {
        return favorites.stream()
                .filter(p)
                .sorted(compareByRateOrDist(by_rate))
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return ("Profesor: " + name + ".\nId: " + id + ".\nFavorites: " +
                favorites.stream()
                        .map(c -> c.getName())
                        .collect(Collectors.toList())
                        .toString()
                        .replace("[", "")
                        .replace("]", "."));
    }

    @Override
    public int compareTo(Profesor profesor) {
        return (id - profesor.getId());
    }

    protected boolean eq(Object o) {
        if (!(o instanceof Profesor))
            return false;
        return (compareTo((Profesor) o) == 0);
    }

    @Override
    public boolean equals(Object o) {
        return (eq(o) && ((ProfesorImpl) o).eq(this));
    }

    @Override
    public int hashCode() {
        // We decided to use the following hash function:
        return (int) ((((1 + Math.sqrt(5)) / 2) * id) % 503);
    }

    public class compareByRating implements Comparator<CasaDeBurrito> {
        @Override
        public int compare(CasaDeBurrito cdb1, CasaDeBurrito cdb2) {
            return (int) (cdb1.averageRating() - cdb2.averageRating());
        }
    }

    public class compareByDistance implements Comparator<CasaDeBurrito> {
        @Override
        public int compare(CasaDeBurrito cdb1, CasaDeBurrito cdb2) {
            return (cdb2.distance() - cdb1.distance());
        }
    }

    public class compareByID implements Comparator<CasaDeBurrito> {
        @Override
        public int compare(CasaDeBurrito cdb1, CasaDeBurrito cdb2) {
            return (cdb2.getId() - cdb1.getId());
        }

    }

    private Comparator<CasaDeBurrito> compareByRateOrDist(boolean by_rate) {
        if (by_rate)
            return new compareByRating()
                    .thenComparing(new compareByDistance()
                            .thenComparing(new compareByID()));
        return new compareByDistance()
                .thenComparing(new compareByRating()
                        .thenComparing(new compareByID()));
    }
}
