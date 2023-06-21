package Application;

import java.util.Scanner;

import exception.CustomerException;
import usecases.admin_only.*;
import usecases.customer.FindTripUseCase;
import usecases.customer.ShowMyTicketsUseCase;
import dao.CustomerDaoImpl;
public class Bus_Reservation_System {
	
	public static void welcome() {
		
		new Welcome().main(null);
	}

	public static void customerChoices() {
		
	    Scanner input = new Scanner(System.in);
	    System.out.println("Enter 1 to Find trip");
	    System.out.println("Enter 2 to My tickets");
		System.out.println("Enter 3 to Exit");
		
		int choice = input.nextInt();
		
		switch(choice) {
		
		case 1:
			new FindTripUseCase().main(null);
			Bus_Reservation_System.customerChoices();
			break;
			
		case 2:
			new ShowMyTicketsUseCase().main(null);
			Bus_Reservation_System.customerChoices();
			break;
			
		case 3:
			System.out.println("Exit from choices..");
		}
	    
	}
	public static void AdminChoices() {

		Scanner input = new Scanner(System.in);
		System.out.println("Enter 0 to Show");
		System.out.println("Enter 1 to Create");
		System.out.println("Enter 2 to Update");
		System.out.println("Enter 3 to Like a Customer");
		System.out.println("Enter 4 to Advanced statistics");
		System.out.println("Enter 5 to Exit");

		int choice = input.nextInt();

		switch(choice) {

			case 0:
				new choose_what_to_show().main(null);
				Bus_Reservation_System.AdminChoices();
				break;
			case 1:
				new choose_what_to_create().main(null);
				Bus_Reservation_System.AdminChoices();
				break;
			case 2:
				new choose_what_to_update().main(null);
				Bus_Reservation_System.customerChoices();
				Bus_Reservation_System.AdminChoices();
				break;
			case 3:
				System.out.println("Enter one of the following choices to continue...");
				Bus_Reservation_System.customerChoices();
				Bus_Reservation_System.AdminChoices();
				break;
			case 4:
				new choose_what_to_see().main(null);
				Bus_Reservation_System.AdminChoices();
				break;
			case 5:
				System.out.println("Exit from choices..");
		}

	}
	
	public static void main(String[] args) throws CustomerException {

	    Bus_Reservation_System.welcome() ;
	    
	    System.out.println("Enter one of the following choices to continue...");
		if(CustomerDaoImpl.isAdmin()){
			Bus_Reservation_System.AdminChoices();
		}else{
			Bus_Reservation_System.customerChoices();
		}
		Bus_Reservation_System.main(null);
	}

}
