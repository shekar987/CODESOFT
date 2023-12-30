import javax.swing.*;
//StudentCourseRegistrationApp  class: This is the main class where the Swing GUI application starts execution.
public class StudentCourseRegistrationApp {
    public static void main(String[] args) {
        //SwingUtilities.invokeLater ensures that GUI-related tasks are processed in the event dispatch thread.
        SwingUtilities.invokeLater(() -> {

            // Initialize the course registration system.
            CourseRegistrationSystem system = new CourseRegistrationSystem();

            // Fetch courses and students from the database.
            system.fetchCourses();
            system.fetchStudents();

            // Set up the main JFrame window for the application.
            JFrame frame = new JFrame("Course Registration System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Create a menu panel and add it to the JFrame.
            MenuPanel menuPanel = new MenuPanel(system);
            frame.getContentPane().add(menuPanel);

            // Display the JFrame.
            frame.setVisible(true);
        });
    }
}


