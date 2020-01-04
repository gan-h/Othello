package project;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;

import javax.swing.*;
public class Display extends JPanel implements ActionListener {
	private JButton[][] buttons;
	private Stat_Display statDisplay;
	private Board boardstate;
	private int current_player;
	private static int empty = 0;
	private static int white = 1;
	private static int black = 2;
	public Display() {        //Constructor overlays a button over every square in the grid.
		boardstate = new Board();
		current_player = white;
		setLayout(null);
		setBounds(0,0,487,487);
		buttons = new JButton[8][8];
		for(int y = 0; y < 8; y++) {
			for(int x = 0; x < 8; x ++) {
				 if(boardstate.getBoard()[y][x] == empty) {
						buttons[y][x] = new JButton(); 
						buttons[y][x].setBounds(x * 60, y * 60 + 0, 60, 60);
						this.add(buttons[y][x]);
					}
				 else if(boardstate.getBoard()[y][x] == white) {
					buttons[y][x] = new JButton(new ImageIcon("resources\\White_Piece.png"));
					buttons[y][x].setBounds(x * 60 , y * 60 + 0, 60, 60);
					this.add(buttons[y][x]);
				}
				 else  {
					buttons[y][x] = new JButton(new ImageIcon("resources\\Black_Piece.png")); 
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
		}
		repaint();
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(480, 480);
	}
	
	public void paintComponent(Graphics g) { 
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
	
	boolean canceled;
	boolean processing;
	@Override
	public void actionPerformed(ActionEvent e) {
		if(customRedrawn == true) { //If the board was redrawn using the "<" or ">" button, then do this instead
			redrawBoard(); //Redraw current board. 
			customRedrawn = false; //Board is now the current "real" board, so no need to repeat this operation.
			statDisplay.resetCurrentPosition();
			System.out.println("Just reset");
			return; 
		}
		
		int x = Integer.parseInt(e.getActionCommand()) % 8;
		int y = ( Integer.parseInt(e.getActionCommand()) - x ) / 8;
		System.out.println("Y: " + y + "    X: " + x);
		System.out.println("Current Turn:" + current_player);
		for(int[] move: boardstate.getLegalMoves(current_player)) { //Check if button clicked was a legal move. If legal, make move and update board.
			if(move[0] == y && move[1] == x && !processing) {
				System.out.println("hello");
				//When cancelled is set to false, minimaxThread will update the board after computing 
				//When cancelled is set to true, minimaxThread will NOT update the board after computing 
				canceled = false; 
				processing = true;
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
				if(!canceled) {
				return Minimax.findMove(boardstate, 7, current_player);
				} else return new int[] {0,0};
			}

			@Override
			protected void done() {
				processing = false;
				try {
					if(!canceled) {
						int[] output_move = get(); //sets output_move to what doInBackground() eventually returns
						statDisplay.appendToPane(getMoveString(output_move[1], output_move[0]), Color.red);
						boardstate = boardstate.makeMove(current_player, output_move);
						statDisplay.updatePieceLabels(boardstate);
						current_player = oppositePlayer(current_player);
						statDisplay.addToHistory(boardstate);
						statDisplay.resetCurrentPosition();
						customRedrawn = false;
						redrawBoard();
					}
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				
				super.done();
			}
		};
		minimax.execute();
		
	}
	
	
	
	
	public void redrawBoard() {
		for(int j = 0; j < 8; j++) {
			for(int i = 0; i < 8; i++) {
				if(boardstate.getBoard()[j][i] == empty)  {
					buttons[j][i].setIcon(null);
					this.add(buttons[j][i]);
				}
				if(boardstate.getBoard()[j][i] == white) {
					buttons[j][i].setIcon(new ImageIcon("resources\\White_Piece.png"));
					this.add(buttons[j][i]);
				}
				if(boardstate.getBoard()[j][i] == black)  {
					buttons[j][i].setIcon(new ImageIcon("resources\\Black_Piece.png"));
					this.add(buttons[j][i]);
				}
			 
			}
	 	}
	}
	
	boolean customRedrawn = false;
	public void redrawBoard(Board board) {
		Board temp_board = board;
		customRedrawn = true;
		for(int j = 0; j < 8; j++) {
			for(int i = 0; i < 8; i++) {
				if(temp_board.getBoard()[j][i] == empty)  {
					buttons[j][i].setIcon(null);
					this.add(buttons[j][i]);
				}
				if(temp_board.getBoard()[j][i] == white) {
					buttons[j][i].setIcon(new ImageIcon("resources\\White_Piece.png"));
					this.add(buttons[j][i]);
				}
				if(temp_board.getBoard()[j][i] == black)  {
					buttons[j][i].setIcon(new ImageIcon("resources\\Black_Piece.png"));
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
		String[] matches = {"a", "b", "c", "d", "e", "f", "g", "h"};
		if(total_moves >= 10 && total_moves % 4 == 0) {
			return (total_moves++ + ". " + matches[x] + y + "\n");
		} else if  (total_moves >= 10 && total_moves % 4 != 0){
			return (total_moves++ + ". " + matches[x] + y + " ");
		} else if  (total_moves < 10 && total_moves % 4 != 0){
			return (" " + total_moves++ + ". " + matches[x] + y + " ");
		} else {
			return (" " + total_moves++ + ". " + matches[x] + y + "\n");
		}
	}
	
}


				
