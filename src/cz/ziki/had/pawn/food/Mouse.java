package cz.ziki.had.pawn.food;

import cz.ziki.had.Constants;
import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Represents a mouse food item that moves around the game field.
 */
public class Mouse extends CommonFood implements Food {

    private static final double moseBaseSpeed = 1/2f;
    private double mouseMoveTime;
    private final double speedDifficulty = 0.94f;


    private double waitTimeLeft;
    private int velx = 1;
    private int vely = 1;

    /**
     * Creates a Mouse food object.
     *
     * @param background The game field background.
     * @param snake      The snake object interacting with the mouse.
     * @param x          The initial x-coordinate.
     * @param y          The initial y-coordinate.
     */
    public Mouse(Rect background, Snake snake, int x, int y) {

        loadImage();
        this.mouseMoveTime = moseBaseSpeed * (Math.pow(speedDifficulty,snake.getSpeedLevel()));
        this.gameField = background;
        this.snake = snake;
        this.myPhysicalShape = new Rect(x, y, Constants.TILE_WIDTH, Constants.TILE_WIDTH);

        xPadding = (int) ((Constants.TILE_WIDTH - this.myPhysicalShape.getWidth()) / 2.0);

    }

    /**
     * Loads the mouse's image.
     */
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

    /**
     * Spawns the mouse at a random position.
     */
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

    /**
     * Moves the mouse based on its velocity and checks for boundaries.
     */
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

    /**
     * Updates the mouse's position and state.
     *
     * @param dt Time delta for the update.
     */
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

    /**
     * Draws the mouse on the screen.
     *
     * @param g2d Graphics2D object for rendering.
     */
    public void draw(Graphics2D g2d) {
        drawIfNotEaten(g2d);
    }
}