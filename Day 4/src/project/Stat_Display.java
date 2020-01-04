package project;

import java.awt.*;

import javax.swing.*;

public class Stat_Display extends JPanel {
	Stat_Display(){
		setLayout(null);
		repaint();
	}
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(220, 480);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.black);
		g.setColor(Color.blue);
		g.fillRect(100, 100, 50, 60);
	}
}
