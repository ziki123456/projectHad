package cz.ziki.had.FoodObjects;


import cz.ziki.had.CommonGameObject;
import cz.ziki.had.Constants;

import java.awt.*;

public class CommonFood extends CommonGameObject {


    public int xPadding;

    private boolean isSpawned;

    public boolean isSpawned() {
        return isSpawned;
    }

    public void setSpawned(boolean spawned) {
        isSpawned = spawned;
    }

    /**
     * Spawns the food item at a random position on the game background.
     */
    public void randomSpawn() {

        do {

            int tileCountX = (int) gameField.getWidth() / Constants.TILE_WIDTH;
            int randomTileX = (int) (Math.random() * (tileCountX -1));

            int tileCountY = (int) gameField.getHeight() / Constants.TILE_WIDTH;
            int randomTileY = (int) (Math.random() * (tileCountY -1));

            this.myPhysicalShape.setX((int) (randomTileX * Constants.TILE_WIDTH + gameField.getX()));
            this.myPhysicalShape.setY((int) (randomTileY * Constants.TILE_WIDTH + gameField.getY()));;
        } while (snake.intersectingWithRect(this.myPhysicalShape));

        this.isSpawned = true;

    }

    public void drawIfNotEaten(Graphics2D g2) {
        if (!snake.shouldGrow) g2.drawImage(this.foodImage, (int) this.myPhysicalShape.getX() + xPadding, (int) this.myPhysicalShape.getY(), null);
    }

    public void checkIfNotEaten() {

        if (snake.intersectingWithRect(this.myPhysicalShape)) {

            snake.grow();
            this.myPhysicalShape.setX(-100);
            this.myPhysicalShape.setY(-100);
            isSpawned = false;

        }
    }
}
