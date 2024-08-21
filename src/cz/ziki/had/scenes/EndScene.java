package cz.ziki.had.scenes;

import cz.ziki.had.*;
import cz.ziki.had.Window;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * EndScene is a class representing the end screen of the game where the final score and
 * high score are displayed.
 */
public class EndScene implements Scene {

    private final int lastScore;
    private int highScore;
    public final MouseL mouseListener;

    /**
     * Initializes the end scene with the given scores and mouse listener.
     *
     * @param score        The player's final score.
     * @param highestScore The current highest score.
     * @param mouseListener Listener for mouse events.
     */
    public EndScene(int score, int highestScore, MouseL mouseListener) {

        this.lastScore = score;
        this.highScore = highestScore;
        this.mouseListener = mouseListener;

    }

    /**
     * Draws the end screen with the player's score, high score, and a congratulatory message.
     *
     * @param g The Graphics object used for drawing.
     */
    @Override
    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        g.setColor(new Color(10, 220, 215));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        Font font = new Font("Tahoma", Font.BOLD, 40);
        FontMetrics metrics = g2.getFontMetrics(font);
        String scoreText = "Score: " + lastScore;
        g2.setFont(font);
        g2.setColor(Color.BLACK);
        g2.drawString(scoreText, Constants.SCREEN_WIDTH / 2 - (metrics.stringWidth(scoreText) / 2), Constants.SCREEN_HEIGHT - 500);

        String nicknameText = "Well played " + cz.ziki.had.Window.getWindow().nickname;
        g2.drawString(nicknameText, Constants.SCREEN_WIDTH / 2 - (metrics.stringWidth(nicknameText) / 2), Constants.SCREEN_HEIGHT - 600);

        Map<String, Integer> scores = FileUtils.loadPlayerScores();
        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(scores.entrySet());
        sortedScores.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        int yPosition = Constants.SCREEN_HEIGHT / 2 - 50;

        for (Map.Entry<String, Integer> entry : sortedScores) {

            String scoreEntry = entry.getKey().substring(20) + ": " + entry.getValue();
            g2.drawString(scoreEntry, Constants.SCREEN_WIDTH / 2 - (metrics.stringWidth(scoreEntry) / 2), yPosition);
            yPosition += 50;

        }

        font = new Font("Tahoma", Font.BOLD, 15);
        g2.setFont(font);
        g2.drawString("click anywhere to get back to menu", 270, Constants.SCREEN_HEIGHT - 50);

    }

    /**
     * Updates the high score if the last score is greater, and changes the state to the menu if the mouse is pressed.
     *
     * @param dt The delta time since the last update.
     */
    @Override
    public void update(double dt) {

        if (lastScore > highScore) {
            highScore = lastScore;
        }
        if (mouseListener.isPressed()) {
            Window.getWindow().changeState(0);
        }

    }
}
