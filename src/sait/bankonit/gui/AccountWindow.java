package sait.bankonit.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import ca.bankonit.exceptions.*;
import ca.bankonit.manager.*;
import ca.bankonit.models.*;

/**
 * Renders the account window.
 * 
 * @author Cole Humeniuk, Marek Lichwa
 * @version Nov 4, 2021
 */
public class AccountWindow extends JFrame
{
	private Account account; 					// Initializing account object
	private BankManager bankManager; 			// Initializing bankManager object
	private JList transactions; 				// Initializing transactions JList
	private MyActionListener actionListener; 	// Initializing actionListener MyActionListener
	private JLabel type; 						// Initializing type JLabel
	private JRadioButton deposit;				// Initializing deposit JRadioButton
	private JRadioButton withdraw; 				// Initializing withdraw JRadioButton
	private JLabel amountLabel; 				// Initializing amountLabel JLabel
	private JTextField amountField; 			// Initializing amountField JTextField
	private JButton submitButton; 				// Initializing submitButton JButton
	private JScrollPane scrollPane; 			// Initializing scrollPane JScrollPane
	private JLabel balanceLabel; 				// Initializing balanceaLabel JLabel
	private JLabel cardLabel; 					// Initializing cardLabel JLabel
	private JButton outButton; 					// Initializing outButton JButton

	/**
	 * Initializes the account window and displays transactions
	 * 
	 * @param bankManager BankManager to manage bankManager object
	 * @param account Account to manage account object
	 * @throws InvalidAccountException thrown when no matching account number provided
	 */
	public AccountWindow(BankManager bankManager, Account account) throws InvalidAccountException
	{
		super("Bank On It Account");

		// Store account as field.
		this.account = account;
		this.bankManager = bankManager;

		// Set size to 600x500
		this.setSize(600, 500);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);

		this.setLocationRelativeTo(null);

		this.actionListener = new MyActionListener();

		JPanel panel = createMainPanel();
		this.add(panel);

		this.populateTransactions();
	}

	/**
	 * This is the Main Account Panel
	 * 
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
	 * This is the South Panel containing the Sign-out Button
	 * 
	 * @return Returns South Panel
	 */
	private JPanel createSouthPanel()
	{
		JPanel panel = new JPanel();
		outButton = new JButton("Signout");
		panel.add(outButton);
		outButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (e.getSource() == AccountWindow.this.outButton)
				{
					JOptionPane.showMessageDialog(AccountWindow.this, "Signed Out.");
					AccountWindow.this.bankManager.persist();
					AccountWindow.this.setVisible(false);
				}
			}
		});
		return panel;
	}

	/**
	 * This is the Center Panel containing the Inner Panels
	 * 
	 * @return Returns Center Panel
	 */
	private JPanel createCenterPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(createCenterNorthPanel(), BorderLayout.NORTH);
		panel.add(createCenterCenterPanel(), BorderLayout.CENTER);
		panel.add(createCenterSouthPanel(), BorderLayout.SOUTH);
		return panel;
	}

	/**
	 * This is the Center South Panel containing the Deposit, Withdraw and Submit buttons 
	 * as well as the Amount fields
	 * @return Returns Center Panel within the South Panel
	 */
	private JPanel createCenterSouthPanel()
	{

		JPanel panel = new JPanel();

		type = new JLabel("Type:");
		panel.add(type);
		deposit = new JRadioButton("Deposit");
		panel.add(deposit);

		withdraw = new JRadioButton("Withdraw");
		panel.add(withdraw);

		ButtonGroup type2 = new ButtonGroup();
		type2.add(deposit);
		type2.add(withdraw);

		amountLabel = new JLabel("Amount: ");
		panel.add(amountLabel);

		amountField = new JTextField(20);
		panel.add(amountField);

		submitButton = new JButton("Submit");
		panel.add(submitButton);

		submitButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				if (e.getSource() == AccountWindow.this.submitButton)
				{
					try
					{
						String amountField = AccountWindow.this.amountField.getText();
						double amount = Double.parseDouble(amountField);
						if (AccountWindow.this.deposit.isSelected())
						{
							AccountWindow.this.bankManager.deposit(account, amount);
							AccountWindow.this.populateTransactions();
							AccountWindow.this.bankManager.persist();
						}

						else if (AccountWindow.this.withdraw.isSelected())
						{

							AccountWindow.this.bankManager.withdraw(account, amount);
							AccountWindow.this.populateTransactions();
							AccountWindow.this.bankManager.persist();

						}
					} catch (NumberFormatException e1)
					{
						JOptionPane.showMessageDialog(AccountWindow.this, "Please try again.");
					}

					catch (InvalidAccountException e2)
					{
						JOptionPane.showMessageDialog(AccountWindow.this, "Invalid Account number");
	
					}
				}

			}

		});

		return panel;
	}

	/**
	 * This is the Center Main displaying the scroll-able transaction list.
	 * 
	 * @return Returns the Center Panel within the Center Panel
	 */
	private JPanel createCenterCenterPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		this.transactions = new JList();
		this.scrollPane = new JScrollPane(this.transactions);
		panel.add(scrollPane);
		return panel;
	}

	/**
	 * This Center North panel displays the account balance
	 * @return Returns the North Panel within the Center Panel
	 */
	private JPanel createCenterNorthPanel()
	{
		JPanel panel = new JPanel();
		balanceLabel = new JLabel("Balance: ");
		panel.add(balanceLabel);

		return panel;
	}

	/**
	 * This North Panel displays the current card number
	 * @return Returns the North Panel
	 */
	private JPanel createNorthPanel()
	{
		JPanel panel = new JPanel();
		cardLabel = new JLabel();
		this.cardLabel.setText(String.format("Card # $%d", account.getCardNumber()));
		panel.add(cardLabel);

		return panel;
	}

	/**
	 * Clears and re-populates transactions as well as updates balance.
	 */
	private void populateTransactions()
	{

		try
		{
			double total = 0;
			ArrayList<Transaction> transactions = bankManager.getTransactionsForAccount(account);
			String[] accountDetail = new String[transactions.size()];
			for (int i = 0; i < transactions.size(); i++)
			{
				Transaction transaction = transactions.get(i);
				accountDetail[i] = transaction.toString();

				if (transaction.getTransactionType() == Transaction.TYPE_WITHDRAW)
				{
					total -= transaction.getAmount();
				} else
				{
					total += transaction.getAmount();
				}

			}
			this.balanceLabel.setText(String.format("Balance $%.2f", total));
			this.transactions.setListData(accountDetail);

		} catch (InvalidAccountException e)
		{
			e.printStackTrace();
		}
	}

	//This class is just here to instantiate MyActionListener
	public class MyActionListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			

		}

	}

}
