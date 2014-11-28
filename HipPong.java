import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.*;


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
//			finish writing what needs to go in the menus
//			figure how to get info back from panels
//			write the actual game code
//*****************************************************************************
public class HipPong{
    public static void main(String[] args){
		//initilization of the frame with Boarder Layout
		JFrame frame = new JFrame("HipPong");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new BorderLayout());
	
		//game loop test???????
		boolean gLoop;
		//do{
		
            //Color, Background, Dimenisons.
            Menu left = new Menu(Color.BLACK, Color.RED, 100, 100, 50, 450);
            Menu right = new Menu(Color.BLACK, Color.BLUE, 100, 100, 850, 850);
            Menu top = new Menu(Color.BLACK, Color.GREEN, 100, 100, 50, 850 );
            Menu bottom = new Menu(Color.BLACK, Color.BLACK, 100, 100, 450, 850);
            Menu center = new Menu(Color.BLACK, Color.WHITE, 100, 100, 450, 450);

		    //putting menu on frame and showing menu to user(s)
	        frame.add(left, BorderLayout.WEST);
	        frame.add(right, BorderLayout.EAST);
	        frame.add(top, BorderLayout.NORTH);
	        frame.add(bottom, BorderLayout.SOUTH);
	        frame.add(center, BorderLayout.CENTER);
	        frame.setSize(900, 900);
        	frame.setVisible(true);


		    //for testing switch between menu and game (should be switched
		    //	for return of accept in center menu)
		    //JFrame popupcheck = new JFrame("check");
		    //JOptionPane.showConfirmDialog (popupcheck,
			//"assume all info has been entered");

		    //errases the menu
		    //frame.setVisible(false);
            
            /*
            frame.remove(left);
		    frame.remove(right);
		    frame.remove(top);
		    frame.remove(bottom);
		    frame.remove(center);
            */

            //Setup the game according to the choices made in the main menu.
		    //	Declare all the classes with the correct passed in
		    //	parameters.
		    //	
	
	        // ************************************
	        //  Main Game Frame
	        //
	        //  Creates frame and panel for the game.
	        // ************************************
	        //JFrame gameFrame = new JFrame("HipPong");
	        //gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        //gameFrame.setLayout(new BorderLayout());
		    
            //GamePanel gamePanel = new GamePanel();
	        //frame.add(gamePanel);
        	//frame.setVisible(true);

		    //for testing exit
		    //int loopNum = JOptionPane.showConfirmDialog (popupcheck,
			//"assume game has completed\nPlayer X is the winner.\nPlay again?");
		    
            //if (loopNum == 0) 
            //     gLoop = true;
		    //else gLoop = false;

		//}while(gLoop);

        //frame.setVisible(false);
		//System.exit(0);
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
	g.fillOval(400,400,100,100);
    
    
	}
}

//*****************************************************************************
//Class:
//	Menu
//
//	Operation:
//		This Panel will paint the paddle choices of the menu.
//		These choices include movement keys, human, computer, or
//		wall;
//*****************************************************************************
class Menu extends JPanel implements ActionListener{

    // ***************
    // Initialize Look
    // ***************
    protected Color color = null;
    protected Color background = null;
    protected int[] dim = {0, 0, 0, 0};

    // ***************
    // Game Keys
    // ***************
    char leftKey = ' ', rightKey = ' ';
    JTextField leftKeyJT = new JTextField(1);
    JTextField rightKeyJT = new JTextField(1);

    // ************
    // Constructors
    // ************
    public Menu(){
        ; //Do Nothing. Everything is already initialized.
    }

    // ********************************************************
    // Constructor
    //  Params: Color, Background Color, Fill Oval Dimensions.
    //  *******************************************************
    public Menu(Color c, Color bg, int x, int y, int width, int height){
        
        // set Look
        color = c;
        background = bg;
        dim[0] = x;
        dim[1] = y;
        dim[2] = width;
        dim[3] = height;

        //Set key
        add(leftKeyJT);
        add(rightKeyJT);
        leftKeyJT.addActionListener(this);
        rightKeyJT.addActionListener(this);
    }

    // ***************
    // Getters/Setters
    // ***************
    public void setKey(char l, char r){ 
        leftKey = l; 
        rightKey = r; 
    }

    public char getLeftKey(){ return leftKey; }
    public char getRightKey(){ return rightKey; }

    public void actionPerformed(ActionEvent e){
        System.out.println("test.");

        if(e.getSource() == leftKeyJT)
        {
            leftKey = leftKeyJT.getText().charAt(0);    //Gets leftKeyJT value
            System.out.println("Left Key: " + leftKey);
        }
        else if(e.getSource() == rightKeyJT )
        {
            rightKey = rightKeyJT.getText().charAt(0);   //Gets leftKeyJT value
            System.out.println("Right Key: " + rightKey);
        }
        else
            ;
    }

    // *****************
    // Paint Data/Specs
    // *****************
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //Paint
        g.setColor(color);
        g.fillOval(dim[0], dim[1], dim[2], dim[3]);
        setBackground(background);

    }
}   //End of class Menu

