/*
 * Program: RPS.java
 * Date: 10/12/13
 * Author: Sarn Wattanasri
 * Objectives: The RPS class provides a template for creating a Rock-Paper-Scissors
 *             game object.  It can be used by both RPSConsole and RPSGUIGame programs.
 *             It has supports for generating computer plays, finding the winner,
 *             and writing out the outcome messages.  The computer plays are randomly
 *             generated for each time the user plays.  The RPS class has an option
 *             that enables the user to provide the bet amount.  As the result of the
 *             game is determined, the balance of the user is also updated.
 */
public class RPS {
	//delcare constants 
	public final int ROCK_MOVE = 1;
	public final int PAPER_MOVE = 2;
	public final int SCISSORS_MOVE = 3;
	
	private final char USER_WIN = 'u';
	private final char COMPUTER_WIN = 'c';
	private final char TIE = 't';
	
	private final String WIN_MESSAGE = "You win!";
	private final String LOSE_MESSAGE = "You lose!";
	private final String TIE_MESSAGE = "It's a tie.";
	
	private final String PAPER_WINS_ROCK = "Paper smothers Rock.  ";
	private final String SCISSORS_WIN_PAPER = "Scissors cut Paper.  ";
	private final String ROCK_WINS_SCISSORS = "Rock smashes Scissors.  ";
	private final String SAME_MOVES = "Same moves.  ";
	
	//declare instance data
	private int countNumberComputerWins;
	private int countNumberUserWins;
	private int countNumberTies;
	
	private char winner;
	private String resultMessage;
	private String outcomeMessage;
	private double betAmount;
	private double balance;
	private boolean willingToBet;
	
	public RPS(double betAmount) {
		//initialize instance variables
		this.countNumberComputerWins = 0;
		this.countNumberUserWins = 0;
		this.countNumberTies = 0;
		this.winner = ' ';
		this.resultMessage = "";
		this.betAmount = betAmount;
		this.willingToBet = (betAmount > 0.0) ? true : false;
		this.balance = 0.0;
	}

	//The writeResultMessage() method writes whether "You win!", "You lose!.",
	//or "It's a tie."
	private void writeResultMessage(char result) {
		switch( result) {
		case 'u': this.resultMessage = WIN_MESSAGE; break;
		case 'c': this.resultMessage = LOSE_MESSAGE; break;
		case 't': this.resultMessage = TIE_MESSAGE; break;
		}
	}
	
	//randomly generate and return the generated move by the computer
	public int generateComputerPlay(){
		return rand(ROCK_MOVE, SCISSORS_MOVE);
	}
	
	//update the number of wins, losses, and ties
	private void updateWinsAndTies( char winner ) {
		switch( winner ) {
		  case USER_WIN: countNumberUserWins++; break;
		  case COMPUTER_WIN: countNumberComputerWins++; break;
		  case TIE: countNumberTies++; break;
		}
	}
	
	//The writeOutcomeMessage() method determines the outcome message that
	//describes how the win/loss took place( ex: Rock smashes Scissors)
	private void writeOutcomeMessage( int computerMove, int userMove ) {
		String outcomeMessage = "";
		final int ROCK_AND_PAPER = 3;
		final int SCISSORS_AND_ROCK = 4;
		final int SCISSORS_AND_PAPER = 5;
		
		int checkSum = computerMove + userMove;
		
		if ( computerMove == userMove ) {
			outcomeMessage = SAME_MOVES;
		} else {
			switch( checkSum ) {
			case ROCK_AND_PAPER: outcomeMessage = PAPER_WINS_ROCK; break;
			case SCISSORS_AND_ROCK: outcomeMessage = ROCK_WINS_SCISSORS; break;
			case SCISSORS_AND_PAPER: outcomeMessage = SCISSORS_WIN_PAPER; break;
			}
		}
		
		this.outcomeMessage = outcomeMessage + "  " + resultMessage;
	}
	
	//this findWinner() method determines who is the winner or a tie
	//it returns the character that reflects the outcome
	public char findWinner( int computerMove, int userMove) {
		char result = ' ';
		
		//determines who is the winner
		if ( computerMove == userMove ) result = 't';
		else if ( (userMove - computerMove + 3) % 3 == 1 ) result = 'u';
		else result = 'c';
		
		//keep track of score
		updateWinsAndTies( result );
				
		//write the message that indicates the result of the play
		writeResultMessage( result );
		
		//update the balance determined by the bet amount
		updateBalance( result );
		
		//write the outcome message to the instance data
		writeOutcomeMessage( computerMove, userMove);
		
		return result;
	}
	
	//update the user's balance given the result of each play
	private void updateBalance( char result ) {
		switch( result ) {
		  case USER_WIN: balance += betAmount; break;
		  case COMPUTER_WIN: balance -= betAmount; break;
		  case TIE: break;
		}
	}
	
	//a helper class providing the random number between lower and upper
	private int rand( int lower, int upper ) {
		return (int)((upper - lower + 1) * Math.random() + lower);
	}

	public int getCountNumberComputerWins() {
		return countNumberComputerWins;
	}

	public int getCountNumberTies() {
		return countNumberTies;
	}

	public int getCountNumberUserWins() {
		return countNumberUserWins;
	}
	
	public String getResultMessage() {
		return resultMessage;
	}
	
	public char getWinner() {
		return winner;
	}

	public String getOutcomeMessage() {
		return outcomeMessage;
	}
	
	public double getBetAmount() {
		return betAmount;
	}

	//This method is for future use to reset the bet amount
	public void setBetAmount(double betAmount) {
		this.betAmount = betAmount;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public boolean isWillingToBet() {
		return this.willingToBet;
	}
}
