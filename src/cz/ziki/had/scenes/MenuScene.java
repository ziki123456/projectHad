package cz.ziki.had.scenes;

import cz.ziki.had.*;
import cz.ziki.had.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents the main menu scene of the game.
 */
public class MenuScene implements Scene {

    public KeyL keyListener;
    public MouseL mouseListener;
    public BufferedImage title, play, playPressed, exit, exitPressed, editMode, editModePressed;
    public Rect playRect, exitRect, titleRect, editRect;

    public BufferedImage playCurrentImage, exitCurrentImage, editCurrentImage;

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

            BufferedImage spritesheet = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("snakeMenuOld.png"));
            title = spritesheet.getSubimage(170, 40, 320, 70);
            play = spritesheet.getSubimage(130, 130, 410, 40);
            playPressed = spritesheet.getSubimage(130, 185, 410, 40);
            exit = spritesheet.getSubimage(180, 240, 310, 40);
            exitPressed = spritesheet.getSubimage(180, 295, 310, 40);
            editMode = spritesheet.getSubimage(180,335,310,20);
            editModePressed = spritesheet.getSubimage(180,355,310,20);

        } catch (Exception e) {
            e.printStackTrace();
        }

        playCurrentImage = play;
        exitCurrentImage = exit;
        editCurrentImage = editMode;

        titleRect = new Rect(240, 100, 320, 70);
        playRect = new Rect(195, 280, 410, 40);
        exitRect = new Rect(245, 355, 310, 40);
        editRect = new Rect(245, 420, 310, 25);

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
                cz.ziki.had.Window.getWindow().changeState(1);
            }
        } else {
            playCurrentImage = play;
        }

        if (mouseListener.getX() >= exitRect.getX() && mouseListener.getX() <= exitRect.getX() + exitRect.getWidth() &&
                mouseListener.getY() >= exitRect.getY() && mouseListener.getY() <= exitRect.getY() + exitRect.getHeight()) {
            exitCurrentImage = exitPressed;
            if (mouseListener.isPressed()) {
                cz.ziki.had.Window.getWindow().close();
            }
        } else {
            exitCurrentImage = exit;
        }

        if (mouseListener.getX() >= editRect.getX() && mouseListener.getX() <= editRect.getX() + editRect.getWidth() &&
                mouseListener.getY() >= editRect.getY() && mouseListener.getY() <= editRect.getY() + editRect.getHeight()) {
            editCurrentImage = editModePressed;
            if (mouseListener.isPressed()) {
                cz.ziki.had.Window.getWindow().changeState(3);
            }
        } else {
            editCurrentImage = editMode;
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

        drawRect(g, titleRect, title);
        drawRect(g, playRect, playCurrentImage);
        drawRect(g, exitRect, exitCurrentImage);
        drawRect(g, editRect, editCurrentImage);

        Font font = new Font("Tahoma", Font.BOLD, 18);
        FontMetrics metrics = g.getFontMetrics(font);
        String nicknameText = "Your nickname: " + Window.getWindow().nickname;

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(nicknameText, Constants.SCREEN_WIDTH / 2 - (metrics.stringWidth(nicknameText) / 2), Constants.SCREEN_HEIGHT - 50);

    }

    public void drawRect(Graphics g, Rect r, BufferedImage b) {
        g.drawImage(b, (int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight(), null);
    }
}