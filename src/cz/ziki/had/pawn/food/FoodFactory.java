package cz.ziki.had.pawn.food;

import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

/**
 * Represents the food item in the game.
 */

public class FoodFactory {
    public enum FoodType{
        MOUSE,
        APPLE,
        LIZARD
    }
    public Food getFood(FoodType foodType, Rect background, Snake snake, int x , int y){
        return switch (foodType) {
            case MOUSE -> new Mouse( background,  snake,  x,  y);
            case APPLE -> new Apple( background,  snake,  x,  y);
            case LIZARD -> new Lizard( background,  snake,  x,  y);
        };
    }
}