package project;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
	private int[][] boardstate;
	public Board() {
		boardstate = new int[][] { //Representation of Othello board. 1 represents white. 2 represents black.
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 1, 2, 0, 0, 0},
				{0, 0, 0, 2, 1, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0}
		};
	}
	public Board(int[][] board) { 
		this.boardstate = board;
		
	}
	public void printBoard() {
		for(int[] x: boardstate) {
			System.out.println(Arrays.toString(x));
			
		}
	}
	public int[][] getBoard() {
		return this.boardstate;
		
	}
	public ArrayList<int[]> getLegalMoves(int player) {
		int opposite = getOppositePlayer(player);
		ArrayList<int[]> output = new ArrayList<int[]>(20);
		for(int y = 0; y < boardstate.length; y++) {
			outerloop:
			for(int x = 0; x < boardstate[y].length; x++) {
				if(boardstate[y][x] != 0) continue;		
				
				if(x != 6 && x != 7 && boardstate[y][x + 1] == opposite) { //East
					
					for(int i = x + 1; i <= 7; i++) {
						
						if(boardstate[y][i] == 0) break;
						if(boardstate[y][i] == player) {
							int[] coordinate = {y, x};
							output.add(coordinate);
							continue outerloop;
						}
					}
				}
				
				if(x != 0 && x != 1 && boardstate[y][x - 1] == opposite) { //West
					
					for(int i = x - 1; i >= 0; i--) {
						if(boardstate[y][i] == 0) break;
						if(boardstate[y][i] == player) {
							//System.out.println("Fired west");
							int[] coordinate = {y, x};
							output.add(coordinate);
							continue outerloop;
						}
					}
				}
				
				if(y != 6 && y != 7 && boardstate[y + 1][x] == opposite) { //South
					for(int i = y + 1; i <= 7; i++) {
						if(boardstate[i][x] == 0) break;
						if(boardstate[i][x] == player) {
							//System.out.println("Fired south");
							int[] coordinate = {y, x};
							output.add(coordinate);
							continue outerloop;
						}
					}
				}
				
				if(y != 0 && y != 1 && boardstate[y - 1][x] == opposite) { //North
					
					for(int i = y - 1; i >= 0; i--) {
						if(boardstate[i][x] == 0) break;
						if(boardstate[i][x] == player) {
							//System.out.println("Fired north");
							int[] coordinate = {y, x};
							output.add(coordinate);
							continue outerloop;
						}
					}
				}
				
				if(x != 7 && x != 6 && y != 1 && y != 0 && boardstate[y-1][x+1] == opposite) { //North-East
					for(int i = x + 1, j = y - 1; i <= 7 && j >= 0; i++, j--) {
						if(boardstate[j][i] == 0) break;
						if(boardstate[j][i] == player) {
							//System.out.println("Fired north-east");
							int[] coordinate = {y, x};
							output.add(coordinate);
							continue outerloop;
						}
					}
				}
				if(x != 1 && x != 0 && y != 1 && y != 0 && boardstate[y-1][x-1] == opposite) { //North-West
					for(int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
						if(boardstate[j][i] == 0) break;
						if(boardstate[j][i] == player) {
							//System.out.println("Fired North-west");
							int[] coordinate = {y, x};
							output.add(coordinate);
							continue outerloop;
						}
					}
				}		
				if(x != 7 && x != 6 && y != 7 && y != 6 && boardstate[y+1][x+1] == opposite) { //South-East
					for(int i = x + 1, j = y + 1; i <= 7 && j <= 7; i++, j++) {
						if(boardstate[j][i] == 0) break;
						if(boardstate[j][i] == player) {
							//System.out.println("Fired south-east");
							int[] coordinate = {y, x};
							output.add(coordinate);
							continue outerloop;
						}
					}
				}	
				if(x != 1 && x != 0 && y != 7 && y != 6 && boardstate[y+1][x-1] == opposite) { //South-West
					for(int i = x - 1, j = y + 1; i >= 0 && j <= 7; i--, j++) {
						if(boardstate[j][i] == 0) break;
						if(boardstate[j][i] == player) {
							//System.out.println("Fired south-west");
							int[] coordinate = {y, x};
							output.add(coordinate);
							continue outerloop;
						}
					}
				}
				
			}
			
		}
		return output;
	}
	
	public Board makeMove(int player, int[] move) {
		int[][] temp_board = new int[8][8];
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				temp_board[i][j] = boardstate[i][j];
			}
		}
		
		int y = move[0];
		int x = move[1];
		//System.out.println("Changing the board");
		int opposite = getOppositePlayer(player);
		if(x != 6 && x != 7 && temp_board[y][x + 1] == opposite) { //East
			
			for(int i = x + 1; i <= 7; i++) {
				
				if(temp_board[y][i] == 0) break;
				if(temp_board[y][i] == player) {
					for(int change = x; change < i; change++) temp_board[y][change] = player;
					break;
				}
			}
		}
		
		
		if(x != 0 && x != 1 && temp_board[y][x - 1] == opposite) { //West
			
			for(int i = x - 1; i >= 0; i--) {
				if(temp_board[y][i] == 0) break;
				if(temp_board[y][i] == player) {
					for(int change = x; change > i; change--) temp_board[y][change] = player;
					break;
				}
			}
		}
		
		if(y != 6 && y != 7 && temp_board[y + 1][x] == opposite) { //South
			for(int i = y + 1; i <= 7; i++) {
				if(temp_board[i][x] == 0) break;
				if(temp_board[i][x] == player) {
					for(int change = y; change < i; change++) temp_board[change][x] = player;
					break;
				}
			}
		}
		
		if(y != 0 && y != 1 && temp_board[y - 1][x] == opposite) { //North
			for(int i = y - 1; i >= 0; i--) {
				if(temp_board[i][x] == 0) break;
				if(temp_board[i][x] == player) {
					for(int change = y; change > i; change--) temp_board[change][x] = player;
					break;
				}
			}
		}
		
		if(x != 7 && x != 6 && y != 1 && y != 0 && temp_board[y-1][x+1] == opposite) { //North-East
			for(int i = x + 1, j = y - 1; i <= 7 && j >= 0; i++, j--) {
				if(temp_board[j][i] == 0) break;
				if(temp_board[j][i] == player) {
					for(int changeX = x, changeY = y; changeX < i && changeY > j; changeX++, changeY--) temp_board[changeY][changeX] = player;
					break;
				}
			}
		}
		if(x != 1 && x != 0 && y != 1 && y != 0 && temp_board[y-1][x-1] == opposite) { //North-West
			for(int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
				if(temp_board[j][i] == 0) break;
				if(temp_board[j][i] == player) {
					for(int changeX = x, changeY = y; changeX > i && changeY > j; changeX--, changeY--) temp_board[changeY][changeX] = player;
					break;
				}
			}
		}		
		if(x != 7 && x != 6 && y != 7 && y != 6 && temp_board[y+1][x+1] == opposite) { //South-East
			for(int i = x + 1, j = y + 1; i <= 7 && j <= 7; i++, j++) {
				if(temp_board[j][i] == 0) break;
				if(temp_board[j][i] == player) {
					for(int changeX = x, changeY = y; changeX < i && changeY < j; changeX++, changeY++) temp_board[changeY][changeX] = player;
					break;
				}
			}
		}	
		if(x != 1 && x != 0 && y != 7 && y != 6 && temp_board[y+1][x-1] == opposite) { //South-West
			for(int i = x - 1, j = y + 1; i >= 0 && j <= 7; i--, j++) {
				if(temp_board[j][i] == 0) break;
				if(temp_board[j][i] == player) {
					for(int changeX = x, changeY = y; changeX > i && changeY < j; changeX--, changeY++) temp_board[changeY][changeX] = player;
					break;
				}
			}
		}
		return new Board(temp_board);
	}
	//
	
	private int getOppositePlayer(int player) {
		if(player == 1) {
			return 2;
		} else {
			return 1;
		}
		
	}
}
