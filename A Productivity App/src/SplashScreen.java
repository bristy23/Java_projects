import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JFrame {
    public SplashScreen() {
        setTitle("MyToDoList");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);

        ImageIcon icon = new ImageIcon(getClass().getResource("/logo/icon.png"));
        JLabel logoLabel = new JLabel(icon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Hey, You productive people!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.DARK_GRAY);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        signUpButton.addActionListener(e -> {
            new SignupForm();
            dispose();
        });

        panel.add(Box.createVerticalStrut(50));
        panel.add(logoLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(welcomeLabel);
        panel.add(Box.createVerticalStrut(20));
        panel.add(loginButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(signUpButton);

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        new SplashScreen();
    }
}
