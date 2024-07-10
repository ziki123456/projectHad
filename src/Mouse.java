import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Mouse extends CommonFood implements Food {

    private static final double moveDelay = 0.1f;
    private double waitTimeLeft;
    private int velx = 1;
    private int vely = 1;

    /**
     * Constructs a new Food object with the specified parameters.
     *
     * @param background
     * @param snake
     * @param width
     * @param height
     * @param color
     */
    public Mouse(Rect background, Snake snake, int width, int height, Color color) {

        try {

            BufferedImage foodImages = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("mys.gif"));
            //Image tmp = foodImages.getSubimage(710, 0, 230, 190).getScaledInstance(Constants.TILE_WIDTH, Constants.TILE_WIDTH, Image.SCALE_SMOOTH);
            //foodImage = new BufferedImage(Constants.TILE_WIDTH, Constants.TILE_WIDTH, BufferedImage.TYPE_INT_ARGB);
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

    public void spawn(){
        randomSpawn();
    }

    private void colisionCheck() {

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

    public void move() {

        if (intersectUpperBound() || intersectLowerBound()) {
            vely = vely * -1;

        }
        if (intersectLeftBound() || intersectRightBound()) {
            velx = velx * -1;

        }


        this.rect.x -= (Constants.TILE_WIDTH * velx);

        this.rect.y -= (Constants.TILE_WIDTH * vely);

    }

    @Override
    public void update(double dt) {
        //mys se pohne az vyprsi cas a pokazde musi zkontrolovat jestli ji had nesezral
        //pokazde co ubehne cas zavola se update
        //zresetovat casovac
        //zavola se super.update

        waitTimeLeft -= dt;
        if (waitTimeLeft <= 0) {

            move();
            //nastaveni casu pro dalsi pohyb myi;
            waitTimeLeft = moveDelay;


        }
        checkIfNotEaten();

    }

    public void draw(Graphics2D g2d) {
        drawIfNotEaten(g2d);
    }


}
