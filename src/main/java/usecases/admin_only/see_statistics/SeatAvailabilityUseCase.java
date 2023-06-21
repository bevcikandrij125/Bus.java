package usecases.admin_only.see_statistics;

import dao.AdminDao;
import dao.AdminDaoImpl;
import dao.CustomerDaoImpl;


import java.util.Scanner;

public class SeatAvailabilityUseCase {
    public static void main(String[] args) {
        AdminDao dao = new AdminDaoImpl();

        Scanner input = new Scanner(System.in);
        System.out.println("Trip Id: ");
        int tripId = input.nextInt();
        int availableSeats = CustomerDaoImpl.numberOfFreePlaces(tripId);
        int reservedSeats = CustomerDaoImpl.numberOfReservedPlaces(tripId);

        System.out.println("Available " + availableSeats + " seats on Trip " + tripId);

        System.out.println("Reserved " +  reservedSeats + " seats on Trip " + tripId);
    }
}
