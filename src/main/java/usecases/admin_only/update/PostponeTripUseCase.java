package usecases.admin_only.update;

import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.TripException;

import java.time.LocalDateTime;
import java.util.Scanner;

public class PostponeTripUseCase {

    public static void main(String[] args) {

        AdminDao dao = new AdminDaoImpl();

        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter Trip Id: ");
            int tripId = input.nextInt();
            input.nextLine(); // Очистити буфер після nextInt()
            System.out.println("Enter New Start Time (yyyy-MM-dd HH:mm:ss): ");
            String newStartTimeString = input.nextLine();
            System.out.println("Enter New End Time (yyyy-MM-dd HH:mm:ss): ");
            String newEndTimeString = input.nextLine();
            LocalDateTime newStartTime = LocalDateTime.parse(newStartTimeString);
            LocalDateTime newEndTime = LocalDateTime.parse(newEndTimeString);
            System.out.println("***************************************");
            dao.postponeTrip(tripId, newStartTime, newEndTime);
            System.out.println("Trip postponed successfully!");

        } catch (TripException e) {
            System.out.println(e.getMessage());
        }
    }
}
