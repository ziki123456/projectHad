package cz.ziki.had.scenes;

import cz.ziki.had.*;
import cz.ziki.had.Window;
import cz.ziki.had.pawn.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the main menu scene of the game.
 */
public class MenuScene implements Scene {

    protected final Set<MenuItem> menuItems = Collections.synchronizedSet(new HashSet<>());

    public KeyL keyListener;
    public MouseL mouseListener;

    /**
     * Constructs a new MenuScene with the specified key and mouse listeners.
     *
     * @param keyListener   the key listener for handling keyboard input
     * @param mouseListener the mouse listener for handling mouse input
     */
    public MenuScene(KeyL keyListener, MouseL mouseListener) {

        this.keyListener = keyListener;
        this.mouseListener = mouseListener;
        mouseListener.registerOnClick(this::toggleOnClick);

        try {

            BufferedImage spritesheet = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("snakeMenuOld.png"));

            menuItems.add(
                    new MenuItemBuilder()
                    .setSpriteSheet(spritesheet)
                    .setPassiveImage(130, 130, 410, 40)
                    .setActiveImage(130, 185, 410, 40)
                    .setMyPhysicalShape(new Rect(195, 280, 410, 40))
                    .setAction( () -> cz.ziki.had.Window.getWindow().changeState(1))
                    .build()
            );

            MenuItemBuilder titleItemBuilder = new MenuItemBuilder();
            titleItemBuilder.setSpriteSheet(spritesheet);
            titleItemBuilder.setPassiveImage(170, 40, 320, 70);
            titleItemBuilder.setActiveImage(170, 40, 320, 70);
            titleItemBuilder.setMyPhysicalShape(new Rect(240, 100, 320, 70));
            titleItemBuilder.setAction(()->{});
            menuItems.add(titleItemBuilder.build());

            MenuItemBuilder exitItemBuilder = new MenuItemBuilder();
            exitItemBuilder.setSpriteSheet(spritesheet);
            exitItemBuilder.setPassiveImage(180, 240, 310, 40);
            exitItemBuilder.setActiveImage(180, 295, 310, 40);
            exitItemBuilder.setMyPhysicalShape(new Rect(245, 355, 310, 40));
            exitItemBuilder.setAction( () -> cz.ziki.had.Window.getWindow().close());
            menuItems.add(exitItemBuilder.build());

            MenuItemBuilder editItemBuilder = new MenuItemBuilder();
            editItemBuilder.setSpriteSheet(spritesheet);
            editItemBuilder.setPassiveImage(180,335,310,20);
            editItemBuilder.setActiveImage(180,355,310,20);
            editItemBuilder.setMyPhysicalShape(new Rect(245, 420, 310, 25));
            editItemBuilder.setAction( () -> cz.ziki.had.Window.getWindow().changeState(3));
            menuItems.add(editItemBuilder.build());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void toggleOnClick(Point2D point2D) {
        menuItems.forEach( item ->
            {
                item.click(point2D);
            }
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
                {
                    item.hover(mouseListener.getX(),mouseListener.getY());
                }
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