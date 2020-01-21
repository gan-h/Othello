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
	
	static int[][] analysisValues = new int[][] { 
		{72, 12, 12, 12, 12, 12, 12, 72},
		{12, 1, 1, 1, 1, 1, 1, 12},
		{12, 1, 1, 1, 1, 1, 1, 12},
		{12, 1, 1, 1, 1, 1, 1, 12},
		{12, 1, 1, 1, 1, 1, 1, 12},
		{12, 1, 1, 1, 1, 1, 1, 12},
		{12, 1, 1, 1, 1, 1, 1, 12},
		{72, 12, 12, 12, 12, 12, 12, 72}
	};
	
	public static int analysis(Board board, int player) {
		int[][] boardstate = board.getBoard();
		int score = 0;
		for(int y = 0; y < 8; y++) {
			for(int x = 0; x < 8; x++) {
				if(boardstate[y][x] == 0) continue;
				if(boardstate[y][x] == player) {
					score += analysisValues[y][x];
				}  else {
					score -= analysisValues[y][x];
				}
				 
			}
		} 
		if(player == 2) score = -1 * score;  //if player is black, make added up score negative, since black is minimizing and white is maximizing.
		return score;
	}
	
	public static void changeValues(int[][] values) {
		playerValues = values;
		
	}
	
	public static int outOfMovesEval(Board board, int player) {
		int whiteCount = 0;
		int blackCount = 0;
		int[][] boardstate = board.getBoard();
		for(int[] y : boardstate) {
			for(int x : y) {
				if(x == 1) {
					whiteCount++;
				} else if (x == 2) {
					blackCount++;
				}
				
			}
		}
		
		if(whiteCount == blackCount) {
			return 0;
		}
		
		if(whiteCount > blackCount) {
			return 604;
			
		}
		
		else  {
			return -604;
			
		}
		
	}
	
	
	public static int piecewise(Board board, int player) {
		int whiteCount = 0;
		int blackCount = 0;
		int[][] boardstate = board.getBoard();
		for(int[] y : boardstate) {
			for(int x : y) {
				if(x == 1) {
					whiteCount++;
				} else if (x == 2) {
					blackCount++;
				}
				
			}
		}
		
		int output = whiteCount - blackCount;
		return output;
		
	}

	
}
