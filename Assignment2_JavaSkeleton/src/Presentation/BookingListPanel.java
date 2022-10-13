package Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Business.Booking;

/**
 * Panel encapsulating booking list
 * @author IwanB
 *
 */
public class BookingListPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1013855025757989473L;
	
	private List<IBookingSelectionNotifiable> notifiables = new ArrayList<IBookingSelectionNotifiable>();
	private Vector<Booking> bookings;
	
	/**
	 * 
	 * @param bookings vector of bookings to display in the booking list panel
	 */
	public BookingListPanel(Vector<Booking> bookings)
	{
		this.bookings = bookings;
		this.setBorder(BorderFactory.createLineBorder(Color.black));	
		initList(this.bookings);
	}

	private void initList(Vector<Booking> bookings) {
		
		final JList<Booking> list = new JList<Booking>(bookings);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listScroller = new JScrollPane(list);
		this.add(listScroller);
		
		BorderLayout listLayout = new BorderLayout();
		listLayout.addLayoutComponent(listScroller, BorderLayout.CENTER);
		this.setLayout(listLayout);
		list.getSelectionModel().addListSelectionListener(new ListSelectionListener()
		{
			@Override
			public void valueChanged(ListSelectionEvent e) {
				for(IBookingSelectionNotifiable notifiable : notifiables)
				{
					Booking selectedBooking = list.getSelectedValue();
					if(selectedBooking != null)
					{
						notifiable.bookingSelected(selectedBooking);
					}
				}
			}		
		});
	}
	
	/**
	 * Refresh booking list to display vector of booking objects
	 * @param bookings - vector of booking objects to display
	 */
	public void refresh(Vector<Booking> bookings)
	{
		this.removeAll();
		this.initList(bookings);
		this.updateUI();
		this.notifiables.clear();
	}
	
	/**
	 * Register an object to be notified of a booking selection change
	 * @param notifiable object to invoke when a new booking is selected
	 */
	public void registerBookingSelectionNotifiableObject(IBookingSelectionNotifiable notifiable)
	{
		notifiables.add(notifiable);
	}

}
