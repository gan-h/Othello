package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import javax.swing.*;

public class Analysis extends JPanel implements ActionListener {
	private Stat_Display statDisplay;
	private Display display;
	private JButton options;
	private final int GraphWidth = 440;
	private final int GraphLength = 440;
	private int XTick;
	private JLabel[] labels;
	private int[] ypoints;
	private JLabel blackInfo;
	private JLabel whiteInfo;
	private boolean useBars;
	
	Analysis(Stat_Display statDisplay, Display display) {
		useBars = false;
		this.setLayout(null);
		this.statDisplay = statDisplay;
		this.display = display;
		
		options = new JButton("Change to Bars");
		options.setActionCommand("Options");
		options.setContentAreaFilled(false);
		options.setBorderPainted(false);
		options.setForeground(Color.red);
		options.setBackground(new Color(26, 25, 23));
		options.addActionListener(this);
		options.setBounds(0, 0, 200, 20);
		this.add(options);
		
		blackInfo = new JLabel("Black");
		blackInfo.setForeground(Color.white);
		blackInfo.setBounds(0, 460, 60, 20);
		this.add(blackInfo);
		
		whiteInfo = new JLabel("White");
		whiteInfo.setForeground(Color.white);
		whiteInfo.setBounds(0, 0, 60, 20);
		this.add(whiteInfo);
		
		
		repaint();
	}
	
	public void beginAnalysis(ArrayList<Board> boardHistory) {
		XTick = 480 / boardHistory.size();
		
		int XLength = ((480 - GraphLength) / 2);
		labels = new JLabel[boardHistory.size()];
		ypoints = new int[boardHistory.size()];
		
		//Setup x-axis ticks
		int pos = 0;
		for(double x = XLength; x < 480 - XLength; x += XTick) {
			if (pos == labels.length) break;
			labels[pos] = new JLabel();
			labels[pos].setBounds((int) x, 236, 1, 8);
			labels[pos].setIcon(new ImageIcon(this.getClass().getResource("/bars.png")));
			this.add(labels[pos]);
			pos++;
		}
		
		//Add points
		minimaxThread(boardHistory);
		
		
	}
	
	private void minimaxThread(ArrayList<Board> boardHistory) { 
		SwingWorker<Void, Void> minimax = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				int totalMoves = 0;
				for(int i = 0; i < boardHistory.size(); i++) {
					totalMoves++;
					if(i % 2 == 0) { //Whites turn
						if(totalMoves > 53) {
							ypoints[i] = (int) ( minimax(boardHistory.get(i), 7, 1) * 0.35596);
						} else {
							ypoints[i] = (int) ( minimax(boardHistory.get(i), 6, 1) * 0.35596);
						}
					} else { //Blacks turn
						if(totalMoves > 53) {
							ypoints[i] = (int) ( minimax(boardHistory.get(i), 7, 2) * 0.35596);
						} else {
							ypoints[i] = (int) ( minimax(boardHistory.get(i), 6, 2) * 0.35596);
						}
					}
					repaint();
				}
				return null;
			}

			@Override
			protected void done() {
				
				
				
				
			}
		};
		minimax.execute();
		
	}
	
	private static int minimax(Board board, int depth, int player) {
		if(depth == 0 || board.getLegalMoves(player).size() == 0) {
			if(depth == 0 ) {
				return Evaluation.analysis(board, player);
			} else {
				return Evaluation.outOfMovesEval(board, player);
			}
		}
		
		if(player == 1) { //player == white
			
			int maxEval = Integer.MIN_VALUE;
			for(int[] move : board.getLegalMoves(player)) {
				int eval = minimax(board.makeMove(player, move), depth - 1, 2) ;
				maxEval = greater(maxEval, eval);
			}
			return maxEval;
		}
		else { //player == black
			
			
			int minEval = Integer.MAX_VALUE;
			for(int[] move : board.getLegalMoves(player)) {
				int eval = minimax(board.makeMove(player, move), depth - 1, 1);
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
	
	
	public void paintComponent(Graphics g) {  //Draw the Othello board
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, 480, 480);
		g.setColor(Color.DARK_GRAY);
		g.fillRect((480 - GraphLength ) / 2, (480 - GraphWidth ) / 2, GraphLength, GraphWidth);
		g.setColor(Color.white);
		g.fillRect((480 - GraphLength ) / 2, (480) / 2, GraphLength, 1);
		
		int XLength = ((480 - GraphLength) / 2);
		int pos = 0;
		int parity = 1;
		for(double x = XLength; x < 480 - XLength; x += XTick) {
			if(pos == ypoints.length) break;
			if(ypoints[pos] <= 0) {
				if(parity % 2 == 1) { 
					g.setColor(Color.green);
				} else {
					g.setColor(Color.white);
				}
				
				if(useBars) g.fillRect((int) x, 240, 5, ypoints[pos] * -1);
				else g.fillRect((int) x, 240 + ypoints[pos] * -1, 5, 5);
			} 
			if(ypoints[pos] > 0) { 
				if(parity % 2 == 1) {
					g.setColor(Color.green);
				} else {
					g.setColor(Color.white);
				}
				if(useBars) g.fillRect((int) x, 240 - ypoints[pos], 5, ypoints[pos]);
				else g.fillRect((int) x, (ypoints[pos] - 240 ) *  -1, 5, 5);
			} 
			pos++; 
			parity++;
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Options")) {
			useBars = !useBars;
			if(useBars) {
				options.setText("Change to Squares");
				options.setForeground(Color.green);
				repaint();
			} else {
				options.setText("Change to Bars");
				options.setForeground(Color.red);
				repaint();
			}
		}
	}
	
}
