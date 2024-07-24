package cz.ziki.had;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
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
     *
     * @param keyListener   the key listener for handling keyboard input
     * @param mouseListener the mouse listener for handling mouse input
     */
    public MenuScene(KeyL keyListener, MouseL mouseListener) {

        this.keyListener = keyListener;
        this.mouseListener = mouseListener;

        try {

            BufferedImage spritesheet = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("snakeMenu.png"));
            title = spritesheet.getSubimage(170, 40, 320, 70);
            play = spritesheet.getSubimage(130, 130, 410, 40);
            playPressed = spritesheet.getSubimage(130, 185, 410, 40);
            exit = spritesheet.getSubimage(180, 240, 310, 40);
            exitPressed = spritesheet.getSubimage(180, 295, 310, 40);

        } catch (Exception e) {
            e.printStackTrace();
        }

        playCurrentImage = play;
        exitCurrentImage = exit;

        titleRect = new Rect(240, 100, 320, 70);
        playRect = new Rect(195, 280, 410, 40);
        exitRect = new Rect(245, 355, 310, 40);

    }

    /**
     * Updates the main menu scene based on the elapsed time and user input.
     *
     * @param dt the time elapsed since the last update in seconds
     */
    @Override
    public void update(double dt) {

        if (mouseListener.getX() >= playRect.getX() && mouseListener.getX() <= playRect.getX() + playRect.getWidth() &&
                mouseListener.getY() >= playRect.getY() && mouseListener.getY() <= playRect.getY() + playRect.getHeight()) {
            playCurrentImage = playPressed;
            if (mouseListener.isPressed()) {
                Window.getWindow().changeState(1);
            }
        } else {
            playCurrentImage = play;
        }

        if (mouseListener.getX() >= exitRect.getX() && mouseListener.getX() <= exitRect.getX() + exitRect.getWidth() &&
                mouseListener.getY() >= exitRect.getY() && mouseListener.getY() <= exitRect.getY() + exitRect.getHeight()) {
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
     *
     * @param g the Graphics object used for drawing
     */
    @Override
    public void draw(Graphics g) {

        g.setColor(new Color(10, 220, 215));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        g.drawImage(title, (int) titleRect.getX(), (int) titleRect.getY(), (int) titleRect.getWidth(), (int) titleRect.getHeight(), null);
        g.drawImage(playCurrentImage, (int) playRect.getX(), (int) playRect.getY(), (int) playRect.getWidth(), (int) playRect.getHeight(), null);
        g.drawImage(exitCurrentImage, (int) exitRect.getX(), (int) exitRect.getY(), (int) exitRect.getWidth(), (int) exitRect.getHeight(), null);

        Font font = new Font("Tahoma", Font.BOLD, 18);
        FontMetrics metrics = g.getFontMetrics(font);
        String nicknameText = "Your nickname: " + Window.getWindow().nickname;

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(nicknameText, Constants.SCREEN_WIDTH / 2 - (metrics.stringWidth(nicknameText) / 2), Constants.SCREEN_HEIGHT - 50);

    }
}