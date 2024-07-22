package cz.ziki.had.FoodObjects;

import cz.ziki.had.Constants;
import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import static java.nio.file.Files.move;

public class Lizard extends CommonFood implements Food{

    private static final double lizardMoveTime = 1/4f;
    private double waitTimeLeft;
    private int velx = 1;
    private int vely = 1;

    /**
     * Constructs a new cz.ziki.had.FoodObjects.Food object with the specified parameters.
     *
     * @param background
     * @param snake
     * @param width
     * @param height
     * @param color
     */
    public Lizard(Rect background, Snake snake, int width, int height, Color color) {

        try {

            BufferedImage foodImages = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("jesterka.gif"));
            foodImage =  foodImages;
            Graphics2D g2d = foodImages.createGraphics();
            g2d.drawImage(foodImages, 0, 0, null);
            g2d.dispose();

        } catch (Exception e) {

            e.printStackTrace();

        }

        this.background = background;
        this.snake = snake;
        this.width = width;
        this.height = height;
        this.color = color;
        this.rect = new Rect(0, 0, width, height);

        xPadding = (int) ((Constants.TILE_WIDTH - this.width) / 2.0);

    }



    @Override
    public void spawn() {
        randomSpawn();
    }

    private boolean intersectLeftBound() {

        return (rect.x < background.x + Constants.TILE_WIDTH);

    }

    private boolean intersectRightBound() {

        return ((rect.x + rect.width) > background.x + background.width - Constants.TILE_WIDTH);

    }

    private boolean intersectUpperBound() {
        return (rect.y < background.y + Constants.TILE_WIDTH);

    }

    private boolean intersectLowerBound() {
        return ((rect.y + rect.height) > background.y + background.height - Constants.TILE_WIDTH);

    }

    @Override
    public void update(double dt) {
        waitTimeLeft -= dt;

        if (waitTimeLeft <= 0) {

            move();

            waitTimeLeft = lizardMoveTime;

        }
        checkIfNotEaten();
    }

    private int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt(3)-1;
    }


    private void move() {


        boolean boundIntersect = false;
        if (intersectUpperBound() || intersectLowerBound()) {
            vely = vely * -1;
            boundIntersect = true;
        }
        if (intersectLeftBound() || intersectRightBound()) {
            velx = velx * -1;
            boundIntersect = true;
        }
        if (!boundIntersect) {
            velx = getRandomNumber();
            vely = getRandomNumber();
        }

        this.rect.x -= (Constants.TILE_WIDTH * velx);

        this.rect.y -= (Constants.TILE_WIDTH * vely);
    }

    @Override
    public void draw(Graphics2D g2) {
        drawIfNotEaten(g2);
    }
}
