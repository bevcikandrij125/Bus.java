package usecases.admin_only.create;

import bean.Route;
import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.RouteException;

import java.util.Scanner;

public class CreateRouteUseCase {

    public static void main(String[] args) {

        AdminDao dao = new AdminDaoImpl();

        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Start City: ");
            String startCity = input.nextLine();
            System.out.println("Target City: ");
            String targetCity = input.nextLine();
            System.out.println("Distance: ");
            int distance = input.nextInt();
            System.out.println("***************************************");
            dao.createRoute(new Route(0, startCity, targetCity, distance));

        } catch (RouteException e) {
            System.out.println(e.getMessage());
        }
    }
}
