import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AccountTest {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public static void main(String[] args) {
        testBasicDepositAndWithdrawal();
        testInvalidDeposit();
        testInvalidWithdrawal();
        testInsufficientFunds();
        testStatementOutput();
    }
    
    private static void testBasicDepositAndWithdrawal() {
        try {
            Account account = new Account();
            account.deposit(1000);
            account.withdraw(500);
            System.out.println("✓ testBasicDepositAndWithdrawal: PASSED");
        } catch (Exception e) {
            System.out.println("✗ testBasicDepositAndWithdrawal: FAILED");
        }
    }
    
    private static void testInvalidDeposit() {
        Account account = new Account();
        try {
            account.deposit(-100);
            System.out.println("✗ testInvalidDeposit: FAILED");
        } catch (IllegalArgumentException e) {
            // Passed
        }
        try {
            account.deposit(0);
            System.out.println("✗ testInvalidDeposit: FAILED");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ testInvalidDeposit: PASSED");
        }
    }
    
    private static void testInvalidWithdrawal() {
        Account account = new Account();
        account.deposit(1000);
        try {
            account.withdraw(-100);
            System.out.println("✗ testInvalidWithdrawal: FAILED");
        } catch (IllegalArgumentException e) {
            // Passed
        }
        try {
            account.withdraw(0);
            System.out.println("✗ testInvalidWithdrawal: FAILED");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ testInvalidWithdrawal: PASSED");
        }
    }

    private static void testInsufficientFunds() {
        try {
            Account account = new Account();
            account.deposit(500);
            account.withdraw(1000);
            System.out.println("✗ testInsufficientFunds: FAILED");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ testInsufficientFunds: PASSED");
        }
    }

    private static void testStatementOutput() {
        // Capture stdout
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        try {
            Account account = new Account();
            
            account.deposit(1000);
            account.deposit(2000);
            account.withdraw(500);
            
            LocalDate today = LocalDate.now();

            account.printStatement();
            System.setOut(originalOut);
            
            String output  = outputStream.toString();
            boolean passed = output.equals("Date\t|| Amount\t|| Balance\n" +
                today.format(DATE_FORMATTER) + "\t|| -500\t|| 2500\n" +
                today.format(DATE_FORMATTER) + "\t|| 2000\t|| 3000\n" +
                today.format(DATE_FORMATTER) + "\t|| 1000\t|| 1000\n");

            
            if (passed) {
                System.out.println("✓ testStatementOutput: PASSED");
            } else {
                System.out.println("✗ testStatementOutput: FAILED");
            }
            
        } catch (Exception e) {
            System.setOut(originalOut);
            System.out.println("✗ testStatementOutput: FAILED - " + e.getMessage());
        }
    }
}