package cz.ziki.had.pawn;

import cz.ziki.had.Constants;
import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

import java.awt.*;
import java.util.Objects;

public class Obstacle extends CommonGameObject {

    public Obstacle(Rect background, Snake snake, int x, int y) {

        this.gameField = background;
        this.snake = snake;
        this.myPhysicalShape = new Rect(  (x * Constants.TILE_WIDTH) + background.getX(), ((y  * Constants.TILE_WIDTH)+ background.getY()), Constants.TILE_WIDTH, Constants.TILE_WIDTH);

    }

    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(myPhysicalShape.getX(), myPhysicalShape.getY(), myPhysicalShape.getWidth(), myPhysicalShape.getHeight());
    }

    public void update (double dt) {
        if (intersectingWithSnake()) {
            snake.die();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obstacle obstacle = (Obstacle) o;
        return myPhysicalShape.getX() == obstacle.myPhysicalShape.getX() && myPhysicalShape.getY() == obstacle.myPhysicalShape.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(myPhysicalShape.getX(), myPhysicalShape.getY());
    }
}