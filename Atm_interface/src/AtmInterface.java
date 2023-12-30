import java.util.Scanner;

public class AtmInterface {

    // Create a static bank account with an initial balance of 500
    static UserBankAccount bankAccount = new UserBankAccount(500);

    // Create a static Scanner object for user input
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Main loop that keeps the ATM running until the user exits
        while (true) {

            // Display the ATM menu options
            displayMenu();

            // Get the user's choice
            int input = sc.nextInt();

            // Handle the user's choice based on the selected option
            switch (input) {
                case 1:
                    performWithdrawal();
                    break;
                case 2:
                    performDeposit();
                    break;
                case 3:
                    checkBalance();
                    break;
                case 4:
                    System.out.println("Exiting ATM. Thank you!");
                    System.exit(0); // Exit the program
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Display the ATM menu options to the user
    public static void displayMenu() {
        System.out.println(" 1. Withdraw\n 2. Deposit\n 3. Check Balance\n 4. Exit");
        System.out.println("-------------------");
        System.out.println("Enter your choice :");
    }

    // Perform a withdrawal from the bank account
    public static void performWithdrawal() {
        System.out.print("Enter the amount to withdraw: ");
        int amount = sc.nextInt();
        if (bankAccount.withdraw(amount)) {
            System.out.println("Successfully withdrawn. Your Remaining balance is: " + bankAccount.getBalance());
        } else {
            System.out.println("Insufficient funds. Your total balance is: " + bankAccount.getBalance());
        }
    }

    // Perform a deposit into the bank account
    public static void performDeposit() {
        System.out.print("Enter the amount to deposit: ");
        int amount = sc.nextInt();
        bankAccount.deposit(amount);
        System.out.println("Successfully deposited. Your Updated balance is: " + bankAccount.getBalance());
    }

    // Check the current balance of the bank account
    public static void checkBalance() {
        System.out.println("Your current balance is: " + bankAccount.getBalance());
    }
}