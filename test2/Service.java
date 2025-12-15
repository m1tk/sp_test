import java.util.ArrayList;
import java.util.Date;

public class Service {
    private ArrayList<Room> rooms;
    private ArrayList<User> users;
    private ArrayList<Booking> bookings;
    
    public Service() {
        this.rooms = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }
    
    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        if (roomPricePerNight < 0) {
            throw new IllegalArgumentException("Room price per night cannot be negative: " + roomPricePerNight);
        }

        Room existingRoom = findRoomByNumber(roomNumber);
        
        if (existingRoom != null) {
            existingRoom.setRoomType(roomType);
            existingRoom.setPricePerNight(roomPricePerNight);
        } else {
            Room newRoom = new Room(roomNumber, roomType, roomPricePerNight);
            rooms.add(0, newRoom);
        }
    }
    
    public void setUser(int userId, int balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("User balance cannot be negative: " + balance);
        }
        
        User existingUser = findUserById(userId);
        
        if (existingUser != null) {
            existingUser.setBalance(balance);
        } else {
            User newUser = new User(userId, balance);
            users.add(0, newUser);
        }
    }
    
    public void bookRoom(int userId, int roomNumber, Date checkIn, Date checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new IllegalArgumentException("Check-in and check-out dates cannot be null");
        }
        
        if (!checkOut.after(checkIn)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        
        User user = findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found: " + userId);
        }
        
        Room room = findRoomByNumber(roomNumber);
        if (room == null) {
            throw new IllegalArgumentException("Room not found: " + roomNumber);
        }
        
        // Check if room is available for the period
        if (!isRoomAvailable(roomNumber, checkIn, checkOut)) {
            throw new IllegalArgumentException("Room " + roomNumber + " is not available for the requested period");
        }
        
        // Calculate total cost
        long nights = calculateNights(checkIn, checkOut);
        int totalCost = (int) (nights * room.getPricePerNight());
        
        // Check if user has enough balance
        if (!user.hasEnoughBalance(totalCost)) {
            throw new IllegalArgumentException("Insufficient balance. User balance: " + user.getBalance() + 
                                             ", Required: " + totalCost);
        }
        
        // Create booking with snapshot of current data
        Booking booking = new Booking(userId, roomNumber, checkIn, checkOut, 
                                     room.getRoomType(), room.getPricePerNight(), 
                                     user.getBalance());
        
        // Deduct balance from user
        user.deductBalance(totalCost);
        
        // Add booking to beginning of list (latest first)
        bookings.add(0, booking);
    }
    
    public void printAll() {
        System.out.println("\n========== ALL ROOMS ==========");
        if (rooms.isEmpty()) {
            System.out.println("No rooms available.");
        } else {
            for (Room room : rooms) {
                System.out.println(room);
            }
        }
        
        System.out.println("\n========== ALL BOOKINGS ==========");
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
        } else {
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
        System.out.println("==================================\n");
    }
    
    public void printAllUsers() {
        System.out.println("\n========== ALL USERS ==========");
        if (users.isEmpty()) {
            System.out.println("No users available.");
        } else {
            for (User user : users) {
                System.out.println(user);
            }
        }
        System.out.println("===============================\n");
    }
    
    private Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }
    
    private User findUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }
    
    private boolean isRoomAvailable(int roomNumber, Date checkIn, Date checkOut) {
        for (Booking booking : bookings) {
            if (booking.getRoomNumber() == roomNumber && booking.overlaps(checkIn, checkOut)) {
                return false;
            }
        }
        return true;
    }
    
    private long calculateNights(Date checkIn, Date checkOut) {
        long diffInMillis = checkOut.getTime() - checkIn.getTime();
        return diffInMillis / (1000 * 60 * 60 * 24); // we basically divide by a day in milliseconds to get total number of nights
    }
}