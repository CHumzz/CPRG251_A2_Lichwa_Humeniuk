package sait.bankonit.application;

import ca.bankonit.exceptions.InvalidAccountException;
import ca.bankonit.manager.*;
import ca.bankonit.models.*;
import sait.bankonit.gui.*;

/**
 * Application driver
 * @author Cole Humeniuk, Marek Lichwa
 * @throws Throws InvalidAccountException when Account is Invalid
 * @version Nov 4, 2021
 */
public class AppDriver {

	/**
	 * Entry point for program
	 * @param args Main Method Arguments
	 * @throws Throws NumberFormatException when Account is Invalid
	 */
	public static void main(String[] args) throws NumberFormatException {
		/* Uncomment for versions 1.0 - 3.0 */
		// cardNumber = 4444111122223333L;
		//short pin = 4444;
		//
		//Account account = BankManagerBroker.getInstance().login(cardNumber, pin);
 		//AccountWindow accountWindow = new AccountWindow(null, account);
		//accountWindow.setVisible(true);
		//
		
		/* Uncomment for version 4.0 */
 		LoginWindow login = new LoginWindow();
		login.setVisible(true);
		//
	}

}
