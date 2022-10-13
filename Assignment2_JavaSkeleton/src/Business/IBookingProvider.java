package Business;

import java.util.Vector;

/**
 * Encapsulates any business logic to be executed on the app server; 
 * and uses the data layer for data queries/creates/updates/deletes
 * @author IwanB
 *
 */
public interface IBookingProvider {

	/**
	 * check credentials is in the database and return their doctorId (or 0 if not known)
	 * @param userName : the userName of doctor credentials
	 * @param password : the password of doctor credentials
	 */
	public int checkUserCredentials(String userName, String password);
	
	/**
	 * Update the details for a given booking
	 * @param booking : the booking for which to update details
	 */
	public void updateBooking(Booking booking);
	
	/**
	 * Find the bookings associated in some way with a user
	 * Bookings which have the id parameter below should be included in the result
	 * @param id
	 * @return
	 */
	public Vector<Booking> findBookingsBySalesAgent(int id);
	
	/**
	 * Given an expression searchString like 'word' or 'this phrase', this method should return any bookings 
	 * that contains this phrase.
	 * @param searchString: the searchString to use for finding bookings in the database
	 * @return
	 */
	public Vector<Booking> findBookingsByCustomerAgentPerformance(String searchString);	
	
	/**
	 * Add the details for a new booking to the database
	 * @param booking: the new booking to add
	 */
	public void addBooking(Booking booking);
}
