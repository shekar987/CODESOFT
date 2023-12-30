import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static java.awt.Color.LIGHT_GRAY;

public class QuizApp {
    private List<Question> questions = new ArrayList<>();  // List to hold the questions for the quiz
    private int currentQuestionIndex = 0;  // Index to keep track of the current question being displayed
    private int score = 0;  // Variable to store the score of the user
    private int timeLimitPerQuestion = 10;  // Time limit for each question in seconds
    private int remainingTime = timeLimitPerQuestion; // Remaining time for the current question

    // Components for the UI
    private JFrame window;
    private JLabel questionLabel;
    private JLabel timeLabel;
    private JLabel scoreLabel;
    private JRadioButton[] options = new JRadioButton[4];
    private ButtonGroup optionGroup = new ButtonGroup();
    private JButton submitButton = new JButton("Submit");

    private Timer timer;
    // Entry point for the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new QuizApp().startQuiz());
    }
    // Constructor to initialize questions and UI
    public QuizApp() {
        initializeQuestions();
        initializeUI();
    }
    // Initialize the list of questions for the quiz
    private void initializeQuestions() {
        questions.add(new Question("What is the capital of France?", "Berlin", "London", "Paris", "Rome", 'C'));
        questions.add(new Question("What is the highest mountain in the world?", "Mount Everest", "K2", "Mount Kilimanjaro", "Mount Fuji", 'A'));
        questions.add(new Question("What is the largest planet in our solar system?", "Neptune", "Mars", "Saturn", "Jupiter", 'D'));
        questions.add(new Question("Who wrote the play 'Romeo and Juliet'?", "William Shakespeare", "Charles Dickens", "Jane Austen", "Mark Twain", 'A'));
        questions.add(new Question("Which element has the chemical symbol 'Au'?", "Silver", "Gold", "Iron", "Aluminum", 'B'));
        questions.add(new Question("What is the currency of Japan?", "Yen", "Won", "Dollar", "Pound", 'A'));
        questions.add(new Question("Who painted the Mona Lisa?", "Leonardo da Vinci", "Vincent van Gogh", "Pablo Picasso", "Michelangelo", 'A'));
    }
    // Initialize the graphical user interface
    private void initializeUI() {
        window = new JFrame("Quiz Application"); // Setting up the main window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500, 300);

        JPanel mainPanel = new JPanel(new BorderLayout());

        questionLabel = new JLabel();
        mainPanel.add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(0, 1));
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            optionsPanel.add(options[i]);
            optionGroup.add(options[i]);
        }
        mainPanel.add(optionsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        timeLabel = new JLabel("Time: " + remainingTime);
        bottomPanel.add(timeLabel);
        bottomPanel.add(submitButton);
        scoreLabel = new JLabel("Score: " + score);
        bottomPanel.add(scoreLabel);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        submitButton.addActionListener(e -> handleAnswerSubmission());
        for (JRadioButton option : options) {
            option.addActionListener(e -> trackSelectedOption());
        }

        window.add(mainPanel);
        window.setVisible(true);
    }
    // Start the quiz by displaying the first question and starting the timer
    private void startQuiz() {
        displayQuestion();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> updateRemainingTime());
            }
        }, 0, 1000);
    }
    // Display a question on the UI
    private void displayQuestion() {
        optionGroup.clearSelection();
        Question question = questions.get(currentQuestionIndex);
        questionLabel.setText(question.getQuestionText());
        options[0].setText(question.getOptionA());
        options[1].setText(question.getOptionB());
        options[2].setText(question.getOptionC());
        options[3].setText(question.getOptionD());
        for (int i = 0; i < 4; i++) {
            options[i].setSelected(false);
            options[i].setBackground(null);
        }
        remainingTime = timeLimitPerQuestion;
        timeLabel.setText("Time: " + remainingTime);
    }
    private boolean resultsShown = false;
    // Update the remaining time for the current question
    private void updateRemainingTime() {
        remainingTime--;
        timeLabel.setText("Time: " + remainingTime);
        if (remainingTime == 0 && !resultsShown) { // Check if results are already shown
            handleEndOfQuiz();
        }
    }
    // Handle the end of the quiz
    private void handleEndOfQuiz() {
        if (!resultsShown) {  // Check if results are already shown
            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                displayQuestion();
            } else {
                //showResults();
                resultsShown = true;  // Set the flag to true after displaying results.
            }
        }
    }
    // Handle submission of an answer
    private void handleAnswerSubmission() {
        // Check if currentQuestionIndex is less than the size of the questions list
        if (currentQuestionIndex < questions.size()) {
            char selectedAnswer = getSelectedOption(currentQuestionIndex);
            if (selectedAnswer == questions.get(currentQuestionIndex).getCorrectAnswer()) {
                score++;
                scoreLabel.setText("Score: " + score);
            }

            currentQuestionIndex++;
            if (currentQuestionIndex < questions.size()) {
                displayQuestion();
            } else {
                showResults();
                resultsShown = true;
            }
        }
    }
    // Get the selected option for a given question index
    private char getSelectedOption(int index) {  // Changed here
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                return (char) ('A' + i);
            }
        }
        return ' ';
    }
    // Track the selected option visually
    private void trackSelectedOption() {
        for (JRadioButton option : options) {
            if (option.isSelected()) {
                option.setBackground(LIGHT_GRAY);
            } else {
                option.setBackground(null);
            }
        }
    }
    // Show the results of the quiz
    private void showResults() {
        StringBuilder resultsSummary = new StringBuilder("Results:\n");
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            char selectedAnswer = getSelectedOption(i);
            resultsSummary.append(String.format("%d. %s - %s\n", i + 1, question.getQuestionText(),
                    selectedAnswer == question.getCorrectAnswer() ? "Correct" : "Incorrect"));  // This line remains unchanged
        }
        JOptionPane.showMessageDialog(window, resultsSummary.toString() + "\nYour final score is: " + score + " out of " + questions.size());
        int choice = JOptionPane.showConfirmDialog(window, "Would you like to play again?", "Play Again?", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            resetQuiz();
        } else {
            window.dispose();
        }
    }
    // Reset the quiz to its initial state
    private void resetQuiz() {
        currentQuestionIndex = 0;
        score = 0;
        remainingTime = timeLimitPerQuestion;
        scoreLabel.setText("Score: " + score);
        timeLabel.setText("Time: " + remainingTime);
        resultsShown = false;  // Ensure that the flag is reset if you allow replaying the quiz.
        displayQuestion();
    }
}
