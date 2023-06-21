package dao;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import exception.CustomerException;
import bean.Bus;
import bean.Booking;
import bean.Trip;
import exception.BusException;
import utility.DBUtil;

public class CustomerDaoImpl implements CustomerDao {

 protected static int user_id;
	public static boolean isAdmin() throws CustomerException {
		try (Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
			ps.setInt(1, user_id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("admin_level") > 0;
			} else {
				throw new CustomerException("User not found");
			}
		} catch (SQLException e) {
			throw new CustomerException(e.getMessage());
		}
	}




	@Override
	public String login(int adId, String password) throws CustomerException {
		String result = null;

		try (Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE id = ? AND password = ?");
			ps.setInt(1, adId);
			String h = hash(password, adId);
			System.out.println(h);
			ps.setString(2, h);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				result = "Login Successful...";
				user_id = adId;
			} else {
				throw new CustomerException("Invalid Id or Password");
			}
		} catch (SQLException | NoSuchAlgorithmException e) {
			throw new CustomerException(e.getMessage());
		}

		return result;
	}

	private static String hash(String str, int number) throws NoSuchAlgorithmException {
		String input = str + number;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

		// Перетворення байтового масиву на шістнадцяткове представлення
		StringBuilder hexString = new StringBuilder();
		for (byte b : encodedHash) {
			String hex = Integer.toHexString(0xff & b);
			if (hex.length() == 1) {
				hexString.append('0');
			}
			hexString.append(hex);
		}

		return hexString.toString();
	}

	@Override
	public int register(String firstName, String lastName, String email, String password) throws CustomerException {
		String INSERT_CUSTOMER_SQL = "INSERT INTO user (firstname, lastname, email, password) VALUES (?, ?, ?, ?)";
		int generatedId = 0;

		try (Connection connection = DBUtil.provideConnection()) {
			PreparedStatement statement = connection.prepareStatement(INSERT_CUSTOMER_SQL, Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, email);
			statement.setString(4, "");

			statement.executeUpdate();

			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				generatedId = rs.getInt(1);
			}

			PreparedStatement ps = connection.prepareStatement("UPDATE user SET password = ? WHERE id = ?");
			String h = hash(password, generatedId);
			System.out.println(h);
			ps.setString(1, h);
			ps.setInt(2, generatedId);

			int rowsAffected = ps.executeUpdate();

		} catch (SQLException | NoSuchAlgorithmException e) {
			throw new CustomerException("Error registering customer: " + e.getMessage());
		}

		return generatedId;
	}


	// Отримання короткої інформації про бронювання за його id
// Отримання короткої інформації про бронювання за його id та user_id
	public String shortBookingInfo(int id) throws CustomerException {
		String SELECT_BOOKING_INFO_SQL = "SELECT b.id, b.trip_id, t.bus_id, t.route_id, t.fare,t.canceled, t.postponed, t.start_time, t.end_time, r.start_city " +
				"FROM booking b " +
				"INNER JOIN trip t ON b.trip_id = t.id " +
				"INNER JOIN route r ON t.route_id = r.id " +
				"WHERE b.id = ? AND b.user_id = ?";
		try (Connection connection = DBUtil.provideConnection()) {
			PreparedStatement statement = connection.prepareStatement(SELECT_BOOKING_INFO_SQL);
			statement.setInt(1, id);
			statement.setInt(2, user_id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int bookingId = resultSet.getInt("id");
				int fare = resultSet.getInt("fare");
				LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
				LocalDateTime endTime = resultSet.getTimestamp("end_time").toLocalDateTime();
				String startCity = resultSet.getString("start_city");
				int tripId = resultSet.getInt("trip_id");
				int busId = resultSet.getInt("bus_id");
				int routeId = resultSet.getInt("route_id");
				boolean canceled = resultSet.getBoolean("canceled");
				boolean postponed = resultSet.getBoolean("postponed");

				String tripStatus = (new Trip(tripId, busId, routeId, fare, startTime, endTime, postponed, canceled)).getStatus();

				return "Booking ID: " + bookingId + "\n" +
						"Fare: " + fare + "\n" +
						"Trip status: " + tripStatus + "\n" +
						"Start Time: " + startTime + "\n" +
						"Start City: " + startCity;
			} else {
				throw new CustomerException("Booking not found for the given user ID");
			}
		} catch (SQLException e) {
			throw new CustomerException("Error retrieving booking information: " + e.getMessage());
		}
	}

	public String detailedBookingInfo(int id) throws CustomerException {
		String SELECT_DETAILED_BOOKING_INFO_SQL = "SELECT b.id,b.trip_id, t.*, r.*, bus.* " +
				"FROM booking b " +
				"INNER JOIN trip t ON b.trip_id = t.id " +
				"INNER JOIN route r ON t.route_id = r.id " +
				"INNER JOIN bus ON t.bus_id = bus.id " +
				"WHERE b.id = ? AND b.user_id = ?";
		try (Connection connection = DBUtil.provideConnection()) {
			PreparedStatement statement = connection.prepareStatement(SELECT_DETAILED_BOOKING_INFO_SQL);
			statement.setInt(1, id);
			statement.setInt(2, user_id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int bookingId = resultSet.getInt("id");
				int tripId = resultSet.getInt("trip_id");
				int busId = resultSet.getInt("bus_id");
				int routeId = resultSet.getInt("route_id");
				boolean canceled = resultSet.getBoolean("canceled");
				boolean postponed = resultSet.getBoolean("postponed");
				int fare = resultSet.getInt("fare");
				LocalDateTime startTime = resultSet.getTimestamp("start_time").toLocalDateTime();
				LocalDateTime endTime = resultSet.getTimestamp("end_time").toLocalDateTime();
				String startCity = resultSet.getString("start_city");
				String targetCity = resultSet.getString("target_city");
				String busBrand = resultSet.getString("brand");
				int busCapacity = resultSet.getInt("capacity");
				int licenseNumber = resultSet.getInt("license_number");

				String tripStatus = (new Trip(tripId, busId, routeId, fare, startTime, endTime, postponed, canceled)).getStatus();

				return "Booking ID: " + bookingId + "\n" +
						"Fare: " + fare + "\n" +
						"Trip status: " + tripStatus + "\n" +
						"Start Time: " + startTime + "\n" +
						"End Time: " + endTime + "\n" +
						"Start City: " + startCity + "\n" +
						"Target City: " + targetCity + "\n" +
						"Bus Brand: " + busBrand + "\n" +
						"Bus Capacity: " + busCapacity + "\n" +
						"License Number: " + licenseNumber;
			} else {
				throw new CustomerException("Booking not found for the given user ID");
			}
		} catch (SQLException e) {
			throw new CustomerException("Error retrieving booking information: " + e.getMessage());
		}
	}




	public List<Booking> showTickets() throws CustomerException {
		List<Booking> bookings = new ArrayList<>();
		String SELECT_BOOKINGS_SQL = "SELECT * FROM booking WHERE user_id = ? AND canceled = false";
		try (Connection connection = DBUtil.provideConnection()) {
			PreparedStatement statement = connection.prepareStatement(SELECT_BOOKINGS_SQL);
			statement.setInt(1, user_id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int bookingId = resultSet.getInt("id");
				int tripId = resultSet.getInt("trip_id");
				boolean canceled = resultSet.getBoolean ("canceled");


				Booking booking = new Booking(bookingId, tripId, user_id, canceled);
				bookings.add(booking);
			}
		} catch (SQLException e) {
			throw new CustomerException("Error retrieving bookings: " + e.getMessage());
		}
		return bookings;
	}

	@Override
	public List<Trip> findTrips(String source, String destination, String range0fDate, String rangeOFare, String sortCriterion) throws BusException {

		List<Trip> trips = new ArrayList<>();

		try (Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement(makeSQL(source,  destination, rangeOFare, range0fDate, sortCriterion));
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				int busId = rs.getInt("bus_id");
				int routeId = rs.getInt("route_id");
				int fare = rs.getInt("fare");
				LocalDateTime startTime = rs.getTimestamp("start_time").toLocalDateTime();
				LocalDateTime endTime = rs.getTimestamp("end_time").toLocalDateTime();
				Trip trip = new Trip(id, busId, routeId, fare, startTime, endTime);
				trips.add(trip);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return trips;
	}



	public String makeSQL(String start_city, String target_city, String rangeOFare, String range0fDate, String sortCriterion) {
		String orderBy = "";

		if (sortCriterion.equals("1")) {
			orderBy = "ORDER BY end_time - start_time";
		} else if (sortCriterion.equals("2")) {
			orderBy = "ORDER BY fare";
		} else if (sortCriterion.equals("3")) {
			orderBy = "ORDER BY start_time";
		}

		String fareCondition = "";

		if (!rangeOFare.equals("-")) {
			// Перевірка формату вводу для діапазону вартості
			if (rangeOFare.contains("f")) {
				// Використано літеру "f" для визначення поля "fare"
				fareCondition = rangeOFare.replaceAll("f", "fare");
			} else {
				// Використано значення вартості
				fareCondition = "fare " + rangeOFare;
			}
		}


		LocalDate currentDate = LocalDate.now();

		// Перевірка формату вводу для дати
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yy-MM-dd");
		LocalDate startDate = currentDate; // Початкова дата за замовчуванням
		LocalDate endDate = currentDate.plusDays(14);


		if (!range0fDate.equals("-")) {
			// Розбиваємо діапазон дат на початкову та кінцеву дати
			String[] dateRange = range0fDate.split(" ");

			if (dateRange.length > 0) {
				String startDateStr = dateRange[0].trim();
				if (!startDateStr.equals("-")) {
					startDate = LocalDate.parse(startDateStr, dateFormatter);
				}

				if (dateRange.length > 1) {
					String endDateStr = dateRange[1].trim();
					if (!endDateStr.equals("-")) {
						endDate = LocalDate.parse(endDateStr, dateFormatter);
					}
				}
			}
		}

		// Формування SQL-запиту з врахуванням усіх умов
		String sqlQuery = "SELECT *, (end_time - start_time) AS time " +
				"FROM trip " +
				"INNER JOIN route ON trip.route_id = route.id " +
				"WHERE canceled = false AND start_city = '" + start_city + "' AND target_city = '" + target_city + "'";


		if (!fareCondition.isEmpty()) {
			sqlQuery += "AND " + fareCondition + " ";
		}

		sqlQuery += "AND start_time >= '" + startDate + "' AND start_time <= '" + endDate + "' " +
				orderBy;
		System.out.println(sqlQuery);
		return sqlQuery;
	}

	public static int numberOfReservedPlaces(int tripId) {
		int reservedPlaces = 0;

		try (Connection conn = DBUtil.provideConnection()) {
			String countBookingsQuery = "SELECT COUNT(*) AS num_bookings FROM booking WHERE trip_id = ?";

			PreparedStatement countBookingsStmt = conn.prepareStatement(countBookingsQuery);
			countBookingsStmt.setInt(1, tripId);

			ResultSet countBookingsResult = countBookingsStmt.executeQuery();

			if (countBookingsResult.next()) {
				reservedPlaces = countBookingsResult.getInt("num_bookings");
			}

			countBookingsResult.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return reservedPlaces;
	}

	public static int numberOfFreePlaces(int tripId) {
		int freePlaces = 0;

		try (Connection conn = DBUtil.provideConnection()) {
			String busCapacityQuery = "SELECT b.capacity - COALESCE(COUNT(bk.id), 0) AS free_places " +
					"FROM bus b " +
					"JOIN trip t ON b.id = t.bus_id " +
					"LEFT JOIN booking bk ON t.id = bk.trip_id AND bk.canceled = false " +
					"WHERE t.id = ?";

			PreparedStatement busCapacityStmt = conn.prepareStatement(busCapacityQuery);
			busCapacityStmt.setInt(1, tripId);

			ResultSet busCapacityResult = busCapacityStmt.executeQuery();

			if (busCapacityResult.next()) {
				freePlaces = busCapacityResult.getInt("free_places");
			}

			busCapacityResult.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return freePlaces;
	}



	@Override
	public String bookTicket(int trip_number, int  numberOfTickets) throws CustomerException {
		String msg = "Ticket could not be bought";
		int free = numberOfFreePlaces(trip_number);
		if(free >= numberOfTickets){
				for(int i = 0; i < numberOfTickets; i++){
					createBooking(trip_number);
				}
		}else {
			msg = "There aren't that many tickets";
		}
		return msg;
	}
	public void createBooking(int tripId) {
		try (Connection conn = DBUtil.provideConnection()) {
			// Перевірка, чи існує поїздка з вказаним tripId
			String tripExistsQuery = "SELECT COUNT(*) AS num_trips FROM trip WHERE id = ?";
			// Перевірка, чи існує користувач з вказаним userId
			String userExistsQuery = "SELECT COUNT(*) AS num_users FROM user WHERE id = ?";
			// Додавання нового бронювання
			String insertBookingQuery = "INSERT INTO booking (trip_id, user_id, created_at) VALUES (?, ?, ?)";

			PreparedStatement tripExistsStmt = conn.prepareStatement(tripExistsQuery);
			PreparedStatement userExistsStmt = conn.prepareStatement(userExistsQuery);
			PreparedStatement insertBookingStmt = conn.prepareStatement(insertBookingQuery);

			// Встановлюємо значення tripId і userId в параметри запитів
			tripExistsStmt.setInt(1, tripId);
			userExistsStmt.setInt(1, user_id);

			// Виконуємо запит для перевірки існування поїздки
			ResultSet tripExistsResult = tripExistsStmt.executeQuery();

			if (tripExistsResult.next()) {
				int numTrips = tripExistsResult.getInt("num_trips");

				if (numTrips == 0) {
					// Поїздка з вказаним tripId не існує
					System.out.println("Trip with ID " + tripId + " does not exist.");
					return;
				}
			}

			// Виконуємо запит для перевірки існування користувача
			ResultSet userExistsResult = userExistsStmt.executeQuery();

			if (userExistsResult.next()) {
				int numUsers = userExistsResult.getInt("num_users");

				if (numUsers == 0) {
					// Користувач з вказаним userId не існує
					System.out.println("User with ID " + user_id + " does not exist.");
					return;
				}
			}

			// Встановлюємо значення tripId, userId і status в параметри запиту вставки бронювання
			insertBookingStmt.setInt(1, tripId);
			insertBookingStmt.setInt(2, user_id);
			insertBookingStmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now())); // Встановлюємо поточну дату і час

			// Виконуємо запит для додавання бронювання
			int rowsAffected = insertBookingStmt.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("Booking created successfully.");
			} else {
				System.out.println("Failed to create booking.");
			}

			tripExistsResult.close();
			userExistsResult.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

		public String cancelBooking(int id) {
			String SELECT_BOOKING_SQL = "SELECT * FROM booking WHERE id = ? AND user_id = ?";
			String UPDATE_BOOKING_SQL = "UPDATE booking SET canceled = true WHERE id = ?";

			try (Connection connection = DBUtil.provideConnection();
				 PreparedStatement selectStatement = connection.prepareStatement(SELECT_BOOKING_SQL);
				 PreparedStatement updateStatement = connection.prepareStatement(UPDATE_BOOKING_SQL)) {

				// Перевірка ідентичності user_id
				selectStatement.setInt(1, id);
				selectStatement.setInt(2, user_id);
				ResultSet resultSet = selectStatement.executeQuery();

				if (!resultSet.next()) {
					return "Booking with ID " + id  + " not found.";
				}

				// Виконання скасування бронювання
				updateStatement.setInt(1, id);
				int rowsAffected = updateStatement.executeUpdate();

				if (rowsAffected > 0) {
					return "Booking with ID " + id + " has been canceled successfully.";
				} else {
					return "Failed to cancel booking with ID " + id + ".";
				}
			} catch (SQLException e) {
				return "Error canceling booking: " + e.getMessage();
			}
		}
}
