package usecases.admin_only.see_statistics;

import bean.Bus;
import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.BusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class BusInfoUseCase {
    public static void main(String[] args) {
        AdminDao dao = new AdminDaoImpl();

        Scanner input = new Scanner(System.in);
        System.out.println("Enter Route Id: ");
        int routeId = input.nextInt();
        input.nextLine(); // Очистити буфер після nextInt()
        System.out.println("Enter Start Time (yyyy-MM-dd'T'HH:mm): ");
        String startTimeString = input.nextLine();
        LocalDateTime date = LocalDateTime.parse(startTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

        List<Bus> buses = dao.getBusesForRouteInDate(routeId, LocalDate.from(date));
        System.out.println("Buses for Route " + routeId + " on Date " + date + ":");
        for (Bus bus : buses) {
            System.out.println("Board Number: " + bus.getBoardNumber());
            System.out.println("Brand: " + bus.getBrand());
            System.out.println("License Number: " + bus.getLicenseNumber());
            System.out.println("***************************************");
        }

    }
}
