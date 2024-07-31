package cz.ziki.had.pawn.food;

import cz.ziki.had.pawn.GameObject;

import java.awt.*;

public interface Food extends GameObject {
    public void spawn();
    public void update(double dt);
    public void draw(Graphics2D g2);
    public boolean isSpawned();
}
