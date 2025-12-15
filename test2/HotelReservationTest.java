import java.util.Calendar;
import java.util.Date;

public class HotelReservationTest {
    
    public static void main(String[] args) {
        testInsufficientBalance();
        testInvalidDateOrder();
        testSuccessfulBooking();
        testRoomUnavailable();
        testMultipleBookings();
        testRoomUpdate();
        testPrintFunctions();
    }
    
    private static void testInsufficientBalance() {
        try {
            Service service = new Service();
            service.setRoom(2, RoomType.JUNIOR, 2000);
            service.setUser(1, 5000);
            Date date1 = createDate(2026, 6, 30);
            Date date2 = createDate(2026, 7, 7);
            service.bookRoom(1, 2, date1, date2);
            System.out.println("✗ testInsufficientBalance: FAILED");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ testInsufficientBalance: PASSED");
        }
    }
    
    private static void testInvalidDateOrder() {
        try {
            Service service = new Service();
            service.setRoom(2, RoomType.JUNIOR, 2000);
            service.setUser(1, 15000);
            Date date1 = createDate(2026, 6, 30);
            Date date2 = createDate(2026, 7, 7);
            service.bookRoom(1, 2, date2, date1);
            System.out.println("✗ testInvalidDateOrder: FAILED");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ testInvalidDateOrder: PASSED");
        }
    }
    
    private static void testSuccessfulBooking() {
        try {
            Service service = new Service();
            service.setRoom(1, RoomType.STANDARD, 1000);
            service.setUser(1, 5000);
            Date date2 = createDate(2026, 7, 7);
            Date date3 = createDate(2026, 7, 8);
            service.bookRoom(1, 1, date2, date3);
            System.out.println("✓ testSuccessfulBooking: PASSED");
        } catch (Exception e) {
            System.out.println("✗ testSuccessfulBooking: FAILED");
        }
    }
    
    private static void testRoomUnavailable() {
        try {
            Service service = new Service();
            service.setRoom(1, RoomType.STANDARD, 1000);
            service.setUser(1, 5000);
            service.setUser(2, 10000);
            Date date2 = createDate(2026, 7, 7);
            Date date3 = createDate(2026, 7, 8);
            Date date4 = createDate(2026, 7, 9);
            service.bookRoom(1, 1, date2, date3);
            service.bookRoom(2, 1, date2, date4);
            System.out.println("✗ testRoomUnavailable: FAILED");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ testRoomUnavailable: PASSED");
        }
    }
    
    private static void testMultipleBookings() {
        try {
            Service service = new Service();
            service.setRoom(1, RoomType.STANDARD, 1000);
            service.setRoom(3, RoomType.SUITE, 3000);
            service.setUser(1, 5000);
            service.setUser(2, 10000);
            Date date2 = createDate(2026, 7, 7);
            Date date3 = createDate(2026, 7, 8);
            service.bookRoom(1, 1, date2, date3);
            service.bookRoom(2, 3, date2, date3);
            System.out.println("✓ testMultipleBookings: PASSED");
        } catch (Exception e) {
            System.out.println("✗ testMultipleBookings: FAILED");
        }
    }
    
    private static void testRoomUpdate() {
        try {
            Service service = new Service();
            service.setRoom(1, RoomType.STANDARD, 1000);
            service.setUser(1, 5000);
            Date date2 = createDate(2026, 7, 7);
            Date date3 = createDate(2026, 7, 8);
            service.bookRoom(1, 1, date2, date3);
            service.setRoom(1, RoomType.SUITE, 10000);
            System.out.println("✓ testRoomUpdate: PASSED");
        } catch (Exception e) {
            System.out.println("✗ testRoomUpdate: FAILED");
        }
    }
    
    private static void testPrintFunctions() {
        try {
            Service service = new Service();
            service.setRoom(1, RoomType.STANDARD, 1000);
            service.setRoom(2, RoomType.JUNIOR, 2000);
            service.setRoom(3, RoomType.SUITE, 3000);
            service.setUser(1, 5000);
            service.setUser(2, 10000);
            Date date2 = createDate(2026, 7, 7);
            Date date3 = createDate(2026, 7, 8);
            service.bookRoom(1, 1, date2, date3);
            service.bookRoom(2, 3, date2, date3);
            service.setRoom(1, RoomType.SUITE, 10000);
            
            service.printAll();
            service.printAllUsers();
            System.out.println("✓ testPrintFunctions: PASSED");
        } catch (Exception e) {
            System.out.println("✗ testPrintFunctions: FAILED");
        }
    }
    
    private static Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}