package sait.bankonit.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import ca.bankonit.exceptions.InvalidAccountException;
import ca.bankonit.exceptions.InvalidCardNumberException;
import ca.bankonit.manager.*;
import ca.bankonit.models.*;
import sait.bankonit.gui.AccountWindow.MyActionListener;

/**
 * Renders the login window
 * 
 * @author Cole Humeniuk, Marek Lichwa
 * @version Nov 4, 2021
 */
public class LoginWindow extends JFrame
{

	private JButton loginButton; 			// Initializing loginButton JButton
	private JLabel cardLabel; 				// InitializingcardLabel JLabel
	private JTextField cardField; 			// Initializing cardField JTextField
	private JLabel pinLabel; 				// Initializing pinLabel JLabel
	private JPasswordField pinField; 			// Initializing pinField JTextField

	/**
	 * Initializes the login window.
	 */
	public LoginWindow()
	{
		super("Bank On It Login");

		JPanel panel = createMainPanel();
		this.add(panel);

		this.setSize(500, 150);

		// Cause process to exit when X is clicked.
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		// Center login window in screen
		this.setLocationRelativeTo(null);

	}

	/**
	 * This displays the main Login window
	 * @return Returns Main Panel
	 */
	private JPanel createMainPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(createNorthPanel(), BorderLayout.NORTH);
		panel.add(createCenterPanel(), BorderLayout.CENTER);
		panel.add(createSouthPanel(), BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * This displays the Login button
	 * @return Returns South Panel
	 */
	private JPanel createSouthPanel()
	{
		JPanel panel = new JPanel();
		this.loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					String inputCard = LoginWindow.this.cardField.getText();
					long card = Long.parseLong(inputCard);
					char[] inputPin = LoginWindow.this.pinField.getPassword();
					String password = new String(pinField.getPassword());
					short pin = Short.parseShort(password);
					BankManager bankManager = BankManagerBroker.getInstance();
					Account account = bankManager.login(card, pin);
					if (account == null)
					{
						JOptionPane.showMessageDialog(LoginWindow.this, "Invalid Card Number");
					} else
					{
						AccountWindow accountWindow = new AccountWindow(bankManager, account);
						accountWindow.setVisible(true);
					}
				} 
				catch (NumberFormatException exp){
					JOptionPane.showMessageDialog(LoginWindow.this, "Please try again.");
				}
				catch (InvalidAccountException exp)
				{
					JOptionPane.showMessageDialog(LoginWindow.this, "Please try again.");
				} 
			}
		});
		panel.add(loginButton);
		return panel;
	}

	/**
	 * This displays card number field and pin number
	 * @return Returns the Center Panel
	 */
	private JPanel createCenterPanel()
	{
		JPanel panel = new JPanel();
		cardLabel = new JLabel("Card Number: ");
		panel.add(cardLabel);

		this.cardField = new JTextField(20);
		panel.add(cardField);

		this.pinLabel = new JLabel("Pin: ");
		panel.add(pinLabel);

		this.pinField = new JPasswordField(5);
		panel.add(pinField);

		return panel;
	}

	/**
	 * This displays the North Panels window header/title
	 * @return Returns the North Panel
	 */
	private JPanel createNorthPanel()
	{
		JPanel panel = new JPanel();
		JLabel titlelabel = new JLabel("Bank On It Login");
		panel.add(titlelabel);

		return panel;
	}

}
