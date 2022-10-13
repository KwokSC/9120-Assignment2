package Business;

import java.util.Vector;

import Data.RepositoryProviderFactory;

/**
 * Encapsulates any business logic to be executed on the app server; 
 * and uses the data layer for data queries/creates/updates/deletes
 * @author IwanB
 *
 */
public class BookingProvider implements IBookingProvider{

	/**
	 * check credentials is in the database and return their agentId (or 0 if not known)
	 * @param userName : the userName of agent credentials
	 * @param password : the password of agent credentials
	 */
	@Override
	public int checkUserCredentials(String userName, String password) {
		return RepositoryProviderFactory.getInstance().getRepositoryProvider().checkUserCredentials(userName, password);
	}

	/**
	 * Update the details for a given booking
	 * @param booking : the booking for which to update details
	 */
	@Override
	public void updateBooking(Booking booking) {
		RepositoryProviderFactory.getInstance().getRepositoryProvider().updateBooking(booking);
	}

	/**
	 * Find the bookings associated in some way with a sales agent
	 * Bookings associated with the agent id parameter below should be included in the result
	 * @param id
	 * @return
	 */
	@Override
	public Vector<Booking> findBookingsBySalesAgent(int id) {		
		return RepositoryProviderFactory.getInstance().getRepositoryProvider().findBookingsBySalesAgent(id);
	}
	
	/**
	 * Add the details for a new booking to the database
	 * @param booking: the new booking to add
	 */
	@Override
	public void addBooking(Booking booking) {
		RepositoryProviderFactory.getInstance().getRepositoryProvider().addBooking(booking);
	}

	/**
	 * Given an expression searchString like 'word' or 'this phrase', this method should return any bookings
	 * that contains this phrase.
	 * @param searchString: the searchString to use for finding bookings in the database
	 * @return
	 */
	@Override
	public Vector<Booking> findBookingsByCustomerAgentPerformance(String searchString) {
		return RepositoryProviderFactory.getInstance().getRepositoryProvider().findBookingsByCustomerAgentPerformance(searchString);

	}

}
