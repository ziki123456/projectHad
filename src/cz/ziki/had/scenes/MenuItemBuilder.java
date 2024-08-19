package cz.ziki.had.scenes;

import cz.ziki.had.Rect;

import java.awt.image.BufferedImage;

public class MenuItemBuilder {
    private BufferedImage activeImage;
    private BufferedImage passiveImage;
    private BufferedImage spriteSheet;
    private Rect myPhysicalShape;
    private Runnable action;

    public MenuItemBuilder(){

    }

    public MenuItem build(){
        boolean isActive = false;
        return new MenuItem(activeImage,passiveImage, isActive,myPhysicalShape,action);
    }

    public MenuItemBuilder setSpriteSheet(BufferedImage spriteSheet) {
        this.spriteSheet = spriteSheet;
        return this;
    }

    public MenuItemBuilder setMyPhysicalShape(Rect myPhysicalShape) {
        this.myPhysicalShape = myPhysicalShape;
        return this;
    }

    public MenuItemBuilder setAction(Runnable action) {
        this.action = action;
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