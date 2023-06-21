package usecases.customer;

import java.util.Scanner;

import dao.CustomerDao;
import dao.CustomerDaoImpl;
import exception.CustomerException;


public class RegisterUseCase {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Register New Customer");
        System.out.println("---------------------");

        System.out.println("Enter First Name:");
        String firstName = input.nextLine();

        System.out.println("Enter Last Name:");
        String lastName = input.nextLine();

        System.out.println("Enter Email:");
        String email = input.nextLine();

        System.out.println("Enter Password:");
        String password = input.nextLine();

        CustomerDao customerDao = new CustomerDaoImpl();

        try {
            System.out.println("Your id: " + customerDao.register(firstName, lastName, email, password));
            System.out.println("Registration Successful!");
        } catch (CustomerException e) {
            System.out.println("Registration Failed: " + e.getMessage());
        }
    }
}
