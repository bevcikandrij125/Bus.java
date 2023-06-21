package usecases.customer;

import java.util.Scanner;

import dao.CustomerDao;
import dao.CustomerDaoImpl;
import exception.CustomerException;

public class BookTicketUseCase {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter trip id:");
		String trip_number = input.next();
		System.out.println("Enter number of tickets:");
		String number = input.next();

		CustomerDao dao = new CustomerDaoImpl();
		
		try {
			String result = dao.bookTicket(Integer.parseInt(trip_number), Integer.parseInt(number));
			
			System.out.println(result);
			
		} catch (CustomerException e) {
			
			System.out.println(e.getMessage());
		}
	}

}
