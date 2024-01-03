import java.sql.*;
import java.util.HashMap;
import java.util.Map;

// DatabaseManager Class: Manages database operations like fetching data, registering, and dropping students for courses.
public class DatabaseManager {
    // Database connection details.
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/course_registration_system";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Password";

    // HashMaps to store courses and students fetched from the database.
    public Map<String, Course> courses = new HashMap<>();
    public Map<String, Student> students = new HashMap<>();

    // Methods to fetch data from database, process result sets, register and drop students for courses.
    public void fetchDataFromDatabase(String query, String type) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            switch (type) {
                case "courses":
                    processCourseResultSet(resultSet);
                    break;
                case "students":
                    processStudentResultSet(resultSet);
                    break;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error fetching " + type + " from database.");
        }
    }

    private void processCourseResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Course course = new Course(
                    resultSet.getString("courseCode"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getInt("capacity"),
                    resultSet.getString("schedule")
            );
            courses.put(course.getCourseCode(), course);
        }
    }

    private void processStudentResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Student student = new Student(
                    resultSet.getString("studentID"),
                    resultSet.getString("name")
            );
            students.put(student.getStudentID(), student);
            //System.out.println("Added student: " + student.getStudentID());  // Logging
        }
    }


    public Map<String, Course> getCourses() {
        return courses;
    }

    public Student getStudentByID(String id) {
        return students.get(id);
    }

    public Course getCourseByCode(String code) {
        return courses.get(code);
    }

    public void registerStudentForCourse(String studentID, String courseCode) {
        Student student = students.get(studentID);
        Course course = courses.get(courseCode);

        if (student != null&& course != null) {
            if (course.registerStudent(student)) {
                updateCourseInDatabase(course);
                System.out.println("Registration successful!");
            } else {
                System.out.println("Registration failed. Student already enrolled in the course.");
            }
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }

    public void dropCourseForStudent(String studentID, String courseCode) {
        Student student = students.get(studentID);
        Course course = courses.get(courseCode);

        if (student != null && course != null) {
            if (course.removeStudent(student)) {
                updateCourseInDatabase(course);  // Update the course in the database after registration
                System.out.println("Course dropped successfully!");
            } else {
                System.out.println("Course drop failed. Student not enrolled in the course.");
            }
        } else {
            System.out.println("Invalid student ID or course code.");
        }
    }
    private void updateCourseInDatabase(Course course) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            connection.setAutoCommit(false);  // Set autocommit to false

            String updateQuery = "UPDATE courses SET capacity = ? WHERE courseCode = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setInt(1, course.getCapacity() - course.getNumberOfRegisteredStudents());
                preparedStatement.setString(2, course.getCourseCode());
                preparedStatement.executeUpdate();
            }

            connection.commit();  // Commit the transaction
            connection.setAutoCommit(true);  // Reset autocommit to true
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating course capacity in database.");
        }
    }

}
