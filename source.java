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
	
	/* Method Name: newMove
	 * Parameters:
	 * 		int pre -> the prefix section of the new integer
	 * 		int suf -> the suffix section of the new integer
	 * Return: int -> the parameters combined
	 * Output: None
	 */
	
	public static int newMove(int pre, int suf) {
		return Integer.parseInt(Integer.toString(pre) + Integer.toString(suf));
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
	
	/* Method Name: syncBoard
	 * Parameters: None
	 * Return: None
	 * Output: Synchronizes the simulated board with the real board
	 */
	
	public static void syncBoard() {
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				simBoard[i][j] = board[i][j];
			}
		}
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
	
	/* Method Name: moveParts
	 * Parameters:
	 * 		int move -> the move to be split up
	 * Return: ArrayList<Integer> -> a list of the parts of the move
	 * Output: None
	 */
	
	public static ArrayList<Integer> moveParts(int move) {
		ArrayList<Integer> parts = new ArrayList<Integer>();
		if (move > 9999) {
			
		}
		else {
			parts.add(move / 100);
			parts.add(move % 100);
		}
		return parts;
	}
	
	/* Method Name: validMove
	 * Parameters: None
	 * Return: ArrayList<Integer> -> a list of all valid moves by both sides in a given position
	 * Output: None
	 */
	
	public static ArrayList<Integer> validMove() {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				if (board[i][j] == 'o' || board[i][j] == 'O' || board[i][j] == 'X') {
					if (board[i + 1][j + 1] == '.') {
						moves.add(newMove(newMove(i, j), newMove(i + 1, j + 1)));
					}
					if (board[i + 1][j - 1] == '.') {
						moves.add(newMove(newMove(i, j), newMove(i + 1, j - 1)));
					}
				}
				if (board[i][j] == 'x' || board[i][j] == 'O' || board[i][j] == 'X') {
					if (board[i - 1][j + 1] == '.') {
						moves.add(newMove(newMove(i, j), newMove(i - 1, j + 1)));
					}
					if (board[i - 1][j - 1] == '.') {
						moves.add(newMove(newMove(i, j), newMove(i - 1, j - 1)));
					}
				}
				ArrayList<Integer> capMoves = altValidMove(newMove(i, j));
				for (int k : capMoves) {
					moves.add(newMove(i, newMove(j, k)));
				}
			}
		}
		return moves;
	}
	
	/* Method Name: altValidMove
	 * Parameters:
	 * 		int pre -> the square which the capturing piece is on
	 * Return: ArrayList<Integer> -> a list of all valid captures for a given piece in a position
	 */
	
	public static ArrayList<Integer> altValidMove(int pre) {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		return moves;
	}
	
	/* Method Name: doMove
	 * Parameters:
	 * 		int move -> the move to be played on the board
	 * Return: None
	 * Output: None
	 */
	
	public static void doMove(int move) {
		ArrayList<Integer> parts = moveParts(move);
		//requires moveParts to be completed
	}
	
	/* Method Name: checkGame
	 * Parameters:
	 * 		int player -> the player of the moves being checked
	 * Return: boolean -> if the game has been won yet
	 * Output: None
	 */
	
	public static boolean checkGame(int player) {
		//requires moveParts to be completed
		return true;
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
		ArrayList<Integer> moves = validMove();
		
		//introduction to each move
		outputBoard();
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
				for (int i : moves) {
					System.out.println(i);
				}
				if (moves.contains(move)) {
					doMove(move);
					break;
				}
				else {
					System.out.println("Invalid move! Please enter a valid move, or enter Help for the help section.");
				}
			}
			else {
				System.out.println("Invalid move! Please enter a valid move, or enter Help for the help section.");
			}
		}
		playerMove(1, "bbbb");
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
		System.out.println("a) 3113");
		System.out.println("b) 2231");
		System.out.println("c) 5344, 5331, 533113\n");
	}
}
