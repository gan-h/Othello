package project;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

public class Drawboard extends JFrame {
	private static final int white = 1;
	private static final int black = 2;
	private static final int WIDTH = 700;
	private static final int HEIGHT = 480;
	private Board boardstate;
	private JButton[][] buttons;
	private JFrame frame;
	Drawboard() {
		frame = new JFrame("Othello");
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setUndecorated(true);
		Stat_Display statDisplay = new Stat_Display();
		Display display = new Display();
		display.setStatDisplay(statDisplay);
		statDisplay.setDisplay(display);
		
		frame.add(display, BorderLayout.WEST);
		frame.add(statDisplay, BorderLayout.EAST);
		
		boardstate = new Board();
		frame.setVisible(true);
	}
	
	
	public static void main(String args[]) {
		SwingUtilities.invokeLater(new Runnable () {
			public void run() {
				new Drawboard();
			}
			
		});
		
	}
}
