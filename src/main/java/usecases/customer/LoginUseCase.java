package usecases.customer;

import java.util.Scanner;

import dao.CustomerDao;
import dao.CustomerDaoImpl;
import exception.CustomerException;

public class LoginUseCase {

	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		String result = "";
		while (result != "Login Successful...") {
			System.out.println("Do you want to register? (y/n)");
			if(input.next().contains("y")){
				RegisterUseCase.main(null);
			}

			System.out.println("Please Login First");

			System.out.println("Enter Your Id");

			int id = input.nextInt();

			System.out.println("Enter Your Password :");

			String pass = input.next();

			CustomerDao customerDao = new CustomerDaoImpl();

			try {
				result = customerDao.login(id, pass);

				System.out.println(result);

			} catch (CustomerException e) {

				System.out.println(e.getMessage());
			}
		}
	}

}
