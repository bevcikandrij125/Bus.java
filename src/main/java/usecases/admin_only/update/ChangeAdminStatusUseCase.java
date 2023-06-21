package usecases.admin_only.update;

import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.CustomerException;


import java.util.Scanner;

public class ChangeAdminStatusUseCase {

    public static void main(String[] args) {

        AdminDao dao = new AdminDaoImpl();

        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter user Id: ");
            int user_Id = input.nextInt();
            System.out.println("Enter new  admin_level: ");
            int admin_level = input.nextInt();
            System.out.println(dao.setAdminLevel(user_Id, admin_level));
        } catch (CustomerException e) {
            System.out.println(e.getMessage());
        }
    }
}
