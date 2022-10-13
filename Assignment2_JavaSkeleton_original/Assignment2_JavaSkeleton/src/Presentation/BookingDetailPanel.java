package Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Business.BusinessComponentFactory;
import Business.Booking;


/**
 * 
 * @author IwanB
 * Panel used for creating and updating bookings
 */
public class BookingDetailPanel extends JPanel implements IBookingSelectionNotifiable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2031054367491790942L;
	
	private Booking currentBooking = null;
	private boolean isUpdatePanel = true;
	
	private JTextField noField; 
	private JTextField customerField;
	private JTextField performanceField;
	private JTextField performanceDateField;
	private JTextField agentField;
	private JTextArea instructions;
	
	/**
	 * Panel used for creating and updating bookings
	 * @param isUpdatePanel : describes whether panel will be used to either create or update booking
	 */
	public BookingDetailPanel(boolean isUpdatePanel)
	{
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.isUpdatePanel = isUpdatePanel;
	}

	/**
	 * Re-populates panel details with given booking object
	 * @param booking new booking object to populate panel details with
	 */
	public void initBookingDetails(Booking booking) {
		removeAll();	
		if(booking != null)
		{
			currentBooking = booking;
			addAll();
		}
	}

	private void addAll() {
		JPanel lTextFieldPanel = new JPanel();
		BoxLayout lTextFieldLayout = new BoxLayout(lTextFieldPanel, BoxLayout.Y_AXIS);
		lTextFieldPanel.setLayout(lTextFieldLayout);

		BorderLayout lPanelLayout = new BorderLayout();	
		lPanelLayout.addLayoutComponent(lTextFieldPanel, BorderLayout.NORTH);

		//create booking text fields
		//application convention is to map null to empty string (if db has null this will be shown as empty string)
		if(currentBooking.getNo() > 0) {
			noField = createLabelTextFieldPair(StringResources.getBookingNoLabel(), ""+currentBooking.getNo(), lTextFieldPanel);
			noField.setEditable(false);
		}
		//If updating a booking, display customer label and set customer field non-editable.
		if (isUpdatePanel) {
			customerField = createLabelTextFieldPair(StringResources.getCustomerLabel(), currentBooking.getCustomer() == null ? "" : currentBooking.getCustomer(), lTextFieldPanel);
			customerField.setEditable(false);
		} else { //Else if adding a booking, display customer email label for user input
			customerField = createLabelTextFieldPair(StringResources.getCustomerEmailLabel(), currentBooking.getCustomer() == null ? "" : currentBooking.getCustomer(), lTextFieldPanel);
		}
		performanceField = createLabelTextFieldPair(StringResources.getPerformanceLabel(), currentBooking.getPerformance() == null ? "" : ""+currentBooking.getPerformance(), lTextFieldPanel);
		performanceDateField = createLabelTextFieldPair(StringResources.getPerformanceDateLabel(),currentBooking.getPerformanceDate() == null ? "" : ""+ currentBooking.getPerformanceDate(), lTextFieldPanel);
		agentField = createLabelTextFieldPair(StringResources.getAgentLabel(), currentBooking.getAgent() == null ? "" : ""+currentBooking.getAgent(), lTextFieldPanel);
		add(lTextFieldPanel);
		
		//create booking instructions text area
		instructions = new JTextArea(currentBooking.getInstructions() == null ? "" : currentBooking.getInstructions());
		instructions.setAutoscrolls(true);
		instructions.setLineWrap(true);
		lPanelLayout.addLayoutComponent(instructions, BorderLayout.CENTER);
		add(instructions);
		
		//create booking save (create or update button)
		JButton saveButton = createBookingSaveButton();
		lPanelLayout.addLayoutComponent(saveButton, BorderLayout.SOUTH);
		this.add(saveButton);

		setLayout(lPanelLayout);
		updateUI();
	}

	private JButton createBookingSaveButton() {
		JButton saveButton = new JButton(isUpdatePanel ? StringResources.getBookingUpdateButtonLabel() : 
			StringResources.getBookingAddButtonLabel());
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//application convention is to map empty string to null (ie: if app has empty string - this will be null in db)
				currentBooking.setPerformance(performanceField.getText().equals("") ? null : performanceField.getText());
				try {
					currentBooking.setPerformanceDate(performanceDateField.getText().equals("") ? null : LocalDate.parse(performanceDateField.getText()));
				} catch (Exception ex) {
					System.out.println("Performance date must be a valid date using yyyy-mm-dd format");
					//ex.printStackTrace();
					return;
				}
				currentBooking.setAgent(agentField.getText().equals("") ? null : agentField.getText());
				currentBooking.setInstructions(instructions.getText().equals("")  ? null : instructions.getText());
				if(isUpdatePanel) {
					BusinessComponentFactory.getInstance().getBookingProvider().updateBooking(currentBooking);
				}
				else {
					currentBooking.setCustomer(customerField.getText().equals("") ? null : customerField.getText());
					BusinessComponentFactory.getInstance().getBookingProvider().addBooking(currentBooking);
				}
			}
		});
		
		return saveButton;
	}

	private JTextField createLabelTextFieldPair(String label, String value, JPanel container) {
		
		JPanel pairPanel = new JPanel();
		JLabel jlabel = new JLabel(label);
		JTextField field = new JTextField(value);
		pairPanel.add(jlabel);
		pairPanel.add(field);

		container.add(pairPanel);

		BorderLayout lPairLayout = new BorderLayout();
		lPairLayout.addLayoutComponent(jlabel, BorderLayout.WEST);
		lPairLayout.addLayoutComponent(field, BorderLayout.CENTER);
		pairPanel.setLayout(lPairLayout);	
		
		return field;
	}

	/**
	 * Implementation of IBookingSelectionNotifiable::bookingSelected used to switch booking
	 * displayed on BookingDisplayPanel
	 */
	@Override
	public void bookingSelected(Booking booking) {
		initBookingDetails(booking);
	}
	
	/**
	 * Clear booking details panel
	 */
	public void refresh()
	{
		initBookingDetails(null);
	}
}
