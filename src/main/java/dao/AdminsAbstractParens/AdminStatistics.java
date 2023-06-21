package dao.AdminsAbstractParens;

import bean.Bus;
import bean.Trip;
import bean.User;
import dao.AdminsFirstChoiceInt.See_satistics;
import utility.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AdminStatistics extends AdminCreate implements See_satistics {

    @Override
    public List<Bus> getBusesForRouteInDate(int routeId, LocalDate date) {
        List<Bus> buses = new ArrayList<>();

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT b.* FROM bus b " +
                    "JOIN trip t ON b.id = t.bus_id " +
                    "WHERE t.route_id = ? AND DATE(t.start_time) = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, routeId);
            stmt.setDate(2, Date.valueOf(date));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int busId = rs.getInt("id");
                String brand = rs.getString("brand");
                int capacity = rs.getInt("capacity");
                int boardNumber = rs.getInt("board_number");
                int licenseNumber = rs.getInt("license_number");

                Bus bus = new Bus(busId, brand, capacity, boardNumber, licenseNumber);
                buses.add(bus);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return buses;
    }

    @Override
    public List<Trip> getCanceledTrips() {
        List<Trip> canceledTrips = new ArrayList<>();

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT * FROM trip WHERE canceled = true";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int tripId = rs.getInt("id");
                int busId = rs.getInt("bus_id");
                int routeId = rs.getInt("route_id");
                int fare = rs.getInt("fare");
                LocalDateTime startTime = rs.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("end_time").toLocalDateTime();
                boolean canceled = rs.getBoolean("canceled");
                boolean postponed = rs.getBoolean("postponed");

                Trip trip = new Trip(tripId, busId, routeId, fare, startTime, endTime, canceled, postponed);
                canceledTrips.add(trip);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return canceledTrips;
    }

    @Override
    public int getTotalCanceledTrips() {
        int totalCanceledTrips = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(*) AS total_canceled_trips FROM trip WHERE canceled = true";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                totalCanceledTrips = rs.getInt("total_canceled_trips");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalCanceledTrips;
    }

    @Override
    public int getTotalDelayedTrips() {
        int totalDelayedTrips = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(*) AS total_delayed_trips FROM trip WHERE postponed = true";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                totalDelayedTrips = rs.getInt("total_delayed_trips");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalDelayedTrips;
    }

    @Override
    public int getTotalTicketsReturnedForDelays() {
        int totalTicketsReturned = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(*) AS total_tickets_returned FROM booking " +
                    "WHERE trip_id IN (SELECT id FROM trip WHERE postponed = true)";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                totalTicketsReturned = rs.getInt("total_tickets_returned");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalTicketsReturned;
    }

    @Override
    public List<User> getPassengersOnCompletedTrip(int tripId) {
        List<User> passengers = new ArrayList<>();

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT u.* FROM user u " +
                    "JOIN booking b ON u.id = b.user_id " +
                    "WHERE b.trip_id = ? AND b.canceled = false";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, tripId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                int adminLevel = rs.getInt("admin_level");
                String email = rs.getString("email");
                String password = rs.getString("password");

                User passenger = new User(userId, firstname, lastname, adminLevel, email, password);
                passengers.add(passenger);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passengers;
    }

    @Override
    public int getDailyPassengerCount(LocalDate date) {
        int dailyPassengerCount = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(DISTINCT user_id) AS daily_passenger_count FROM booking " +
                    "WHERE DATE_FORMAT(created_at, '%Y-%m-%d') = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, date.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                dailyPassengerCount = rs.getInt("daily_passenger_count");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dailyPassengerCount;
    }

    @Override
    public int getDailyRevenue(LocalDate date) {
        int dailyRevenue = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT SUM(fare) AS daily_revenue FROM booking " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "WHERE DATE_FORMAT(booking.created_at, '%Y-%m-%d') = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, date.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                dailyRevenue = rs.getInt("daily_revenue");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dailyRevenue;
    }

    @Override
    public int getMonthlyPassengerCount(LocalDate date) {
        int monthlyPassengerCount = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(DISTINCT user_id) AS monthly_passenger_count FROM booking " +
                    "WHERE YEAR(created_at) = ? AND MONTH(created_at) = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, date.getYear());
            stmt.setInt(2, date.getMonthValue());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                monthlyPassengerCount = rs.getInt("monthly_passenger_count");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monthlyPassengerCount;
    }

    @Override
    public int getMonthlyRevenue(LocalDate date) {
        int monthlyRevenue = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT SUM(fare) AS monthly_revenue FROM booking " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "WHERE YEAR(booking.created_at) = ? AND MONTH(booking.created_at) = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, date.getYear());
            stmt.setInt(2, date.getMonthValue());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                monthlyRevenue = rs.getInt("monthly_revenue");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return monthlyRevenue;
    }


    @Override
    public int getPassengerCountForRoute(int routeId) {
        int passengerCount = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(DISTINCT user_id) AS passenger_count FROM booking " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "WHERE trip.route_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, routeId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                passengerCount = rs.getInt("passenger_count");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passengerCount;
    }

    @Override
    public int getRevenueForRoute(int routeId) {
        int revenue = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT SUM(fare) AS revenue FROM booking " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "WHERE trip.route_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, routeId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                revenue = rs.getInt("revenue");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return revenue;
    }
    @Override
    public int getTicketCountForRouteInTimeRange(int routeId, LocalDateTime startTime, LocalDateTime endTime) {
        int ticketCount = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(*) AS ticket_count FROM booking " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "WHERE trip.route_id = ? AND trip.start_time >= ? AND trip.end_time <= ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, routeId);
            stmt.setObject(2, startTime);
            stmt.setObject(3, endTime);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ticketCount = rs.getInt("ticket_count");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticketCount;
    }

    @Override
    public double getAverageTicketPriceForRouteInTimeRange(int routeId, LocalDateTime startTime, LocalDateTime endTime) {
        double averageTicketPrice = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT AVG(trip.fare) AS average_ticket_price FROM booking " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "WHERE trip.route_id = ? AND trip.start_time >= ? AND trip.end_time <= ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, routeId);
            stmt.setObject(2, startTime);
            stmt.setObject(3, endTime);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                averageTicketPrice = rs.getDouble("average_ticket_price");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return averageTicketPrice;
    }

    @Override
    public int getTicketCountForRoute(int routeId) {
        int ticketCount = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(*) AS ticket_count FROM booking " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "WHERE trip.route_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, routeId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ticketCount = rs.getInt("ticket_count");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ticketCount;
    }

    @Override
    public double getAverageTicketPriceForRoute(int routeId) {
        double averageTicketPrice = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT AVG(trip.fare) AS average_ticket_price FROM booking " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "WHERE trip.route_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, routeId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                averageTicketPrice = rs.getDouble("average_ticket_price");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return averageTicketPrice;
    }

    @Override
    public int getSoldTicketsOnDate(LocalDate date) {
        int soldTickets = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(*) AS sold_tickets FROM booking " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "WHERE DATE(trip.start_time) = ? AND booking.canceled = false";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setObject(1, date);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                soldTickets = rs.getInt("sold_tickets");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return soldTickets;
    }

    @Override
    public int getUnsoldTicketsOnDate(LocalDate date) {
        int unsoldTickets = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT (bus.capacity - COALESCE(COUNT(booking.id), 0)) AS unsold_tickets " +
                    "FROM trip " +
                    "LEFT JOIN booking ON trip.id = booking.trip_id AND booking.canceled = false " +
                    "JOIN bus ON trip.bus_id = bus.id " +
                    "WHERE DATE(trip.start_time) = ? " +
                    "AND trip.canceled = false " +
                    "GROUP BY trip.id";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setObject(1, date);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                unsoldTickets = rs.getInt("unsold_tickets");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unsoldTickets;
    }




    @Override
    public int getReturnedTicketsOnDate(LocalDate date) {
        int returnedTickets = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(*) AS returned_tickets FROM booking " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "WHERE DATE(trip.start_time) = ? AND booking.canceled = true";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setObject(1, date);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                returnedTickets = rs.getInt("returned_tickets");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnedTickets;
    }

    @Override
    public int getSoldTicketsOnRoute(int routeId) {
        int soldTickets = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(*) AS sold_tickets FROM booking " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "WHERE trip.route_id = ? AND booking.canceled = false";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, routeId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                soldTickets = rs.getInt("sold_tickets");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return soldTickets;
    }

    @Override
    public int getUnsoldTicketsOnRoute(int routeId) {
        int unsoldTickets = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT (bus.capacity - COALESCE(COUNT(booking.id), 0)) AS unsold_tickets " +
                    "FROM trip " +
                    "LEFT JOIN booking ON trip.id = booking.trip_id " +
                    "JOIN bus ON trip.bus_id = bus.id " +
                    "WHERE trip.route_id = ? AND booking.canceled = false " +
                    "GROUP BY trip.id";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, routeId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                unsoldTickets = rs.getInt("unsold_tickets");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unsoldTickets;
    }


    @Override
    public int getReturnedTicketsOnRoute(int routeId) {
        int returnedTickets = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(*) AS returned_tickets FROM booking " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "WHERE trip.route_id = ? AND booking.canceled = true";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, routeId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                returnedTickets = rs.getInt("returned_tickets");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnedTickets;
    }

    @Override
    public int getSoldTicketsOnTrip(int tripId) {
        int soldTickets = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(*) AS sold_tickets FROM booking WHERE trip_id = ? AND canceled = false";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, tripId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                soldTickets = rs.getInt("sold_tickets");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return soldTickets;
    }

    @Override
    public int getUnsoldTicketsOnTrip(int tripId) {
        int unsoldTickets = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT (bus.capacity - COUNT(*)) AS unsold_tickets " +
                    "FROM booking " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "JOIN bus ON trip.bus_id = bus.id " +
                    "WHERE trip.id = ? AND booking.canceled = false";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, tripId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                unsoldTickets = rs.getInt("unsold_tickets");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unsoldTickets;
    }


    @Override
    public int getReturnedTicketsOnTrip(int tripId) {
        int returnedTickets = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(*) AS returned_tickets FROM booking WHERE trip_id = ? AND canceled = true";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, tripId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                returnedTickets = rs.getInt("returned_tickets");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return returnedTickets;
    }

    @Override
    public boolean hasTicketWithLastName(String lastName) {
        boolean hasTicket = false;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT EXISTS(SELECT 1 FROM booking " +
                    "JOIN user ON booking.user_id = user.id " +
                    "WHERE user.lastname = ?) AS has_ticket";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, lastName);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                hasTicket = rs.getBoolean("has_ticket");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hasTicket;
    }

    @Override
    public Trip getTripByPassengerLastName(String lastName) {
        Trip trip = null;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT trip.* FROM booking " +
                    "JOIN user ON booking.user_id = user.id " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "WHERE user.lastname = ? LIMIT 1";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, lastName);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                trip = extractTripFromResultSet(rs);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return trip;
    }

    @Override
    public int getPassengerCountOnTrip(int tripId) {
        int passengerCount = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT COUNT(user_id) AS passenger_count FROM booking " +
                    "WHERE trip_id = ? AND canceled = false";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, tripId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                passengerCount = rs.getInt("passenger_count");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passengerCount;
    }

    @Override
    public int getTripRevenue(int tripId) {
        int tripRevenue = 0;

        try (Connection conn = DBUtil.provideConnection()) {
            String query = "SELECT SUM(fare) AS trip_revenue FROM booking " +
                    "JOIN trip ON booking.trip_id = trip.id " +
                    "WHERE trip.id = ? AND booking.canceled = false";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, tripId);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                tripRevenue = rs.getInt("trip_revenue");
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tripRevenue;
    }
    private Trip extractTripFromResultSet(ResultSet rs) throws SQLException {
        Trip trip = new Trip(rs.getInt("id"), rs.getInt("bus_id"),rs.getInt("route_id"),rs.getInt("route_id"),rs.getTimestamp("start_time").toLocalDateTime(),rs.getTimestamp("end_time").toLocalDateTime(),rs.getBoolean("canceled"),rs.getBoolean("postponed")  );
        return trip;
    }

    // ... реалізація інших методів ...
}
