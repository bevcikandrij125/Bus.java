package usecases.admin_only.create;

import bean.User;
import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.CustomerException;

import java.util.Scanner;

public class CreateUserUseCase {

    public static void main(String[] args) {

        AdminDao dao = new AdminDaoImpl();

        try {
            Scanner input = new Scanner(System.in);
            System.out.println("First Name: ");
            String firstName = input.nextLine();
            System.out.println("Last Name: ");
            String lastName = input.nextLine();
            System.out.println("Admin level: ");
            int admin_level = input.nextInt();
            input.nextLine(); // Consume newline character
            System.out.println("Email: ");
            String email = input.nextLine();
            System.out.println("Password: ");
            String password = input.nextLine();
            System.out.println("***************************************");
            dao.createUser(new User(0, firstName, lastName, admin_level, email, password));

        } catch (CustomerException e) {
            System.out.println(e.getMessage());
        }
    }
}
