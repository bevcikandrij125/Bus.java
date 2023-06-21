package usecases.admin_only.create;

import bean.Bus;
import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.BusException;

import java.util.Scanner;

public class CreateBusUseCase {

    public static void main(String[] args) {

        AdminDao dao = new AdminDaoImpl();

        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Brand: ");
            String brand = input.nextLine();
            System.out.println("Capacity: ");
            int capacity = input.nextInt();
            System.out.println("Board Number: ");
            int boardNumber = input.nextInt();
            System.out.println("License Number: ");
            int licenseNumber = input.nextInt();
            System.out.println("***************************************");
            dao.createBus(new Bus(0, brand, capacity, boardNumber, licenseNumber));

        } catch (BusException e) {
            System.out.println(e.getMessage());
        }
    }
}
