package cz.ziki.had;

import java.awt.*;

public class Obstacle extends CommonGameObject{

    public Obstacle(Rect background, Snake snake, int x, int y) {

        this.gameField = background;

        this.snake = snake;
        this.myPhysicalShape = new Rect(  (x * Constants.TILE_WIDTH) + background.getX(), ((y  * Constants.TILE_WIDTH)+ background.getY()), Constants.TILE_WIDTH, Constants.TILE_WIDTH);

    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) myPhysicalShape.getX(), (int) myPhysicalShape.getY(), myPhysicalShape.getWidth(), myPhysicalShape.getHeight());
    }

}

