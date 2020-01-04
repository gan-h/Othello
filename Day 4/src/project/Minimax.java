package project;

import java.util.Arrays;

public class Minimax {
	final static int white = 1;
	final static int black = 2;
	
	public static int[] findMove(Board board, int depth, int player) {
		int[] final_move = new int[1]; //Is left as [0] if there are no moves found.
		if(player == white) {
			int maxEval = Integer.MIN_VALUE;
			for(int[] move : board.getLegalMoves(player)) {
				System.out.println(Arrays.toString(move));
				int eval = Minimax.minimax(board.makeMove(player, move), depth - 1, black);
				board.makeMove(player, move).printBoard();
				System.out.println(eval);
				int previous = maxEval;
				maxEval = greater(maxEval, eval);
				if (previous != maxEval) final_move = move;
			}
		}
		else { //player == black
			int minEval = Integer.MAX_VALUE;
			for(int[] move : board.getLegalMoves(player)) {
				int eval = Minimax.minimax(board.makeMove(player, move), depth - 1, white);
				
				int previous = minEval;
				minEval = lesser(minEval, eval);
				if (previous != minEval) final_move = move;
			}
		}
		return final_move;
	}
	
	public static int minimax(Board board, int depth, int player) {
		if(depth == 0 || board.getLegalMoves(player).size() == 0) {
			return Evaluation.evaluate(board, player);
		}
		if(player == white) {
			System.out.println(depth);
			int maxEval = Integer.MIN_VALUE;
			for(int[] move : board.getLegalMoves(player)) {
				int eval = Minimax.minimax(board.makeMove(player, move), depth - 1, black);
				maxEval = greater(maxEval, eval);
			}
			return maxEval;
		}
		else { //player == black
			int minEval = Integer.MAX_VALUE;
			for(int[] move : board.getLegalMoves(player)) {
				int eval = Minimax.minimax(board.makeMove(player, move), depth - 1, white);
				minEval = lesser(minEval, eval);
			}
			return minEval;
		}
	}
	
	private static int greater(int a, int b) {
		if(a >= b) {
			return a;
		} else {
			return b;
		}
	}
	
	private static int lesser(int a, int b) {
		if(a <= b) {
			return a;
		} else {
			return b;
		}
	}
}
