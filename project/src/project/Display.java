package project;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.*;
public class Display extends JPanel implements ActionListener {
	protected JButton[][] buttons;
	private Stat_Display statDisplay; //Designated variable for storing reference to statDisplay so that statDisplay may be influenced.
	private Board boardstate;
	private int current_player;
	private static int empty = 0;
	private static int white = 1;
	private static int black = 2;
	private JLabel[] numbers;
	private JLabel[] letters;
	private EndScreen endScreen;
	boolean gameOver;
	public Display() {        //Constructor overlays a button over every square in the grid
		gameOver = false;
		endScreen = new EndScreen();
		endScreen.setBounds(152, 121, 182, 240);
		endScreen.display = this;
		endScreen.setVisible(false);
		this.add(endScreen);
		
		boardstate = new Board();
		current_player = white;
		setLayout(null);
		buttons = new JButton[8][8];
		numbers = new JLabel[8];
		letters = new JLabel[8];
		for(int y = 0; y < 8; y++) {
			for(int x = 0; x < 8; x ++) {
				 if(boardstate.getBoard()[y][x] == empty) {
						buttons[y][x] = new JButton(); 
						buttons[y][x].setBounds(x * 60, y * 60 + 0, 60, 60);
						this.add(buttons[y][x]);
					}
				 else if(boardstate.getBoard()[y][x] == white) {
					buttons[y][x] = new JButton(new ImageIcon(this.getClass().getResource("/White_Piece.png"))); 
					buttons[y][x].setBounds(x * 60 , y * 60 + 0, 60, 60);
					this.add(buttons[y][x]);
				}
				 else  {
					buttons[y][x] = new JButton(new ImageIcon(this.getClass().getResource("/Black_Piece.png"))); 
					buttons[y][x].setBounds(x * 60 , y * 60 + 0, 60, 60);
					this.add(buttons[y][x]);
				}
				 buttons[y][x].setActionCommand((y * 8 + x) + ""); //Create unique ID tag for each square for event handling
				 buttons[y][x].addActionListener(this); //When button is clicked, pass on event to an instance of this class.
				 buttons[y][x].setOpaque(false); 
				 buttons[y][x].setContentAreaFilled(false);
				 buttons[y][x].setBorderPainted(false);
				 buttons[y][x].setFocusPainted(false);
				 
				
			}
			numbers[0] = new JLabel("1");
			numbers[1] = new JLabel("2");
			numbers[2] = new JLabel("3");
			numbers[3] = new JLabel("4");
			numbers[4] = new JLabel("5");
			numbers[5] = new JLabel("6");
			numbers[6] = new JLabel("7");
			numbers[7] = new JLabel("8");
			
			letters[0] = new JLabel("a");
			letters[1] = new JLabel("b");
			letters[2] = new JLabel("c");
			letters[3] = new JLabel("d");
			letters[4] = new JLabel("e");
			letters[5] = new JLabel("f");
			letters[6] = new JLabel("g");
			letters[7] = new JLabel("h");
			
			
			for(int i = 0; i < 8; i++) {
				letters[i].setBounds(0, 60 * i - 1, 9, 15);
				numbers[i].setBounds(60 * i + 52, 467, 9, 9);
				this.add(numbers[i]);
				this.add(letters[i]);
			}
		}
		repaint();
	}
	@Override
	public Dimension getPreferredSize() { //Set size for this panel
		return new Dimension(480, 480);
	}
	
	public void paintComponent(Graphics g) {  //Draw the Othello board
		super.paintComponent(g);
		g.setColor(new Color(0, 199, 17));
		g.fillRect(0, 0, 480, 480);
		g.setColor(Color.BLACK);
		//Horizontal Black Lines
		g.fillRect(0, 61,  480, 1);
		g.fillRect(0, 121, 480, 1);
		g.fillRect(0, 181, 480, 1);
		g.fillRect(0, 241, 480, 1);
		g.fillRect(0, 301, 480, 1);
		g.fillRect(0, 361, 480, 1);
		g.fillRect(0, 421, 480, 1);
		//Vertical Black Lines
		g.fillRect(61, 0,  1, 480); 
		g.fillRect(121, 0, 1, 480);
		g.fillRect(181, 0, 1, 480);
		g.fillRect(241, 0, 1, 480);
		g.fillRect(301, 0, 1, 480);
		g.fillRect(361, 0, 1, 480);
		g.fillRect(421, 0, 1, 480);
	}
	
	boolean canceled; //Boolean flags
	boolean processing;
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		if(customRedrawn == true) { //If the board was redrawn using the "<" or ">" button
			redrawBoard(); //Redraw current board. 
			customRedrawn = false; //Board is now the current "real" board, so set this flag to false.
			statDisplay.resetCurrentPosition(); //Reset current_position to match the current board state. 
			statDisplay.highlight(); //Update highlight so that it matches current_position
			return; 
		}
		
		//Calculate which square the click occurred
		int x = Integer.parseInt(e.getActionCommand()) % 8; 
		int y = ( Integer.parseInt(e.getActionCommand()) - x ) / 8;
		
		checkIfGameOver();
		
		for(int[] move: boardstate.getLegalMoves(current_player)) { //Check if button clicked was a legal move. If legal, make move and update board.
			if(move[0] == y && move[1] == x && !processing) {
				
				//When cancelled is set to false, minimaxThread will update the board after computing 
				//When cancelled is set to true, minimaxThread will NOT update the board after computing 
				canceled = false; 
				processing = true;
				statDisplay.resetHinterLabel();
				statDisplay.appendToPane(getMoveString(x, y), Color.white);
				boardstate = boardstate.makeMove(current_player, move);
				redrawBoard();
				statDisplay.updatePieceLabels(boardstate);
				statDisplay.addToHistory(boardstate);
				current_player = oppositePlayer(current_player);			
				minimaxThread();
				break;
				
			}
		}
		
	}
	
	private void minimaxThread() { 
		SwingWorker<int[], Void> minimax = new SwingWorker<int[], Void>(){

			@Override
			protected int[] doInBackground() throws Exception { 
				if(!canceled) { //If user did not click "New Game", do this:
				return Minimax.findMove(boardstate, statDisplay.difficultySetting, current_player);
				} else return new int[] {-1, -1}; //This is just here because there must be something returned. This code is never going to affect anything.
			}

			@Override
			protected void done() {
				processing = false;
				try {
					if(!canceled) {
						Minimax.resetMinimaxMoveCount();
						int[] output_move = get(); //sets output_move to what doInBackground() eventually returns
						if(output_move.length < 2) return;
						statDisplay.appendToPane(getMoveString(output_move[1], output_move[0]), Color.red);
						boardstate = boardstate.makeMove(current_player, output_move);
						statDisplay.updatePieceLabels(boardstate);
						current_player = oppositePlayer(current_player);
						statDisplay.addToHistory(boardstate);
						statDisplay.resetCurrentPosition();
						customRedrawn = false;
						statDisplay.highlight();
						redrawBoard();
						checkIfGameOver();
					}
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				
				super.done();
			}
		};
		minimax.execute();
		
	}
	
	
	
	
	public void redrawBoard() { //Update the look of the board to match the array representing the board.
		for(int j = 0; j < 8; j++) {
			for(int i = 0; i < 8; i++) {
				if(boardstate.getBoard()[j][i] == empty)  {
					buttons[j][i].setIcon(null);
					this.add(buttons[j][i]);
				}
				if(boardstate.getBoard()[j][i] == white) {
					buttons[j][i].setIcon(new ImageIcon(this.getClass().getResource("/White_Piece.png")));
					this.add(buttons[j][i]);
				}
				if(boardstate.getBoard()[j][i] == black)  {
					buttons[j][i].setIcon(new ImageIcon(this.getClass().getResource("/Black_Piece.png")));
					this.add(buttons[j][i]);
				}
			 
			}
	 	}
	}
	
	boolean customRedrawn = false; //Boolean flag for telling the rest of the program if the user clicked one of the buttons.
	public void redrawBoard(Board board) {
		Board temp_board = board;
		for(int j = 0; j < 8; j++) {
			for(int i = 0; i < 8; i++) {
				if(temp_board.getBoard()[j][i] == empty)  {
					buttons[j][i].setIcon(null);
					this.add(buttons[j][i]);
				}
				if(temp_board.getBoard()[j][i] == white) {
					buttons[j][i].setIcon(new ImageIcon(this.getClass().getResource("/White_Piece.png")));
					this.add(buttons[j][i]);
				}
				if(temp_board.getBoard()[j][i] == black)  {
					buttons[j][i].setIcon(new ImageIcon(this.getClass().getResource("/Black_Piece.png")));
					this.add(buttons[j][i]);
				}
			 
			}
	 	}
	}
	
	private int oppositePlayer(int player) {
		if(player == white) return black;
		else return white;
		
	}
	
	public void newGame() {
		gameOver = false;
		Minimax.resetMinimaxMoveCount();
		customRedrawn = false;
		processing = false;
		canceled = true; //Stop the computer's move from being drawn.
		boardstate = new Board();
		current_player = white;
		redrawBoard();
		statDisplay.updatePieceLabels(boardstate);
	}
	
	//Setter used to pass reference of statDisplay so that an instance of this class can call methods in an instance of statDisplay
	public void setStatDisplay(Stat_Display statDisplay) { 
		this.statDisplay = statDisplay; 
	}
	
	public int total_moves = 1;
	private String getMoveString(int x, int y) {
		x++;
		String[] matches = {"a", "b", "c", "d", "e", "f", "g", "h"};
		if(total_moves >= 10 && total_moves % 4 == 0) {
			return (total_moves++ + ". " + matches[y] + x + "\n");
		} else if  (total_moves >= 10 && total_moves % 4 != 0){
			return (total_moves++ + ". " + matches[y] + x + " ");
		} else if  (total_moves < 10 && total_moves % 4 != 0){
			return (" " + total_moves++ + ". " + matches[y] + x + " ");
		} else {
			return (" " + total_moves++ + ". " + matches[y] + x + "\n");
		}
	}
	public void setCustomFlagState(boolean state) {
		customRedrawn = state;
	}
	public Board getBoard() {
		return boardstate;
	}
	public int getCurrentPlayer() {
		return current_player;
	}
	
	private void checkIfGameOver() {
		if( gameOver != true ) {
			if(boardstate.getLegalMoves(current_player).size() == 0) gameOver = true;
			if(gameOver) {
				int white = 0;
				int black = 0;
				for(int[] x : boardstate.getBoard()) {
					for(int y : x) {
						if(y == 1) white++;
						if(y == 2) black++;
					}
				}
				if(white > black) {
					endScreen.result = 1;
				}
				
				if(black > white) {
					endScreen.result = 2;
				}
				
				if(black == white) {
					endScreen.result = 0;
				}
				
				endScreen.setVisible(true);
			}
		}
	}
	
	
}


				
