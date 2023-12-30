import java.util.HashSet;
// Student class: Represents a student with a unique ID, name, and a set of registered courses.
public class Student {
    // Attributes for student details.
    private String studentID;
    private String name;
    private HashSet<Course> registeredCourses = new HashSet<>();

    // Constructor to initialize student details.
    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
    }

    // Getter methods for student details.
    // Methods to register and drop courses.
    public String getStudentID() {
        return studentID;
    }
    public boolean registerCourse(Course course) {
        return registeredCourses.add(course);
    }

    public boolean dropCourse(Course course) {
        return registeredCourses.remove(course);
    }

}
