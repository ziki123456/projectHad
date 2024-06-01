import java.awt.*;
import java.util.*;
import java.util.List;

public class EndScene extends Scene {

    private int lastScore;
    private int highScore;
    public KeyL keyListener;
    public MouseL mouseListener;

    public EndScene(int score, int highestScore, KeyL keyListener, MouseL mouseListener) {
        this.lastScore = score;
        this.highScore = highestScore;
        this.keyListener = keyListener;
        this.mouseListener = mouseListener;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        g.setColor(new Color(10, 220, 215));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        Font font = new Font("Tahoma", Font.BOLD, 40);
        FontMetrics metrics = g2.getFontMetrics(font);
        String scoreText = "Score: " + lastScore;
        String highScoreText = "High score: " + highScore;
        g2.setFont(font);
        g2.setColor(Color.BLACK);
        g2.drawString(scoreText, Constants.SCREEN_WIDTH / 2 - (metrics.stringWidth(scoreText) / 2), Constants.SCREEN_HEIGHT - 500);

        String nicknameText = "Well played " + Window.getWindow().nickname;
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
        g2.drawString("click anywhere to get back to menu",  270, Constants.SCREEN_HEIGHT - 50);

    }

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
