package cz.ziki.had.pawn;

import cz.ziki.had.Constants;
import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Obstacle extends CommonGameObject {


    public Obstacle(Rect background, Snake snake, int x, int y) {

        loadImage();

        this.gameField = background;
        this.snake = snake;
        this.myPhysicalShape = new Rect(  (x * Constants.TILE_WIDTH) + background.getX(), ((y  * Constants.TILE_WIDTH)+ background.getY()), Constants.TILE_WIDTH, Constants.TILE_WIDTH);

    }

    @Override
    public void loadImage() {
        try {

            BufferedImage foodImages = ImageIO.read(
                    Objects.
                            requireNonNull(
                                    this.getClass().
                                            getClassLoader().
                                            getResourceAsStream("obstacle.png")));
            foodImage =  foodImages;
            Graphics2D g2d = foodImages.createGraphics();
            g2d.drawImage(foodImages, 0, 0, null);
            g2d.dispose();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public void draw(Graphics2D g) {

        g.drawImage(this.foodImage, this.myPhysicalShape.getX() + xPadding, this.myPhysicalShape.getY(), null);
    }


    public void update (double dt) {
        if (intersectingWithSnake()) {
            snake.die();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obstacle obstacle = (Obstacle) o;
        return myPhysicalShape.getX() == obstacle.myPhysicalShape.getX() && myPhysicalShape.getY() == obstacle.myPhysicalShape.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(myPhysicalShape.getX(), myPhysicalShape.getY());
    }
}