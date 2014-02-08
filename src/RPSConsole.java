/*
 * Program: RPSConsole.java
 * Date: 10/12/13
 * Author: Sarn Wattanasri
 * Objectives: The RPSConsole class servers as a RPS game console.  The user has
 *             a choice whether s/he would like to bet.  This RPSConsole class makes
 *             use of the RPS class.  The Console class provides various helper classes
 *             that interacts with the user to get inputs and displays the result.
 *             The RPSConsole class creates an instance of the RPS class and use methods
 *             to determine the win/loss/tie.  Results and messages are obtained via the
 *             getters of the RPS class.
 *             
 */

import java.text.NumberFormat;
import java.util.Scanner;

public class RPSConsole {
	private final static String ROCK = "ROCK";
	private final static String PAPER = "PAPER";
	private final static String SCISSORS = "SCISSORS";
	
	private final static String PROMPT_MESSAGES = 
	                       "\n\nTo play, enter: \n" + 
						   " 'r' to play ROCK \n" +
						   " 'p' to play PAPER \n" +
						   " 's' to play SCISSORS \n" +
						   " or any other character to quit.";
	private static RPS rps;
	
	//the getMoveString() method provides a representation of
	//the moves selected: serving the same function as the 
	//pictures in the GUI program
	private static String getMoveString(int move) {
		String strMove = "";
		if( move == rps.ROCK_MOVE ) strMove = ROCK;
		else if ( move == rps.PAPER_MOVE ) strMove = PAPER;
		else strMove = SCISSORS;
		return strMove;
	}
	
	//The getUserChoice() method obtains the choice selected
	//by the user.  Valid choices include 'r' for rock, 'p' for paper
	//'s' for scissors.  Other characters will cause the program exit
	private static char getUserChoice(RPS rps, Scanner scanner) {
		System.out.println(PROMPT_MESSAGES);
		String userInput = scanner.nextLine();
		char userChoice = userInput.charAt(0);
		
		//Valid choices include 'r', 'p', and 's'.
		//Other characters will cause the program to exit
		if( !(userChoice == 'r' ||userChoice == 'p' ||
			userChoice == 's' )) {
			System.out.println("Thanks for playing!");
			System.exit(0);
		}
		return userChoice;
	}	
	
	//The getIntegerUserMove() method is a helper method that 
	//translate the character of user choice to the corresponding integer
	private static int getIntegerUserMove( char userChoice ) {
        int userMove = 0;
		switch( userChoice){
		    case 'r': userMove = rps.ROCK_MOVE; break;
		    case 'p': userMove = rps.PAPER_MOVE; break;
		    case 's': userMove = rps.SCISSORS_MOVE; break;
		}
		return userMove;
	}
	
	//the displayStatsAndStatus() method presents the statistics of the wins, losses,
	//ties, and the balance(when the user chose to bet)
	private static void displayStatsAndStatus (int intComputerMove, int intUserMove) {
		System.out.println("You played " + getMoveString( intUserMove ));
		System.out.println("Computer played " + getMoveString( intComputerMove));
		System.out.println( rps.getOutcomeMessage());
		System.out.print("\nWins: " + rps.getCountNumberUserWins());
		System.out.print("  Losses: " + rps.getCountNumberComputerWins());
		System.out.print("  Ties: " + rps.getCountNumberTies());
		if( rps.isWillingToBet() ) {
			System.out.print(" Balance: " + formatBalance(rps.getBalance()));
		}
	}
	
	//the getIntegerComputerMove() method gets the randomly generated move
	//by the computer
	private static int getIntegerComputerMove() {
	    return rps.generateComputerPlay();
	}
	
	//The formatBalance() method formats the balance as a dollar amount
	private static String formatBalance( double balance ) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(balance);
	}
	
	//The getBetAmount() method asks the user whether s/he would like
	//to place a bet amount.  The amount entered is also validated here.
	//Assumption: The valid bet amount is between 0 and $1000.
	private static double getBetAmount(Scanner scanner ){
		System.out.println("Enter the bet amount (or hit 'enter' " +
	                       "to play for fun without betting)");
		String betInput;
		boolean validBetAmount = false;
	    double betAmount = 0.0;
		
		while( ! validBetAmount ) {
			betInput = scanner.nextLine();
			if ( ! betInput.isEmpty() ) {
    			if ( betInput.charAt(0) == '$' ) {
    				betInput = betInput.substring( 1, betInput.length());
    			}
    		} 
    		if ( betInput.isEmpty() ) {
    			betInput = "0.0";
    			//Skip validation when the user enters nothing
    			validBetAmount = true;
    		}
    		
			try{
				betAmount = Double.parseDouble( betInput );
				if ( betAmount < 0 ) throw new NegativeNumberException();
				validBetAmount = true;
			} catch( NumberFormatException nfe ) {
				System.err.println("Enter the valid bet amount!");
			} catch( NegativeNumberException nne ) {
				System.err.println("The amount cannot be negative");
			} 
		}
		return betAmount;
	}
	
	//The playGame() mehtod takes the user move input, generate the
	//computer move, and determine who is the winner or loser or 
	//whether the tie occurs.  It then shows the stats and the status
	//of the latest play.
	private static void playGame( Scanner scanner ) {
		while( true ) {
			char userChoice = getUserChoice( rps, scanner );
			int intUserMove = getIntegerUserMove( userChoice );
			int intComputerMove = getIntegerComputerMove( );
			rps.findWinner( intComputerMove , intUserMove);
			displayStatsAndStatus( intComputerMove, intUserMove);
		}
	}
	
	public static void main(String[] args){
		System.out.println("Welcome to Rock, Paper, Scissors!");
		Scanner scanner = new Scanner(System.in);
		double betAmount = getBetAmount( scanner );
		rps = new RPS( betAmount );
		playGame( scanner );
	}
	
	//The NegativeNumberException is a customized exception to reflect
	//the negative bet amount.  It will be used when asking for bet input
	public static class NegativeNumberException extends Exception { }
}
