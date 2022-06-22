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
	//variables, containers, and objects used in a static reference (deal with it)
	static boolean twoPlayer;
	static int comDepth = -1;
	static int turns = -1;
	static final int inf = 2147483647;
	static String fileName;
	static int capture[] = {0, 0};
	static char board[][] = new char[10][10];
	static char simBoard[][] = new char[10][10];
	static char playerNum[] = {'o', 'x', 'O', 'X'};
	static String playerName[] = new String[2];
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		//flow control
		menu();
	}
	
	/* Method Name: menu
	 * Parameters: None
	 * Return: None
	 * Output: Prompts from the menu
	 * Function: Starting point of each game
	 */	
	public static void menu() {
		//variables, containers, and objects
		int loadingGame;
		String input;
		
		//flow control
		System.out.println("Menu:");
		while (true) {
			System.out.print("One player (1) or two players (2): ");
			input = sc.nextLine();
			if (isNum(input)) {
				if (Integer.parseInt(input) == 1) {
					twoPlayer = false;
					break;
				}
				if (Integer.parseInt(input) == 2) {
					twoPlayer = true;
					break;
				}
			}
			System.out.println("That is not a valid input, please try again.");
		}
		newGameStart();
		if (twoPlayer) {
			//two player
			System.out.print("Player 1's name: ");
			playerName[0] = sc.nextLine();
			System.out.print("Player 2's name: ");
			playerName[1] = sc.nextLine();
			while (true) {
				fileName = playerName[0] + "-" + playerName[1] + ".txt";
				System.out.print("New game (1) or load a game (2): ");
				input = sc.nextLine();
				if (isNum(input)) {
					loadingGame = Integer.parseInt(input);
					if (loadingGame == 1 || loadingGame == 2) {
						break;
					}
				}
				System.out.println("That is not a valid input, please try again.");
			}
			if (loadingGame == 2) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(fileName));
					br.close();
					loadGame();
				} catch (Exception e) {
					System.out.println("There is no saved game under this matching, making new game instead.");
				}
			}
			playerMove(0);
		}
		else {
			//one player
			System.out.print("Player's name: ");
			input = sc.nextLine();
			playerName[0] = input;
			playerName[1] = input;
			fileName = input + ".txt";
			while (true) {
				System.out.print("New game (1) or load a game (2): ");
				input = sc.nextLine();
				if (isNum(input)) {
					loadingGame = Integer.parseInt(input);
					if (loadingGame == 1 || loadingGame == 2) {
						break;
					}
				}
				System.out.println("That is not a valid input, please try again.");
			}
			if (loadingGame == 2) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(fileName));
					br.close();
					loadGame();
				} catch (Exception e) {
					System.out.println("There is no saved game under this matching, making new game instead.");
				}
			}
			while (true) {
				System.out.print("Computer depth (between 1 and 6 for faster but less accurate play, 7 or 8 for slower but more accurate play): ");
				input = sc.nextLine();
				if (isNum(input)) {
					comDepth = Integer.parseInt(input);
					if (comDepth >= 1 && comDepth <= 8) {
						break;
					}
				}
				System.out.println("That is not a valid input, please try again.");
			}
			while (true) {
				System.out.print("Would you like to go first (1) or second (2): ");
				input = sc.nextLine();
				if (isNum(input)) {
					if (Integer.parseInt(input) == 1) {
						playerName[1] = null;
						playerMove(0);
						break;
					}
					if (Integer.parseInt(input) == 2) {
						playerName[0] = null;
						comMove(0, comDepth);
						break;
					}
				}
				System.out.println("That is not a valid input, please try again.");
			}
		}
	}
	
	/* Method Name: loadGame
	 * Parameters: None
	 * Return: None
	 * Output: None
	 * Function: Loads a game from a text file
	 */
	
	public static void loadGame() {
		//variables, containers, and objects
		int playerData;
		int playerTurn;
		String input;
		
		//flow control
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			playerData = Integer.parseInt(br.readLine());
			for (int i = 1; i <= 8; i++) {
				input = br.readLine();
				for (int j = 1; j <= 8; j++) {
					board[i][j] = input.charAt(j - 1);
				}
			}
			playerTurn = Integer.parseInt(br.readLine());
			turns = Integer.parseInt(br.readLine());
			capture[0] = Integer.parseInt(br.readLine());
			capture[1] = Integer.parseInt(br.readLine());
			comDepth = Integer.parseInt(br.readLine());
			br.close();
			if (playerData == 2) {
				playerMove(playerTurn);
			}
			else if (playerData == playerTurn) {
				playerMove(playerTurn);
			}
			else {
				comMove(playerTurn, comDepth);
			}
		} catch (Exception e) {
			System.out.println("You should not be seeing this message.");
		}
	}
	
	/* Method Name: saveGame
	 * Parameters:
	 * 		int player -> the player who plays next
	 * Return: None
	 * Output: None
	 * Function: Saves the game in a text file
	 */
	
	public static void saveGame(int player) {
		//flow control
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, false));
			if (twoPlayer) {
				bw.write("2\n");
			}
			else if (playerName[0] == null) {
				bw.write("1\n");
			}
			else {
				bw.write("0\n");
			}
			for (int i = 1; i <= 8; i++) {
				for (int j = 1; j <= 8; j++) {
					bw.write(board[i][j]);
				}
				bw.write("\n");
			}
			bw.write(Integer.toString(player) + "\n");
			bw.write(Integer.toString(turns) + "\n");
			bw.write(Integer.toString(capture[0]) + "\n");
			bw.write(Integer.toString(capture[1]) + "\n");
			bw.write(Integer.toString(comDepth));
			bw.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/* Method Name: quitGame
	 * Parameters: None
	 * Return: None
	 * Output: A quit statement
	 * Function: Acts as a logical dead end for a game to quit a game
	 */
	public static void quitGame() {
		//variables, containers, and objects
		String input;
		
		//flow control
		while (true) {
			System.out.print("Menu (1) or quit (2): ");
			input = sc.nextLine();
			if (isNum(input)) {
				if (Integer.parseInt(input) == 1) {
					System.out.println();
					menu();
				}
				if (Integer.parseInt(input) == 2) {
					System.out.println("Quitting game.");
					break;
				}
			}
			System.out.println("That is not a valid input, please try again.");
		}
	}
	
	/* Method Name: isNum
	 * Parameters:
	 * 		String inString -> a string which may be parsed into an integer
	 * Return: boolean -> if the string inString can be parsed into an integer
	 * Output: None
	 * Function: Returns a boolean value for if a given string can be parsed into an integer
	 */
	
	public static boolean isNum(String inString) {
		//variables, containers, and objects
		boolean isNum = false;
		
		//flow control
		try {
			Integer.parseInt(inString);
			isNum = true;
		} catch (NumberFormatException e) {}
		return isNum;
	}
	
	/* Method Name: outputBoard
	 * Parameters: None
	 * Return: None
	 * Output: Game board along with axis coordinates
	 * Function: Outputs the real board with row and column axis coordinates
	 */
	
	public static void outputBoard() {
		//flow control and output
		System.out.print("\n  ");
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
		//flow control
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
		//flow control
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
		//variables, containers, and objects
		ArrayList<Integer> tempParts = new ArrayList<Integer>();
		ArrayList<Integer> parts = new ArrayList<Integer>();
		
		//flow control
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
	
	/* Method Name: insert
	 * Parameters:
	 * 		ArrayList<ArrayList<Integer>> moves -> a list of valid moves
	 * 		ArrayList<Integer> move -> a composition of the move to be inserted
	 * Return: ArrayList<Integer> -> a blank move composition
	 * Output: None
	 * Function: Properly adds moves to a list while returning a new slate to be used
	 */
	
	public static ArrayList<Integer> insert(ArrayList<Integer> move, ArrayList<ArrayList<Integer>> moves) {
		//variables, containers, and objects
		ArrayList<Integer> newMove = new ArrayList<Integer>();
		
		//flow control
		moves.add(move);
		return newMove;
	}
	
	/* Method Name: validMove
	 * Parameters:
	 * 		int player -> the player of which the moves are found
	 * Return: ArrayList<ArrayList<Integer>> -> a list of all valid moves by a player in a given position
	 * Output: None
	 * Function: Finds out all the valid moves for a player in a given position
	 */
	
	public static ArrayList<ArrayList<Integer>> validMove(int player) {
		//variables, containers, and objects
		ArrayList<Integer> move = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
		
		//flow control
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				if (simBoard[i][j] == playerNum[player] || simBoard[i][j] == playerNum[player + 2]) {
					if (simBoard[i][j] != playerNum[0]) {
						if (simBoard[i - 1][j - 1] == '.') {
							move.add((i * 10) + j);
							move.add(((i - 1) * 10) + j - 1);
							move = insert(move, moves);
						}
						if (simBoard[i - 1][j + 1] == '.') {
							move.add((i * 10) + j);
							move.add(((i - 1) * 10) + j + 1);
							move = insert(move, moves);
						}
					}
					if (simBoard[i][j] != playerNum[1]) {
						if (simBoard[i + 1][j - 1] == '.') {
							move.add((i * 10) + j);
							move.add(((i + 1) * 10) + j - 1);
							move = insert(move, moves);
						}
						if (simBoard[i + 1][j + 1] == '.') {
							move.add((i * 10) + j);
							move.add(((i + 1) * 10) + j + 1);
							move = insert(move, moves);
						}
					}
				}
				for (ArrayList<Integer> k : altValidMove(player, (i * 10) + j)) {
					k.add(0, (i * 10) + j);
					moves.add(k);
				}
			}
		}
		return moves;
	}
	
	/* Method Name: altValidMove
	 * Parameters:
	 * 		int player -> the side of which we are using
	 * 		int pre -> the square which the capturing piece is on
	 * Return: ArrayList<ArrayList<Integer>> -> a list of all valid captures for a given piece in a position
	 * Output: None
	 * Function: Given a starting square in the simulated board, it returns all possible captures and chain captures from that square for a certain player in a given position
	 */
	
	public static ArrayList<ArrayList<Integer>> altValidMove(int player, int pre) {
		//variables, containers, and objects
		char save;
		ArrayList<Integer> move = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> tempMoves = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
		
		//flow control
		if (simBoard[pre / 10][pre % 10] == playerNum[player] || simBoard[pre / 10][pre % 10] == playerNum[player + 2]) {
			if (simBoard[pre / 10][pre % 10] != playerNum[0]) {
				if (simBoard[(pre / 10) - 1][(pre % 10) - 1] == playerNum[(player + 1) % 4] || simBoard[(pre / 10) - 1][(pre % 10) - 1] == playerNum[(player + 3) % 4]) {
					if (simBoard[(pre / 10) - 2][(pre % 10) - 2] == '.') {
						move.add((((pre / 10) - 2) * 10) + (pre % 10) - 2);
						move = insert(move, tempMoves);
					}
				}
				if (simBoard[(pre / 10) - 1][(pre % 10) + 1] == playerNum[(player + 1) % 4] || simBoard[(pre / 10) - 1][(pre % 10) + 1] == playerNum[(player + 3) % 4]) {
					if (simBoard[(pre / 10) - 2][(pre % 10) + 2] == '.') {
						move.add((((pre / 10) - 2) * 10) + (pre % 10) + 2);
						move = insert(move, tempMoves);
					}
				}
			}
			if (simBoard[pre / 10][pre % 10] != playerNum[1]) {
				if (simBoard[(pre / 10) + 1][(pre % 10) - 1] == playerNum[(player + 1) % 4] || simBoard[(pre / 10) + 1][(pre % 10) - 1] == playerNum[(player + 3) % 4]) {
					if (simBoard[(pre / 10) + 2][(pre % 10) - 2] == '.') {
						move.add((((pre / 10) + 2) * 10) + (pre % 10) - 2);
						move = insert(move, tempMoves);
					}
				}
				if (simBoard[(pre / 10) + 1][(pre % 10) + 1] == playerNum[(player + 1) % 4] || simBoard[(pre / 10) + 1][(pre % 10) + 1] == playerNum[(player + 3) % 4]) {
					if (simBoard[(pre / 10) + 2][(pre % 10) + 2] == '.') {
						move.add((((pre / 10) + 2) * 10) + (pre % 10) + 2);
						move = insert(move, tempMoves);
					}
				}
			}
		}
		for (ArrayList<Integer> i : tempMoves) {
			moves.add(i);
			save = simBoard[((pre / 10) + (i.get(0) / 10)) / 2][((pre % 10) + (i.get(0) % 10)) / 2];
			simBoard[((pre / 10) + (i.get(0) / 10)) / 2][((pre % 10) + (i.get(0) % 10)) / 2] = '.';
			simBoard[i.get(0) / 10][i.get(0) % 10] = simBoard[pre / 10][pre % 10];
			simBoard[pre / 10][pre % 10] = '.';
			for (ArrayList<Integer> j : altValidMove(player, i.get(0))) {
				j.add(0, i.get(0));
				moves.add(j);
			}
			simBoard[((pre / 10) + (i.get(0) / 10)) / 2][((pre % 10) + (i.get(0) % 10)) / 2] = save;
			simBoard[pre / 10][pre % 10] = simBoard[i.get(0) / 10][i.get(0) % 10];
			simBoard[i.get(0) / 10][i.get(0) % 10] = '.';
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
		//flow control
		if (Math.abs((parts.get(0) % 10) - (parts.get(1) % 10)) == 1) {
			board[parts.get(1) / 10][parts.get(1) % 10] = board[parts.get(0) / 10][parts.get(0) % 10];
			board[parts.get(0) / 10][parts.get(0) % 10] = '.';
		}
		else {
			for (int j = 0; j < parts.size() - 1; j++) {
				if (board[parts.get(j) / 10][parts.get(j) % 10] == 'o' || board[parts.get(j) / 10][parts.get(j) % 10] == 'O') {
					capture[0]++;
				}
				else {
					capture[1]++;
				}
				board[parts.get(j + 1) / 10][parts.get(j + 1) % 10] = board[parts.get(j) / 10][parts.get(j) % 10];
				board[((parts.get(j) / 10) + (parts.get(j + 1) / 10)) / 2][((parts.get(j) % 10) + (parts.get(j + 1) % 10)) / 2] = '.';
				board[parts.get(j) / 10][parts.get(j) % 10] = '.';
			}
		}
		promote();
		syncBoard();
	}
	
	/* Method Name: promote
	 * Parameters: None
	 * Return: None
	 * Output: None
	 * Function: Promotes any piece found on the last row of their respective side
	 */
	
	public static void promote() {
		//flow control
		for (int i = 1; i <= 8; i++) {
			if (board[8][i] == 'o') {
				board[8][i] = 'O';
			}
			if (board[1][i] == 'x') {
				board[1][i] = 'X';
			}
		}
	}
	
	/* Method Name: endGame
	 * Parameters:
	 * 		boolean isCom -> if the winner is a computer
	 * 		int player -> if the winner is playing as X or O
	 * Return None:
	 * Output: The ending outputs for a game ending
	 * Function: Ends a game with proper outputs
	 */
	
	public static void endGame(boolean isCom, int player) {
		//variables, containers, and objects
		File file = new File(fileName);
		
		//flow control and output
		if (isCom) {
			System.out.println("The computer has won!");
		}
		else {
			System.out.println("Player " + playerName[player] + " has won!");
		}
		System.out.println("The game lasted " + turns + " turns.");
		if (twoPlayer) {
			System.out.println("Player " + playerName[0] + " captured " + capture[0] + " X pieces.");
			System.out.println("Player " + playerName[1] + " captured " + capture[1] + " O pieces.");
		}
		else {
			if (playerName[0] == null) {
				System.out.println("The computer captured " + capture[0] + " X pieces.");
				System.out.println("Player " + playerName[1] + " captured " + capture[1] + " O pieces");
			}
			else {
				System.out.println("Player " + playerName[0] + " captured " + capture[0] + " O pieces");
				System.out.println("The computer captured " + capture[1] + " X pieces.");
			}
		}
		System.out.println("");
		file.delete();
		quitGame();
	}
	
	/* Method Name: checkGame
	 * Parameters:
	 * 		int player -> the player of the moves being checked
	 * Return: boolean -> if the player has any moves left
	 * Output: None
	 * Function: Finds out if a given player has any valid moves left to play
	 */
	
	public static boolean checkGame(int player) {
		//variables, containers, and objects
		ArrayList<ArrayList<Integer>> moves = validMove(player);
		
		//flow control
		if (moves.size() == 0) {
			return false;
		}
		return true;
	}
	
	/* Method Name: checkMove
	 * Parameters:
	 * 		ArrayList<Integer> move -> the move received to be checked
	 * 		ArrayList<ArrayList<Integer>> -> the set of moves to check
	 * Return: boolean -> if the move set contains a certain move
	 * Output: None
	 * Function: Finds if a move set contains a certain move
	 */
	
	public static boolean checkMove(ArrayList<Integer> move, ArrayList<ArrayList<Integer>> moves) {
		//flow control
		for (ArrayList<Integer> i : moves) {
			if (move.equals(i)) {
				return true;
			}
		}
		return false;
	}
	
	/* Method Name: calcValue
	 * Parameters:
	 * 		int player: the player of which to calculate a value
	 * Return: int -> the calculated value of a given board
	 * Output: None
	 * Function: Finds out the calculated value of a given board
	 */
	
	public static double calcValue(int player) {
		//variables, containers, and objects
		double value = 0;
		
		//flow control
		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				if (simBoard[i][j] == 'o') {
					if (player == 0) {
						value += (10 + i);
					}
					else {
						value -= (10 + i);
					}
				}
				if (simBoard[i][j] == 'O') {
					if (player == 0) {
						value += (25 - Math.abs(4.5 - i) - Math.abs(4.5 - j));
					}
					else {
						value -= (25 - Math.abs(4.5 - i) - Math.abs(4.5 - j));
					}
				}
				if (simBoard[i][j] == 'x') {
					if (player == 0) {
						value -= (10 + (9 - i));
					}
					else {
						value += (10 + (9 - i));
					}
					
				}
				if (simBoard[i][j] == 'X') {
					if (player == 0) {
						value -= (25 - Math.abs(4.5 - i) - Math.abs(4.5 - j));
					}
					else {
						value += (25 - Math.abs(4.5 - i) - Math.abs(4.5 - j));
					}
				}
			}
		}
		return value;
	}
	
	/* Method Name: playerMove
	 * Parameters:
	 * 		int player -> if the player is playing as X or O
	 * Return: None
	 * Output: Prompts for user input
	 * Function: Goes through a single human's player's turn
	 */
	
	public static void playerMove(int player) {
		//variables, containers, and objects
		boolean resign = false;
		boolean quit = false;
		int move;
		String input;
		syncBoard();
		ArrayList<ArrayList<Integer>> moves = validMove(player);
		
		//starting turn
		outputBoard();
		turns++;
		if (player == 0) {
			System.out.println(playerName[player] + ", make your turn. You are playing as O.");
		}
		else {
			System.out.println(playerName[player] + ", make your turn. You are playing as X.");
		}
		System.out.print("Moves: ");
		for (ArrayList<Integer> i : moves) {
			for (int j : i) {
				System.out.print(j);
			}
			System.out.print(" ");
		}
		System.out.println("");
		
		//flow control
		while(true) {
			System.out.print("Move: ");
			input = sc.nextLine();
			if (input.toUpperCase().equals("HELP")) {
				help();
			}
			else if (input.toUpperCase().equals("RESIGN")) {
				resign = true;
				break;
			}
			else if (input.toUpperCase().equals("QUIT")) {
				quit = true;
				break;
			}
			else if (input.length() == 0) {
				System.out.println("Invalid move! Please enter a valid move, or enter Help for the help section.");
			}
			else if (input.charAt(0) == '?') {
				query(input);
			}
			else if (isNum(input)) {
				move = Integer.parseInt(input);
				if (checkMove(moveParts(move), moves)) {
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
		
		//ending turn
		if (quit) {
			quitGame();
		}
		else {
			saveGame((player + 1) % 2);
			if (!checkGame((player + 1) % 2)) {
				endGame(true, player);
			}
			else if (resign) {
				endGame(!twoPlayer, (player + 1) % 2);
			}
			else {
				if (twoPlayer) {
					playerMove((player + 1) % 2);
				}
				else {
					comMove((player + 1) % 2, comDepth);
				}
			}
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
		//variables, containers, and objects
		double value = -inf;
		double tempValue;
		ArrayList<Integer> move = new ArrayList<Integer>();
		ArrayList<Character> saves = new ArrayList<Character>();
		syncBoard();
		ArrayList<ArrayList<Integer>> moves = validMove(player);
		
		//starting turn
		outputBoard();
		turns++;
		System.out.print("Moves: ");
		for (ArrayList<Integer> i : moves) {
			for (int j : i) {
				System.out.print(j);
			}
			System.out.print(" ");
		}
		System.out.println("");
		
		//flow control
		for (ArrayList<Integer> i : moves) {
			saves.clear();
			if (Math.abs((i.get(0) / 10) - (i.get(1) / 10)) == 1) {
				simBoard[i.get(1) / 10][i.get(1) % 10] = simBoard[i.get(0) / 10][i.get(0) % 10];
				simBoard[i.get(0) / 10][i.get(0) % 10] = '.';
			}
			else {
				for (int j = 0; j < i.size() - 1; j++) {
					simBoard[i.get(j + 1) / 10][i.get(j + 1) % 10] = simBoard[i.get(j) / 10][i.get(j) % 10];
					saves.add(simBoard[((i.get(j) / 10) + (i.get(j + 1) / 10)) / 2][((i.get(j) % 10) + (i.get(j + 1) % 10)) / 2]);
					simBoard[((i.get(j) / 10) + (i.get(j + 1) / 10)) / 2][((i.get(j) % 10) + (i.get(j + 1) % 10)) / 2] = '.';
					simBoard[i.get(j) / 10][i.get(j) % 10] = '.';
				}
			}
			if (depth == 1) {
				tempValue = -calcValue((player + 1) % 2);
			}
			else {
				tempValue = -altComMove((player + 1) % 2, depth - 1);
			}
			if (tempValue >= value) {
				value = tempValue;
				move = i;
			}
			if (Math.abs((i.get(0) / 10) - (i.get(1) / 10)) == 1) {
				simBoard[i.get(0) / 10][i.get(0) % 10] = simBoard[i.get(1) / 10][i.get(1) % 10];
				simBoard[i.get(1) / 10][i.get(1) % 10] = '.';
			}
			else {
				for (int j = i.size() - 1; j > 0; j--) {
					simBoard[i.get(j - 1) / 10][i.get(j - 1) % 10] = simBoard[i.get(j) / 10][i.get(j) % 10];
					simBoard[((i.get(j - 1) / 10) + (i.get(j) / 10)) / 2][((i.get(j - 1) % 10) + (i.get(j) % 10)) / 2] = saves.get(j - 1);
					simBoard[i.get(j) / 10][i.get(j) % 10] = '.';
				}
			}
		}
		doMove(move);
		System.out.print("The computer has played ");
		for (int i : move) {
			System.out.print(i);
		}
		System.out.println(".");
		
		//ending turn
		saveGame((player + 1) % 2);
		if (!checkGame((player + 1) % 2)) {
			endGame(true, player);
		}
		else {
			playerMove((player + 1) % 2);
		}
	}
	
	/* Method Name: altComMove
	 * Parameters:
	 * 		int player -> if the computer simulates playing as X or O
	 * 		int depth -> how far the computer searches in the future to simulate moves
	 * Return: double -> the optimal evaluation for the current simulated board
	 * Output: None
	 * Function: Simulates a possible play if a turn is played, all simulated by the computer to be used in turn calculations
	 */
	
	public static double altComMove(int player, int depth) {
		//variables, containers, and objects
		double value = -inf;
		double tempValue;
		ArrayList<Character> saves = new ArrayList<Character>();
		ArrayList<ArrayList<Integer>> moves = validMove(player);
		
		//flow control
		for (ArrayList<Integer> i : moves) {
			saves.clear();
			if (Math.abs((i.get(0) / 10) - (i.get(1) / 10)) == 1) {
				simBoard[i.get(1) / 10][i.get(1) % 10] = simBoard[i.get(0) / 10][i.get(0) % 10];
				simBoard[i.get(0) / 10][i.get(0) % 10] = '.';
			}
			else {
				for (int j = 0; j < i.size() - 1; j++) {
					simBoard[i.get(j + 1) / 10][i.get(j + 1) % 10] = simBoard[i.get(j) / 10][i.get(j) % 10];
					saves.add(simBoard[((i.get(j) / 10) + (i.get(j + 1) / 10)) / 2][((i.get(j) % 10) + (i.get(j + 1) % 10)) / 2]);
					simBoard[((i.get(j) / 10) + (i.get(j + 1) / 10)) / 2][((i.get(j) % 10) + (i.get(j + 1) % 10)) / 2] = '.';
					simBoard[i.get(j) / 10][i.get(j) % 10] = '.';
				}
			}
			if (depth == 1) {
				tempValue = -calcValue((player + 1) % 2);
			}
			else {
				tempValue = -altComMove((player + 1) % 2, depth - 1);
			}
			if (tempValue >= value) {
				value = tempValue;
			}
			if (Math.abs((i.get(0) / 10) - (i.get(1) / 10)) == 1) {
				simBoard[i.get(0) / 10][i.get(0) % 10] = simBoard[i.get(1) / 10][i.get(1) % 10];
				simBoard[i.get(1) / 10][i.get(1) % 10] = '.';
			}
			else {
				for (int j = i.size() - 1; j > 0; j--) {
					simBoard[i.get(j - 1) / 10][i.get(j - 1) % 10] = simBoard[i.get(j) / 10][i.get(j) % 10];
					simBoard[((i.get(j - 1) / 10) + (i.get(j) / 10)) / 2][((i.get(j - 1) % 10) + (i.get(j) % 10)) / 2] = saves.get(j - 1);
					simBoard[i.get(j) / 10][i.get(j) % 10] = '.';
				}
			}
		}
		return value;
	}
	
	/* Method Name: query
	 * Parameters:
	 * 		String pre -> the string containing the query
	 * Return: None
	 * Output: The metadata of the given square
	 * Function: Given a square, it returns information about that square if a query is demanded
	 */
	
	public static void query(String pre) {
		//variables, containers, and objects
		int row;
		int col;
		char piece;
		
		//flow control and output
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
		System.out.println("Resigning:");
		System.out.println("Resigning is an option that can be made at any time during a turn, and can be done by entering <Resign>.\n");
		System.out.println("Saving And Quitting:");
		System.out.println("During you turn, you can quit. Saving is done automatically after each move.");
		System.out.println("Quitting simply requires the user to enter <Quit>.\n");
	}
}
