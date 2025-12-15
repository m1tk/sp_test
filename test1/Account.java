import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account implements AccountService {
    private int balance;
    private final List<Transaction> transactions;
    
    public Account() {
        this.balance = 0;
        this.transactions = new ArrayList<>();
    }
    
    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive. Provided: " + amount);
        }
        
        LocalDate date = LocalDate.now();
        balance += amount;
        transactions.add(new Transaction(date, amount, balance, Transaction.TransactionType.DEPOSIT));
    }
    
    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive. Provided: " + amount);
        } else if (amount > balance) {
            throw new IllegalArgumentException(
                "Insufficient funds. Balance: " + balance + ", Withdrawal amount: " + amount
            );
        }
        
        LocalDate date = LocalDate.now();
        balance -= amount;
        transactions.add(new Transaction(date, amount, balance, Transaction.TransactionType.WITHDRAWAL));
    }
    
    public void printStatement() {
        System.out.println("Date\t|| Amount\t|| Balance");
        // Most recent first
        for (int i = transactions.size() - 1; i >= 0; i--) {
            System.out.println(transactions.get(i).toStatementLine());
        }
    }
}