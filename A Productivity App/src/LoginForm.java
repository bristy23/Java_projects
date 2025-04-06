import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton, forgotPasswordButton;

    public LoginForm() {
        setTitle("Login");
        setSize(400, 300);  // Updated size for better layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Padding around elements
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 5, 5));

        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");
        forgotPasswordButton = new JButton("Forgot Password?");

        buttonPanel.add(loginButton);
        buttonPanel.add(signupButton);
        buttonPanel.add(forgotPasswordButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        gbc.gridy = 3;
        add(signupButton, gbc);

        gbc.gridy = 4;
        add(forgotPasswordButton, gbc);

        loginButton.addActionListener(e -> loginUser());

        signupButton.addActionListener(e -> {
            dispose();
            new SignupForm();
        });

        forgotPasswordButton.addActionListener(e -> new ForgotPasswordForm());

        setVisible(true);
    }

    private void loginUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DatabaseConnection.connect()) {
            System.out.println("Connected to: "+conn.getCatalog());

            // Step 1: Authenticate the user
            String sql = "SELECT * FROM users WHERE username=? AND password_hash=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Step 2: If login is successful, insert login info
                int userId = rs.getInt("user_id"); // Get the user_id of the logged-in user

                // Debugging output to check userId
                System.out.println("User ID: " + userId);

                String loginSql = "INSERT INTO login_info (user_id) VALUES (?)";
                PreparedStatement loginStmt = conn.prepareStatement(loginSql);
                loginStmt.setInt(1, userId);

                System.out.println("Executing query: " + loginStmt.toString());
                int rowsAffected = loginStmt.executeUpdate();
                //conn.commit();
                System.out.println("Login Info Insert Rows: " + rowsAffected); // Debugging

                //System.out.println("Rows affected: " + rowsAffected);

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Login Successful!");
                    dispose();
                    SwingUtilities.invokeLater(MyToDoList::new);
                    //new MyToDoList();
                } else {
                    JOptionPane.showMessageDialog(this, "Login info not recorded. Try again.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();  // Print the full error stack trace to help debug
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("Error code: " + ex.getErrorCode());
            JOptionPane.showMessageDialog(this, "Login Failed: " + ex.getMessage());
        }
    }
    public static void main(String[] args) {
        new LoginForm();
    }

}
