package Data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.CallableStatement;
import java.util.Vector;

import org.postgresql.ds.PGSimpleDataSource;
import Business.Booking;
import Presentation.IRepositoryProvider;

/**
 * Encapsulates create/read/update/delete operations to PostgreSQL database
 * @author IwanB
 */
public class PostgresRepositoryProvider implements IRepositoryProvider {
	//DB connection parameters - ENTER YOUR LOGIN AND PASSWORD HERE
    private final String userid = "y21s1c9120_unikey";
    private final String passwd = "passwd";
    private final String myHost = "soit-db-pro-2.ucc.usyd.edu.au";

	private Connection openConnection() throws SQLException
	{
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
	 * @param userName: the user userName trying to login
	 * @param password: the user password used to login
	 * @return userId for user (return 0 if userName not found or invalid)
	 */
	@Override
	public int checkUserCredentials(String userName, String password) {
	    //TODO - validate a user login request
		return 1;
	}
	
	/**
	 * Find associated bookings given a userId as per the assignment description
	 * @param userId is the agent id
	 * @return
	 */
	@Override
	public Vector<Booking> findBookingsBySalesAgent(int userId) {		

		// TODO - list all bookings associated with a sales agent in DB
		Vector<Booking> results = new Vector<Booking>();
		
		Booking booking1 = new Booking();
		booking1.setNo(1);
		booking1.setCustomer("Bob Smith");
		booking1.setPerformance("The Lion King");
		booking1.setPerformanceDate(LocalDate.parse("2021-06-05"));
		booking1.setAgent("novak");
		booking1.setInstructions("I'd like to book 3 additional seats");

		Booking booking2 = new Booking();
		booking2.setNo(5);
		booking2.setCustomer("Mia Clark");
		booking2.setPerformance("Disney's Frozen");
		booking2.setPerformanceDate(LocalDate.parse("2021-07-18"));
		booking2.setAgent("novak");
		booking2.setInstructions("Please upgrade my seats to Box Seats");
		
		Booking booking3 = new Booking();
		booking3.setNo(3);
		booking3.setCustomer("Ruby Miller");
		booking3.setPerformance("Death of a Salesman");
		booking3.setPerformanceDate(LocalDate.parse("2021-07-26"));
		booking3.setAgent("novak");
		booking3.setInstructions("I want to add meals to my booking");
		
		results.add(booking1);
		results.add(booking2);
		results.add(booking3);

		return results;
	}

	/**
	 * Find a list of bookings based on the searchString provided as parameter
	 * @param searchString: see assignment description for search specification
	 * @return
	 */
	@Override
	public Vector<Booking> findBookingsByCustomerAgentPerformance(String searchString) {		

		// TODO - find a list of bookings in DB based on searchString input
		Vector<Booking> results = new Vector<Booking>();

		Booking booking1 = new Booking();
		booking1.setNo(1);
		booking1.setCustomer("Bob Smith");
		booking1.setPerformance("The Lion King");
		booking1.setPerformanceDate(LocalDate.parse("2021-06-05"));
		booking1.setAgent("novak");
		booking1.setInstructions("I'd like to book 3 additional seats");

		Booking booking2 = new Booking();
		booking2.setNo(4);
		booking2.setCustomer("Peter Wood");
		booking2.setPerformance("The Lion King");
		booking2.setPerformanceDate(LocalDate.parse("2021-06-05"));
		booking2.setAgent("jeff");
		booking2.setInstructions("Can you please waitlist 4 seats?");
		
		results.add(booking1);
		results.add(booking2);

		return results;
	}

	/**
	 * Add a new booking into the Database
	 * @param booking: the new booking to add
	 */
	@Override
	public void addBooking(Booking booking) {
	    //TODO - add a new booking into DB
	}

	/**
	 * Update the details of a given booking
	 * @param booking: the booking for which to update details
	 */
	@Override
	public void updateBooking(Booking booking) {
		//TODO - update the booking in DB
	}
}
