package cz.ziki.had.scenes;

import cz.ziki.had.*;
import cz.ziki.had.pawn.Obstacle;
import cz.ziki.had.pawn.food.Food;
import cz.ziki.had.pawn.food.FoodFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

public class EditScene extends CommonGameScene implements Scene {



    public EditScene(KeyL keyListener, MouseL mouseListener) {
        super(keyListener);
        this.mouseListener = mouseListener;
        mouseListener.registerOnClick(this::toggleOnClick);
    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
    }

    private void toggleOnClick(Point2D point2D) {
        if(keyListener.isKeyPressed(KeyEvent.VK_F)) {
            System.out.println("asdf");
            toggleFood(point2D);
        } else {
            toggleObstacle(point2D);
            System.out.println("fdas");
        }

    }
    private void toggleFood(Point2D point2D) {
        Food clickedFood = foodFactory.getFood(FoodFactory.FoodType.APPLE,
                foreground,
                null,
                getNearestTile(point2D.getX()-foreground.getX()) * Constants.TILE_WIDTH + foreground.getX(),
                getNearestTile(point2D.getY()-foreground.getY()) * Constants.TILE_WIDTH + foreground.getY());

        if (foods.contains(clickedFood)) {

            removeFood(clickedFood);
        } else {

            addFood(clickedFood);
        }
    }


    private void toggleObstacle(Point2D point2D) {
        Obstacle clickedObstacle = new Obstacle(foreground,null, getNearestTile(point2D.getX()-foreground.getX()) , getNearestTile(point2D.getY()-foreground.getY()));

        if (obstacles.contains(clickedObstacle)) {

            removeObstacle(clickedObstacle);
        } else {

            addObstacle(clickedObstacle);
        }
    }





}
