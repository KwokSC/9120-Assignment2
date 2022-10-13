package Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * @author IwanB
 * AddEntitiesPanel is shown at the top of the booking tracker window
 * - this is where all buttons used to add entities like Booking/Project/User should be added
 * 
 */
public class AddEntitiesPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2256207501462485047L;
	

	private JButton addBookingButton = new JButton("Add New Booking");


	public AddEntitiesPanel()
	{
		setBorder(BorderFactory.createLineBorder(Color.black));
		add(addBookingButton);
		updateLayout();
		addBookingButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddBookingDialog dialog = new AddBookingDialog();
				dialog.setVisible(true);
			}
		});	
	}


	private void updateLayout() {
		BorderLayout layout = new BorderLayout();
		setLayout(layout);
		layout.addLayoutComponent(addBookingButton, BorderLayout.CENTER);
	}
}
