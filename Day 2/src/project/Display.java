package project;
import java.awt.*;

import javax.swing.*;
public class Display extends JPanel {
	public Display() {
		setLayout(null);
		repaint();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, 567, 567);
		
		
	}
}
