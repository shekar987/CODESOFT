public class UserBankAccount {
    private int balance;

    public UserBankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public int getBalance() {
        return balance;
    }

    public boolean withdraw(int amount) {
        if (amount > balance) {
            return false; // Insufficient funds
        }
        balance -= amount;
        return true; // Successful withdrawal
    }

    public void deposit(int amount) {
        balance += amount;
    }
}
