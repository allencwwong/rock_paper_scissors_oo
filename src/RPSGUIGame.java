/*
 * Program: RPSGUIGame.java
 * Date: 10/12/13
 * Author: Sarn Wattanasri
 * Objectives: The RPSGUIGame class provides a graphical user interface for the RPS game.
 *             The user has a choice whether s/he would like to bet.  The GUI class has
 *             many GUI components and layouts and the inner class that contains helper methods
 *             that interact with the user to get inputs and displays the result.
 *             The RPSGUIGame class creates an instance of the RPS class and use methods
 *             to determine the win/loss/tie.  Results and messages are obtained via the
 *             getters of the RPS class.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.NumberFormat;


public class RPSGUIGame extends JFrame {

	//declare GUI components
	private JButton rockButton, paperButton, scissorsButton;
	private JLabel statusC, statusU, statusT, statusB;
	private ImageIcon rockImage, paperImage, scissorsImage;
	private JLabel compPlay, userPlay;
	private JLabel outcome;
	
	//declare the RPS reference
	private RPS game;
	//Extra Credit: declare the betAmount variable
	private static double betAmount;
	
	//declare constants
	private static final String BET_ASK = "Would you like to bet?";
	private static final String BET_AMOUNT_PROMPT = "How much would you like to bet?";

	public RPSGUIGame() {

		// initializes the window
		super("Rock, Paper, Scissors, Go!");
		setSize(420, 300);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(Color.black);
		setResizable(false);

		// creates the game object with the bet amount
		game = new RPS( betAmount );
		
		// creates the labels for displaying the computer and user's move;
		// the images for the moves and the outcome of a match-up will be displayed
		// in a single panel
		rockImage = new ImageIcon("rock.jpg");
		paperImage = new ImageIcon("paper.jpg");
		scissorsImage = new ImageIcon("scissors.jpg");

		compPlay = new JLabel();
		compPlay.setVerticalTextPosition(SwingConstants.BOTTOM);
		compPlay.setHorizontalTextPosition(SwingConstants.CENTER);
		compPlay.setBorder(BorderFactory.createLineBorder(Color.black, 5));
		compPlay.setForeground(Color.cyan);
		userPlay = new JLabel();
		userPlay.setVerticalTextPosition(SwingConstants.BOTTOM);
		userPlay.setHorizontalTextPosition(SwingConstants.CENTER);
		userPlay.setBorder(BorderFactory.createLineBorder(Color.black, 5));
		userPlay.setForeground(Color.orange);
		
		outcome = new JLabel();
		outcome.setHorizontalTextPosition(SwingConstants.CENTER);
		outcome.setForeground(Color.pink);
		
		JPanel imageOutcomePanel = new JPanel();
		imageOutcomePanel.setBackground(Color.black);
		imageOutcomePanel.setLayout(new BorderLayout());
		imageOutcomePanel.add(compPlay, BorderLayout.WEST);
		imageOutcomePanel.add(userPlay, BorderLayout.EAST);
		imageOutcomePanel.add(outcome, BorderLayout.SOUTH);
		
		// creates the labels for the status of the game (number of wins, losses, and ties);
		// the status labels will be displayed in a single panel
		statusC = new JLabel("Computer Wins: " + game.getCountNumberComputerWins());
		statusU = new JLabel("User Wins: " + game.getCountNumberUserWins());
		statusT = new JLabel("Ties: " + game.getCountNumberTies());
		statusB = new JLabel("Balance: " + game.getBalance());
		statusC.setForeground(Color.white);
		statusU.setForeground(Color.white);
		statusT.setForeground(Color.white);
		statusB.setForeground(Color.green);
		JPanel statusPanel = new JPanel();
		statusPanel.setBackground(Color.black);
		statusPanel.add(statusC);
		statusPanel.add(statusU);
		statusPanel.add(statusT);
		
		//Extra credit: show balance only if the user would like to bet
		if( game.isWillingToBet() ) {
			statusPanel.add(statusB);
		}

		// the play and status panels are nested in a single panel
		JPanel gamePanel = new JPanel();
		gamePanel.setPreferredSize(new Dimension(350, 350));
		gamePanel.setBackground(Color.black);
		gamePanel.add(imageOutcomePanel);
		gamePanel.add(statusPanel);
		
		// creates the buttons and displays them in a control panel;
		// one listener is used for all three buttons
		rockButton = new JButton("Play Rock");
		rockButton.addActionListener(new GameListener());
		paperButton = new JButton("Play Paper");
		paperButton.addActionListener(new GameListener());
		scissorsButton = new JButton("Play Scissors");
		scissorsButton.addActionListener(new GameListener());

		JPanel controlPanel = new JPanel();
		controlPanel.add(rockButton);
		controlPanel.add(paperButton);
		controlPanel.add(scissorsButton);
		controlPanel.setBackground(Color.black);

		// the gaming and control panel are added to the window
		contentPane.add(gamePanel, BorderLayout.CENTER);
		contentPane.add(controlPanel, BorderLayout.SOUTH);
		
		setVisible(true);
	}

	// determines which button was clicked and updates the game accordingly 
	private class GameListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			int intUserMove = updateUserMoveDisplay( source, event);
			int intComputerMove = updateComputerMoveDisplay();
			updateStatusDisplay( source, intUserMove, intComputerMove );
		}
		
		//update the user move
		private int updateUserMoveDisplay( Object source, ActionEvent event){
			int intUserMove = 0;
			
			if( source == rockButton ) {
				userPlay.setIcon( rockImage );
				intUserMove = game.ROCK_MOVE;
			}
			else if ( source == paperButton ) {
				userPlay.setIcon( paperImage );
				intUserMove = game.PAPER_MOVE;
			}
			else { // source == scissorsImage
				userPlay.setIcon( scissorsImage );
				intUserMove = game.SCISSORS_MOVE;
			}
			userPlay.setText("You selected : ");
			
			return intUserMove;
		}
		
		//update the computer's move
		private int updateComputerMoveDisplay() {
			int intComputerMove = game.generateComputerPlay();
			
			if (intComputerMove == game.ROCK_MOVE) {
				compPlay.setIcon(rockImage);
			} else if (intComputerMove == game.PAPER_MOVE) {
				compPlay.setIcon(paperImage);
			} else {
				compPlay.setIcon(scissorsImage);
			}
			compPlay.setText("Computer's move : ");
			
			return intComputerMove;
		}
		
		//format the balance using the currency formatter
		private String formatBalance( double balance ) {
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			return formatter.format(balance);
		}
		
		//additional feature to show the positive balance in green
		private void showColorizedBalance() {
			//differentiate the color of the balance. Green when balance is 
			//positive or zero
			if( game.getBalance() >= 0 ) {
				statusB.setForeground( Color.green );
			}
			else {
				statusB.setForeground( Color.red );
			}
			statusB.setText("Balance: " + formatBalance(game.getBalance()));
		}
		
		//Get outcome messages and set the texts
		private void setOutcomeMessages( char winner ) {
			//differentiate the winning outcome using the green color 
			//for the favorable outcome message
			if( winner == 'u') outcome.setForeground( Color.green );
			else outcome.setForeground( Color.pink );
			
			outcome.setText( game.getOutcomeMessage() );
			statusC.setText("Computer Wins: " + game.getCountNumberComputerWins());
			statusU.setText("User Wins: " + game.getCountNumberUserWins());
			statusT.setText("Ties: " + game.getCountNumberTies());
		}
		
		private void updateStatusDisplay(Object source, int intUserMove, 
				                                       int intComputerMove) {
			char winner = game.findWinner( intComputerMove, intUserMove );
			setOutcomeMessages( winner );
			
			//Extra credit: show balance if the user enter the bet amount
			if( game.isWillingToBet() ) showColorizedBalance();
		}
	}
	
	//This helper method shows the error given the error message
	private static void showError( String errorMessage ) {
		JOptionPane.showMessageDialog(null, errorMessage,
				"Invalid Input Error!", JOptionPane.ERROR_MESSAGE);
	}
	
	//This promptForInput() method allows the user to enter the choice via
	//the input dialog
	private static String promptForInput( String promptMessage ) {
		return JOptionPane.showInputDialog (null, promptMessage,
	               "Rock-Paper-Scissors", JOptionPane.QUESTION_MESSAGE);
	}
	
	//This bettable() method tells if the user wants the game to be bettable
	private static boolean bettable(String betAsk) {
		int answer = JOptionPane.showConfirmDialog ( null, betAsk);
		boolean betConfirmed = false;
		
		if ( answer == JOptionPane.YES_OPTION ) betConfirmed = true;
		else if ( answer == JOptionPane.NO_OPTION ) betConfirmed = false;
		else System.exit(0);
		
		return betConfirmed;
	}
	
	//The processBetAmount() method asks the user whether s/he would like
	//to place a bet amount.  The amount entered is also validated here.
	//Assumption: The valid bet amount is between one cent and $1000.
	private static void processBetInput(String betAmountPrompt) {
		boolean validInput = false;
        while( ! validInput ) {
        	//get the initial bet amount from the user
    		String betInput = promptForInput( betAmountPrompt );
    		
    		if ( ! betInput.isEmpty() ) {
    			if ( betInput.charAt(0) == '$' ) {
    				betInput = betInput.substring( 1, betInput.length());
    			}
    		} 
    		
    		if ( betInput.isEmpty() ) betInput = "0.0";
    		
    		try {
    			betAmount = Double.parseDouble( betInput );
    			if( betAmount != 0 ){
    				if ( betAmount < 0 ) throw new NegativeNumberException();
    			}
    			validInput = true;
    		} catch (NumberFormatException nfe) {
    			showError( "Enter the valid amount." );
    		} catch (NegativeNumberException nne) {
    			showError( "The bet amount cannot be negative" );
    		} 
        }
	}
	
	public static void main(String args[]) {
		//ask the user if s/he would like to bet
		if ( bettable( BET_ASK ) ) {
			processBetInput( BET_AMOUNT_PROMPT );
		}
		else {
			//When the user does not want to bid, the program sets the bid amount to 0.0
			betAmount = 0.0;
		}
		// create an object of your class
		RPSGUIGame frame = new RPSGUIGame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//The NegativeNumberException is a customized exception to reflect
	//the negative bet amount.  It will be used when asking for bet input
	private static class NegativeNumberException extends Exception { }
}