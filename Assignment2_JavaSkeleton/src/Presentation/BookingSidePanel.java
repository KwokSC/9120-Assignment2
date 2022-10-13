package Presentation;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JPanel;

import Business.Booking;

/**
 * 
 * Represents booking list panel of booking tracker that includes
 * both the search box/button and text field; AND the booking list
 * @author IwanB
 *
 */
public class BookingSidePanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2693528613703066603L;

	private BookingListPanel mListPanel;
	
	/**
	 * Represents left panel of booking tracker that includes
	 * both the search box/button and text field; AND the booking list
	 * 
	 * @param searchEventListener : used to retrieve user search query in search box
	 * @param listPanel : booking list panel
	 */
	public BookingSidePanel(ISearchEventListener searchEventListener, BookingListPanel listPanel)
	{
		mListPanel = listPanel;
		BookingSearchComponents searchComponents = new BookingSearchComponents(searchEventListener);
	
		add(searchComponents);
		add(listPanel);
		
		BorderLayout layout = new BorderLayout();
		layout.addLayoutComponent(searchComponents, BorderLayout.NORTH);
		layout.addLayoutComponent(listPanel, BorderLayout.CENTER);
		setLayout(layout);
	}
	
	public void refresh(Vector<Booking> bookings)
	{
		mListPanel.refresh(bookings);
	}
	
	public void registerBookingSelectionNotifiableObject(IBookingSelectionNotifiable notifiable)
	{
		mListPanel.registerBookingSelectionNotifiableObject(notifiable);
	}
}
