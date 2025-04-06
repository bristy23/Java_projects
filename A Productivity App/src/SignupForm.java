import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SignupForm extends JFrame {
    private JTextField fullnameField, usernameField, phoneField, emailField, dobField;
    private JPasswordField passwordField;
    private JButton signUpButton;

    public SignupForm() {
        setTitle("Sign Up");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels and Fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1;
        fullnameField = new JTextField(15);
        add(fullnameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Phone Number:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(15);
        add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(15);
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Date of Birth (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        dobField = new JTextField(15);
        add(dobField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        // Sign Up Button
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        signUpButton = new JButton("Sign Up");
        add(signUpButton, gbc);

        // Sign Up Button Action
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signUpUser();
            }
        });

        setVisible(true);
    }

    private void signUpUser() {
        String fullname = fullnameField.getText();
        String username = usernameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String dob = dobField.getText();
        String password = new String(passwordField.getPassword());

        if (fullname.isEmpty() || username.isEmpty() || phone.isEmpty() || email.isEmpty() || dob.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "INSERT INTO users (fullname, username, phone_number, email, dob, password_hash) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, fullname);
            pstmt.setString(2, username);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            pstmt.setString(5, dob);
            pstmt.setString(6, password); // Use proper hashing in real projects

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                java.sql.ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if(generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    //Insert
                    String sqlSignup = "INSERT INTO signup_info(user_id) VALUES(?)";
                    PreparedStatement signupStmt = conn.prepareStatement(sqlSignup);
                    signupStmt.setInt(1, userId);
                    signupStmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Sign Up Successful!");
                    this.dispose();  // Close the sign-up form after success
                    new MyToDoList().setVisible(true);//open the to do list
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new SignupForm();
    }
}
