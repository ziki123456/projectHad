package cz.ziki.had.pawn.food;

import cz.ziki.had.pawn.GameObject;

import java.awt.*;

public interface Food extends GameObject {
    void loadImage();

    void spawn();
    void update(double dt);
    void draw(Graphics2D g2);
    boolean isSpawned();
}
