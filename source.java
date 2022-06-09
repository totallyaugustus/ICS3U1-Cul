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
	//variables and containers used in a static reference (deal with it)
	static char board[][] = new char[10][10];
	static char simBoard[][] = new char[10][10];
	static char playerNum[] = {'o', 'x', 'O', 'X'};
	static String playerName[] = new String[2];
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.print("Player 1's name: ");
		playerName[0] = sc.nextLine();
		System.out.print("Player 2's name: ");
		playerName[1] = sc.nextLine();
		newGameStart();
		playerMove(0);
	}
	
	/* Method Name: isNum
	 * Parameters:
	 * 		String inString -> a string which may be parsed into an integer
	 * Return: boolean -> if the string inString can be parsed into an integer
	 * Output: None
	 * Function: Returns a boolean value for if a given string can be parsed into an integer
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
	 * Function: Given a prefix move section and a suffix move section, it concatenates them and returns a combined move
	 */
	
	public static int newMove(int pre, int suf) {
		return Integer.parseInt(Integer.toString(pre) + Integer.toString(suf));
	}
	
	/* Method Name: outputBoard
	 * Parameters: None
	 * Return: None
	 * Output: Game Board, Axis Coordinates
	 * Function: Outputs the real board with row and column axis coordinates
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
	 * Output: None
	 * Function: Synchronizes the simulated board with the real board
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
	 * Output: None
	 * Function: Sets up the real board as if starting a new game
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
	 * Function: Given a move, it splits it up into a list of the squares touched or referenced by the move
	 */
	
	public static ArrayList<Integer> moveParts(int move) {
		ArrayList<Integer> tempParts = new ArrayList<Integer>();
		ArrayList<Integer> parts = new ArrayList<Integer>();
		while (move > 100) {
			tempParts.add(move % 100);
			move /= 100;
		}
		tempParts.add(move);
		for (int i = tempParts.size() - 1; i >= 0; i--) {
			parts.add(tempParts.get(i));
		}
		return parts;
	}
	
	/* Method Name: validMove
	 * Parameters:
	 * 		int player -> the player of which the moves are found
	 * Return: ArrayList<Integer> -> a list of all valid moves by a player in a given position
	 * Output: None
	 * Function: Finds out all the valid moves for a player in a given position
	 */
	
	public static ArrayList<Integer> validMove(int player) {
		ArrayList<Integer> moves = new ArrayList<Integer>();
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				if (board[i][j] == playerNum[player] || board[i][j] == playerNum[player + 2]) {
					if (board[i][j] != playerNum[0]) {
						if (board[i - 1][j - 1] == '.') {
							moves.add(newMove(newMove(i, j), newMove(i - 1, j - 1)));
						}
						if (board[i - 1][j + 1] == '.') {
							moves.add(newMove(newMove(i, j), newMove(i - 1, j + 1)));
						}
					}
					if (board[i][j] != playerNum[1]) {
						if (board[i + 1][j - 1] == '.') {
							moves.add(newMove(newMove(i, j), newMove(i + 1, j - 1)));
						}
						if (board[i + 1][j + 1] == '.') {
							moves.add(newMove(newMove(i, j), newMove(i + 1, j + 1)));
						}
					}
				}
				for (int k : altValidMove(player, newMove(i, j))) {
					moves.add(newMove(newMove(i, j), k));
					syncBoard();
				}
			}
		}
		return moves;
	}
	
	/* Method Name: altValidMove
	 * Parameters:
	 * 		int player -> the side of which we are using
	 * 		int pre -> the square which the capturing piece is on
	 * Return: ArrayList<Integer> -> a list of all valid captures for a given piece in a position
	 * Output: None
	 * Function: Given a starting square in the simulated board, it returns all possible captures and chain captures from that square for a certain player in a given position
	 */
	
	public static ArrayList<Integer> altValidMove(int player, int pre) {
		char save;
		ArrayList<Integer> tempMoves = new ArrayList<Integer>();
		ArrayList<Integer> moves = new ArrayList<Integer>();
		if (simBoard[pre / 10][pre % 10] == playerNum[player] || simBoard[pre / 10][pre % 10] == playerNum[player + 2]) {
			if (simBoard[pre / 10][pre % 10] != playerNum[0]) {
				if (simBoard[(pre / 10) - 1][(pre % 10) - 1] == playerNum[(player + 1) % 4] || simBoard[(pre / 10) - 1][(pre % 10) - 1] == playerNum[(player + 3) % 4]) {
					if (simBoard[(pre / 10) - 2][(pre % 10) - 2] == '.') {
						tempMoves.add(newMove((pre / 10) - 2, (pre % 10) - 2));
					}
				}
				if (simBoard[(pre / 10) - 1][(pre % 10) + 1] == playerNum[(player + 1) % 4] || simBoard[(pre / 10) - 1][(pre % 10) + 1] == playerNum[(player + 3) % 4]) {
					if (simBoard[(pre / 10) - 2][(pre % 10) + 2] == '.') {
						tempMoves.add(newMove((pre / 10) - 2, (pre % 10) + 2));
					}
				}
			}
			if (simBoard[pre / 10][pre % 10] != playerNum[1]) {
				if (simBoard[(pre / 10) + 1][(pre % 10) - 1] == playerNum[(player + 1) % 4] || simBoard[(pre / 10) + 1][(pre % 10) - 1] == playerNum[(player + 3) % 4]) {
					if (simBoard[(pre / 10) + 2][(pre % 10) - 2] == '.') {
						tempMoves.add(newMove((pre / 10) + 2, (pre % 10) - 2));
					}
				}
				if (simBoard[(pre / 10) + 1][(pre % 10) + 1] == playerNum[(player + 1) % 4] || simBoard[(pre / 10) + 1][(pre % 10) + 1] == playerNum[(player + 3) % 4]) {
					if (simBoard[(pre / 10) + 2][(pre % 10) + 2] == '.') {
						tempMoves.add(newMove((pre / 10) + 2, (pre % 10) + 2));
					}
				}
			}
		}
		for (int i : tempMoves) {
			moves.add(i);
			save = simBoard[((pre / 10) + (i / 10)) / 2][((pre % 10) + (i % 10)) / 2];
			simBoard[((pre / 10) + (i / 10)) / 2][((pre % 10) + (i % 10)) / 2] = '.';
			simBoard[i / 10][i % 10] = simBoard[pre / 10][pre % 10];
			simBoard[pre / 10][pre % 10] = '.';
			for (int j : altValidMove(player, i)) {
				moves.add(newMove(i, j));
			}
			simBoard[((pre / 10) + (i / 10)) / 2][((pre % 10) + (i % 10)) / 2] = save;
			simBoard[pre / 10][pre % 10] = simBoard[i / 10][i % 10];
			simBoard[i / 10][i % 10] = '.';
		}
		return moves;
	}
	
	/* Method Name: doMove
	 * Parameters:
	 * 		ArrayList<Integer> parts -> the parts of the move to be played
	 * Return: None
	 * Output: None
	 * Function: Plays a given move on the real board
	 */
	
	public static void doMove(ArrayList<Integer> parts) {
		if (Math.abs((parts.get(0) % 10) - (parts.get(1) % 10)) == 1) {
			board[parts.get(1) / 10][parts.get(1) % 10] = board[parts.get(0) / 10][parts.get(0) % 10];
			board[parts.get(0) / 10][parts.get(0) % 10] = '.';
		}
		else {
			while (parts.size() > 1) {
				board[parts.get(1) / 10][parts.get(1) % 10] = board[parts.get(0) / 10][parts.get(0) % 10];
				board[((parts.get(0) / 10) + (parts.get(1) / 10)) / 2][((parts.get(0) % 10) + (parts.get(1) % 10)) / 2] = '.';
				board[parts.get(0) / 10][parts.get(0) % 10] = '.';
				parts.remove(0);
			}
		}
		promote();
	}
	
	/* Method Name: promote
	 * Parameters: None
	 * Return: None
	 * Output: None
	 * Function: Promotes any piece found on the last row of their respective side
	 */
	
	public static void promote() {
		for (int i = 1; i <= 8; i++) {
			if (board[8][i] == 'o') {
				board[8][i] = 'O';
			}
			if (board[1][i] == 'x') {
				board[1][i] = 'X';
			}
		}
	}
	
	/* Method Name: checkGame
	 * Parameters:
	 * 		int player -> the player of the moves being checked
	 * Return: boolean -> if the player has any moves left
	 * Output: None
	 * Function: Finds out if a given player has any valid moves left to play
	 */
	
	public static boolean checkGame(int player) {
		ArrayList<Integer> moves = validMove(player);
		if (moves.size() == 0) {
			return false;
		}
		return true;
	}
	
	/* Method Name: playerMove
	 * Parameters:
	 * 		int player -> if the player is playing as X or O
	 * Return: None
	 * Output: Prompts for user input
	 * Function: Goes through a single human's player's turn
	 */
	
	public static void playerMove(int player) {
		//variables for each move
		int move;
		String input;
		syncBoard();
		ArrayList<Integer> moves = validMove(player);
		
		//introduction to each move
		outputBoard();
		if (player == 0) {
			System.out.println(playerName[player] + ", make your turn. You are playing as O.");
		}
		else {
			System.out.println(playerName[player] + ", make your turn. You are playing as X.");
		}
		
		//input for each move
		while(true) {
			System.out.print("Move: ");
			input = sc.nextLine();
			if (input.toUpperCase().equals("HELP")) {
				help();
			}
			else if (input.charAt(0) == '?') {
				query(input);
			}
			else if (isNum(input)) {
				move = Integer.parseInt(input);
				if (moves.contains(move)) {
					doMove(moveParts(move));
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
		if (!checkGame((player + 1) % 2)) {
			System.out.println("Player " + playerName[player] + " has won!");
		}
		else {
			playerMove((player + 1) % 2);
		}
	}
	
	/* Method Name: comMove
	 * Parameters:
	 * 		int player -> if the computer is playing as X or O
	 * 		int depth -> how far the computer searches ahead to decide its moves
	 * Return: None
	 * Output: Move done by the computer
	 * Function: Goes through a single move done by the computer
	 */
	
	public static void comMove(int player, int depth) {
	}
	
	/* Method Name: altComMove
	 * Parameters:
	 * 		int player -> if the computer simulates playing as X or O
	 * 		int depth -> how far the computer searches in the future to simulate moves
	 * Return: int -> the optimal move for the current simulated board
	 * Output: None
	 * Function: Simulates a possible play if a turn is played, all simulated by the computer to be used in turn calculations
	 */
	
	public static int altComMove(int player, int depth) {
		return 3;
	}
	
	/* Method Name: query
	 * Parameters:
	 * 		String pre -> the string containing the query
	 * Return: None
	 * Output: The metadata of the given square
	 * Function: Given a square, it returns information about that square if a query is demanded
	 */
	
	public static void query(String pre) {
		//method variables
		int row;
		int col;
		char piece;
		
		//control flow and output
		if (pre.length() == 3 && isNum(pre.substring(1, 2)) && isNum(pre.substring(2, 3))) {
			row = Integer.parseInt(pre.substring(1, 2));
			col = Integer.parseInt(pre.substring(2, 3));
			if (row >= 1 && row <= 8 && col >= 1 && col <= 8) {
				piece = board[row][col];
				if ((row + col) % 2 == 1) {
					System.out.println("The square at row " + row + " and column " + col + " is unreachable.");
				}
				else if (piece == '.') {
					System.out.println("The square at row " + row + " and column " + col + " is empty.");
				}
				else if (piece == 'o') {
					System.out.println("The square at row " + row + " and column " + col + " has a non-king O piece.");
				}
				else if (piece == 'O') {
					System.out.println("The square at row " + row + " and column " + col + " has a king O piece.");
				}
				else if (piece == 'x') {
					System.out.println("The square at row " + row + " and column " + col + " has a non-king X piece.");
				}
				else if (piece == 'X') {
					System.out.println("The square at row " + row + " and column " + col + " has a king X piece.");
				}
				else {
					System.out.println("Invalid query! Please enter a valid query, or enter Help for the help section.");
				}
			}
			else {
				System.out.println("Invalid query! Please enter a valid query, or enter Help for the help section.");
			}
		}
		else {
			System.out.println("Invalid query! Please enter a valid query, or enter Help for the help section.");
		}
	}
	
	/* Method Name: help
	 * Parameters: None
	 * Return: None
	 * Output: A guide on the rules, move input format, and how to enter chain capture moves
	 * Function: Outputs a general help guide for the game of Checkers
	 */
	
	public static void help() {
		//output
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
		System.out.println("Chain captures will always terminate when the capturing piece promotes.");
		System.out.println("Non-king pieces can only move forwards, while king pieces can go both forwards and backwards.");
		System.out.println("Several examples of movement:");
		System.out.println("Only the movement of the O pieces are shown, with (<piece>) being a possible square for any O <piece>.");
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
		System.out.println("This is assuming that the rows start at the bottom with 1, and start on the left side with 1, and both increase by 1 with each square.");
		System.out.println("a) 3113");
		System.out.println("b) 2231");
		System.out.println("c) 5344, 5331, 533113\n");
		System.out.println("Querying:");
		System.out.println("Querying is a way to clearly identify any square.");
		System.out.println("A query can be described as ?<row><column>.");
		System.out.println("Here is an example set for example board a):");
		System.out.println("?11 -> King O Piece, ?12 -> Empty Square, ?22 -> Non-King X Piece\n");
	}
}
