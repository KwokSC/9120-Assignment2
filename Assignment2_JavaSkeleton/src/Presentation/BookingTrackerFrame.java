package Presentation;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import Business.BusinessComponentFactory;
import Business.Booking;

public class BookingTrackerFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5532618722097754725L;
	
	private AddEntitiesPanel addEntitiesPanel = null;
	private BookingDetailPanel detailPanel = null;
	private BookingSidePanel sidePanel = null;
	
	
	private int loggedInUserId = -1;

	/**
	 * Main booking tracker frame
	 * Logs on the user
	 * Initialize side panel + add entities panel + containing booking list + detail panel
	 */
	public BookingTrackerFrame()
	{
		setTitle(StringResources.getAppTitle());
	    setLocationRelativeTo(null);
	    
	    logOnUser();
	    initialise();
	    
	    setDefaultCloseOperation(EXIT_ON_CLOSE);  
	}
	
	/**
	 *  !!! 
	 *  Only used to simulate login
	 *  This should really be implemented using proper salted hashing
	 *	and compare hash to that in DB
	 *  really should display an error message for bad login as well
	 *	!!!
	 */
	private void logOnUser() {
		boolean OK = false;
		while (!OK) {		
				String user = (String)JOptionPane.showInputDialog(
									this,
									null,
									StringResources.getEnterUserNameString(),
									JOptionPane.QUESTION_MESSAGE);
				
				JPasswordField jpf = new JPasswordField();
				int okCancel = JOptionPane.showConfirmDialog(
									null,
									jpf,
									StringResources.getEnterPasswordString(),
									JOptionPane.OK_CANCEL_OPTION,
									JOptionPane.QUESTION_MESSAGE);
				
				String password = null;
				if (okCancel == JOptionPane.OK_OPTION) {
					password = new String(jpf.getPassword());
				}

				if (user == null || password == null)
					System.exit(0);
				else
					if (!user.isEmpty() && !password.isEmpty()) {
						loggedInUserId = checkUserCredentials(user, password);
						if (loggedInUserId != 0)
							OK = true;
					}
		}
	}

	private void initialise()
	{
		addEntitiesPanel = new AddEntitiesPanel();	
	    detailPanel = new BookingDetailPanel(true);	    
	    sidePanel = getSidePanel(new BookingListPanel(getAllBookings()));
	    
	    BorderLayout borderLayout = new BorderLayout();
	    borderLayout.addLayoutComponent(addEntitiesPanel, BorderLayout.NORTH);
	    borderLayout.addLayoutComponent(sidePanel, BorderLayout.WEST);
	    borderLayout.addLayoutComponent(detailPanel, BorderLayout.CENTER);
	    
	    JButton  refreshButton = new JButton(StringResources.getRefreshButtonLabel());
	    final BookingTrackerFrame frame = this;
	    refreshButton.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.refresh(frame.getAllBookings());
			}
		});
	    
	    borderLayout.addLayoutComponent(refreshButton, BorderLayout.SOUTH);
	    
	    this.setLayout(borderLayout);
	    this.add(addEntitiesPanel);
	    this.add(refreshButton);
	    this.add(sidePanel);
	    this.add(detailPanel);
	    this.setSize(600, 300);
	}
	
	private BookingSidePanel getSidePanel(BookingListPanel listPanel)
	{
		final BookingTrackerFrame frame = this;
		listPanel.registerBookingSelectionNotifiableObject(detailPanel);
		return new BookingSidePanel(new ISearchEventListener() {
			@Override
			public void searchClicked(String searchString) {
				frame.refresh(frame.findBookingsByTitle(searchString));
			}
		},listPanel);
	}
	
	private int checkUserCredentials(String userName, String password)
	{
		return BusinessComponentFactory.getInstance().getBookingProvider().checkUserCredentials(userName, password);
	}
	
	private Vector<Booking> getAllBookings()
	{
		return BusinessComponentFactory.getInstance().getBookingProvider().findBookingsBySalesAgent(loggedInUserId);
	}
	
	private Vector<Booking> findBookingsByTitle(String pSearchString)
	{
		pSearchString = pSearchString.trim();
		if (!pSearchString.equals(""))
			return BusinessComponentFactory.getInstance().getBookingProvider().findBookingsByCustomerAgentPerformance(pSearchString);
		else
			return BusinessComponentFactory.getInstance().getBookingProvider().findBookingsBySalesAgent(loggedInUserId);
	}
	
	private  void refresh(Vector<Booking> bookings)
	{
		if(sidePanel != null && detailPanel!= null)
		{
			sidePanel.refresh(bookings);
			detailPanel.refresh();
			sidePanel.registerBookingSelectionNotifiableObject(detailPanel);
		}
	}
	
	
	public static void main(String[] args)
	{
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	BookingTrackerFrame ex = new BookingTrackerFrame();
                ex.setVisible(true);
            }
        });		
	}
}
