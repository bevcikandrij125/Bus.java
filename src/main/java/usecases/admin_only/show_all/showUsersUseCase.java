package usecases.admin_only.show_all;

import java.util.Iterator;
import java.util.List;

import bean.User;
import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.CustomerException;

public class showUsersUseCase {

    public static void main(String[] args) {

        AdminDao dao = new AdminDaoImpl();

        try {
            List<User> users = dao.showUsers();

            Iterator<User> itr = users.iterator();

            users.forEach((User u) -> {

                System.out.println("User Id: " + u.getId());
                System.out.println("First Name: " + u.getFirstName());
                System.out.println("Last Name: " + u.getLastName());
                System.out.println("Is Admin: " + u.isAdmin());
                System.out.println("Email: " + u.getEmail());
                System.out.println("Password: " + u.getPassword());

                System.out.println("***************************************");
            });

        } catch (CustomerException e) {

            System.out.println(e.getMessage());
        }
    }
}
