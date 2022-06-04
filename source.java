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
	static char board[][] = new char[8][8];
	static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.print("Welcome to a game of checkers!");
		System.out.print("How many players are there?");
		
		newGameStart();
		outputBoard();
	}
	
	
	//outputs board with axis coordinates - works
	public static void outputBoard() {
		System.out.print("  ");
		for (int i = 0; i < 8; i++) {
			System.out.print(i + 1 + " ");
		}
		System.out.print(" \n");
		for (int i = 0; i < 8; i++) {
			System.out.print(i + 1 + " ");
			for (int j = 0; j < 8; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.print(i + 1 + "\n");
		}
		System.out.print("  ");
		for (int i = 0; i < 8; i++) {
			System.out.print(i + 1 + " ");
		}
		System.out.print(" \n");
	}
	
	//sets up a new board - works
	public static void newGameStart() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 1) {
					board[i][j] = 'X';
				}
				else {
					board[i][j] = '*';
				}
			}
		}
		for (int i = 3; i < 5; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = '*';
			}
		}
		for (int i = 5; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 1) {
					board[i][j] = 'O';
				}
				else {
					board[i][j] = '*';
				}
			}
		}
	}
	
	//O move
	
	//X move
	
	//returns a boolean value of if a string can be parsed into a integer
	public static boolean isNum(String input) {
		boolean isNum = false;
		try {
			Integer.parseInt(input);
			isNum = true;
		} catch (NumberFormatException e) {}
		return isNum;
	}
}
