import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private final LocalDate date;
    private final int amount;
    private final int balance;
    private final TransactionType type;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public enum TransactionType {
        DEPOSIT, WITHDRAWAL
    }
    
    public Transaction(LocalDate date, int amount, int balance, TransactionType type) {
        this.date = date;
        this.amount = amount;
        this.balance = balance;
        this.type = type;
    }
        
    public String toStatementLine() {
        String formattedDate = date.format(DATE_FORMATTER);
        String amountStr = (type == TransactionType.DEPOSIT ? "" : "-") + Math.abs(amount);
        return String.format("%s\t|| %s\t|| %d", formattedDate, amountStr, balance);
    }
}