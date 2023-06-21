package usecases.admin_only.create;

import bean.Trip;
import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.TripException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CreateTripUseCase {

    public static void main(String[] args) {

        AdminDao dao = new AdminDaoImpl();

        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Bus Id: ");
            int busId = input.nextInt();
            input.nextLine();

            System.out.println("Route Id: ");
            int routeId = input.nextInt();
            input.nextLine();

            System.out.println("Fare: ");
            int fare = input.nextInt();
            input.nextLine();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd'T'HH:mm");

            System.out.println("Start Time (yy-MM-dd'T'HH:mm): ");
            String startTimeString = input.nextLine();
            LocalDateTime startTime = LocalDateTime.parse(startTimeString, formatter);

            System.out.println("End Time (yy-MM-dd'T'HH:mm): ");
            String endTimeString = input.nextLine();
            LocalDateTime endTime = LocalDateTime.parse(endTimeString, formatter);

            System.out.println("Postponed (true/false): ");
            boolean postponed = input.nextBoolean();
            System.out.println("Canceled (true/false): ");
            boolean canceled = input.nextBoolean();

            System.out.println("***************************************");
            dao.createTrip(new Trip(0, busId, routeId, fare, startTime, endTime, postponed, canceled));

        } catch (TripException e) {
            System.out.println(e.getMessage());
        }
    }
}
