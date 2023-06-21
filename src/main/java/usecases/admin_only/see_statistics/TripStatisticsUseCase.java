package usecases.admin_only.see_statistics;

import dao.AdminDao;
import dao.AdminDaoImpl;


import java.util.Scanner;

public class TripStatisticsUseCase {
    public static void main(String[] args) {
        AdminDao dao = new AdminDaoImpl();

        Scanner input = new Scanner(System.in);
        System.out.println("Trip Id: ");
        int tripId = input.nextInt();
        int passengerCount = dao.getPassengerCountOnTrip(tripId);
        double tripRevenue = dao.getTripRevenue(tripId);

        System.out.println("Passenger Count on Trip " + tripId + ": " + passengerCount);
        System.out.println("Trip Revenue: " + tripRevenue);

    }
}
