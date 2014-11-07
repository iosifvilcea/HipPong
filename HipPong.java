import java.awt.*;
import javax.swing.*;


//*****************************************************************************
//Class:
//	HipPong
//
//	Operations:
//		This class is the main class. It uses the Player, Computer,
//		Ball, and BlackHole classes to run the game. It's main
//		is function painting the field, ball, blackhole, computers
//		and players constantly. It also takes in input from the
//		and translates it the the movement of the paddles. Lastely,
//		it runs all the updates for the movement of all the objects.
//
//		TO DO:
//
//
//*****************************************************************************
public class HipPong{
	public static void main(String[] args){
		//Draw main menu where which players are human players,
		//	computer players or walls are choosen as drawn
		//	on the top of the included image in this folder titled
		//	plans.jpg. Should also have the ability to select
		//	keyboard inputs for each player (any keyboard inputs
		//	entered for computers or walls will be ignored when
		//	setting everything up).

		//Setup the game according to the choices made in the main menu.
		//	Declare all the classes with the correct passed in
		//	parameters.
		//	
	
        // ************************************
        //  Main Game Frame
        //
        //  Creates frame and panel for the game.
        // ************************************
        JFrame gameFrame = new JFrame("HipPong");
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(new BorderLayout());

        GamePanel gamePanel = new GamePanel();
        gameFrame.add(gamePanel, BorderLayout.CENTER);
        gameFrame.setSize(500, 500);
        gameFrame.setVisible(true);
    
    }
}

//*****************************************************************************
//  GamePanel
//      Will hold game graphics.
//*****************************************************************************

class GamePanel extends JPanel{
    public GamePanel(){
        
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        //Testing to see if it works.
        g.setColor(Color.BLACK);
        g.fillOval(250,250,20,20);
    
    
    }
} 
