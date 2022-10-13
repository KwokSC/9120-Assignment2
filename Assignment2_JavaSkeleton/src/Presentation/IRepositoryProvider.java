package Presentation;

import java.util.Vector;

import Business.Booking;

/**
 * Encapsulates create/read/update/delete operations to database
 * @author IwanB
 *
 */
public interface IRepositoryProvider {
	/**
	 * check credentials is in the database and return their agentId (or 0 if not known)
	 * @param userName : the userName of agent credentials
	 * @param password : the password of agent credentials
	 */
	public int checkUserCredentials(String userName, String password);
	
	/**
	 * Update the details for a given booking
	 * @param booking : the booking for which to update details
	 */
	public void updateBooking(Booking booking);
	
	/**
	 * Find the associated bookings as per the assignment description
	 * @param id the agent id
	 * @return
	 */
	public Vector<Booking>  findBookingsBySalesAgent(int id);
	
	/**
	 * Find the associated bookings based on the searchString provided as the parameter
	 * @param searchString: see assignment description search specification
	 * @return
	 */
	public Vector<Booking> findBookingsByCustomerAgentPerformance(String searchString);	
	
	/**
	 * Add the details for a new booking to the database
	 * @param booking: the new booking to add
	 */
	public void addBooking(Booking booking);
}
