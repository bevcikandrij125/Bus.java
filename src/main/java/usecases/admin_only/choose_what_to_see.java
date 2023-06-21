package usecases.admin_only;

import usecases.admin_only.see_statistics.*;

import java.util.Scanner;

public class choose_what_to_see {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("0 - Bus Information");
        System.out.println("1 - Canceled Trips");
        System.out.println("2 - Delayed Trips");
        System.out.println("3 - Passenger List");
        System.out.println("4 - Passenger Volume");
        System.out.println("5 - Seat Availability");
        System.out.println("6 - Ticket Statistics");
        System.out.println("7 - Ticket Status");
        System.out.println("8 - Trips by Passenger Lastname");
        System.out.println("9 - Trip Statistics");
        System.out.println("10 - Exit");

        int choice = input.nextInt();

        switch(choice) {

            case 0:
                new BusInfoUseCase().main(null);
                new choose_what_to_see().main(null);
                break;
            case 1:
                new CanceledTripsUseCase().main(null);
                new choose_what_to_see().main(null);
                break;
            case 2:
                new DelayedTripsUseCase().main(null);
                new choose_what_to_see().main(null);
                break;
            case 3:
                new PassengerListUseCase().main(null);
                new choose_what_to_see().main(null);
                break;
            case 4:
                new PassengerVolumeUseCase().main(null);
                new choose_what_to_see().main(null);
                break;
            case 5:
                new SeatAvailabilityUseCase().main(null);
                new choose_what_to_see().main(null);
                break;
            case 6:
                new TicketStatisticsUseCase().main(null);
                new choose_what_to_see().main(null);
                break;
            case 7:
                new TicketStatusUseCase().main(null);
                new choose_what_to_see().main(null);
                break;
            case 8:
                new TripsByPLastnameUseCase().main(null);
                new choose_what_to_see().main(null);
                break;
            case 9:
                new TripStatisticsUseCase().main(null);
                new choose_what_to_see().main(null);
                break;
            case 10:
                break;
            default:
                System.out.println("Invalid choice. Exiting...");
        }
    }
}
