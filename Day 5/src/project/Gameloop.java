package project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Gameloop {
	final static int white = 1;
	final static int black = 2;
	
	public static void main(String args[]) {
		boolean finished = false;
		Scanner keyboard = new Scanner(System.in);
		Board board = new Board();
		
		String message = Arrays.toString(Minimax.findMove(board, 1, black));
		System.out.println(message);
		
		
		
		
		
		
		
		
		
		
	}
}
