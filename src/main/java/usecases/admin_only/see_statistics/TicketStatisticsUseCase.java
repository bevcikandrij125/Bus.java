package usecases.admin_only.see_statistics;

import bean.Route;
import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.BookingException;
import exception.RouteException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TicketStatisticsUseCase {
    public static void main(String[] args) {
        AdminDao dao = new AdminDaoImpl();

        try {
            Scanner input = new Scanner(System.in);
            List<Route> routes = dao.showRoutes();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd'T'HH");
            System.out.println("Start Time (yy-MM-dd'T'HH): ");
            String startTimeInput = input.nextLine();
            LocalDateTime startTime = LocalDateTime.parse(startTimeInput, formatter);
            System.out.println("End Time (yy-MM-dd'T'HH): ");
            String endTimeInput = input.nextLine();
            LocalDateTime endTime = LocalDateTime.parse(endTimeInput, formatter);


            for (Route route : routes) {
                int ticketCount = dao.getTicketCountForRouteInTimeRange(route.getId(), startTime, endTime);
                double averagePrice = dao.getAverageTicketPriceForRouteInTimeRange(route.getId(), startTime, endTime);

                System.out.println("Route ID: " + route.getId());
                System.out.println("Start City: " + route.getStartCity());
                System.out.println("Target City: " + route.getTargetCity());
                System.out.println("Ticket Count: " + ticketCount);
                System.out.println("Average Ticket Price: " + averagePrice);
                System.out.println("***************************************");
            }

        } catch (RouteException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
