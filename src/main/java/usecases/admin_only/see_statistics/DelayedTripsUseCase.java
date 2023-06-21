package usecases.admin_only.see_statistics;

import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.TripException;


public class DelayedTripsUseCase {
    public static void main(String[] args) {
        AdminDao dao = new AdminDaoImpl();

        int totalDelayedTrips = dao.getTotalDelayedTrips();
        int totalTicketsReturned = dao.getTotalTicketsReturnedForDelays();


        System.out.println("Total Delayed Trips: " + totalDelayedTrips);
        System.out.println("Total Tickets Returned for Delays: " + totalTicketsReturned);

    }
}
