package cz.ziki.had.FoodObjects;


import cz.ziki.had.Constants;
import cz.ziki.had.Rect;
import cz.ziki.had.Snake;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CommonFood {
    public Rect background;
    public Snake snake;
    public int width, height;
    public Color color;
    public Rect rect;
    public BufferedImage foodImage;

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

            //Vyrobit nahody cislo mezi 0 a maximalnim poctem horizontalnich dlazdic
            int tileCountX = (int) background.width / Constants.TILE_WIDTH;
            int randomTileX = (int) (Math.random() * (tileCountX -1));

            int tileCountY = (int) background.height / Constants.TILE_WIDTH;
            int randomTileY = (int) (Math.random() * (tileCountY -1));

            this.rect.x = (int) (randomTileX * Constants.TILE_WIDTH + background.x);
            this.rect.y = (int) (randomTileY * Constants.TILE_WIDTH + background.y);;
        } while (snake.intersectingWithRect(this.rect));

        this.isSpawned = true;

    }

    public void drawIfNotEaten(Graphics2D g2) {
        if (!snake.shouldGrow) g2.drawImage(this.foodImage, (int) this.rect.x + xPadding, (int) this.rect.y, null);
    }

    public void checkIfNotEaten() {

        if (snake.intersectingWithRect(this.rect)) {

            snake.grow();
            this.rect.x = -100;
            this.rect.y = -100;
            isSpawned = false;

        }
    }
}
