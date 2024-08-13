package cz.ziki.had.scenes;

import cz.ziki.had.*;
import cz.ziki.had.pawn.Obstacle;
import cz.ziki.had.pawn.food.Food;
import cz.ziki.had.pawn.food.FoodFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EditScene extends CommonGameScene implements Scene {

    protected final Set<MenuItem> editItems = Collections.synchronizedSet(new HashSet<>());



    public EditScene(KeyL keyListener, MouseL mouseListener) {
        super(keyListener);
        this.mouseListener = mouseListener;
        mouseListener.registerOnClick(this::toggleOnClick);

        try {

            BufferedImage spritesheet = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("snakeEditScene.png"));

            editItems.add(
                    new MenuItemBuilder()
                            .setSpriteSheet(spritesheet)
                            .setPassiveImage(0, 0, 179, 48)
                            .setActiveImage(0, 48, 179, 48)
                            .setMyPhysicalShape(new Rect(28, 600, 179, 48))
                            .setAction( () -> cz.ziki.had.Window.getWindow().changeState(1))
                            .build()
            );

            editItems.add(
                    new MenuItemBuilder()
                            .setSpriteSheet(spritesheet)
                            .setPassiveImage(0, 96, 179, 48)
                            .setActiveImage(0, 144, 179, 48)
                            .setMyPhysicalShape(new Rect(330, 600, 179, 48))
                            .setAction(()->{})
                            .build()

            );

            editItems.add(
                    new MenuItemBuilder()
                            .setSpriteSheet(spritesheet)
                            .setPassiveImage(0, 192, 179, 48)
                            .setActiveImage(0, 240, 179, 48)
                            .setMyPhysicalShape(new Rect(600, 600, 179, 48))
                            .setAction( () -> cz.ziki.had.Window.getWindow().close())
                            .build()

            );


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        editItems.forEach( item -> item.draw(g) );
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
