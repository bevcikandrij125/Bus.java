package usecases.admin_only;

import usecases.admin_only.create.CreateBusUseCase;
import usecases.admin_only.create.CreateRouteUseCase;
import usecases.admin_only.create.CreateTripUseCase;
import usecases.admin_only.create.CreateUserUseCase;
import usecases.admin_only.show_all.CreateBookingUseCase;

import java.util.Scanner;

public class choose_what_to_create {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("0 - Create Booking");
        System.out.println("1 - Create Bus");
        System.out.println("2 - Create Route");
        System.out.println("3 - Create Trip");
        System.out.println("4 - Create User");
        System.out.println("5 - Exit");
        int choice = input.nextInt();

        switch(choice) {

            case 0:
                new CreateBookingUseCase().main(null);
                new choose_what_to_create().main(null);
                break;
            case 1:
                new CreateBusUseCase().main(null);
                new choose_what_to_create().main(null);
                break;
            case 2:
                new CreateRouteUseCase().main(null);
                new choose_what_to_create().main(null);
                break;
            case 3:
                new CreateTripUseCase().main(null);
                new choose_what_to_create().main(null);
                break;
            case 4:
                new CreateUserUseCase().main(null);
                new choose_what_to_create().main(null);
                break;
            case 5:
                break;
            default:
                System.out.println("Invalid choice. Exiting...");
        }
    }
}
