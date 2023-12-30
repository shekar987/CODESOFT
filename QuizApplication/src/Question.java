class Question {

    private String questionText;
    private String optionA, optionB, optionC, optionD;
    private char correctAnswer;

    // Constructor to initialize question details
    public Question(String questionText, String optionA, String optionB, String optionC, String optionD, char correctAnswer) {
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    // Getter methods to access question information
    public String getQuestionText() {
        return questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public char getCorrectAnswer() {
        return correctAnswer;
    }
}

