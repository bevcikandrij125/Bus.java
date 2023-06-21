package dao;
import dao.AdminsAbstractParens.AdminStatistics;
import exception.*;
import utility.DBUtil;

import java.sql.*;
import java.time.LocalDateTime;

public class AdminDaoImpl extends AdminStatistics implements AdminDao {

	public static int getAdminLevel() throws CustomerException {
		try (Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE id = ?");
			ps.setInt(1, user_id);
			ResultSet rs = ps.executeQuery();
			return rs.getInt("admin_level");
		} catch (SQLException e) {
			throw new CustomerException(e.getMessage());
		}
	}
	@Override
	public String setAdminLevel(int userId, int adminLevel) throws CustomerException {
		try (Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("SELECT admin_level FROM user WHERE id = ?");
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				throw new CustomerException("User not found with id: " + userId);
			}

			int myLevel = getAdminLevel();
			int hisLevel = rs.getInt("admin_level");

			if (!(myLevel > adminLevel) || !(myLevel > hisLevel)) {
				return "Your admin level is too low";
			}
		} catch (SQLException e) {
			throw new CustomerException(e.getMessage());
		}

		try (Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("UPDATE user SET admin_level = ? WHERE id = ?");
			ps.setInt(1, adminLevel);
			ps.setInt(2, userId);
			int rowsUpdated = ps.executeUpdate();

			if (rowsUpdated == 0) {
				throw new CustomerException("User not found with id: " + userId);
			}

			return "Admin level set successfully for user with id: " + userId;
		} catch (SQLException e) {
			throw new CustomerException("Error setting admin level: " + e.getMessage());
		}
	}


	@Override
	public void postponeTrip(int tripId, LocalDateTime newStartTime, LocalDateTime newEndTime) throws TripException {
		try (Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("UPDATE trip SET start_time = ?, end_time = ? WHERE id = ?");
			ps.setTimestamp(1, Timestamp.valueOf(newStartTime));
			ps.setTimestamp(2, Timestamp.valueOf(newEndTime));
			ps.setInt(3, tripId);
			int rowsUpdated = ps.executeUpdate();
			if (rowsUpdated == 0) {
				throw new TripException("Trip not found with id: " + tripId);
			}
		} catch (SQLException e) {
			throw new TripException("Error updating trip: " + e.getMessage());
		}
	}

	@Override
	public void cancelTrip(int tripId) throws TripException {
		try (Connection conn = DBUtil.provideConnection()) {
			PreparedStatement ps = conn.prepareStatement("UPDATE trip SET canceled = true WHERE id = ?");
			ps.setInt(1, tripId);
			int rowsUpdated = ps.executeUpdate();
			if (rowsUpdated == 0) {
				throw new TripException("Trip not found with id: " + tripId);
			}
		} catch (SQLException e) {
			throw new TripException("Error canceling trip: " + e.getMessage());
		}
	}

}
