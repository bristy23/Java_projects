import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPasswordForm extends JFrame {
    private JTextField phoneField;
    private JPasswordField newPasswordField;
    private JButton resetButton;

    public ForgotPasswordForm() {
        setTitle("Reset Password");
        setSize(400, 250); // Increased size for better visibility
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout()); // Center alignment
        setLocationRelativeTo(null); // Center the window

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        add(new JLabel("Phone Number:"), gbc);
        phoneField = new JTextField(15);
        gbc.gridx = 1;
        add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("New Password:"), gbc);
        newPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(newPasswordField, gbc);

        resetButton = new JButton("Reset Password");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(resetButton, gbc);

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetPassword();
            }
        });

        setVisible(true);
    }

    private void resetPassword() {
        String phoneNumber = phoneField.getText();
        String newPassword = new String(newPasswordField.getPassword());

        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "UPDATE users SET password_hash = ? WHERE phone_number = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newPassword);
            pstmt.setString(2, phoneNumber);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Password Reset Successful!");
                dispose();
                new LoginForm(); // Redirect to login after resetting password
            } else {
                JOptionPane.showMessageDialog(this, "Phone Number not found!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error resetting password: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new ForgotPasswordForm();
    }
}
