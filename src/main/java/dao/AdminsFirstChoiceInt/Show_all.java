package dao.AdminsFirstChoiceInt;

import bean.*;
import exception.*;

import java.sql.SQLException;
import java.util.List;

public interface Show_all {
    public List<Bus> showBuses() throws BusException;
    public List<Route> showRoutes() throws SQLException, RouteException;
    public List<Trip> showTrips() throws TripException;
    public List<User> showUsers() throws CustomerException;
    public List<Booking> showBookings() throws BookingException;
}
