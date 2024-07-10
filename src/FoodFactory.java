import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents the food item in the game.
 */

public class FoodFactory {
    public enum FoodType{
        MOUSE,
        APPLE
    }
    public Food getFood(FoodType foodType, Rect background, Snake snake, int width , int height, Color color){
        return switch (foodType) {
            case MOUSE -> new Mouse( background,  snake,  width,  height,  color);
            case APPLE -> new Apple( background,  snake,  width,  height,  color);
        };
    }
}