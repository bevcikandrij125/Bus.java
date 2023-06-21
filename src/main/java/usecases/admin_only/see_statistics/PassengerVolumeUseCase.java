package usecases.admin_only.see_statistics;

import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.TripException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PassengerVolumeUseCase {
    public static void main(String[] args) {
        AdminDao dao = new AdminDaoImpl();

        Scanner input = new Scanner(System.in);

        // Зчитування дати
        System.out.println("Enter Date (yyyy-MM-dd) or '-' for current date: ");
        String dateInput = input.nextLine();

        LocalDate date;
        if (dateInput.equals("-")) {
            date = LocalDate.now();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            date = LocalDate.parse(dateInput, formatter);
        }

        int dailyPassengerCount = dao.getDailyPassengerCount(date);
        double dailyRevenue = dao.getDailyRevenue(date);

        int monthlyPassengerCount = dao.getMonthlyPassengerCount(date);
        double monthlyRevenue = dao.getMonthlyRevenue(date);

        System.out.println("Enter Route Id: ");
        int routeId = input.nextInt();
        int routePassengerCount = dao.getPassengerCountForRoute(routeId);
        double routeRevenue = dao.getRevenueForRoute(routeId);

        System.out.println("Daily Passenger Count: " + dailyPassengerCount);
        System.out.println("Daily Revenue: " + dailyRevenue);
        System.out.println("Monthly Passenger Count: " + monthlyPassengerCount);
        System.out.println("Monthly Revenue: " + monthlyRevenue);
        System.out.println("Passenger Count for Route " + routeId + " on " + date + ": " + routePassengerCount);
        System.out.println("Revenue for Route " + routeId + " on " + date + ": " + routeRevenue);

    }
}
