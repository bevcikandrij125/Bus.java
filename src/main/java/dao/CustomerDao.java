package dao;

import java.util.List;

import bean.Booking;
import bean.Bus;
import bean.Trip;
import exception.BusException;
import exception.CustomerException;

public interface CustomerDao {

	public String login(int AdId, String password)throws CustomerException;
	public List<Trip> findTrips(String source, String  destination, String  range0fDate, String  rangeOFare, String  sortCriterion) throws BusException;
	public List<Booking> showTickets() throws CustomerException;
	public String shortBookingInfo(int id) throws CustomerException;
	public String detailedBookingInfo(int id) throws CustomerException;
	public int register(String firstName, String lastName, String email, String password) throws CustomerException;
	
	public String bookTicket(int trip_number, int  numberOfTickets) throws CustomerException;

	public String cancelBooking(int id);
}
