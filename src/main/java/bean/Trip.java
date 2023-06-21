package bean;

import java.time.LocalDateTime;

public class Trip {
    private int id;
    private int busId;
    private int routeId;
    private int fare;
    private boolean canceled = false;
    private boolean postponed = false;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Trip() {
        super();
    }

    public Trip(int id, int busId, int routeId, int fare, LocalDateTime startTime, LocalDateTime endTime) {
        super();
        this.id = id;
        this.busId = busId;
        this.routeId = routeId;
        this.fare = fare;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Trip(int id, int busId, int routeId, int fare, LocalDateTime startTime, LocalDateTime endTime, boolean postponed, boolean canceled) {
        super();
        this.id = id;
        this.busId = busId;
        this.routeId = routeId;
        this.fare = fare;
        this.startTime = startTime;
        this.endTime = endTime;
        this.postponed = postponed;
        this.canceled = canceled;
    }

    public int getId() {
        return id;
    }

    public boolean isPostponed() {
        return postponed;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public int getBusId() {
        return busId;
    }

    public int getRouteId() {
        return routeId;
    }

    public int getFare() {
        return fare;
    }

    public void setFare(int fare) {
        this.fare = fare;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        LocalDateTime now = LocalDateTime.now();

        if (canceled) {
            return "Canceled";
        } else if (postponed) {
            return "Postponed";
        } else if (endTime.isBefore(now)) {
            return "Done";
        } else if (startTime.isBefore(now)) {
            return "In process";
        } else {
            return "Active";
        }
    }
}
