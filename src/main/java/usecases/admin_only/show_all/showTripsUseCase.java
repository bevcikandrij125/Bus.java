package usecases.admin_only.show_all;

import java.util.Iterator;
import java.util.List;

import bean.Trip;
import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.TripException;

public class showTripsUseCase {

    public static void main(String[] args) {

        AdminDao dao = new AdminDaoImpl();

        try {
            List<Trip> trips = dao.showTrips();

            Iterator<Trip> itr = trips.iterator();

            trips.forEach((Trip t) -> {

                System.out.println("Trip Id: " + t.getId());
                System.out.println("Bus Id: " + t.getBusId());
                System.out.println("Route Id: " + t.getRouteId());
                System.out.println("Fare: " + t.getFare());
                System.out.println("Status: " + t.getStatus());
                System.out.println("Start Time: " + t.getStartTime());
                System.out.println("End Time: " + t.getEndTime());

                System.out.println("***************************************");
            });

        } catch (TripException e) {

            System.out.println(e.getMessage());
        }
    }
}
