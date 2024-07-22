package cz.ziki.had.FoodObjects;

import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

import java.awt.*;

/**
 * Represents the food item in the game.
 */

public class FoodFactory {
    public enum FoodType{
        MOUSE,
        APPLE,
        LIZARD
    }
    public Food getFood(FoodType foodType, Rect background, Snake snake, int width , int height, Color color){
        return switch (foodType) {
            case MOUSE -> new Mouse( background,  snake,  width,  height,  color);
            case APPLE -> new Apple( background,  snake,  width,  height,  color);
            case LIZARD -> new Lizard( background,  snake,  width,  height,  color);
        };
    }
}