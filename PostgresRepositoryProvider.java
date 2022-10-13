package Data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.postgresql.ds.PGSimpleDataSource;
import Business.Booking;
import Presentation.IRepositoryProvider;

/**
 * Encapsulates create/read/update/delete operations to PostgreSQL database
 * 
 * @author IwanB
 */
public class PostgresRepositoryProvider implements IRepositoryProvider {
	// DB connection parameters - ENTER YOUR LOGIN AND PASSWORD HERE
	private final String userid = "y21s1c9120_sguo0672";
	private final String passwd = "510191768";
	private final String myHost = "soit-db-pro-2.ucc.usyd.edu.au";

	private Connection openConnection() throws SQLException {
		PGSimpleDataSource source = new PGSimpleDataSource();
		source.setServerName(myHost);
		source.setDatabaseName(userid);
		source.setUser(userid);
		source.setPassword(passwd);
		Connection conn = source.getConnection();

		return conn;
	}

	/**
	 * Validate a user login request
	 * 
	 * @param userName: the user userName trying to login
	 * @param password: the user password used to login
	 * @return userId for user (return 0 if userName not found or invalid)
	 */
	@Override
	public int checkUserCredentials(String userName, String password) {
		Connection conn = null;
		try {
			conn = openConnection();
			PreparedStatement stmt = conn.prepareStatement("SELECT AGENTID, PASSWORD FROM AGENT WHERE USERNAME=?");
			stmt.setString(1, userName);
			ResultSet rs = stmt.executeQuery();
			String pwd = "";
			int id = 0;
			while (rs.next()) {
				pwd = rs.getString("PASSWORD");
				id = rs.getInt("AGENTID");
			}
			if (password.equals(pwd)) {
				return id;
			} else
				JOptionPane.showMessageDialog(null, "Invalid username or password", "Login failed", JOptionPane.ERROR_MESSAGE);
				return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return 0;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Find associated bookings given a userId as per the assignment description
	 * 
	 * @param userId is the agent id
	 * @return
	 */
	@Override
	public Vector<Booking> findBookingsBySalesAgent(int userId) {
		Vector<Booking> results = new Vector<Booking>();
		Connection conn = null;
		try {
			conn = openConnection();
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT BOOKING_NO, CUSTOMER.FIRSTNAME, CUSTOMER.LASTNAME, PERFORMANCE, PERFORMANCE_DATE, USERNAME, INSTRUCTION FROM (BOOKING JOIN AGENT ON AGENTID = BOOKED_BY) JOIN CUSTOMER ON CUSTOMER = EMAIL WHERE BOOKED_BY=? ORDER BY CUSTOMER.FIRSTNAME, CUSTOMER.LASTNAME");
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Booking tmp = new Booking();
				tmp.setNo(rs.getInt("BOOKING_NO"));
				tmp.setCustomer(rs.getString("FIRSTNAME")+" "+rs.getString("LASTNAME"));
				tmp.setPerformance(rs.getString("PERFORMANCE"));
				tmp.setPerformanceDate(rs.getDate("PERFORMANCE_DATE").toLocalDate());
				tmp.setAgent(rs.getString("USERNAME"));
				tmp.setInstructions(rs.getString("INSTRUCTION"));
				results.add(tmp);
			}
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Find a list of bookings based on the searchString provided as parameter
	 * 
	 * @param searchString: see assignment description for search specification
	 * @return
	 */
	@Override
	public Vector<Booking> findBookingsByCustomerAgentPerformance(String searchString) {

		// TODO - find a list of bookings in DB based on searchString input
		Vector<Booking> results = new Vector<Booking>();
		Connection conn = null;
		try {
			conn = openConnection();
			PreparedStatement stmt = conn.prepareStatement(
					"SELECT BOOKING_NO, CUSTOMER.FIRSTNAME, CUSTOMER.LASTNAME, PERFORMANCE, PERFORMANCE_DATE, USERNAME, INSTRUCTION FROM (BOOKING JOIN AGENT ON AGENTID = BOOKED_BY) JOIN CUSTOMER ON CUSTOMER = EMAIL WHERE CUSTOMER LIKE ? OR PERFORMANCE LIKE ? OR USERNAME LIKE ?");
			stmt.setString(1, "%" + searchString + "%");
			stmt.setString(2, "%" + searchString + "%");
			stmt.setString(3, "%" + searchString + "%");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Booking tmp = new Booking();
				tmp.setNo(rs.getInt("BOOKING_NO"));
				tmp.setCustomer(rs.getString("FIRSTNAME")+" "+rs.getString("LASTNAME"));
				tmp.setPerformance(rs.getString("PERFORMANCE"));
				tmp.setPerformanceDate(rs.getDate("PERFORMANCE_DATE").toLocalDate());
				tmp.setAgent(rs.getString("USERNAME"));
				tmp.setInstructions(rs.getString("INSTRUCTION"));
				results.add(tmp);
			}
			return results;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return null;
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Add a new booking into the Database
	 * 
	 * @param booking: the new booking to add
	 */
	@Override
	public void addBooking(Booking booking) {
		// TODO - add a new booking into DB
		Connection conn = null;
		try {
			conn = openConnection();
			CallableStatement stmt1 = conn.prepareCall("{call ADD_BOOKING(?,?,?,?,?)}");
			stmt1.setString(1, booking.getCustomer());
			stmt1.setString(2, booking.getPerformance());
			stmt1.setDate(3, Date.valueOf(booking.getPerformanceDate()));
			CallableStatement stmt2 = conn.prepareCall("{?=call OBTAIN_AGENTID_BY_NAME(?)}");
			stmt2.registerOutParameter(1, Types.INTEGER);
			stmt2.setString(2, booking.getAgent());
			stmt2.execute();
			int id = stmt2.getInt(1);
			stmt1.setInt(4, id);
			stmt1.setString(5, booking.getInstructions());
			stmt1.execute();
			JOptionPane.showMessageDialog(null, "Successfully add a booking", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Update the details of a given booking
	 * 
	 * @param booking: the booking for which to update details
	 */
	@Override
	public void updateBooking(Booking booking) {
		// TODO - update the booking in DB
		Connection conn = null;
		try {
			conn = openConnection();
			CallableStatement stmt1 = conn.prepareCall("{call UPDATE_BOOKING(?,?,?,?,?)}");
			stmt1.setString(1, booking.getPerformance());
			stmt1.setDate(2, Date.valueOf(booking.getPerformanceDate()));
			CallableStatement stmt2 = conn.prepareCall("{?=call OBTAIN_AGENTID_BY_NAME(?)}");
			stmt2.registerOutParameter(1, Types.INTEGER);
			stmt2.setString(2, booking.getAgent());
			stmt2.execute();
			int id = stmt2.getInt(1);
			stmt1.setInt(3, id);
			stmt1.setString(4, booking.getInstructions());
			stmt1.setInt(5, booking.getNo());
			stmt1.execute();
			JOptionPane.showMessageDialog(null, "Successfully update a booking", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
