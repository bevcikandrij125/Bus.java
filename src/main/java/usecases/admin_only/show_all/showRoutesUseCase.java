package usecases.admin_only.show_all;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import bean.Route;
import dao.AdminDao;
import dao.AdminDaoImpl;
import exception.RouteException;

public class showRoutesUseCase {

    public static void main(String[] args) {

        AdminDao dao = new AdminDaoImpl();

        try {
            List<Route> routes = dao.showRoutes();

            Iterator<Route> iterator = routes.iterator();

            routes.forEach((Route route) -> {
                System.out.println("Route ID: " + route.getId());
                System.out.println("Start City: " + route.getStartCity());
                System.out.println("Target City: " + route.getTargetCity());
                System.out.println("Distance: " + route.getDistance());
                System.out.println("***************************************");
            });

        } catch (RouteException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
