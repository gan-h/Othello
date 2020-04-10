<b>What is this?</b>
- A program you can run if you would like to play the board game, Othello. This program features a GUI that I wrote using Swing, a java graphical interface library, and a minimax based computer player.

Feel free to contribute or clone this project!

<b> The Game Interface: </b>
<img width="526" alt="githubOthello" src="https://user-images.githubusercontent.com/34823594/79027745-5cf4aa00-7b5b-11ea-96c3-c933699d1a67.PNG">

<b> Analyzing the game after you're done with it </b>
<img width="525" alt="githubImage2" src="https://user-images.githubusercontent.com/34823594/79027815-b361e880-7b5b-11ea-8d83-3814e702f7ba.PNG">

<b>How to run the game:</b>

- Download latest master commit
- Go into the "Final Project" folder
- Compile all the .java files in the "project" folder. (javac project/*.java)
- Run "java project.Drawboard" (Be sure to be in the Final Project folder, not the "project" folder)

<b>OR download Othello.jar and run it. (Requires a 1.7+ version of java to be installed on your computer) </b>

<i>This program is JRE 1.7+ compatible</i>

<b>--------------------------------------------------------------------------------------------------------------------------</b>

How to play:
- By default, you are white, and the first player to move each game.
  - Click a position on the board to make a move. 
- The computer (black by default) will respond with a move of its own. The computer is based off on the minimax algorithm. If you feel like the computer is too hard to play against, you can lower its difficulty in the settings tab. (The settings tab is the little settings gear. Click it to toggle the settings menu)

- When you finish your game against the computer (game ends when either player lacks a valid move to play), a popup will appear announcing the winner of the game. That popup will have an "Analyze?" button. Click the button if you would like to start a computer analysis of your game.
  - If you decide to click analysis, you will be greeted with a graph with lots of tick marks. These tick marks represent each move you           made during the game. Bars/Squares on the graph will represent computer evaluations. There will be a leading red square as the computer analyzes the game. This square represents the current position the computer is looking at. Later, after the computer is finished evaluating, and the red square has stopped moving, you can use the arrow buttons (< >) to move the red square/bar through the graph. The evaluation score and move # of the red square/graph will be shown on the top of the analysis screen.
  - When you click "Analyze?", an eye will appear on the top left of the program GUI. Click it to toggle between seeing the game, and seeing the analysis board.
  
  -After you are finished with one game, you can choose to start another by pressing "New Game".
<b>--------------------------------------------------------------------------------------------------------------------------</b>


Development Log:
Winter Break:

Day 1: 
- finished legal move detection system
- finished move update system.

Day 1 Fustrations:
- I typed "break outerloop" instead of "continue outerloop" and didn't notice the mistake for a while
- I set the starting othello board wrong, and so the legal move detection method gave me puzzling print outs
- I confused the x-axis with the y-axis and wrote all of my code around that confusion. Had to rewrite everything.

Day 2:
- Wrote a computer player that employs the minimax algorithm to find the best move, given an othello board and the current player's turn
- Update -> finished computer player now that bug below has been resolved.

Day 2 Fustrations:
- There's some kind of problem with the minimax algorithm where the algorithm is changing the same Board object's board-representation.
- Not sure how to resolve this...my code seems correct. This is driving me crazy.
- Update: figured my problem out -- arrays are passed by reference. I forgot about that. 

Day 3:
- Learning swing to make the GUI. 
- Created 3 classes for the GUI, created board background using drawRectangle() but the board has no functionality yet. 

Day 4:
- Board GUI now has full functionality

Day 4 Fustrations:
- Appearently I made a mistake in my legal move detection system -- I wrote continue instead of break. Took me a while to figure that out.

Day 5:
- Added "New Game" Button 
- Incorporated multithreading to prevent gui from freezing as the minimax method computes the best move.

Day 6:
- Added textbox that displays moves made by the computer and player
- Fixed bug where multiple minimax threads would run at the same time, resulting in moves being made when they are not supposed to.

Day 7:
- Added arrow buttons that allow you to step through past moves
- Weird behavior where if I place a line of code one place the program behaves not as intended, but if I put it in a place that you would think would
produce the same weird behavior, the program runs as intended

day 8:
I read a website about designing good UI. I improved the program UI. 
- Red and white moves in the textbox now line up which is satisfying imho
- Buttons are now black, and turn bluish white when clicked
- Buttons are now centered

day 9:
- moves in textbox are now highlighted when they are stepped into.

day 9 fustrations:
- I had trouble with getting the highlight to align with the moves in the textbox properly.
- I had trouble because I found that jtp.getString() -- which returns a string of the text in the jtp, returns a string that contains "\n" but when you try to modify the jtp's document, "\n"s are recognized as "\" and "n" -- two characters instead of just one. So that confused me. 

Day 10: 
- added hint button

Day 11: 
- Figured out how to compile without using eclipse.
- Updated image paths so that we can compile the program without the images breaking.

Ever since school started:

1/4/20 :
- Made pixel art for settings wheel
- Added a settings menu
- Added a up and down button in the settings menu for adjusting Minimax depth

1/15/20:
-Added option to play human
-Added analysis functionality.






