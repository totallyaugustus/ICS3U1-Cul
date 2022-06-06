/* Program Name: Checkers.java
 * Name: Simon Shen
 * Last Edited: ???
 * ICS3U1
 * 
 * This program is a game of checkers that is playable
 */

import java.util.*;
import java.io.*;

public class Checkers {
	//work out variables
	static char board[][] = new char[10][10];
	static char simBoard[][] = new char[10][10];
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		newGameStart();
		outputBoard();
		playerMove(0, "aaa");
	}
	
	/* Method Name: isNum
	 * Parameters:
	 * 		String inString -> a string which may be parsed into an integer
	 * Return: boolean -> if the string inString can be parsed into an integer
	 * Output: None
	 */
	
	public static boolean isNum(String inString) {
		boolean isNum = false;
		try {
			Integer.parseInt(inString);
			isNum = true;
		} catch (NumberFormatException e) {}
		return isNum;
	}
	
	/* Method Name: outputBoard
	 * Parameters: None
	 * Return: None
	 * Output: Outputs the game board with row and column axis coordinates
	 */
	
	public static void outputBoard() {
		System.out.print("  ");
		for (int i = 1; i <= 8; i++) {
			System.out.print(i + " ");
		}
		System.out.print(" \n");
		for (int i = 8; i >= 1; i--) {
			System.out.print(i + " ");
			for (int j = 1; j <= 8; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.print(i + "\n");
		}
		System.out.print("  ");
		for (int i = 1; i <= 8; i++) {
			System.out.print(i + " ");
		}
		System.out.print(" \n");
	}
	
	/* Method Name: newGameStart
	 * Parameters: None
	 * Return: None
	 * Output: Sets up the game board with the correct characters as pieces at each game start
	 */
	
	public static void newGameStart() {
		for (int i = 0; i < 10; i++) {
			board[0][i] = '*';
		}
		for (int i = 1; i <= 3; i++) {
			board[i][0] = '*';
			for (int j = 1; j <= 8; j++) {
				if ((i + j) % 2 == 0) {
					board[i][j] = 'o';
				}
				else {
					board[i][j] = '.';
				}
			}
			board[i][9] = '*';
		}
		for (int i = 4; i <= 5; i++) {
			board[i][0] = '*';
			for (int j = 1; j <= 8; j++) {
				board[i][j] = '.';
			}
			board[i][9] = '*';
		}
		for (int i = 6; i <= 8; i++) {
			board[i][0] = '*';
			for (int j = 1; j <= 8; j++) {
				if ((i + j) % 2 == 0) {
					board[i][j] = 'x';
				}
				else {
					board[i][j] = '.';
				}
			}
			board[i][9] = '*';
		}
		for (int i = 0; i < 10; i++) {
			board[9][i] = '*';
		}
	}
	
	/* Method Name: validMove
	 * Parameters: None
	 * Return: ArrayList<Integer> -> a list of all valid moves by both sides in a given position
	 * Output: None
	 */
	
	public static ArrayList<Integer> validMove() {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		/* RETURN A LIST OF MOVES
		 * Three parts:
		 * 1. Non-capturing moves (single analysis)
		 * 2. Capturing moves (single analysis)
		 * 3. Chained capturing moves (recursive or iterative analysis)
		 */
		return moves;
	}
	
	/* Method Name: playerMove
	 * Parameters:
	 * 		int player -> if the player is playing as X or O
	 * 		String name -> the name of a player
	 * Return: None
	 * Output: Prompts for user input
	 */
	
	public static void playerMove(int player, String name) {
		//variables for each move
		int move;
		String input;
		ArrayList<Integer> movelist = validMove();
		
		//introduction to each move
		System.out.print(name + ", make your turn. ");
		if (player == 0) {
			System.out.println("You are playing as O");
		}
		else {
			System.out.println("You are playing as X");
		}
		
		//input for each move
		while(true) {
			System.out.print("Move: ");
			input = sc.nextLine();
			if (input.toUpperCase().equals("HELP")) {
				help();
			}
			else if (isNum(input)) {
				move = Integer.parseInt(input);
				//CHECK IF MOVE IS VALID IN MOVELIST
				break;
			}
			else {
				System.out.println("Invalid move! Please enter a valid move, or enter Help for the help section.");
			}
		}
	}
	
	/* Method Name: comMove
	 * Parameters:
	 * 		int player -> if the computer is playing as X or O
	 * 		int depth -> how far the computer searches ahead to decide its moves
	 * Return: None
	 * Output: Move done by the computer
	 */
	
	public static void comMove(int player, int depth) {
	}
	
	/* Method Name: altComMove
	 * Parameters:
	 * 		int player -> if the computer simulates playing as X or O
	 * 		int depth -> how far the computer searches in the future to simulate moves
	 * Return: int -> the optimal move for the current simulated board
	 * Output: None
	 */
	
	public static int altComMove(int player, int depth) {
		return 3;
	}
	
	/* Method Name: help
	 * Parameters: None
	 * Return: None
	 * Output: A guide on the rules, move input format, and how to enter chain capture moves
	 */
	
	public static void help() {
		System.out.println("\nHelp Section:\n");
		System.out.println("Goal:");
		System.out.println("The game of checkers is played between two players.");
		System.out.println("The goal of the game is to win, which can be done by three ways:");
		System.out.println("	1. Capture all of the opponent's pieces.");
		System.out.println("	2. None of the opponent's pieces that are on the board can move.");
		System.out.println("	3. The opposing player resigns.");
		System.out.println("Ties can also happen, with threefold repetition or an agreement to draw.\n");
		System.out.println("Piece Types:");
		System.out.println("For forwards orientation, player O faces upwards and player X faces downwards.");
		System.out.println("There are two sides, and two types of pieces.");
		System.out.println("------------------------------------------");
		System.out.println("|          | Non-King Piece | King Piece |");
		System.out.println("------------------------------------------");
		System.out.println("| Player X |        x       |      X     |");
		System.out.println("------------------------------------------");
		System.out.println("| Player O |        o       |      O     |");
		System.out.println("------------------------------------------");
		System.out.println("Non-king pieces which reach the end of the board from their respective direction will promote to a king piece.\n");
		System.out.println("Piece Movement:");
		System.out.println("Pieces not capturing a piece can only move diagonally, given that the sqaure is unoccupied.");
		System.out.println("Pieces can capture one of the opponent's pieces, given that it is:");
		System.out.println("	1. Diagonally adjacent and the direction is valid to the type of piece.");
		System.out.println("	2. The next space in the direction of the capture is unoccupied.");
		System.out.println("Chain captures can happen if capture puts the capturing piece into a space which allows it to capture another piece.");
		System.out.println("Non-king pieces can only move forwards, while king pieces can go both forwards and backwards.");
		System.out.println("Several examples of movement:");
		System.out.println("(Only the movement of the O pieces are shown, with (<piece>) being a possible square for any O <piece>)");
		System.out.println("a) ************* b) *************");
		System.out.println("   * O |   |   *    *(o)|   | x *");
		System.out.println("   *-----------*    *-----------*");
		System.out.println("   *   | x |   *    *   | o |   *");
		System.out.println("   *-----------*    *-----------*");
		System.out.println("   *   |   |(O)*    *   |   | X *");
		System.out.println("   *************    *************");
		System.out.println("c) *****************");
		System.out.println("   *   |   | O |   *");
		System.out.println("   *---------------*");
		System.out.println("   *   | x |   |(O)*");
		System.out.println("   *---------------*");
		System.out.println("   *(O)|   |   |   *");
		System.out.println("   *---------------*");
		System.out.println("   *   | X |   |   *");
		System.out.println("   *---------------*");
		System.out.println("   *   |   |(O)|   *");
		System.out.println("   *****************\n");
		System.out.println("Move Notation:");
		System.out.println("Movement is written as an integer using the provided axis coordinates for both row and column coordinates.");
		System.out.println("Movement can be described as <starting row><starting column><ending row><ending column>.");
		System.out.println("For chain captures, the notation will include any passing squares in the order that they are passed.");
		System.out.println("Movement for chain captures is as such: <starting row><starting column><passing row><passing column><ending row><ending column>.");
		System.out.println("The examples for notation for the previous three examples of movement are as such:");
		System.out.println("(This is assuming that the rows start at the bottom with 1, and start on the left side with 1, and both increase by 1 with each square)");
		System.out.println("a) 1133");
		System.out.println("b) 2211");
		System.out.println("c) 1331, 1324, 133153\n");
	}
}
