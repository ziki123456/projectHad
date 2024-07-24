package cz.ziki.had.pawn;

import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

import java.awt.image.BufferedImage;

public abstract class CommonGameObject implements GameObject{

    public Rect gameField;
    public Snake snake;

    public Rect myPhysicalShape;
    public BufferedImage foodImage;

    public boolean intersectingWithSnake() {
        return snake.intersectingWithRect(myPhysicalShape);
    }
}