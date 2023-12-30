
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;
import java.util.function.BiConsumer;

// MenuPanel Class: Represents the GUI panel with buttons to display courses, register, drop courses for students.
public class MenuPanel extends JPanel {

// Attributes for CourseRegistrationSystem and JTable to display courses.
    private CourseRegistrationSystem system;
    private JTable courseTable;

    // Constructor to initialize the MenuPanel.
    public MenuPanel(CourseRegistrationSystem system) {
        this.system = system;
        initComponents();
    }

    // Method to initialize components like buttons, table, and set action listeners.
    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        courseTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(courseTable);
        scrollPane.setBackground(Color.BLACK);
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 20));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        JButton displayCoursesButton = createStyledButton("Display Available Courses");
        JButton registerCourseButton = createStyledButton("Register Student for Course");
        JButton dropCourseButton = createStyledButton("Drop Course for Student");
        JButton exitButton = createStyledButton("Exit");

        displayCoursesButton.addActionListener(e -> displayAvailableCourses());
        registerCourseButton.addActionListener(e -> performStudentCourseOperation("Registered", system::registerStudentForCourse));
        dropCourseButton.addActionListener(e -> performStudentCourseOperation("Drop", system::dropCourseForStudent));
        exitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(displayCoursesButton);
        buttonPanel.add(registerCourseButton);
        buttonPanel.add(dropCourseButton);
        buttonPanel.add(exitButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return button;
    }

    // Methods to display available courses, perform student course operations like registration and dropping.
    public void displayAvailableCourses() {
        Map<String, Course> courses = system.getCourses();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Course Code");
        model.addColumn("Title");
        model.addColumn("Available Slots");
        model.addColumn("Description");
        model.addColumn("Schedule");
        for (Course course : courses.values()) {
            int availableSlots = course.getCapacity() - course.getNumberOfRegisteredStudents();
            model.addRow(new Object[]{course.getCourseCode(), course.getTitle(), availableSlots, course.getDescription(), course.getSchedule()});
        }

        // First set the model
        courseTable.setModel(model);
        courseTable.setGridColor(Color.BLACK);
        courseTable.setBackground(ColorUIResource.orange);
        // Then adjust the column widths
        setColumnWidths(courseTable);
    }

    private void setColumnWidths(JTable table) {
        table.getColumnModel().getColumn(0).setPreferredWidth(100);  // Course Code
        table.getColumnModel().getColumn(1).setPreferredWidth(200);  // Title
        table.getColumnModel().getColumn(2).setPreferredWidth(100);  // Available Slots
        table.getColumnModel().getColumn(3).setPreferredWidth(300);  // Description
        table.getColumnModel().getColumn(4).setPreferredWidth(200);  // Schedule
    }

    private void performStudentCourseOperation(String actionType, BiConsumer<String, String> action) {
        String studentID = JOptionPane.showInputDialog(this, "Enter student ID:");
        String courseCode = JOptionPane.showInputDialog(this, "Enter course code:");

        if (studentID != null && courseCode != null) {

            DatabaseManager dbManager = system.getDbManager();
            Student student = dbManager.getStudentByID(studentID);
            Course course = dbManager.getCourseByCode(courseCode);

            if (student == null || course == null) {
                JOptionPane.showMessageDialog(this, "Invalid input!");
                return;
            }

            if (actionType.equals("Registered") && course.isStudentEnrolled(student)) {
                JOptionPane.showMessageDialog(this, "Student already enrolled in the course!");
                return;
            } else if (actionType.equals("Drop") && course.isStudentDropped(student)) {
                JOptionPane.showMessageDialog(this, "Student already dropped the course!");
                return;
            }

            action.accept(studentID, courseCode);
            JOptionPane.showMessageDialog(this, actionType + " successful!");
            displayAvailableCourses();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter valid inputs!");
        }
    }

}
