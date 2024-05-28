import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NicknameDialog extends JDialog {

    private JTextField nicknameField;
    private String nickname;

    public NicknameDialog(Frame owner) {
        super(owner, "Enter Nickname", true);
        setLayout(new BorderLayout());
        setSize(300, 150);
        setLocationRelativeTo(owner);

        JLabel promptLabel = new JLabel("Please enter your nickname:");
        nicknameField = new JTextField(20);

        JPanel inputPanel = new JPanel();
        inputPanel.add(promptLabel);
        inputPanel.add(nicknameField);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateNickname()) {
                    nickname = nicknameField.getText();
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(NicknameDialog.this,
                            "Nickname must be up to 20 letters or numbers.",
                            "Invalid Nickname",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private boolean validateNickname() {
        String text = nicknameField.getText();
        return text != null && text.matches("[a-zA-Z0-9 ]{1,20}");
    }

    public static String showDialog(Frame owner) {
        NicknameDialog dialog = new NicknameDialog(owner);
        dialog.setVisible(true);
        return dialog.nickname;
    }
}