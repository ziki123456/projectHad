package cz.ziki.had;

import cz.ziki.had.pawn.GameObject;
import cz.ziki.had.pawn.Obstacle;
import cz.ziki.had.pawn.food.Food;
import cz.ziki.had.scenes.*;

import javax.swing.JFrame;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;

/**
 * Represents the main window of the application.
 * Extends JFrame and implements Runnable for running the game loop.
 */
public class Window extends JFrame implements Runnable {

    public int lastScore = 0;
    public final int bestScore = 0;

    public static Window window = null;
    public boolean isRunning;

    public int currentState;
    public Scene currentScene;

    public final KeyL keyListener = new KeyL();
    public final MouseL mouseListener = new MouseL();

    private static final double DELTA_WANTED = 0.02167;

    public String nickname;

    /**
     * Constructs the game window.
     *
     * @param width  The width of the window.
     * @param height The height of the window.
     * @param title  The title of the window.
     */
    public Window(int width, int height, String title) {

        setSize(width, height);
        setTitle(title);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(keyListener);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        isRunning = true;
        changeState(0);

    }

    /**
     * Gets the singleton instance of the Window class.
     *
     * @return The Window instance.
     */
    public static Window getWindow() {

        if (Window.window == null) {
            Window.window = new Window(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, Constants.SCREEN_TITLE);
        }

        return Window.window;
    }

    public void close() {
        isRunning = false;
    }

    public void changeState(int newState) {
        changeState(newState,null,null,null);
    }
    /**
     * Changes the state of the game to the specified state.
     *
     * @param newState The new state of the game.
     */
    public void changeState(int newState, Set<GameObject> gameObjects, Set<Food> foods, Set<Obstacle> obstacles) {

        currentState = newState;

        switch (currentState) {

            case 0:
                currentScene = new MenuScene(mouseListener);
                break;
            case 1:

                if (gameObjects != null && foods != null && obstacles != null) {
                    currentScene = new GameScene(keyListener,gameObjects,foods,obstacles);
                }else{
                    currentScene = new GameScene(keyListener);
                }
                break;
            case 2:
                currentScene = new EndScene(this.lastScore, this.bestScore, mouseListener);
                break;
            case 3:
                currentScene = new EditScene(keyListener, mouseListener);
                break;
            default:
                System.out.println("Unknown scene.");
                currentScene = null;
                break;

        }
    }

    /**
     * Updates the game logic.
     *
     * @param dt The time elapsed since the last frame.
     */
    public void update(double dt) {

        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        getGraphics().drawImage(dbImage, 0, 0, this);
        currentScene.update(dt);

    }

    /**
     * Draws the current scene.
     *
     * @param g The graphics context.
     */
    public void draw(Graphics g) {
        currentScene.draw(g);
    }

    /**
     * Runs the game loop.
     * It updates the game logic and redraws the scene at a fixed time step.
     */
    @Override
    public void run() {

        this.nickname = NicknameDialog.showDialog(this);
        Instant lastFrameTime = Instant.now();
        try {
            while (isRunning) {
                Instant time = Instant.now();
                //number of seconds between frames unrounded(0,00001)
                double deltaTime = Duration.between(lastFrameTime, time).toNanos() * 10E-10;
                lastFrameTime = Instant.now();

                update(DELTA_WANTED);

                long msToSleep = (long) ((DELTA_WANTED - deltaTime) * 1000);
                if (msToSleep > 0) {
                    Thread.sleep(msToSleep);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dispose();
    }
}