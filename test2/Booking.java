import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Booking {
    private int userId;
    private int roomNumber;
    private Date checkIn;
    private Date checkOut;
    private int totalCost;
    
    // Snapshot of room and user data at booking time
    private RoomType roomTypeAtBooking;
    private int roomPriceAtBooking;
    private int userBalanceAtBooking;
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    
    public Booking(int userId, int roomNumber, Date checkIn, Date checkOut, 
                   RoomType roomType, int roomPrice, int userBalance) {
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        
        // Normalize dates to ignore time
        this.checkIn = normalizeDate(checkIn);
        this.checkOut = normalizeDate(checkOut);
        
        if (!this.checkOut.after(this.checkIn)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        
        this.userId = userId;
        this.roomNumber = roomNumber;
        this.roomTypeAtBooking = roomType;
        this.roomPriceAtBooking = roomPrice;
        this.userBalanceAtBooking = userBalance;
        
        // Calculate total cost
        long nights = calculateNights(this.checkIn, this.checkOut);
        this.totalCost = (int) (nights * roomPrice);
    }
    
    private Date normalizeDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    
    private long calculateNights(Date start, Date end) {
        long diffInMillis = end.getTime() - start.getTime();
        return diffInMillis / (1000 * 60 * 60 * 24);
    }
    
    public int getUserId() {
        return userId;
    }
    
    public int getRoomNumber() {
        return roomNumber;
    }
    
    public Date getCheckIn() {
        return checkIn;
    }
    
    public Date getCheckOut() {
        return checkOut;
    }
    
    public int getTotalCost() {
        return totalCost;
    }
    
    public boolean overlaps(Date otherCheckIn, Date otherCheckOut) {
        Date normalizedOtherCheckIn = normalizeDate(otherCheckIn);
        Date normalizedOtherCheckOut = normalizeDate(otherCheckOut);
        
        // Check if dates overlap
        return !(checkOut.compareTo(normalizedOtherCheckIn) <= 0 || 
                 normalizedOtherCheckOut.compareTo(checkIn) <= 0);
    }
    
    @Override
    public String toString() {
        long nights = calculateNights(checkIn, checkOut);
        return "Booking [User: " + userId + 
               ", Room: " + roomNumber + 
               " (Type: " + roomTypeAtBooking + 
               ", Price/night: " + roomPriceAtBooking + ")" +
               ", Check-in: " + DATE_FORMAT.format(checkIn) + 
               ", Check-out: " + DATE_FORMAT.format(checkOut) + 
               ", Nights: " + nights +
               ", Total Cost: " + totalCost +
               ", User Balance at Booking: " + userBalanceAtBooking + "]";
    }
}