package cz.ziki.had;

import cz.ziki.had.pawn.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the snake in the game.
 */
public class Snake implements GameObject, Serializable {

    public BufferedImage headN, headS, headE, headW, bodyImg;

    List<BodyPiece> body = new ArrayList<>();

    BodyPiece headPiece, tailPiece;

    public int bodyWidth, bodyHeight;

    public int size;

    public boolean shouldGrow = false;

    public Direction direction = Direction.RIGHT;

    public double ogWaitBetweenUpdates = 1/2f;

    public double waitTimeLeft = ogWaitBetweenUpdates;

    public Rect background;

    public int score = 0;

    /**
     * Constructs a new snake with the specified parameters.
     *
     * @param size       The size of the snake.
     * @param startX     The starting x-coordinate of the snake.
     * @param startY     The starting y-coordinate of the snake.
     * @param bodyWidth  The width of each body piece.
     * @param bodyHeight The height of each body piece.
     * @param background The background rectangle of the game.
     */
    public Snake(int size, int startX, int startY, int bodyWidth, int bodyHeight, Rect background) {

        this.size = size;
        this.bodyWidth = bodyWidth;
        this.bodyHeight = bodyHeight;
        this.background = background;

        for (int i = 0; i <= size; i++) {

            BodyPiece bodyPiece = new BodyPiece(new Rect(startX + i * bodyWidth, startY, bodyWidth, bodyHeight));
            if (i == 0) this.tailPiece = bodyPiece;
            if (i == size) this.headPiece = bodyPiece;
            body.add(bodyPiece);

        }

        try {

            BufferedImage snakeImages = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("snakehead.png"));
            Image tmp = snakeImages.getSubimage(245, 0, 230, 190).getScaledInstance(Constants.TILE_WIDTH, Constants.TILE_WIDTH, Image.SCALE_SMOOTH);
            bodyImg = new BufferedImage(Constants.TILE_WIDTH, Constants.TILE_WIDTH, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bodyImg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            tmp = snakeImages.getSubimage(0, 0, 230, 190).getScaledInstance(Constants.TILE_WIDTH, Constants.TILE_WIDTH, Image.SCALE_SMOOTH);
            headE = new BufferedImage(Constants.TILE_WIDTH, Constants.TILE_WIDTH, BufferedImage.TYPE_INT_ARGB);

            g2d = headE.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            AffineTransform tx = new AffineTransform();
            tx.rotate(3.14 / 2, headE.getWidth() / 2, headE.getHeight() / 2);

            AffineTransformOp op = new AffineTransformOp(tx,
                    AffineTransformOp.TYPE_BILINEAR);
            headS = op.filter(headE, null);
            headW = op.filter(headS, null);
            headN = op.filter(headW, null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints information about the snake's body.
     *
     * @return A string containing information about the snake's body.
     */
    public String printBody() {

        String text = "-------------" + "\n";
        text += "size :" + body.size() + "\n";
        text += "head :" + body.get(body.size() - 1) + "\n";
        text += "tail :" + body.get(0) + "\n";

        for (int i = 0; i < body.size(); i++) {

            text += body.get(i).rect.getX() + " " + body.get(i).rect.getY() + "\n";

        }

        return text;

    }

    /**
     * Changes the direction of the snake.
     *
     * @param newDirection The new direction of the snake.
     */
    public void changeDirecton(Direction newDirection) {

        if (newDirection == Direction.RIGHT && direction != Direction.LEFT)
            direction = newDirection;

        else if (newDirection == Direction.LEFT && direction != Direction.RIGHT)
            direction = newDirection;

        else if (newDirection == Direction.UP && direction != Direction.DOWN)
            direction = newDirection;

        else if (newDirection == Direction.DOWN && direction != Direction.UP)
            direction = newDirection;

    }

    /**
     * Updates the snake's position.
     *
     * @param dt The time elapsed since the last update.
     */
    public void update(double dt) {

        if (waitTimeLeft > 0) {

            waitTimeLeft -= dt;
            return;

        }
        if (intersectingWithSelf()) {

            this.die();

        }

        waitTimeLeft = ogWaitBetweenUpdates;
        int newX = 0;
        int newY = 0;

        if (direction == Direction.RIGHT) {
            newX = body.get(body.size() - 1).rect.getX() + bodyWidth;
            newY = body.get(body.size() - 1).rect.getY();

        } else if (direction == Direction.LEFT) {
            newX = body.get(body.size() - 1).rect.getX() - bodyWidth;
            newY = body.get(body.size() - 1).rect.getY();

        } else if (direction == Direction.UP) {
            newX = body.get(body.size() - 1).rect.getX();
            newY = body.get(body.size() - 1).rect.getY() - bodyHeight;

        } else if (direction == Direction.DOWN) {
            newX = body.get(body.size() - 1).rect.getX();
            newY = body.get(body.size() - 1).rect.getY() + bodyHeight;

        }

        if (this.shouldGrow) {
            this.shouldGrow = false;
        } else body.remove(0);

        BodyPiece bodyPiece = new BodyPiece(new Rect(newX, newY, bodyWidth, bodyHeight));
        body.add(bodyPiece);

    }

    /**
     * Checks if the snake is intersecting with itself.
     */
    public boolean intersectingWithSelf() {
        Rect headR = body.get(body.size() - 1).rect;
        return intersectingWithRect(headR) || intersectingWithScreenBoundaries(headR);
    }

    /**
     * Checks if the snake is intersecting with a given rectangle.
     */
    public boolean intersectingWithRect(Rect rect) {
        for (int i = 0; i < body.size() - 1; i++) {
            if (intersecting(rect, body.get(i).rect)) return true;
        }
        return false;
    }

    /**
     * Checks if two rectangles are intersecting.
     */
    public boolean intersecting(Rect r1, Rect r2) {
        return (r1.getX() >= r2.getX() && r1.getX() + r1.getWidth() <= r2.getX() + r2.getWidth() &&
                r1.getY() >= r2.getY() && r1.getY() + r1.getHeight() <= r2.getY() + r2.getHeight());
    }

    /**
     * Checks if the snake is intersecting with the screen boundaries.
     */
    public boolean intersectingWithScreenBoundaries(Rect head) {
        return (head.getX() < background.getX() || (head.getX() + head.getWidth()) > background.getX() + background.getWidth() ||
                head.getY() < background.getY() || (head.getY() + head.getHeight()) > background.getY() + background.getHeight());
    }

    /**
     * Makes the snake grow.
     * Makes the snake faster after eating food.
     */
    public void grow() {

        this.shouldGrow = true;
        this.ogWaitBetweenUpdates *= 0.9;
        this.score++;

    }

    /**
     * Draws the snake on the graphics context.
     *
     * @param g2 The graphics context to draw on.
     */
    public void draw(Graphics2D g2) {

        for (int i = 0; i < body.size(); i++) {

            BodyPiece piece = body.get(i);
            double subWidth = (piece.rect.getWidth() - 6.0) / 2.0;
            double subHeight = (piece.rect.getHeight() - 6.0) / 2.0;

            g2.setColor(Color.BLACK);
            if (i == body.size() - 1) {
                if (direction == Direction.RIGHT) {
                    g2.drawImage(this.headE, (int) piece.rect.getX(), (int) piece.rect.getY(), null);
                } else if (direction == Direction.LEFT) {
                    g2.drawImage(this.headW, (int) piece.rect.getX(), (int) piece.rect.getY(), null);
                } else if (direction == Direction.UP) {
                    g2.drawImage(this.headN, (int) piece.rect.getX(), (int) piece.rect.getY(), null);
                } else if (direction == Direction.DOWN) {
                    g2.drawImage(this.headS, (int) piece.rect.getX(), (int) piece.rect.getY(), null);
                }

            } else g2.drawImage(this.bodyImg, (int) piece.rect.getX(), (int) piece.rect.getY(), null);
        }
    }

    @Override
    public void setSnake(Snake snake) {

    }

    public void die() {
        Window.getWindow().lastScore = this.score;
        FileUtils.savePlayerScore(Window.getWindow().nickname, this.score);
        Window.getWindow().changeState(2);
    }
}