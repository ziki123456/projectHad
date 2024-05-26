import java.awt.*;

public class EndScene extends Scene {

    private int lastScore;
    private int highScore;

    public EndScene(int score, int highestScore) {
        this.lastScore = score;
        this.highScore = highestScore;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        g.setColor(new Color(10, 220, 215));
        g.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        Font font = new Font("Arial", Font.BOLD, 40);
        FontMetrics metrics = g2.getFontMetrics(font);
        String scoreText = "Score: " + lastScore;
        String highScoreText = "High score: " + highScore;
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        g2.drawString(highScoreText, Constants.SCREEN_WIDTH / 2 - (metrics.stringWidth(highScoreText) / 2), Constants.SCREEN_HEIGHT - 50);
        g2.drawString(scoreText, Constants.SCREEN_WIDTH / 2 - (metrics.stringWidth(scoreText) / 2), Constants.SCREEN_HEIGHT - 150);

    }

    @Override
    public void update(double dt) {
        if (lastScore > highScore) {
            highScore = lastScore;
        }
    }
}
