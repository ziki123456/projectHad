package cz.ziki.had.pawn;

import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public abstract class CommonGameObject implements GameObject, Serializable {

    public Rect gameField;
    public Snake snake;

    public Rect myPhysicalShape;
    public transient BufferedImage foodImage;

    public boolean intersectingWithSnake() {
        return snake.intersectingWithRect(myPhysicalShape);
    }
}