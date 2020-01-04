package project;

import java.awt.Dimension;

import javax.swing.*;


public class Drawboard {
	private static final int WIDTH = 650 + 7;
	private static final int HEIGHT = 560 + 7;
	
	public static void main(String args[]) {
		JFrame frame = new JFrame("Othello");
		
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE); 
		frame.add(new Display());
		frame.setVisible(true);
	}
}
