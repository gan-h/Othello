package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Stat_Display extends JPanel implements ActionListener{
	private Display display;
	JButton newGame;
	JLabel whiteCount;
	JLabel blackCount;
	Stat_Display(){
		setLayout(null);
		setBackground(Color.black);
		newGame = new JButton("New Game");
		newGame.setBounds(55, 400, 110, 50);
		newGame.addActionListener(this);
		whiteCount = new JLabel();
		blackCount = new JLabel();
		this.add(newGame);
		this.add(whiteCount);
		this.add(blackCount);
		repaint();
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(220, 480);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New Game")) {
			display.newGame();
		}
		
	}
	
	public void setDisplay(Display display) {
		this.display = display;
	}
	
}
