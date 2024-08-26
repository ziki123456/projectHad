package cz.ziki.had.scenes;

import cz.ziki.had.Rect;

import java.awt.image.BufferedImage;

/**
 * Builder class for creating instances of MenuItem.
 */
public class MenuItemBuilder {
    private BufferedImage activeImage;
    private BufferedImage passiveImage;
    private BufferedImage spriteSheet;
    private Rect myPhysicalShape;
    private Runnable action;

    /**
     * Creates a new instance of MenuItemBuilder.
     * @param spriteSheet the image containing all frames
     */
    public MenuItemBuilder(BufferedImage spriteSheet){
        this.spriteSheet = spriteSheet;

    }

    /**
     * Builds and returns a new MenuItem instance based on the current parameters.
     *
     * @return a new instance of MenuItem
     */
    public MenuItem build(){
        boolean isActive = false;
        return new MenuItem(activeImage,passiveImage, isActive,myPhysicalShape,action);
    }


    /**
     * Sets the physical area of the menu item.
     *
     * @param myPhysicalShape the rectangle defining the area of the menu item
     * @return the current instance of MenuItemBuilder
     */
    public MenuItemBuilder setMyPhysicalShape(Rect myPhysicalShape) {
        this.myPhysicalShape = myPhysicalShape;
        return this;
    }

    /**
     * Sets the action to be performed when the menu item is clicked.
     *
     * @param action the action to be performed
     * @return the current instance of MenuItemBuilder
     */
    public MenuItemBuilder setAction(Runnable action) {
        this.action = action;
        return this;
    }

    /**
     * Sets the passive image of the menu item based on the specified rectangle in the sprite sheet.
     *
     * @param x the x-coordinate in the sprite sheet
     * @param y the y-coordinate in the sprite sheet
     * @param w the width of the image
     * @param h the height of the image
     * @return the current instance of MenuItemBuilder
     */
    public MenuItemBuilder setPassiveImage(int x, int y, int w, int h) {
        passiveImage = spriteSheet.getSubimage(x,y,w,h);
        return this;
    }

    /**
     * Sets the active image of the menu item based on the specified rectangle in the sprite sheet.
     *
     * @param x the x-coordinate in the sprite sheet
     * @param y the y-coordinate in the sprite sheet
     * @param w the width of the image
     * @param h the height of the image
     * @return the current instance of MenuItemBuilder
     */
    public MenuItemBuilder setActiveImage(int x, int y, int w, int h) {
        activeImage = spriteSheet.getSubimage(x, y, w, h);
        return this;
    }
}