package usecases.admin_only.update;

import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.TripException;

import java.util.Scanner;

public class CancelTripUseCase {

    public static void main(String[] args) {

        AdminDao dao = new AdminDaoImpl();

        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter Trip Id: ");
            int tripId = input.nextInt();
            System.out.println("***************************************");
            dao.cancelTrip(tripId);
            System.out.println("Trip canceled successfully!");

        } catch (TripException e) {
            System.out.println(e.getMessage());
        }
    }
}
