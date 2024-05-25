import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Represents the main menu scene of the game.
 */

public class MenuScene extends Scene {

    public KeyL keyListener;
    public MouseL mouseListener;
    public BufferedImage title, play, playPressed, exit, exitPressed;
    public Rect playRect, exitRect, titleRect;

    public BufferedImage playCurrentImage, exitCurrentImage;

    /**
     * Constructs a new MenuScene with the specified key and mouse listeners.
     * @param keyListener the key listener for handling keyboard input
     * @param mouseListener the mouse listener for handling mouse input
     */

    public MenuScene(KeyL keyListener, MouseL mouseListener) {
        this.keyListener = keyListener;
        this.mouseListener = mouseListener;

        try {
            BufferedImage spritesheet = ImageIO.read(new File("C:\\Users\\tadea\\Desktop\\projectHad\\snakeMenu.png"));
            title = spritesheet.getSubimage(320, 120, 760, 200);
            play = spritesheet.getSubimage(0, 121, 261, 121);
            playPressed = spritesheet.getSubimage(264, 121, 261, 121);
            exit = spritesheet.getSubimage(0, 0, 233, 93);
            exitPressed = spritesheet.getSubimage(264, 0, 233, 93);

        } catch(Exception e) {
            e.printStackTrace();
        }

        playCurrentImage = play;
        exitCurrentImage = exit;

        titleRect = new Rect(230, 100, 320, 100);
        playRect = new Rect(310, 280, 150, 70);
        exitRect = new Rect(318, 355, 130, 55);
    }

    /**
     * Updates the main menu scene based on the elapsed time and user input.
     * @param dt the time elapsed since the last update in seconds
     */

    @Override
    public void update(double dt) {
        if (mouseListener.getX() >= playRect.x && mouseListener.getX() <= playRect.x + playRect.width &&
                mouseListener.getY() >= playRect.y && mouseListener.getY() <= playRect.y + playRect.height) {
            playCurrentImage = playPressed;
            if (mouseListener.isPressed()) {
                Window.getWindow().changeState(1);
            }
        } else {
            playCurrentImage = play;
        }

        if (mouseListener.getX() >= exitRect.x && mouseListener.getX() <= exitRect.x + exitRect.width &&
                mouseListener.getY() >= exitRect.y && mouseListener.getY() <= exitRect.y + exitRect.height) {
            exitCurrentImage = exitPressed;
            if (mouseListener.isPressed()) {
                Window.getWindow().close();
            }
        } else {
            exitCurrentImage = exit;
        }
    }

    /**
     * Draws the main menu scene.
     * @param g the Graphics object used for drawing
     */

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(176, 248, 255));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);

        g.drawImage(title, (int)titleRect.x, (int)titleRect.y, (int)titleRect.width, (int)titleRect.height, null);
        g.drawImage(playCurrentImage, (int)playRect.x, (int)playRect.y, (int)playRect.width, (int)playRect.height, null);
        g.drawImage(exitCurrentImage, (int)exitRect.x, (int)exitRect.y, (int)exitRect.width, (int)exitRect.height, null);
    }
}