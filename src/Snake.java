import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import java.awt.image.BufferedImage;
import java.io.File;

public class Snake {

    public BufferedImage headN, headS, headE, headW, bodyImg;
    public Rect[] body = new Rect[30];
    public double bodyWidth, bodyHeight;

    public int size;
    public int tail = 0;
    public int head = 0;

    public Direction direction = Direction.RIGHT;

    public double ogWaitBetweenUpdates = 0.1f;
    public double waitTimeLeft = ogWaitBetweenUpdates;

    public Rect background;

    public Snake(int size, double startX, double startY, double bodyWidth, double bodyHeight, Rect background) {
        this.size = size;
        this.bodyWidth = bodyWidth;
        this.bodyHeight = bodyHeight;
        this.background = background;

        for (int i=0; i <= size; i++) {
            Rect bodyPiece = new Rect(startX + i * bodyWidth, startY, bodyWidth, bodyHeight);
            body[i] = bodyPiece;
            head++;
        }
        head--;
        try {
            BufferedImage snakeImages = ImageIO.read(new File("C:\\Users\\tadea\\Desktop\\projectHad\\snakehead.png"));
            //headN = snakeImages.getSubimage(0, 0, 230, 190);
           // headS = headN.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            //headE = spritesheet.getSubimage(0, 0, 230, 190);
           // headW = spritesheet.getSubimage(0, 0, 230, 190);
            Image tmp = snakeImages.getSubimage(245, 0, 230, 190).getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            bodyImg = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bodyImg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            tmp = snakeImages.getSubimage(0, 0, 230, 190).getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            headE = new BufferedImage(24, 24, BufferedImage.TYPE_INT_ARGB);

            g2d = headE.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            g2d.dispose();

            AffineTransform tx = new AffineTransform();
            tx.rotate(3.14/2, headE.getWidth() / 2, headE.getHeight() / 2);

            AffineTransformOp op = new AffineTransformOp(tx,
                    AffineTransformOp.TYPE_BILINEAR);
            headS = op.filter(headE, null);
            headW = op.filter(headS, null);
            headN = op.filter(headW, null);

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

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

    public void update(double dt) {
        if (waitTimeLeft > 0) {
            waitTimeLeft -= dt;
            return;
        }
        if (intersectingWithSelf()) {
            Window.getWindow().changeState(0);
        }

        waitTimeLeft = ogWaitBetweenUpdates;
        double newX = 0;
        double newY = 0;

        if (direction == Direction.RIGHT) {
            newX = body[head].x + bodyWidth;
            newY = body[head].y;
        } else if (direction == Direction.LEFT) {
            newX = body[head].x - bodyWidth;
            newY = body[head].y;
        } else if (direction == Direction.UP) {
            newX = body[head].x;
            newY = body[head].y - bodyHeight;
        } else if (direction == Direction.DOWN) {
            newX = body[head].x;
            newY = body[head].y + bodyHeight;
        }

        System.out.println(body[head].x + "-" + body[head].y);
        //System.out.println(body.length);
        //System.out.println(tail);
        body[(head + 1) % body.length] = body[tail];
        body[tail] = null;
        head = (head + 1) % body.length;
        //System.out.println(head);
        tail = (tail + 1) % body.length;

        body[head].x = newX;
        body[head].y = newY;
    }

    public boolean intersectingWithSelf() {
        Rect headR = body[head];
        return intersectingWithRect(headR) || intersectingWithScreenBoundaries(headR);
    }

    public boolean intersectingWithRect(Rect rect) {
        for (int i = tail; i != head; i = (i + 1) % body.length) {
            if (intersecting(rect, body[i])) return true;
        }
        return false;
    }

    public boolean intersecting(Rect r1, Rect r2) {
        return (r1.x >= r2.x && r1.x + r1.width <= r2.x + r2.width &&
                r1.y >= r2.y && r1.y + r1.height <= r2.y + r2.height);
    }

    public boolean intersectingWithScreenBoundaries(Rect head) {
        return (head.x < background.x || (head.x + head.width) > background.x + background.width ||
                head.y < background.y || (head.y + head.height) > background.y + background.height);
    }

    public void grow() {
        double newX = 0;
        double newY = 0;

        if (direction == Direction.RIGHT) {
            newX = body[tail].x - bodyWidth;
            newY = body[tail].y;
        } else if (direction == Direction.LEFT) {
            newX = body[tail].x + bodyWidth;
            newY = body[tail].y;
        } else if (direction == Direction.UP) {
            newX = body[tail].x;
            newY = body[tail].y + bodyHeight;
        } else if (direction == Direction.DOWN) {
            newX = body[tail].x;
            newY = body[tail].y - bodyHeight;
        }

        Rect newBodyPiece = new Rect(newX, newY, bodyWidth, bodyHeight);

        tail = (tail - 1) % body.length;
        body[tail] = newBodyPiece;
    }

    public void draw(Graphics2D g2) {
        for (int i = tail; i != head; i = (i + 1) % body.length) {
            Rect piece = body[i];
            double subWidth = (piece.width - 6.0) / 2.0;
            double subHeight = (piece.height - 6.0) / 2.0;

            g2.setColor(Color.BLACK);
            //g2.fill(new RoundRectangle2D.Double(piece.x + 2.0, piece.y + 2.0, piece.width-2, piece.height-2, 2, 2));
            if(i==head-1) {
                if (direction == Direction.RIGHT) {
                    g2.drawImage(this.headE, (int)piece.x ,(int)piece.y,null);
                } else if (direction == Direction.LEFT) {
                    g2.drawImage(this.headW, (int)piece.x ,(int)piece.y,null);
                } else if (direction == Direction.UP) {
                    g2.drawImage(this.headN, (int)piece.x ,(int)piece.y,null);
                } else if (direction == Direction.DOWN) {
                    g2.drawImage(this.headS, (int)piece.x ,(int)piece.y,null);
                }

            } else g2.drawImage(this.bodyImg, (int)piece.x ,(int)piece.y,null);
            //g2.fill(new RoundRectangle2D.Double(piece.x + 4.0 + subWidth, piece.y + 2.0, subWidth, subHeight, 5, 5));
          //  g2.fill(new RoundRectangle2D.Double(piece.x + 2.0, piece.y + 4.0 + subHeight, subWidth, subHeight,5 ,5));
           // g2.fill(new RoundRectangle2D.Double(piece.x + 4.0 + subWidth, piece.y + 4.0 + subHeight, subWidth, subHeight, 5, 5));
        }
    }
}