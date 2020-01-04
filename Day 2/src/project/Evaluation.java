package project;

import java.util.Arrays;

public class Evaluation {
	static int[][] playerValues = new int[][] { 
		{1000, 20, 20, 20, 20, 20, 20, 1000},
		{20, 1, 1, 1, 1, 1, 1, 20},
		{20, 1, 1, 1, 1, 1, 1, 20},
		{20, 1, 1, 1, 1, 1, 1, 20},
		{20, 1, 1, 1, 1, 1, 1, 20},
		{20, 1, 1, 1, 1, 1, 1, 20},
		{20, 1, 1, 1, 1, 1, 1, 20},
		{1000, 20, 20, 20, 20, 20, 20, 1000}
	};
	public static int evaluate(Board board, int player) {
		int[][] boardstate = board.getBoard();
		int score = 0;
		for(int y = 0; y < 8; y++) {
			for(int x = 0; x < 8; x++) {
				if(boardstate[y][x] == 0) continue;
				if(boardstate[y][x] == player) {
					score += playerValues[y][x];
				}  else {
					score -= playerValues[y][x];
				}
				 
			}
		} 
		if(player == 2) score = -1 * score;  //if player is black, make added up score negative, since black is minimizing and white is maximizing.
		return score;
	}
	
	public static void changeValues(int[][] values) {
		playerValues = values;
		
	}

	
}
