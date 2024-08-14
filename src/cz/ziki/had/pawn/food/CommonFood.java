package cz.ziki.had.pawn.food;


import cz.ziki.had.pawn.CommonGameObject;
import cz.ziki.had.Constants;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;

public abstract class CommonFood extends CommonGameObject implements Serializable {


    public int xPadding;

    private boolean isSpawned;

    /**
     * Spawns the food item at a random position on the game background.
     */
    public void randomSpawn() {

        do {

            int tileCountX = gameField.getWidth() / Constants.TILE_WIDTH;
            int randomTileX = (int) (Math.random() * (tileCountX -1));

            int tileCountY = gameField.getHeight() / Constants.TILE_WIDTH;
            int randomTileY = (int) (Math.random() * (tileCountY -1));

            this.myPhysicalShape.setX(randomTileX * Constants.TILE_WIDTH + gameField.getX());
            this.myPhysicalShape.setY(randomTileY * Constants.TILE_WIDTH + gameField.getY());
        } while (snake.intersectingWithRect(this.myPhysicalShape));

        this.isSpawned = true;

    }

    public void drawIfNotEaten(Graphics2D g2) {
        if (snake == null || !snake.shouldGrow) g2.drawImage(this.foodImage, this.myPhysicalShape.getX() + xPadding, this.myPhysicalShape.getY(), null);
    }

    public void checkIfNotEaten() {

        if (intersectingWithSnake()) {

            snake.grow();
            this.myPhysicalShape.setX(-100);
            this.myPhysicalShape.setY(-100);
            isSpawned = false;

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommonFood food = (CommonFood) o;
        return myPhysicalShape.getX() == food.myPhysicalShape.getX() && myPhysicalShape.getY() == food.myPhysicalShape.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(myPhysicalShape.getX(), myPhysicalShape.getY());
    }

    public boolean isSpawned() {
        return isSpawned;
    }

}
