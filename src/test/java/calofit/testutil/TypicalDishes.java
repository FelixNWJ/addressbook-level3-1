package calofit.testutil;

import static calofit.logic.commands.CommandTestUtil.VALID_NAME_DUCK_RICE;
import static calofit.logic.commands.CommandTestUtil.VALID_NAME_MACARONI;
import static calofit.logic.commands.CommandTestUtil.VALID_TAG_EXPENSIVE;
import static calofit.logic.commands.CommandTestUtil.VALID_TAG_SALTY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import calofit.model.dish.Dish;
import calofit.model.dish.DishDatabase;

/**
 * A utility class containing a list of {@code Dish} objects to be used in tests.
 */
public class TypicalDishes {

    public static final Dish SPAGHETTI = new DishBuilder().withName("Spaghetti")
            .withTags("creamys").build();
    public static final Dish MUSHROOM_SOUP = new DishBuilder().withName("Mushroom Soup")
            .withTags("expensive", "shitake").build();
    public static final Dish CHICKEN_RICE = new DishBuilder().withName("Chicken Rice").build();
    public static final Dish NASI_LEMAK = new DishBuilder().withName("Nasi Lemak").withTags("value").build();
    public static final Dish CHEESE_BAKED_RICE = new DishBuilder().withName("Cheese Baked Rice").build();
    public static final Dish APPLE_PIE = new DishBuilder().withName("Apple Pie").build();
    public static final Dish CHENDOL = new DishBuilder().withName("Chendol").build();

    // Manually added
    public static final Dish CEREAL = new DishBuilder().withName("Cereal").build();
    public static final Dish STEAK = new DishBuilder().withName("Steak").build();

    // Manually added - Dish's details found in {@code CommandTestUtil}
    public static final Dish DUCK_RICE = new DishBuilder().withName(VALID_NAME_DUCK_RICE)
            .withTags(VALID_TAG_EXPENSIVE).build();
    public static final Dish MACARONI = new DishBuilder().withName(VALID_NAME_MACARONI)
            .withTags(VALID_TAG_SALTY, VALID_TAG_EXPENSIVE)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalDishes() {
    } // prevents instantiation

    /**
     * Returns an {@code DishDatabase} with all the typical persons.
     */
    public static DishDatabase getTypicalDishDatabase() {
        DishDatabase ab = new DishDatabase();
        for (Dish dish : getTypicalDishes()) {
            ab.addDish(dish);
        }
        return ab;
    }

    public static List<Dish> getTypicalDishes() {
        return new ArrayList<>(Arrays.asList(
                SPAGHETTI, MUSHROOM_SOUP, CHICKEN_RICE, NASI_LEMAK, CHEESE_BAKED_RICE, APPLE_PIE, CHENDOL));
    }
}
