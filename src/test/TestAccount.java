package test;

import java.util.ArrayList;

import ca.bankonit.exceptions.InvalidAccountException;
import ca.bankonit.manager.BankManager;
import ca.bankonit.manager.BankManagerBroker;
import ca.bankonit.models.Account;
import ca.bankonit.models.Transaction;
import sait.bankonit.gui.AccountWindow;

public class TestAccount {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long cardNumber = 4444111122223333L;
		short pin = 4444;
		BankManager bankManager = BankManagerBroker.getInstance();
		Account account = bankManager.login(cardNumber, pin);
		//Account account = BankManagerBroker.getInstance().login(cardNumber, pin);
 		try {
			ArrayList<Transaction> transactions = bankManager.getTransactionsForAccount(account);
			for(Transaction transaction : transactions) {
				System.out.println(transaction);
			}
			
			//bankManager.deposit(account, 75);
			
//			transactions = bankManager.getTransactionsForAccount(account);
//			for(Transaction transaction : transactions) {
//				System.out.println(transaction);
//			}
			
			//bankManager.withdraw(account, 750);
			
//			transactions = bankManager.getTransactionsForAccount(account);
//			for(Transaction transaction : transactions) {
//				System.out.println(transaction);
//			}
			
			bankManager.persist();
			
		} catch (InvalidAccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
