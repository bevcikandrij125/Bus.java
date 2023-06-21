package dao.AdminsAbstractParens;

import bean.*;
import dao.AdminsFirstChoiceInt.Show_all;
import dao.CustomerDaoImpl;
import exception.*;
import utility.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AdminShow extends CustomerDaoImpl implements Show_all {
    @Override
    public List<Bus> showBuses() throws BusException {
        try (Connection conn = DBUtil.provideConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM bus");
            ResultSet rs = ps.executeQuery();
            List<Bus> buses = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String brand = rs.getString("brand");
                int capacity = rs.getInt("capacity");
                int boardNumber = rs.getInt("board_number");
                int licenseNumber = rs.getInt("license_number");
                Bus bus = new Bus(id, brand, capacity, boardNumber, licenseNumber);
                buses.add(bus);
            }
            return buses;
        } catch (SQLException e) {
            throw new BusException("Error fetching buses: " + e.getMessage());
        }
    }

    public List<Route> showRoutes() throws RouteException {
        try (Connection conn = DBUtil.provideConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM route");
            ResultSet rs = ps.executeQuery();
            List<Route> routes = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String startCity = rs.getString("start_city");
                String targetCity = rs.getString("target_city");
                int distance = rs.getInt("distance");
                Route route = new Route(id, startCity, targetCity, distance);
                routes.add(route);
            }
            return routes;
        } catch (SQLException e) {
            throw new RouteException("Error fetching routes: " + e.getMessage());
        }
    }

    public List<User> showUsers() throws CustomerException {
        try (Connection conn = DBUtil.provideConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM user");
            ResultSet rs = ps.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("firstname");
                String lastName = rs.getString("lastname");
                int admin_level = rs.getInt("admin_level");
                String email = rs.getString("email");
                String password = rs.getString("password");
                User user = new User(id, firstName, lastName, admin_level, email, password);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new CustomerException("Error fetching users: " + e.getMessage());
        }
    }

    public List<Trip> showTrips() throws TripException {
        try (Connection conn = DBUtil.provideConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM trip");
            ResultSet rs = ps.executeQuery();
            List<Trip> trips = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                int busId = rs.getInt("bus_id");
                int routeId = rs.getInt("route_id");
                int fare = rs.getInt("fare");
                boolean canceled = rs.getBoolean("canceled");
                boolean postponed = rs.getBoolean("postponed");
                LocalDateTime startTime = rs.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("end_time").toLocalDateTime();
                Trip trip = new Trip(id, busId, routeId, fare, startTime, endTime, postponed, canceled);
                trips.add(trip);
            }
            return trips;
        } catch (SQLException e) {
            throw new TripException("Error fetching trips: " + e.getMessage());
        }
    }

    public List<Booking> showBookings() throws BookingException {
        try (Connection conn = DBUtil.provideConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM booking");
            ResultSet rs = ps.executeQuery();
            List<Booking> bookings = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                int tripId = rs.getInt("trip_id");
                int userId = rs.getInt("user_id");
                boolean canceled = rs.getBoolean("canceled");
                Booking booking = new Booking(id, tripId, userId, canceled);
                bookings.add(booking);
            }
            return bookings;
        } catch (SQLException e) {
            throw new BookingException("Error fetching bookings: " + e.getMessage());
        }
    }
}
