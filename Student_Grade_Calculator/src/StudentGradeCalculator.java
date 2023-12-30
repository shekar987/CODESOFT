
import java.util.Scanner;

/**
 * This class calculates the total marks, average percentage, and grade of a student based on their marks in different subjects.
 */
public class StudentGradeCalculator {

    //The main method that drives the program execution.
    public static void main(String[] args) {
        // Create a Scanner object to take user input
        Scanner scanner = new Scanner(System.in);

        // Get the number of subjects from the user
        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();

        // Create arrays to store subject names and marks
        String[] subjectNames = new String[numSubjects];
        int[] marks = new int[numSubjects];

        // Get subject names and marks from the user
        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter the name of subject " + (i + 1) + ": ");
            subjectNames[i] = scanner.next();
            System.out.print("Enter marks for " + subjectNames[i] + " (out of 100): ");
            marks[i] = scanner.nextInt();
        }


        // Calculate total marks, average percentage, and grade
        int totalMarks = calculateTotalMarks(marks);  // Call method to calculate total marks
        double averagePercentage = calculateAveragePercentage(totalMarks, numSubjects);  // Call method to calculate average percentage
        String grade = assignGrade(averagePercentage);  // Call method to assign grade

        // Display the results to the user
        displayResults(totalMarks, averagePercentage, grade);  // Call method to display results
    }

     //Calculates the total marks obtained by the student in all subjects.
    private static int calculateTotalMarks(int[] marks) {
        int total = 0;
        for (int mark : marks) {
            total += mark;  // Add each mark to the total
        }
        return total;
    }

    //Calculates the average percentage obtained by the student.
    private static double calculateAveragePercentage(int totalMarks, int numSubjects) {
        // Multiply by 100 for percentage and adjust for the maximum marks per subject
        return (double) totalMarks / (numSubjects * 100) * 100;
    }


     // Assigns a grade based on the average percentage.
    private static String assignGrade(double averagePercentage) {
        if (averagePercentage >= 85) {
            return "A";
        } else if (averagePercentage >= 70) {
            return "B";
        } else if (averagePercentage >= 55) {
            return "C";
        } else if (averagePercentage >= 40) {
            return "D";
        } else {
            return "F";
        }
    }

    // Displays the total marks, average percentage, and grade to the user.
    private static void displayResults(int totalMarks, double averagePercentage, String grade) {
        System.out.println("\nTotal Marks: " + totalMarks);
        System.out.println("Average Percentage: " + averagePercentage + "%");
        System.out.println("Grade: " + grade);
    }
}