package dao.AdminsFirstChoiceInt;

import exception.CustomerException;
import exception.TripException;

import java.time.LocalDateTime;

public interface Update {
    void postponeTrip(int tripId, LocalDateTime newStartTime, LocalDateTime newEndTime) throws TripException;
    void cancelTrip(int tripId) throws TripException;

    String setAdminLevel(int userId, int adminLevel) throws CustomerException;
}
