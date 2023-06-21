package usecases.admin_only;

import usecases.admin_only.create.CreateBusUseCase;
import usecases.admin_only.create.CreateRouteUseCase;
import usecases.admin_only.create.CreateTripUseCase;
import usecases.admin_only.create.CreateUserUseCase;
import usecases.admin_only.show_all.CreateBookingUseCase;
import usecases.admin_only.update.CancelTripUseCase;
import usecases.admin_only.update.ChangeAdminStatusUseCase;
import usecases.admin_only.update.PostponeTripUseCase;

import java.util.Scanner;

public class choose_what_to_update {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("1 - Postpone the trip");
        System.out.println("2 - Cancel the trip");
        System.out.println("3 - Change Admin Level to User");
        System.out.println("4 - Exit");

        int choice = input.nextInt();

        switch(choice) {

            case 1:
                new PostponeTripUseCase().main(null);
                new choose_what_to_update().main(null);
                break;
            case 2:
                new CancelTripUseCase().main(null);
                new choose_what_to_update().main(null);
                break;
            case 3:
                new ChangeAdminStatusUseCase().main(null);
                new choose_what_to_update().main(null);
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid choice. Exiting...");
        }
    }
}
