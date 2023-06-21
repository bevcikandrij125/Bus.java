package usecases.admin_only.see_statistics;

import bean.Trip;
import bean.User;
import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.CustomerException;
import exception.TripException;

import java.util.List;
import java.util.Scanner;

public class PassengerListUseCase {
    public static void main(String[] args) {
        AdminDao dao = new AdminDaoImpl();

        Scanner input = new Scanner(System.in);
        System.out.println("Trip Id: ");
        int Tid = input.nextInt();
        List<User> passengers = dao.getPassengersOnCompletedTrip(Tid);

        System.out.println("Passengers Trip:");
        for (User passenger : passengers) {
            System.out.println("Passenger ID: " + passenger.getId());
            System.out.println("Passenger Name: " + passenger.getFirstName());
            System.out.println("Passenger Second Name: " + passenger.getLastName());
            // Вивести іншу необхідну інформацію про пасажира
            System.out.println("***************************************");
        }

    }
}
