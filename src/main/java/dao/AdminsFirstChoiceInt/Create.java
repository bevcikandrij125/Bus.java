package dao.AdminsFirstChoiceInt;

import bean.*;
import exception.*;

public interface Create {
    public void createBus(Bus bus) throws BusException;
    public void createRoute(Route route) throws RouteException;
    public void createUser(User user) throws CustomerException;
    public void createTrip(Trip trip) throws TripException;
    public void createBooking(Booking booking) throws BookingException;
}
