package OOP.Tests;

import OOP.Provided.Profesor;
import OOP.Provided.CasaDeBurrito;
import OOP.Solution.ProfesorImpl;
import OOP.Solution.CasaDeBurritoImpl;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ProfesorTest {
    // when changing those - update ToStringTest accordingly
    private static final Set<String> traditional_pizzas = new HashSet<>(Arrays.asList(
            "Margarita", "Hawaiian", "Greek", "Napoli", "Sicilian", "Marinara"
    ));
    private static final Set<String> meat_topping_pizzas = new HashSet<>(Arrays.asList(
            "Chicken", "Bacon", "Ground Beef", "Pepperoni", "Anchovies"
    ));
    private static final Set<String> veggie_topping_pizzas = new HashSet<>(Arrays.asList(
            "Mushrooms", "Tomatoes", "Onion", "Olives"
    ));
    private static final Set<String> complements = new HashSet<>(Arrays.asList(
            "Calzone", "Fries", "Onion rings", "Coca-cola", "Cola zero"
    ));

    @Test
    public void ConstructorAndGettersTest(){
        Profesor leonardo = new ProfesorImpl(12345, "Leonardo");
        assertEquals(leonardo.getId(), 12345);

        Profesor donatello = new ProfesorImpl(23456, "Donatello");
        assertEquals(donatello.getId(), 23456);
    }

    @Test
    public void FavoriteAndFavouritesTest() {
        // rated and favorited:
        CasaDeBurrito p1 = new CasaDeBurritoImpl(10, "Italiano", 125, traditional_pizzas);
        CasaDeBurrito p2 = new CasaDeBurritoImpl(20, "Hut", 15, veggie_topping_pizzas);
        CasaDeBurrito p3 = new CasaDeBurritoImpl(30, "Dominos", 650, meat_topping_pizzas);
        // rated, not favourited:
        CasaDeBurrito p4 = new CasaDeBurritoImpl(40, "BadPizza", 350, complements);

        Profesor lover = new ProfesorImpl(12345, "Leonardo");

        try {
            lover.favorite(p1);
            fail("favorite() should throw, when CasaDeBurrito wasn't rated before");
        } catch (Exception e) {
            assertTrue(e instanceof Profesor.UnratedFavoriteCasaDeBurritoException);
        }

        try {
            p1.rate(lover, 3);
            p2.rate(lover, 4);
            p3.rate(lover, 5);

            p4.rate(lover, 1);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }

        // now we've rated all CasaDeBurritos, let's test good path:
        try {
            lover.favorite(p1).favorite(p2).favorite(p3);
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException e){
            fail("CasaDeBurrito was rated, but Unrated exception still thrown");
        }

        Collection actualFavs = lover.favorites();
        Collection expectedFavs = new HashSet<>(Arrays.asList(p1,p2,p3));
        // check equality by 2-way inclusion:
        assertTrue(actualFavs.containsAll(expectedFavs) && expectedFavs.containsAll(actualFavs));
    }

    @Test
    public void AddAndGetFriendsTest() {
        Profesor Leonardo     = new ProfesorImpl(12345, "Leonardo");
        Profesor Donatello    = new ProfesorImpl(23456, "Donatello");
        Profesor Michelangelo = new ProfesorImpl(56789, "Michelangelo");
        Profesor Rafael       = new ProfesorImpl(45678, "Rafael");
        // obviously, the following two aren't friends of TNMT:
        Profesor Shredder = new ProfesorImpl(666555, "Shredder");
        Profesor Krang = new ProfesorImpl(333444, "Krang");

        // initially no one has any friend:
        assertTrue(Leonardo.getFriends().isEmpty());
        assertTrue(Shredder.getFriends().isEmpty());

        try {
            Shredder.addFriend(Shredder);
            fail("You can't be a friend of yourself");
        } catch (Exception e) {
            assertTrue(e instanceof Profesor.SameProfesorException);
        }

        try {
            // Make TNMT a clique:
            Leonardo.addFriend(Donatello).addFriend(Michelangelo).addFriend(Rafael);
            Donatello.addFriend(Leonardo).addFriend(Michelangelo).addFriend(Rafael);
            Michelangelo.addFriend(Donatello).addFriend(Leonardo).addFriend(Rafael);
            Rafael.addFriend(Donatello).addFriend(Michelangelo).addFriend(Leonardo);
            // Krang is a friend of Shredder, but not the other way around:
            Shredder.addFriend(Krang);
        }catch (Profesor.SameProfesorException e){
            fail("You have probably messed something up in a test");
        } catch (Profesor.ConnectionAlreadyExistsException e){
            fail();
        }

        assertEquals(Shredder.getFriends().size(), 1);
        assertTrue(Shredder.getFriends().contains(Krang));
        assertTrue(Krang.getFriends().isEmpty());

        assertEquals(Leonardo.getFriends().size(), 3);
        assertTrue(Michelangelo.getFriends().containsAll(Arrays.asList(Donatello, Leonardo, Rafael)));
        assertEquals(Michelangelo.getFriends().size() , 3);
        assertTrue(Michelangelo.getFriends().containsAll(Arrays.asList(Donatello, Leonardo, Rafael)));

        try {
            Leonardo.addFriend(Michelangelo);
            fail("You can't add the same friend twice");
        } catch (Exception e) {
            assertTrue(e instanceof Profesor.ConnectionAlreadyExistsException);
        }
    }

    @Test
    public void FavouritesByRating_GeneralCaseTest(){
        CasaDeBurrito p1 = new CasaDeBurritoImpl(10, "Italiano", 125, traditional_pizzas);
        CasaDeBurrito p2 = new CasaDeBurritoImpl(20, "Hut", 15, veggie_topping_pizzas);
        CasaDeBurrito p3 = new CasaDeBurritoImpl(30, "Dominos", 650, meat_topping_pizzas);
        CasaDeBurrito p4 = new CasaDeBurritoImpl(40, "BadPizza", 350, complements);

        Profesor Leonardo     = new ProfesorImpl(12345, "Leonardo");
        Profesor Donatello    = new ProfesorImpl(23456, "Donatello");
        Profesor Michelangelo = new ProfesorImpl(56789, "Michelangelo");
        Profesor Rafael       = new ProfesorImpl(45678, "Rafael");

        // Let's make average rating of p1 to be 1, p2 - 2, and so on.
        try {
            p1.rate(Leonardo, 1).rate(Donatello, 1).rate(Michelangelo, 1);
            p2.rate(Leonardo, 1).rate(Donatello, 3);
            p3.rate(Leonardo, 1).rate(Donatello, 2).rate(Michelangelo, 4).rate(Rafael, 5);
            p4.rate(Leonardo, 3).rate(Donatello, 4).rate(Michelangelo, 5);
        } catch (CasaDeBurrito.RateRangeException e){
            fail("You have probably messed something up in a test");
        }


        Profesor Student = new ProfesorImpl(555, "Student");
        try {
            p1.rate(Student, 1);
            p2.rate(Student, 2);
            p3.rate(Student, 3);
            p4.rate(Student, 4);
            Student.favorite(p1).favorite(p2).favorite(p3).favorite(p4);
        } catch (Exception e){
            fail();
        }

        assertEquals(Student.favoritesByRating(2), (Arrays.asList(p4, p3, p2)));
        assertEquals(Student.favoritesByRating(3), (Arrays.asList(p4, p3)));
        assertEquals(Student.favoritesByRating(4), (Arrays.asList(p4)));
        assertTrue(Student.favoritesByRating(5).isEmpty());
    }

    @Test
    public void FavouritesByRating_SecondaryOrderTest(){
        // PP are defined in the expected order:
        CasaDeBurrito p1 = new CasaDeBurritoImpl(20, "Hut", 15, veggie_topping_pizzas);
        CasaDeBurrito p2 = new CasaDeBurritoImpl(10, "Italiano", 125, traditional_pizzas);
        CasaDeBurrito p3 = new CasaDeBurritoImpl(30, "Dominos", 125, meat_topping_pizzas);
        CasaDeBurrito p4 = new CasaDeBurritoImpl(40, "BadPizza", 350, complements);

        Profesor Student = new ProfesorImpl(555, "Student");
        // Now every pizzeria will have the same rating.
        try {
            p3.rate(Student, 1);
            p4.rate(Student, 1);
            p1.rate(Student, 1);
            p2.rate(Student, 1);
            Student.favorite(p2).favorite(p1).favorite(p4).favorite(p3);
        } catch (Exception e){
            fail();
        }

        assertEquals(Student.favoritesByRating(1), (Arrays.asList(p1, p2, p3, p4)));
    }

    @Test
    public void FavouritesByDist_GeneralCaseTest(){
        CasaDeBurrito p1 = new CasaDeBurritoImpl(10, "Italiano", 125, traditional_pizzas);
        CasaDeBurrito p2 = new CasaDeBurritoImpl(20, "Hut", 15, veggie_topping_pizzas);
        CasaDeBurrito p3 = new CasaDeBurritoImpl(30, "Dominos", 650, meat_topping_pizzas);
        CasaDeBurrito p4 = new CasaDeBurritoImpl(40, "BadPizza", 350, complements);

        Profesor Leonardo     = new ProfesorImpl(12345, "Leonardo");
        Profesor Donatello    = new ProfesorImpl(23456, "Donatello");
        Profesor Michelangelo = new ProfesorImpl(56789, "Michelangelo");
        Profesor Rafael       = new ProfesorImpl(45678, "Rafael");

        // Let's make average rating of p1 to be 1, p2 - 2, and so on.
        try {
            p1.rate(Leonardo, 1).rate(Donatello, 1).rate(Michelangelo, 1);
            p2.rate(Leonardo, 1).rate(Donatello, 3);
            p3.rate(Leonardo, 1).rate(Donatello, 2).rate(Michelangelo, 4).rate(Rafael, 5);
            p4.rate(Leonardo, 3).rate(Donatello, 4).rate(Michelangelo, 5);
        } catch (CasaDeBurrito.RateRangeException e){
            fail("You have probably messed something up in a test");
        }

        Profesor Student = new ProfesorImpl(555, "Student");
        try {
            p1.rate(Student, 1);
            p2.rate(Student, 2);
            p3.rate(Student, 3);
            p4.rate(Student, 4);
            Student.favorite(p1).favorite(p2).favorite(p3).favorite(p4);
        } catch (Exception e){
            fail();
        }

        assertTrue(Student.favoritesByDist(650).equals(Arrays.asList(p2, p1, p4, p3)));
        assertTrue(Student.favoritesByDist(350).equals(Arrays.asList(p2, p1, p4)));
        assertTrue(Student.favoritesByDist(125).equals(Arrays.asList(p2, p1)));
        assertTrue(Student.favoritesByDist(15).equals(Arrays.asList(p2)));
        assertTrue(Student.favoritesByDist(2).isEmpty());
    }

    @Test
    public void FavouritesByDist_SecondaryOrderTest(){
        // PP are defined in expected order.
        // Note, that now all distances are the same.
        CasaDeBurrito p4 = new CasaDeBurritoImpl(40, "BadPizza", 100, complements);
        CasaDeBurrito p2_low_id = new CasaDeBurritoImpl(20, "Hut", 100, veggie_topping_pizzas);
        CasaDeBurrito p2_high_id = new CasaDeBurritoImpl(30, "Dominos", 100, meat_topping_pizzas);
        CasaDeBurrito p1 = new CasaDeBurritoImpl(10, "Italiano", 100, traditional_pizzas);

        Profesor Student = new ProfesorImpl(555, "Student");

        // Let's make average rating of p1 to be 1, p2 - 2, and so on.
        try {
            p1.rate(Student, 1);
            p2_low_id.rate(Student, 2);
            p2_high_id.rate(Student, 2);
            p4.rate(Student, 4);
            Student.favorite(p2_low_id).favorite(p1).favorite(p4).favorite(p2_high_id);
        } catch (CasaDeBurrito.RateRangeException | Profesor.UnratedFavoriteCasaDeBurritoException e){
            fail("You have probably messed something up in a test");
        }

        assertTrue(Student.favoritesByDist(100).equals(Arrays.asList(p4, p2_low_id, p2_high_id, p1)));
    }

    @Test
    public void EqualsTest() {
        Profesor Leonardo = new ProfesorImpl(12345, "Leonardo");
        Profesor LeonardoSameEverything = new ProfesorImpl(12345, "Leonardo");
        Profesor LeonardoSameIDOnly = new ProfesorImpl(12345, "Not Leonardo");
        Profesor Rafael = new ProfesorImpl(45678, "Rafael");
        Profesor noLover = null;

        CasaDeBurrito CasaDeBurrito = new CasaDeBurritoImpl(10, "Italiano", 100, traditional_pizzas);

        // general case:
        assertTrue(Leonardo.equals(LeonardoSameEverything));
        assertTrue(LeonardoSameEverything.equals(Leonardo));

        assertFalse(Leonardo.equals(Rafael));
        assertFalse(Rafael.equals(Leonardo));

        // nothing equals null:
        assertFalse(Leonardo.equals(noLover));
        assertFalse(Rafael.equals(noLover));

        // unrelated classes comparison:
        assertFalse(Leonardo.equals(0.0));
        assertFalse(Leonardo.equals(CasaDeBurrito));

        // compare id's only:
        assertTrue(Leonardo.equals(LeonardoSameIDOnly));

        // reflexivity:
        assertTrue(Leonardo.equals(Leonardo));
        assertTrue(Rafael.equals(Rafael));

        // commutativity:
        assertTrue(Leonardo.equals(LeonardoSameIDOnly));
        assertTrue(LeonardoSameIDOnly.equals(Leonardo));
    }


    @Test
    public void ToStringTest() {
        CasaDeBurrito p1 = new CasaDeBurritoImpl(10, "Italiano", 125, traditional_pizzas);
        CasaDeBurrito p2 = new CasaDeBurritoImpl(20, "Hut", 15, veggie_topping_pizzas);
        CasaDeBurrito p3 = new CasaDeBurritoImpl(30, "Dominos", 650, meat_topping_pizzas);

        Profesor lover = new ProfesorImpl(12345, "Leonardo");

        try {
            p1.rate(lover, 3);
            p2.rate(lover, 4);
            p3.rate(lover, 5);
        } catch (CasaDeBurrito.RateRangeException e) {
            fail();
        }

        try {
            lover.favorite(p1);
            lover.favorite(p2);
            lover.favorite(p3);
        } catch (Profesor.UnratedFavoriteCasaDeBurritoException e){
            fail();
        }

        String expectedStr = "Profesor: Leonardo.\n" +
                "Id: 12345.\n" +
                "Favorites: Dominos, Hut, Italiano.";
        assertEquals(lover.toString(), expectedStr);

        Profesor Donatello = new ProfesorImpl(23456, "Donatello");
        expectedStr = "Profesor: Donatello.\n" +
                "Id: 23456.\n" +
                "Favorites: .";
        assertEquals(Donatello.toString(), expectedStr);
    }

}
