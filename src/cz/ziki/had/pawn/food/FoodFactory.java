package cz.ziki.had.pawn.food;

import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

/**
 * Factory class for creating different types of food objects in the game.
 * The factory provides a method to instantiate various food types based on an enum value.
 */
public class FoodFactory {

    /**
     * Enum representing the different types of food that can be created.
     */
    public enum FoodType{
        MOUSE,
        APPLE,
        LIZARD
    }

    /**
     * Creates a food object of the specified type.
     *
     * @param foodType   the type of food to create, specified as a {@link FoodType} enum
     * @param background the game field where the food will appear
     * @param snake      the snake object that interacts with the food
     * @param x          the x-coordinate of the food's initial position
     * @param y          the y-coordinate of the food's initial position
     * @return a new instance of a class implementing the {@link Food} interface, based on the specified food type
     */
    public Food getFood(FoodType foodType, Rect background, Snake snake, int x , int y){
        return switch (foodType) {
            case MOUSE -> new Mouse( background,  snake,  x,  y);
            case APPLE -> new Apple( background,  snake,  x,  y);
            case LIZARD -> new Lizard( background,  snake,  x,  y);
        };
    }
}