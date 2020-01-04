package project;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.*;
public class Display extends JPanel implements ActionListener {
	private JButton[][] buttons;
	private Board boardstate;
	private int current_player;
	private static int empty = 0;
	private static int white = 1;
	private static int black = 2;
	public Display() {                 //Constructor overlays a button over every square in the grid.
		current_player = white;
		setLayout(null);
		setBounds(0,0,487,487);
		boardstate = new Board();
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
				 buttons[y][x].setActionCommand((y * 8 + x) + ""); //Unique ID tag for each square for event handling
				 buttons[y][x].addActionListener(this); //When button is clicked, pass on event to an object of this class.
				 buttons[y][x].setOpaque(false);
				 buttons[y][x].setContentAreaFilled(false);
				 buttons[y][x].setBorderPainted(false);
				 
				
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
	@Override
	public void actionPerformed(ActionEvent e) {
		int x = Integer.parseInt(e.getActionCommand()) % 8;
		int y = ( Integer.parseInt(e.getActionCommand()) - x ) / 8;
		System.out.println("Y: " + y + "    X: " + x);
		for(int[] z : boardstate.getLegalMoves(current_player)) {
			System.out.println(Arrays.toString(z));
		}
		for(int[] move: boardstate.getLegalMoves(current_player)) {
			if(move[0] == y && move[1] == x) {
				boardstate = boardstate.makeMove(current_player, move);
				redrawBoard();
				current_player = oppositePlayer(current_player);
				break;
				
			}
		}
	}
	
	private void redrawBoard() {
		for(int j = 0; j < 8; j++) {
			for(int i = 0; i < 8; i++) {
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
	
	private int oppositePlayer(int player) {
		if(player == white) return black;
		else return white;
		
	}
}


				
