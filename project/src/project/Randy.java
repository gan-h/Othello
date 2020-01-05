package project;

import java.util.ArrayList;
import java.util.Random;

public class Randy { //This class assumes that there is at least 1 legal move
	public static Board findMove(Board board, int current_player) {
		ArrayList<int[]> moves = board.getLegalMoves(current_player);
		Random r = new Random();
		int move_number = r.nextInt(moves.size() + 1);
		return board.makeMove(current_player, moves.get(move_number));
	}
}
