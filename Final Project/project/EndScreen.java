package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class EndScreen extends JPanel implements ActionListener {
	protected Display display;
	protected int result;
	private JButton exit;
	private JLabel outcome;
	private JButton analysis;
	
	public EndScreen(Display display) {
		this.display = display;
		
		
		this.setLayout(null);
		this.setBackground(new Color(26, 25, 23));
		
		outcome = new JLabel();
		outcome.setBounds(49, 50, 100, 30);
		
		exit = new JButton();
		exit.setBounds(5,5,18,18);
		exit.setActionCommand("Exit");
		exit.setBackground(Color.white);
		exit.addActionListener(this);
		exit.setContentAreaFilled(false);
		exit.setBorderPainted(false);
		exit.setFocusPainted(false);
		
		analysis = new JButton();
		analysis.setActionCommand("Analysis");
		analysis.setBounds(17, 115, 147, 33);
		analysis.addActionListener(display);
		analysis.setContentAreaFilled(false);
		analysis.setBorderPainted(false);
		analysis.setFocusPainted(false);
		
		this.add(analysis);
		this.add(outcome);
		this.add(exit);
		
		repaint();
	}
	
	
	
	public void setOutcomeText(String text) {
		outcome.setText(text);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Exit")) {
			this.setVisible(false);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(result == 1) {
			try {
				g.drawImage(ImageIO.read(this.getClass().getResource("/WhiteWin.png")), 0, 0, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(result == 2) {
			try {
				g.drawImage(ImageIO.read(this.getClass().getResource("/BlackWin.png")), 0, 0, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(result == 0) {
			try {
				g.drawImage(ImageIO.read(this.getClass().getResource("/Tie.png")), 0, 0, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
