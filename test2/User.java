public class User {
    private int userId;
    private int balance;
    
    public User(int userId, int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative: " + balance);
        }
        this.userId = userId;
        this.balance = balance;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public int getBalance() {
        return balance;
    }
    
    public void setBalance(int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative: " + balance);
        }
        this.balance = balance;
    }
    
    public boolean hasEnoughBalance(int amount) {
        return balance >= amount;
    }
    
    public void deductBalance(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount to deduct cannot be negative: " + amount);
        }
        if (balance < amount) {
            throw new IllegalArgumentException("Insufficient balance. Current: " + balance + ", Required: " + amount);
        }
        balance -= amount;
    }
    
    @Override
    public String toString() {
        return "User " + userId + " [Balance: " + balance + "]";
    }
}