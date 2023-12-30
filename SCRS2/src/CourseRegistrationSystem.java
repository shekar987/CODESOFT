import java.util.Map;

public class CourseRegistrationSystem {
    private DatabaseManager dbManager;

    // DatabaseManager instance for database operations.
    public CourseRegistrationSystem() {
        this.dbManager = new DatabaseManager();
    }

    // Constructor to initialize the DatabaseManager.
    // Methods to fetch courses, students, register and drop courses
    public void fetchCourses() {
        dbManager.fetchDataFromDatabase("SELECT * FROM courses", "courses");
    }

    public void fetchStudents() {
        dbManager.fetchDataFromDatabase("SELECT * FROM students", "students");
    }
    public Map<String, Course> getCourses() {
        return dbManager.getCourses();
    }

    public void registerStudentForCourse(String studentID, String courseCode) {
        dbManager.registerStudentForCourse(studentID, courseCode);
    }

    public void dropCourseForStudent(String studentID, String courseCode) {
        dbManager.dropCourseForStudent(studentID, courseCode);
    }
    public DatabaseManager getDbManager() {
        return dbManager;
    }

}
