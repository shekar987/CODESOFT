
import java.util.Random;
import java.util.Scanner;

public class Guess_number_game {

    // Constant to define the maximum number of attempts allowed per round.
    private static final int MAX_ATTEMPTS = 8;

    // Scanner object for user input and Random object for generating random numbers.
    private final Scanner scanner;
    private final Random random;

    // Constructor to initialize the scanner and random objects.
    public Guess_number_game() {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }

    // Main method to start the game by creating an instance of the Guess_number_game class.
    public static void main(String[] args) {
        Guess_number_game game = new Guess_number_game();
        game.start_game();
    }

    // Method to start the game.
    private void start_game() {
        int no_of_roundsWon = 0; // Variable to keep track of rounds won.

        // Loop to allow multiple rounds of the game.
        do {
            boolean hasWon = playRound(); // Play a single round and check if the user won.
            if (hasWon) {
                no_of_roundsWon++; // Increment the number of rounds won if the user guessed correctly.
            }
            System.out.print("Do you want to play again? (yes/no): "); // Prompt to play again.
        } while (scanner.next().equalsIgnoreCase("yes")); // Continue playing rounds if the user inputs 'yes'.

        // Display the total number of rounds won when the user decides to stop playing.
        System.out.println("You won " + no_of_roundsWon + " round(s)!");
        scanner.close(); // Close the scanner to free resources.
    }

    // Method to play a single round of the game.
    private boolean playRound() {
        int numberToGuess = random.nextInt(100) + 1; // Generate a random number between 1 and 100 as the target number.

        // Loop to manage the maximum number of attempts for guessing.
        for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
            System.out.print("Guess the number between 1 and 100: "); // Prompt the user to make a guess.
            int userGuess = scanner.nextInt(); // Read the user's guess.

            // Compare the user's guess with the target number and provide feedback.
            if (userGuess == numberToGuess) {
                System.out.println("Congratulations! You guessed the number " + numberToGuess + " correctly!");
                return true; // Return true if the user guessed correctly.
            } else if (userGuess < numberToGuess) {
                System.out.println("Too low!"); // Inform the user that the guess is too low.
            } else {
                System.out.println("Too high!"); // Inform the user that the guess is too high.
            }

            // Display the remaining attempts for the user.
            System.out.println("You have " + (MAX_ATTEMPTS - attempts - 1) + " attempt(s) left.");
        }

        // Inform the user that they've reached the maximum number of attempts and reveal the correct number.
        System.out.println("Sorry, you've reached the maximum number of attempts. The number was " + numberToGuess + ".");
        return false; // Return false since the user couldn't guess the number within the attempts.
    }
}
