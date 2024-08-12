package cz.ziki.had.scenes;

import cz.ziki.had.Rect;

import java.awt.image.BufferedImage;

public class MenuItemBuilder {
    private BufferedImage activeImage;
    private BufferedImage passiveImage;
    private BufferedImage spriteSheet;
    private Rect myPhysicalShape;
    private Runnable action;
    private boolean isActive = false;

    public MenuItemBuilder(){

    };

    public MenuItem build(){
        return new MenuItem(activeImage,passiveImage,isActive,spriteSheet,myPhysicalShape,action);
    }


    public BufferedImage getActiveImage() {
        return activeImage;
    }

    public MenuItemBuilder setActiveImage(BufferedImage activeImage) {
        this.activeImage = activeImage;
        return this;
    }

    public BufferedImage getPassiveImage() {
        return passiveImage;
    }

    public MenuItemBuilder setPassiveImage(BufferedImage passiveImage) {
        this.passiveImage = passiveImage;
        return this;
    }

    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }

    public MenuItemBuilder setSpriteSheet(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
        return this;
    }

    public Rect getMyPhysicalShape() {
        return myPhysicalShape;
    }

    public MenuItemBuilder setMyPhysicalShape(Rect myPhysicalShape) {
        this.myPhysicalShape = myPhysicalShape;
        return this;
    }

    public Runnable getAction() {
        return action;
    }

    public MenuItemBuilder setAction(Runnable action) {
        this.action = action;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public MenuItemBuilder setActive(boolean active) {
        isActive = active;
        return this;
    }

    public MenuItemBuilder setPassiveImage(int x, int y, int w, int h) {
        passiveImage = spriteSheet.getSubimage(x,y,w,h);
        return this;
    }

    public MenuItemBuilder setActiveImage(int x, int y, int w, int h) {
        activeImage = spriteSheet.getSubimage(x, y, w, h);
        return this;
    }
}
