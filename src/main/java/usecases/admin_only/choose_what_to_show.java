package usecases.admin_only;

import usecases.admin_only.show_all.*;

import java.util.Scanner;

public class choose_what_to_show {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("0 - Bookings");
        System.out.println("1 - Buses");
        System.out.println("2 - Routes");
        System.out.println("3 - Trips");
        System.out.println("4 - Users");
        System.out.println("5 - Exit");

        int choice = input.nextInt();

        switch(choice) {

            case 0:
                new showBookingsUseCase().main(null);
                new choose_what_to_show().main(null);
                break;

            case 1:
                new showBusUseCase().main(null);
                new choose_what_to_show().main(null);
                break;
            case 2:
                new showRoutesUseCase().main(null);
                new choose_what_to_show().main(null);
                break;
            case 3:
                new showTripsUseCase().main(null);
                new choose_what_to_show().main(null);
                break;
            case 4:
                new showUsersUseCase().main(null);
                new choose_what_to_show().main(null);
                break;
            case 5:
                break;
            default:
                System.out.println("Invalid choice. Exiting...");
        }
    }
}