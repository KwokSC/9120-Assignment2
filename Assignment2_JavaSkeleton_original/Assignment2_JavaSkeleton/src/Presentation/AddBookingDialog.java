package Presentation;

import java.awt.BorderLayout;

import javax.swing.JDialog;

import Business.Booking;

/**
 * 
 * @author IwanB
 *
 * AddBookingDialog - used to add a new booking
 * 
 */
public class AddBookingDialog extends JDialog{

	/**
	 * 
	 */
	private static final long serialVersionUID = 173323780409671768L;
	
	/**
	 * detailPanel: reuse BookingDetailPanel to add bookings
	 */
	private BookingDetailPanel detailPanel = new BookingDetailPanel(false);

	public AddBookingDialog()
	{
		setTitle(StringResources.getAppTitle());
		detailPanel.initBookingDetails(getBlankBooking());
		add(detailPanel);
		updateLayout();
		setSize(400, 400);
	}

	private void updateLayout() {
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		layout.addLayoutComponent(detailPanel, BorderLayout.CENTER);
	}

	private Booking getBlankBooking() {
		Booking booking = new Booking();
		return booking;
	}
}
