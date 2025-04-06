import com.toedter.calendar.JDateChooser;  // Import JCalendar
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyToDoList extends JFrame {
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskField;
    private JDateChooser dateChooser; // Calendar component

    public MyToDoList() {
        setTitle("MyToDoList");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        // Setting colors
        Color babyPink = new Color(255, 182, 193);  // Baby pink background
        Color softBlue = new Color(173, 216, 230);  // Soft blue buttons
        Color taskBoxColor = new Color(240, 230, 255); // Soft lavender task box

        getContentPane().setBackground(babyPink);

        // Top panel with title
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel("All Lists", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Task List Model
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setFont(new Font("Arial", Font.BOLD, 16));
        taskList.setBackground(taskBoxColor); // Adding color to the task list box

        JScrollPane scrollPane = new JScrollPane(taskList);

        // Bottom panel with input field and buttons
        JPanel bottomPanel = new JPanel(new GridLayout(4, 1, 5, 5)); // 4 Rows for Add, Delete, Date Picker & Mark Done
        bottomPanel.setBackground(babyPink);

        // Panel for adding tasks
        JPanel addTaskPanel = new JPanel(new BorderLayout());
        taskField = new JTextField("Enter Quick Task Here");
        JButton addButton = new JButton("+");
        addButton.setFont(new Font("Arial", Font.BOLD, 22));
        addButton.setBackground(softBlue); // Soft blue button
        addTaskPanel.add(taskField, BorderLayout.CENTER);
        addTaskPanel.add(addButton, BorderLayout.EAST);

        // Panel for Date Picker
        JPanel datePanel = new JPanel();
        datePanel.setBackground(babyPink);
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd"); // Set date format
        datePanel.add(new JLabel("Due Date:"));
        datePanel.add(dateChooser, BorderLayout.CENTER);

        // Panel for task actions (Delete Task & Mark as Done)
        JPanel taskActionsPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        taskActionsPanel.setBackground(babyPink);
        JButton deleteButton = new JButton("Delete Task");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(softBlue);
        JButton markDoneButton = new JButton("Mark as Done");
        markDoneButton.setFont(new Font("Arial", Font.BOLD, 14));
        markDoneButton.setBackground(softBlue);
        taskActionsPanel.add(deleteButton);
        taskActionsPanel.add(markDoneButton);

        // Add panels to the bottom section
        bottomPanel.add(datePanel);  // Date Picker panel
        bottomPanel.add(addTaskPanel);  // First row: Add Task
        bottomPanel.add(taskActionsPanel); // Second row: Delete & Mark Done

        // Add ActionListener for Add Task
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String task = taskField.getText().trim();
                if (!task.isEmpty()) {
                    String dueDate = ((JTextField) dateChooser.getDateEditor().getUiComponent()).getText();
                    taskListModel.addElement(task + " (Due: " + dueDate + ")"); // Add task with due date
                    taskField.setText(""); // Clear input field
                }
            }
        });

        // Add ActionListener for Delete Task
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex(); // Get selected task
                if (selectedIndex != -1) {
                    taskListModel.remove(selectedIndex); // Delete selected task
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a task to delete.");
                }
            }
        });

        // Add ActionListener for Mark as Done
        markDoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex(); // Get selected task
                if (selectedIndex != -1) {
                    String task = taskListModel.getElementAt(selectedIndex);
                    if (!task.startsWith("✅")) { // Prevent re-marking
                        taskListModel.set(selectedIndex, "✅ " + task);
                        JOptionPane.showMessageDialog(null, "Great job! Task completed 🎉");
                    } else {
                        JOptionPane.showMessageDialog(null, "This task is already marked as done.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a task to mark as done.");
                }
            }
        });

        // Layout settings
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);  // Keep Add and Delete buttons at the bottom
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MyToDoList().setVisible(true));
    }
}
