// Course Class: Represents a course with details such as course code, title, capacity, etc.
import java.util.HashSet;
public class Course {
    // Attributes for course details.
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    public HashSet<Student> registeredStudents = new HashSet<>();

    // Constructor to initialize course details.
    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    // Getter and setter methods for course attributes.
    // Methods to register, drop students and check if course is full.
    public String getDescription() {
        return description;
    }
    public String getCourseCode() {
        return courseCode;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getTitle() {
        return title;
    }

    public String getSchedule() {
        return schedule;
    }


    public boolean registerStudent(Student student) {
        if (isFull()) {
            return false; // Course is full
        }
        registeredStudents.add(student);
        student.registerCourse(this);
        return true;
    }
    public int getNumberOfRegisteredStudents() {
        return registeredStudents.size();
    }
    public boolean removeStudent(Student student) {
        if (!registeredStudents.contains(student)) {
            return false; // Student not enrolled
        }
        registeredStudents.remove(student);
        student.dropCourse(this);
        return true;
    }

    public boolean isFull() {
        return registeredStudents.size() >= capacity;
    }
    public boolean isStudentEnrolled(Student student) {
        return registeredStudents.contains(student);
    }

    public boolean isStudentDropped(Student student) {
        return !registeredStudents.contains(student);
    }
}