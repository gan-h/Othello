package project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import javax.swing.text.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class Stat_Display extends JPanel implements ActionListener{
	private Display display; //This holds a reference to the active instance of "display" so that we can influence it
	private ArrayList<Board> boardHistory; //ArrayList reference for board state storage
	private int current_position; //This records which move the user is currently observing.
	JButton newGame;
	JButton back;
	JButton forwards;
	JButton hint;
	JLabel hinter;
	JLabel whiteCount;
	JLabel blackCount;
	JTextPane jtp;
	JScrollPane jsp;
	
	Stat_Display(){
		setLayout(null);
		setBackground(new Color(39,37,34));
	
		current_position = 0;
		boardHistory = new ArrayList<Board>(60);  //ArrayList to store all board_states that have been generated
		boardHistory.add(new Board()); //Start the board history with the default board.
	    
		newGame = new JButton("New Game");  //New game button configuration
		newGame.setBounds(55, 400, 110, 50);
		newGame.addActionListener(this);
		newGame.setBackground(new Color(26, 25, 23));
		newGame.setContentAreaFilled(true);
		newGame.setBorderPainted(false);
		newGame.setForeground(Color.white);
		newGame.setFocusPainted(false);
		
		
		hint = new JButton("?");
		hint.addActionListener(this);
		hint.setBounds(0, 175, 45, 25);
		hint.setContentAreaFilled(true);
		hint.setBorderPainted(false);
		hint.setForeground(Color.white);
		hint.setFocusPainted(false);
		hint.setBackground(new Color(26, 25, 23));
		hint.setToolTipText("Need a hint?");
		
		hinter = new JLabel();
		hinter.setBounds(55, 175, 200, 25);
		hinter.setForeground(Color.white);
		
		back = new JButton("<");  //Back button configuration
		back.addActionListener(this);
		back.setBounds(62, 360, 45, 25);
		back.setContentAreaFilled(true);
		back.setBorderPainted(false);
		back.setForeground(Color.white);
		back.setFocusPainted(false);
		back.setBackground(new Color(26, 25, 23));
		
		forwards = new JButton(">"); //Forwards button configuration
		forwards.addActionListener(this);
		forwards.setBounds(113, 360, 45, 25);
		forwards.setContentAreaFilled(true);
		forwards.setBorderPainted(false);
		forwards.setForeground(Color.white);
		forwards.setFocusPainted(false);
		forwards.setBackground(new Color(26, 25, 23));
		
		jtp = new JTextPane(); 				//Text pane for adding text to
		jtp.setEditable(false);				//User is able to highlight, but not edit text in pane
		jtp.setBackground(Color.DARK_GRAY);  
		jtp.setCaretColor(Color.white);
		
		jsp = new JScrollPane(jtp, 							//Attach a jscrollpane to the textpane
		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setViewportBorder(null);  //Get rid of the bad looking borders
		jsp.setBorder(null);
		jsp.getVerticalScrollBar().setPreferredSize(new Dimension(7, Integer.MAX_VALUE));
		jsp.setBounds(0, 200, 220, 160);   //This is the position of the textpane
		jsp.getVerticalScrollBar().setBackground(Color.DARK_GRAY);
		
		jsp.getVerticalScrollBar().setUI(new BasicScrollBarUI()    //Overriding default settings for the scroll bar look
	    {   
			@Override
		    protected void configureScrollBarColors() {
		        this.thumbColor = Color.lightGray;
		        
		    }
	        @Override
	        protected JButton createDecreaseButton(int orientation) { //Decrement button is given no size so that it no longer exists
	            return createZeroButton();
	        }

	        @Override    
	        protected JButton createIncreaseButton(int orientation) { //Same is done for this button
	            return createZeroButton();
	        }

	        private JButton createZeroButton() {
	            JButton jbutton = new JButton();
	            jbutton.setPreferredSize(new Dimension(0, 0)); //This is how the button settings are set to zero
	            jbutton.setMinimumSize(new Dimension(0, 0));
	            jbutton.setMaximumSize(new Dimension(0, 0));
	            return jbutton;
	        }
	    });
		
		whiteCount = new JLabel("White Pieces: 2"); //Start off with labels
		blackCount = new JLabel("Black Pieces: 2"); //Default board always starts with 2 white and 2 black pieces
		whiteCount.setBounds(55, 30, 110, 50); //Positions of text
		whiteCount.setForeground(Color.white);
		blackCount.setBounds(55, 50, 110, 50);
		blackCount.setForeground(Color.white);
		
		this.add(jsp);					//Add all components to the panel so they can be viewed
		this.add(newGame);
		this.add(whiteCount);
		this.add(blackCount);
		this.add(forwards);
		this.add(back);
		this.add(hint);
		this.add(hinter);
		
	}
	@Override
	public Dimension getPreferredSize() { //Specifies the size of the panel. This is important for the JFrame.
		return new Dimension(220, 480);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) { //Action event handler
		
		if(e.getActionCommand().equals("New Game")) { //If new game button is clicked, then do this:
			display.newGame();
			jtp.setText("");
			display.total_moves = 1;
			boardHistory.clear();
			boardHistory.add(new Board());
			current_position = 0;
		}
		if(e.getActionCommand().equals("<")) { //If the back button was clicked, then do this:
			if(current_position - 1 >= 0) current_position--;
			if(current_position == boardHistory.size() - 1) {
				display.setCustomFlagState(false);
				display.redrawBoard();
				highlight();
			} else {
				display.setCustomFlagState(true);
				display.redrawBoard(boardHistory.get(current_position));
				highlight();
			}
			
			
		} 
		
		if(e.getActionCommand().equals(">")) { //If the forwards button was clicked, then do this:
			if(current_position + 1 <= boardHistory.size() - 1) current_position++;
			if(current_position == boardHistory.size() - 1) {
				display.setCustomFlagState(false);
				display.redrawBoard();
				highlight();
			} else {
				display.setCustomFlagState(true);
				display.redrawBoard(boardHistory.get(current_position));
				highlight();
			}
			
		}
		
		boolean hintJustClicked = false;
		if(e.getActionCommand().equals("?")) {
			if(!hintJustClicked) {
				hintJustClicked = true;
				hinter.setText("Generating a hint for you!");
				generateHint();
				hintJustClicked = false;
			}
			
			
		}
	}
	
	public void setDisplay(Display display) { //Setter for setting reference to display
		this.display = display;
	}
	
	public void appendToPane(String msg, Color c) { //Append to the pane text, given a color and string. Appends to style document.
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
	
	public void highlight() { //Method for highlighting text in the JTextPane. Highlighted text is based on current_position
		if (current_position == 0) return; //Don't run if current_position is zero.
		int len = jtp.getDocument().getLength();
		StyledDocument doc = jtp.getStyledDocument();
		SimpleAttributeSet sas = new SimpleAttributeSet();
		StyleConstants.setBackground(sas, Color.DARK_GRAY);
		doc.setCharacterAttributes(0, len, sas, false);
	    StyleConstants.setBackground(sas, new Color(100,100,0));
	    int start;
	    int length;
	    start = jtp.getText().indexOf(current_position + ".");
	   
	    
	    start -= ( current_position / 4 );
	    if(current_position % 4 == 0) start += 1;
	    
	    
	    if (current_position >= 10) {
	    	length = 6;
	    } else  { //     (current_position <= 9)
	    	length = 5;
	    }
	   
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
	
	public void resetHinterLabel() {
		hinter.setText("");
	}
	
	private void generateHint() { 
		SwingWorker<int[], Void> minimax = new SwingWorker<int[], Void>(){
			@Override
			protected int[] doInBackground() throws Exception { 
				
				return Minimax.findMove(display.getBoard(), 6, display.getCurrentPlayer());
			}
			@Override
			protected void done() {
				try {
						Minimax.resetMinimaxMoveCount();
						int[] move = get(); //sets output_move to what doInBackground() eventually returns
						if(move.length < 2) {
							hinter.setText("Uh oh. No possible moves!");
							return;
						}
						String[] matches = {"a", "b", "c", "d", "e", "f", "g", "h"};
						hinter.setText(matches[move[0]] + (move[1] + 1));
						
					
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
				
				super.done();
			}
		};
		minimax.execute();
	}
}
