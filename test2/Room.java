public class Room {
    private int roomNumber;
    private RoomType roomType;
    private int pricePerNight;
    
    public Room(int roomNumber, RoomType roomType, int pricePerNight) {
        if (pricePerNight < 0) {
            throw new IllegalArgumentException("Price per night cannot be negative: " + pricePerNight);
        }
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.pricePerNight = pricePerNight;
    }
    
    public int getRoomNumber() {
        return roomNumber;
    }
    
    public RoomType getRoomType() {
        return roomType;
    }
    
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    
    public int getPricePerNight() {
        return pricePerNight;
    }
    
    public void setPricePerNight(int pricePerNight) {
        if (pricePerNight < 0) {
            throw new IllegalArgumentException("Price per night cannot be negative: " + pricePerNight);
        }
        this.pricePerNight = pricePerNight;
    }
    
    @Override
    public String toString() {
        return "Room " + roomNumber + " [Type: " + roomType + ", Price/night: " + pricePerNight + "]";
    }
}