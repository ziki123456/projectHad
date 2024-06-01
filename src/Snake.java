import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the snake in the game.
 */
public class Snake {

    public BufferedImage headN, headS, headE, headW, bodyImg;

    List<BodyPiece> body = new ArrayList<>();

    BodyPiece headPiece, tailPiece;

    public double bodyWidth, bodyHeight;

    public int size;

    public boolean shouldGrow = false;

    public Direction direction = Direction.RIGHT;

    public double ogWaitBetweenUpdates = 0.51f;

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
    public Snake(int size, double startX, double startY, double bodyWidth, double bodyHeight, Rect background) {

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

            text += body.get(i).rect.x + " " + body.get(i).rect.y + "\n";

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

            Window.getWindow().lastScore = this.score;
            FileUtils.savePlayerScore(Window.getWindow().nickname, this.score);
            Window.getWindow().changeState(2);

        }

        waitTimeLeft = ogWaitBetweenUpdates;
        double newX = 0;
        double newY = 0;

        if (direction == Direction.RIGHT) {
            newX = body.get(body.size() - 1).rect.x + bodyWidth;
            newY = body.get(body.size() - 1).rect.y;

        } else if (direction == Direction.LEFT) {
            newX = body.get(body.size() - 1).rect.x - bodyWidth;
            newY = body.get(body.size() - 1).rect.y;

        } else if (direction == Direction.UP) {
            newX = body.get(body.size() - 1).rect.x;
            newY = body.get(body.size() - 1).rect.y - bodyHeight;

        } else if (direction == Direction.DOWN) {
            newX = body.get(body.size() - 1).rect.x;
            newY = body.get(body.size() - 1).rect.y + bodyHeight;

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
        return (r1.x >= r2.x && r1.x + r1.width <= r2.x + r2.width &&
                r1.y >= r2.y && r1.y + r1.height <= r2.y + r2.height);
    }

    /**
     * Checks if the snake is intersecting with the screen boundaries.
     */
    public boolean intersectingWithScreenBoundaries(Rect head) {
        return (head.x < background.x || (head.x + head.width) > background.x + background.width ||
                head.y < background.y || (head.y + head.height) > background.y + background.height);
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
            double subWidth = (piece.rect.width - 6.0) / 2.0;
            double subHeight = (piece.rect.height - 6.0) / 2.0;

            g2.setColor(Color.BLACK);
            if (i == body.size() - 1) {
                if (direction == Direction.RIGHT) {
                    g2.drawImage(this.headE, (int) piece.rect.x, (int) piece.rect.y, null);
                } else if (direction == Direction.LEFT) {
                    g2.drawImage(this.headW, (int) piece.rect.x, (int) piece.rect.y, null);
                } else if (direction == Direction.UP) {
                    g2.drawImage(this.headN, (int) piece.rect.x, (int) piece.rect.y, null);
                } else if (direction == Direction.DOWN) {
                    g2.drawImage(this.headS, (int) piece.rect.x, (int) piece.rect.y, null);
                }

            } else g2.drawImage(this.bodyImg, (int) piece.rect.x, (int) piece.rect.y, null);
        }
    }
}