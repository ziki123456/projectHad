package cz.ziki.had;

import javax.swing.*;
import java.awt.*;

/**
 * A dialog for entering a nickname.
 */
public class NicknameDialog extends JDialog {

    private final JTextField nicknameField;
    private String nickname;

    /**
     * Constructs a new NicknameDialog.
     *
     * @param owner the owner frame of the dialog
     */
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

        JPanel buttonPanel = getOkButton();

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel getOkButton() {
        JButton okButton = new JButton("OK");
        okButton.addActionListener((i) -> {
            if (validateNickname()) {
                nickname = nicknameField.getText();
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(NicknameDialog.this,
                        "Nickname must be up to 20 letters or numbers.",
                        "Invalid Nickname",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        return buttonPanel;
    }

    /**
     * Validates the entered nickname.
     *
     * @return true if the nickname is valid, false otherwise
     */
    private boolean validateNickname() {

        String text = nicknameField.getText();
        return text != null && text.matches("[a-zA-Z0-9 ]{1,20}");

    }

    /**
     * Displays the nickname dialog and returns the entered nickname.
     *
     * @param owner the owner frame of the dialog
     * @return the entered nickname
     */
    public static String showDialog(Frame owner) {

        NicknameDialog dialog = new NicknameDialog(owner);
        dialog.setVisible(true);
        return dialog.nickname;

    }
}