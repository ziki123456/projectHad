package cz.ziki.had.pawn.food;

import cz.ziki.had.Constants;
import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Mouse extends CommonFood implements Food {

    private static final double mouseMoveTime = 1/3f;
    private double waitTimeLeft;
    private int velx = 1;
    private int vely = 1;

    /**
     * Constructs a new cz.ziki.had.FoodObjects.Food object with the specified parameters.
     *
     * @param background
     * @param snake
     * @param x
     * @param y
     */
    public Mouse(Rect background, Snake snake, int x, int y) {

        loadImage();

        this.gameField = background;
        this.snake = snake;
        this.myPhysicalShape = new Rect(x, y, Constants.TILE_WIDTH, Constants.TILE_WIDTH);

        xPadding = (int) ((Constants.TILE_WIDTH - this.myPhysicalShape.getWidth()) / 2.0);

    }

    @Override
    public void loadImage() {
        try {

            BufferedImage foodImages = ImageIO.read(
                    Objects.
                            requireNonNull(
                                    this.getClass().
                                            getClassLoader().
                                            getResourceAsStream("mys.gif")));
            foodImage =  foodImages;
            Graphics2D g2d = foodImages.createGraphics();
            g2d.drawImage(foodImages, 0, 0, null);
            g2d.dispose();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }

    public void spawn(){
        randomSpawn();
    }


    private boolean intersectLeftBound() {

        return (myPhysicalShape.getX() < gameField.getX() + Constants.TILE_WIDTH);

    }

    private boolean intersectRightBound() {

        return ((myPhysicalShape.getX() + myPhysicalShape.getWidth()) > gameField.getX() + gameField.getWidth() - Constants.TILE_WIDTH);

    }

    private boolean intersectUpperBound() {
        return (myPhysicalShape.getY() < gameField.getY() + Constants.TILE_WIDTH);

    }

    private boolean intersectLowerBound() {
        return ((myPhysicalShape.getY() + myPhysicalShape.getHeight()) > gameField.getY() + gameField.getHeight() - Constants.TILE_WIDTH);

    }

    public void move() {

        if (intersectUpperBound() || intersectLowerBound()) {
            vely = vely * -1;

        }
        if (intersectLeftBound() || intersectRightBound()) {
            velx = velx * -1;

        }


        this.myPhysicalShape.setX( this.myPhysicalShape.getX() - (Constants.TILE_WIDTH * velx));

        this.myPhysicalShape.setY( this.myPhysicalShape.getY() - (Constants.TILE_WIDTH * vely));

    }

    @Override
    public void update(double dt) {
        //od casovace odectu ubehl cas
        waitTimeLeft -= dt;
        //pokud ubehl cas
        if (waitTimeLeft <= 0) {

            move();
            //reset casovace
            waitTimeLeft = mouseMoveTime;

        }
        checkIfNotEaten();

    }

    public void draw(Graphics2D g2d) {
        drawIfNotEaten(g2d);
    }
}