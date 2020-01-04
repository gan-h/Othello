package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.text.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class Stat_Display extends JPanel implements ActionListener{
	private Display display;
	private ArrayList<Board> boardHistory;
	private int current_position;
	JButton newGame;
	JButton back;
	JButton forwards;
	JLabel whiteCount;
	JLabel blackCount;
	JTextPane jtp;
	JScrollPane jsp;
	Stat_Display(){
		setLayout(null);
		setBackground(new Color(39,37,34));
	
		current_position = 0;
		boardHistory = new ArrayList<Board>(30);
		boardHistory.add(new Board());
	    
		newGame = new JButton("New Game");
		newGame.setBounds(55, 400, 110, 50);
		newGame.addActionListener(this);
		
		back = new JButton("<");
		back.addActionListener(this);
		back.setBounds(62, 360, 45, 25);
		back.setContentAreaFilled(true);
		back.setBorderPainted(false);
		back.setForeground(Color.white);
		back.setFocusPainted(false);
		back.setBackground(new Color(26, 25, 23));
		
		forwards = new JButton(">");
		forwards.addActionListener(this);
		forwards.setBounds(113, 360, 45, 25);
		forwards.setContentAreaFilled(true);
		forwards.setBorderPainted(false);
		forwards.setForeground(Color.white);
		forwards.setFocusPainted(false);
		forwards.setBackground(new Color(26, 25, 23));
		
		jtp = new JTextPane();
		jtp.setEditable(true);
		jtp.setBackground(Color.DARK_GRAY);
		jtp.setCaretColor(Color.white);
		
		jsp = new JScrollPane(jtp, 
		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setViewportBorder(null);
		jsp.setBorder(null);
		jsp.getVerticalScrollBar().setPreferredSize(new Dimension(7, Integer.MAX_VALUE));
		jsp.setBounds(0, 200, 220, 160);
		jsp.getVerticalScrollBar().setBackground(Color.DARK_GRAY);
		
		jsp.getVerticalScrollBar().setUI(new BasicScrollBarUI()
	    {   
			@Override
		    protected void configureScrollBarColors() {
		        this.thumbColor = Color.lightGray;
		        
		    }
	        @Override
	        protected JButton createDecreaseButton(int orientation) {
	            return createZeroButton();
	        }

	        @Override    
	        protected JButton createIncreaseButton(int orientation) {
	            return createZeroButton();
	        }

	        private JButton createZeroButton() {
	            JButton jbutton = new JButton();
	            jbutton.setPreferredSize(new Dimension(0, 0));
	            jbutton.setMinimumSize(new Dimension(0, 0));
	            jbutton.setMaximumSize(new Dimension(0, 0));
	            return jbutton;
	        }
	    });
		
		whiteCount = new JLabel("White Pieces: 2");
		blackCount = new JLabel("White Pieces: 2");
		whiteCount.setBounds(55, 30, 110, 50);
		whiteCount.setForeground(Color.white);
		blackCount.setBounds(55, 50, 110, 50);
		blackCount.setForeground(Color.white);
		
		this.add(jsp);
		this.add(newGame);
		this.add(whiteCount);
		this.add(blackCount);
		this.add(forwards);
		this.add(back);
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
			jtp.setText("");
			display.total_moves = 1;
			boardHistory.clear();
			boardHistory.add(new Board());
			current_position = 0;
		}
		if(e.getActionCommand().equals("<")) {
			if(current_position - 1 >= 0) current_position--;
			display.redrawBoard(boardHistory.get(current_position));
			highlight();
			
			
		} 
		
		if(e.getActionCommand().equals(">")) {
			if(current_position + 1 <= boardHistory.size() - 1) current_position++;
			if(current_position == boardHistory.size() - 1) {
				display.redrawBoard();
				highlight();
			} else {
				display.redrawBoard(boardHistory.get(current_position));
				highlight();
			}
			
		} 
	}
	
	public void setDisplay(Display display) {
		this.display = display;
	}
	
	public void appendToPane(String msg, Color c) {
		StyledDocument doc = jtp.getStyledDocument();
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        
        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_CENTER);

        int len = jtp.getDocument().getLength();
        try {
			doc.insertString(len, msg, aset);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
    }
	
	public void highlight() {
		if (current_position == 0) return; //Dont run if current_position is zero.
		int len = jtp.getDocument().getLength();
		StyledDocument doc = jtp.getStyledDocument();
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setBackground(sas, Color.DARK_GRAY);
		doc.setCharacterAttributes(0, len, sas, false);
	    StyleConstants.setBackground(sas, new Color(100,100,0));
	    int start;
	    int length;
	    start = jtp.getText().indexOf(current_position + ".");
	    System.out.println(jtp.getText());
	    
	    start -= ( current_position / 4 );
	    if(current_position % 4 == 0) start += 1;
	    
	    
	    if (current_position >= 10) {
	    	length = 6;
	    } else  { //     (current_position <= 9)
	    	length = 5;
	    }
	    System.out.println("Start: " + start);
	    doc.setCharacterAttributes(start, length, sas, false);
	    
	}
	
	
	
	
	public void updatePieceLabels(Board board) {
		int white = 0;
		int black = 0;
		for(int[] y : board.getBoard()) {
			for(int x : y) {
				if(x == 1) white++;
				if(x == 2) black++; 
			}
			
		}
		whiteCount.setText("White Pieces: " + white);
		blackCount.setText("Black Pieces: " + black);
	}
	
	public void addToHistory(Board board) {
		boardHistory.add(board);
		current_position++;
		highlight();
		
	}
	
	public void resetCurrentPosition() {
		current_position = boardHistory.size() - 1;
	}
	
	
}
