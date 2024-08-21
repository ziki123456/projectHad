package cz.ziki.had.scenes;

import cz.ziki.had.*;
import cz.ziki.had.Window;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents the main menu scene of the game.
 */
public class MenuScene implements Scene {

    protected final Set<MenuItem> menuItems = Collections.synchronizedSet(new HashSet<>());

    public final MouseL mouseListener;

    /**
     * Constructs a new MenuScene with the specified mouse listener.
     *
     * @param mouseListener the mouse listener for handling mouse input
     */
    public MenuScene(MouseL mouseListener) {

        this.mouseListener = mouseListener;
        mouseListener.registerOnClick(this::toggleOnClick);

        try {

            BufferedImage spritesheet = ImageIO.read(
                    Objects.
                            requireNonNull(
                                    this.getClass().
                                            getClassLoader().
                                            getResourceAsStream("snakeMenuOld.png")));

            menuItems.add(
                    new MenuItemBuilder()
                    .setSpriteSheet(spritesheet)
                    .setPassiveImage(130, 130, 410, 40)
                    .setActiveImage(130, 185, 410, 40)
                    .setMyPhysicalShape(new Rect(195, 280, 410, 40))
                    .setAction( () -> cz.ziki.had.Window.getWindow().changeState(1))
                    .build()
            );

            menuItems.add(
                    new MenuItemBuilder()
                    .setSpriteSheet(spritesheet)
                    .setPassiveImage(170, 40, 320, 70)
                    .setActiveImage(170, 40, 320, 70)
                    .setMyPhysicalShape(new Rect(240, 100, 320, 70))
                    .setAction(()->{})
                    .build()
            );

            menuItems.add(
                    new MenuItemBuilder()
                            .setSpriteSheet(spritesheet)
                            .setPassiveImage(180, 240, 310, 40)
                            .setActiveImage(180, 295, 310, 40)
                            .setMyPhysicalShape(new Rect(245, 355, 310, 40))
                            .setAction( () -> cz.ziki.had.Window.getWindow().close())
                            .build()
            );

            menuItems.add(
                    new MenuItemBuilder()
                            .setSpriteSheet(spritesheet)
                            .setPassiveImage(180,335,310,20)
                            .setActiveImage(180,355,310,20)
                            .setMyPhysicalShape(new Rect(245, 420, 310, 25))
                            .setAction(() -> cz.ziki.had.Window.getWindow().changeState(3))
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Handles mouse click events and triggers actions for menu items.
     *
     * @param point2D the point where the mouse was clicked
     */
    private void toggleOnClick(Point2D point2D) {
        menuItems.forEach( item ->
                item.click(point2D)
        );
    }

    /**
     * Updates the main menu scene based on the elapsed time and user input.
     *
     * @param dt the time elapsed since the last update in seconds
     */
    @Override
    public void update(double dt) {
        menuItems.forEach( item ->
                item.hover(mouseListener.getX(),mouseListener.getY())
        );
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

        menuItems.forEach( item -> item.draw(g) );

        Font font = new Font("Tahoma", Font.BOLD, 18);
        FontMetrics metrics = g.getFontMetrics(font);
        String nicknameText = "Your nickname: " + Window.getWindow().nickname;

        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(nicknameText, Constants.SCREEN_WIDTH / 2 - (metrics.stringWidth(nicknameText) / 2), Constants.SCREEN_HEIGHT - 50);
    }
}