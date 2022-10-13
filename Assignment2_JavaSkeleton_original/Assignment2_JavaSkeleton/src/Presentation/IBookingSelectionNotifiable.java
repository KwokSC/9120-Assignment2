package Presentation;

import Business.Booking;

/**
 * 
 * @author IwanB
 * 
 * Used to notify any interested object that implements this interface
 * and registers with BookingListPanel of an BookingSelection
 *
 */
public interface IBookingSelectionNotifiable {
	public void bookingSelected(Booking booking);
}
