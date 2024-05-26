import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndScene extends JFrame {

    public EndScene(int score, int highestScore) {
        setTitle("Game Over");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel scoreLabel = new JLabel("Score: " + score);
        JLabel highestScoreLabel = new JLabel("Highest Score: " + highestScore);
        JButton restartButton = new JButton("Restart Game");

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        panel.add(scoreLabel);
        panel.add(highestScoreLabel);
        panel.add(restartButton);

        getContentPane().add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
