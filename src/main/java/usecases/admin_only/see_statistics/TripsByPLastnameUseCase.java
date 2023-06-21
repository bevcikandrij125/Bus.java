package usecases.admin_only.see_statistics;

import bean.Trip;
import dao.AdminDao;
import dao.AdminDaoImpl;

import java.util.Scanner;

public class TripsByPLastnameUseCase {
    public static void main(String[] args) {
        AdminDao dao = new AdminDaoImpl();

        Scanner input = new Scanner(System.in);
        System.out.println("Last Name: ");
        String lastName = input.nextLine();
        boolean hasTicket = dao.hasTicketWithLastName(lastName);

        if (hasTicket) {
            Trip trip = dao.getTripByPassengerLastName(lastName);
            System.out.println("Passenger with Last Name " + lastName + " has a ticket.");
            System.out.println("Ticket is for Trip ID: " + trip.getId());
            System.out.println("Bus Id: " + trip.getBusId());
            System.out.println("Route Id: " + trip.getRouteId());
            System.out.println("Fare: " + trip.getFare());
            System.out.println("Start Time: " + trip.getStartTime());
            System.out.println("End Time: " + trip.getEndTime());
        } else {
            System.out.println("Passenger with Last Name " + lastName + " does not have a ticket.");
        }

    }
}
