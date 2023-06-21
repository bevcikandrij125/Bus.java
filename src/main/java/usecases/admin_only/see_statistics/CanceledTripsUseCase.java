package usecases.admin_only.see_statistics;

import bean.Trip;
import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.TripException;

import java.util.List;

public class CanceledTripsUseCase {
    public static void main(String[] args) {
        AdminDao dao = new AdminDaoImpl();

        List<Trip> canceledTrips = dao.getCanceledTrips();
        int totalCanceledTrips = dao.getTotalCanceledTrips();

        System.out.println("Canceled Trips:");
        for (Trip t : canceledTrips) {
            System.out.println("Trip ID: " + t.getId());
            System.out.println("Bus Id: " + t.getBusId());
            System.out.println("Route Id: " + t.getRouteId());
            System.out.println("Fare: " + t.getFare());
            System.out.println("Start Time: " + t.getStartTime());
            System.out.println("End Time: " + t.getEndTime());
            System.out.println("***************************************");
        }

        System.out.println("Total Canceled Trips: " + totalCanceledTrips);

    }
}
