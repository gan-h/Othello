<b>Othello Game with Minimax Computer Player and Slick GUI</b>

How to run this program:
- Download latest master commit.
- First: Try running the jar file. If that works, then great! You are done here. 
- If the jar file did not work, then:
- - Compile the java files in the src folder
- - Replace the .class files in bin/project with the newly compiled java files 
- - Run: "java project.Drawboard" (be sure to be in the bin folder, and that you are only in the bin folder, not in the bin/projects folder)

<i>This program is JRE 1.7 and above compliant.</i>


Development Log:
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
- Figured out how to compile without using eclipse like a big boy. 
Day 11: 
- Updated image paths so that we can compile the program without the images breaking.







