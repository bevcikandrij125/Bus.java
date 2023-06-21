package usecases.admin_only.see_statistics;

import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.BookingException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TicketStatusUseCase {
    public static void main(String[] args) {
        AdminDao dao = new AdminDaoImpl();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the option:\n1. By Trip ID\n2. By Date\n3. By Route ID");
        int option = scanner.nextInt();

        if (option == 1) {
            System.out.println("Enter the Trip ID:");
            int tripId = scanner.nextInt();
            int soldTickets = dao.getSoldTicketsOnTrip(tripId);
            int unsoldTickets = dao.getUnsoldTicketsOnTrip(tripId);
            int returnedTickets = dao.getReturnedTicketsOnTrip(tripId);

            System.out.println("Sold Tickets on Trip " + tripId + ": " + soldTickets);
            System.out.println("Unsold Tickets on Trip " + tripId + ": " + unsoldTickets);
            System.out.println("Returned Tickets on Trip " + tripId + ": " + returnedTickets);
        } else if (option == 2) {
            System.out.println("Enter the Date (yy-MM-dd):");
            String date = scanner.next();
            LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yy-MM-dd"));
            int soldTickets = dao.getSoldTicketsOnDate(parsedDate);
            int unsoldTickets = dao.getUnsoldTicketsOnDate(parsedDate);
            int returnedTickets = dao.getReturnedTicketsOnDate(parsedDate);

            System.out.println("Sold Tickets for Date " + date + ": " + soldTickets);
            System.out.println("Unsold Tickets for Date " + date + ": " + unsoldTickets);
            System.out.println("Returned Tickets for Date " + date + ": " + returnedTickets);
        } else if (option == 3) {
            System.out.println("Enter the Route ID:");
            int routeId = scanner.nextInt();
            int soldTickets = dao.getSoldTicketsOnRoute(routeId);
            int unsoldTickets = dao.getUnsoldTicketsOnRoute(routeId);
            int returnedTickets = dao.getReturnedTicketsOnRoute(routeId);

            System.out.println("Sold Tickets on Route " + routeId + ": " + soldTickets);
            System.out.println("Unsold Tickets on Route " + routeId + ": " + unsoldTickets);
            System.out.println("Returned Tickets on Route " + routeId + ": " + returnedTickets);
        } else {
            System.out.println("Invalid option selected.");
        }
    }
}
