package cz.ziki.had.pawn;

import cz.ziki.had.Snake;

import java.awt.*;
import java.io.Serializable;

public interface GameObject {
    void update(double dt);
    void draw(Graphics2D g2);
    void setSnake(Snake snake);
}
