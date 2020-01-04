package project;

import java.util.ArrayList;
import java.util.Arrays;


public class Gameloop {
	final static int white = 1;
	final static int black = 2;
	
	public static void main(String args[]) {
		Board test = new Board();
		ArrayList<int[]> legal_list = test.getLegalMoves(white);
		
		for(int[] x : legal_list) {
			System.out.println(Arrays.toString(x));
			test.makeMove(white, x);
		}
		
		test.printBoard();
	}
}
