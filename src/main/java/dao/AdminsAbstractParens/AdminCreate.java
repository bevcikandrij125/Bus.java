package dao.AdminsAbstractParens;

import bean.*;
import dao.AdminsFirstChoiceInt.Create;
import exception.*;
import utility.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AdminCreate extends AdminShow implements Create {


    @Override
    public void createBus(Bus bus) throws BusException {
        try (Connection conn = DBUtil.provideConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO bus (brand, capacity, board_number, license_number) VALUES (?, ?, ?, ?)");
            ps.setString(1, bus.getBrand());
            ps.setInt(2, bus.getCapacity());
            ps.setInt(3, bus.getBoardNumber());
            ps.setInt(4, bus.getLicenseNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BusException("Error creating bus: " + e.getMessage());
        }
    }

    @Override
    public void createRoute(Route route) throws RouteException {
        try (Connection conn = DBUtil.provideConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO route (start_city, target_city, distance) VALUES (?, ?, ?)");
            ps.setString(1, route.getStartCity());
            ps.setString(2, route.getTargetCity());
            ps.setInt(3, route.getDistance());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RouteException("Error creating route: " + e.getMessage());
        }
    }

    @Override
    public void createUser(User user) throws CustomerException {
        try (Connection conn = DBUtil.provideConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO user (firstname, lastname, is_admin, email, password) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setBoolean(3, user.isAdmin());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CustomerException("Error creating user: " + e.getMessage());
        }
    }

    @Override
    public void createTrip(Trip trip) throws TripException {
        try (Connection conn = DBUtil.provideConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO trip (bus_id, route_id, fare, start_time, end_time) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, trip.getBusId());
            ps.setInt(2, trip.getRouteId());
            ps.setInt(3, trip.getFare());
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(trip.getStartTime()));
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(trip.getEndTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TripException("Error creating trip: " + e.getMessage());
        }
    }

    @Override
    public void createBooking(Booking booking) throws BookingException {
        try (Connection conn = DBUtil.provideConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO booking (trip_id, user_id, canceled, created_at) VALUES (?, ?, ?, CURDATE())");
            ps.setInt(1, booking.getTripId());
            ps.setInt(2, booking.getUserId());
            ps.setBoolean(3, booking.isCanceled());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new BookingException("Error creating booking: " + e.getMessage());
        }
    }

}
