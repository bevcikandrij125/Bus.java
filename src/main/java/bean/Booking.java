package bean;
public class Booking {
    private int id;
    private int tripId;
    private int userId;
    private boolean canceled;

    public Booking() {
        super();
    }

    public Booking(int id, int tripId, int userId) {
        super();
        this.id = id;
        this.tripId = tripId;
        this.userId = userId;
    }
    public Booking(int id, int tripId, int userId, boolean canceled) {
        super();
        this.id = id;
        this.tripId = tripId;
        this.userId = userId;
        this.canceled = canceled;
    }

    public int getId() {
        return id;
    }

    public int getTripId() {
        return tripId;
    }

    public void setTripId(int tripId) {
        this.tripId = tripId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isCanceled() {
        return canceled;
    }
}