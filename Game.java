// ********************************************************
// * Game Class
// *    Actual game play is drawn/handled here.
// *
// * Note: 
// *
// * Credits:
// *   Ball boundaries inspired from
// *    ~/myers/cop3252/notes/examples/gui/Ball.java
// ********************************************************

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.event.KeyListener;
import java.util.Random;

public class Game extends JPanel implements ActionListener, KeyListener{
    
    // *********************
    // Game Options
    // *********************
    private int[] players;
    private String[] controls;
    private int difficulty;
    private boolean loop;
    private boolean paused = false;
    private int maxPoints;

    // *********************
    // Wall Attributes
    // *********************
    private boolean wallTop = false;
    private boolean wallBottom = false;

    // *********************
    // Ball Attributes
    // *********************
    //Ball Position.
    private int ballX = 250;
    private int ballY = 250;
    
    //Ball Position Change
    private int ballDX = -3;
    private int ballDY = 5;
    private int rounds = 0;

    //Ball Size
    private int diameter = 20;

    //Ball Direction Randomizer
    private Random randX = new Random (System.currentTimeMillis());
    private Random randY = new Random (System.currentTimeMillis() + 100);

    // *********************
    // Player Attributes
    // *********************
    
    //Note: Real width: 698, height: 675

    //LEFT
    private int p1x = 25;
    private int p1y = 325;
    private int p1Width = 10;
    private int p1Height = 50;
    private int p1Speed = 5;
    private int p1Score = 0;

    private boolean p1LeftPress = false;
    private boolean p1RightPress = false;

    //RIGHT
    private int p2x = 700 - 25 - 10;
    private int p2y = 325;
    private int p2Width = 10;
    private int p2Height = 50;
    private int p2Speed = 5;
    private int p2Score = 0;

    private boolean p2LeftPress = false;
    private boolean p2RightPress = false;

    //TOP
    private int p3x = 325;
    private int p3y = 25;
    private int p3Width = 50;
    private int p3Height = 10;
    private int p3Speed = 5;
    private int p3Score = 0;

    private boolean p3LeftPress = false;
    private boolean p3RightPress = false;

    //BOTTOM
    private int p4x = 325;
    private int p4y = 700 - 25 - 10;
    private int p4Width = 50;
    private int p4Height = 10;
    private int p4Speed = 5;
    private int p4Score = 0;

    private boolean p4LeftPress = false;
    private boolean p4RightPress = false;


    // ********************
    //  Constructor
    // ********************
    public Game(int[] p, String[] c, int d, int pl, boolean l){
        
        //setting in passed in values
        players = p;
	controls = c;
	difficulty = d;
	maxPoints = pl;
	loop = l;

        if(p[1] == 2)
            wallTop = true;
        if(p[3] == 2)
            wallBottom = true;

        setBackground(Color.BLACK);

        //Check Key Presses
        setFocusable(true);     //Allows key component to receive focus.
        addKeyListener(this);   //Allows panel to access key events.

        // 60fps
        Timer refresh = new Timer(1000/60, this);
        refresh.start();
    }

    // ************************************
    //  Paint Game Screen
    // ************************************
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.WHITE);                    //Set Component Colors
        g.fillOval(ballX,ballY,diameter,diameter);  //Create Ball
        g.fillRect(p1x, p1y, p1Width, p1Height);    //Create P1 Paddle
        g.fillRect(p2x, p2y, p2Width, p2Height);    //Create P2 Paddle 
        
        if(!wallTop)
            g.fillRect(p3x, p3y, p3Width, p3Height);    //Create P3 Paddle
        else
            ;   //Don't draw anything.

        if(!wallBottom)
            g.fillRect(p4x, p4y, p4Width, p4Height);    //Create p4 Paddle
        else
            ; //Don't draw anything.
        
    }

    // ***********************************
    //  Starts the Game when constructor
    //   is called.
    // ***********************************
    public void actionPerformed(ActionEvent e){
        
        //Speed Up Game
        /*
        if(rounds > 2 && rounds < 4)
        {
            ballDX = -3;
            ballDY = 5;
        }
        else if(rounds > 4 && rounds < 6)
        {
            ballDX = -4;
            ballDY = 6;
        }
        else if(rounds > 6)
        {
            ballDX = -5;
            ballDY = 7;
        }
        */

        run();
/*
        System.out.println("pLeft:" + p1x + " y:" + p1y);
        System.out.println("pRight:" + p2x + " y:" + p2y);
        System.out.println("pTop:" + p3x + " y:" + p3y);
        System.out.println("pBottom:" + p4x + " y:" + p4y);
        System.out.println("width:" + getWidth() + " height:" + getHeight());
        System.out.println("round:"+rounds+" Bdx:"+ballDX+" Bdy:"+ballDY);
*/	}

    // ***********************************
    // Handles KeyEvents
    // ***********************************
    public void keyPressed(KeyEvent e){
        //LEFT PLAYER
        if( e.getKeyText( e.getKeyCode()).equals(controls[0])){
            p1LeftPress = true;
		System.out.println("move");
	}
        if( e.getKeyText( e.getKeyCode()).equals(controls[1]))
            p1RightPress = true;

        //RIGHT PLAYER
        if( e.getKeyText( e.getKeyCode()).equals(controls[4]))
            p2LeftPress = true;
        if( e.getKeyText( e.getKeyCode()).equals(controls[5]))
            p2RightPress = true;

        //TOP PLAYER
        if( e.getKeyText( e.getKeyCode()).equals(controls[2]))
            p3LeftPress = true;
        if( e.getKeyText( e.getKeyCode()).equals(controls[3]))
            p3RightPress = true;
       
        //BOTTOM PLAYER
        if( e.getKeyText( e.getKeyCode()).equals(controls[6]))
            p4LeftPress = true;
        if( e.getKeyText( e.getKeyCode()).equals(controls[7]))
            p4RightPress = true;

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
	    if (!paused)
               pause();
	}

    }

    public void keyReleased(KeyEvent e){
        //LEFT PLAYER
        if( e.getKeyText( e.getKeyCode()).equals(controls[0]))
            p1LeftPress = false;
        if( e.getKeyText( e.getKeyCode()).equals(controls[1]))
            p1RightPress = false;

        //RIGHT PLAYER
        if( e.getKeyText( e.getKeyCode()).equals(controls[4]))
            p2LeftPress = false;
        if( e.getKeyText( e.getKeyCode()).equals(controls[5]))
            p2RightPress = false;

        //TOP PLAYER
        if( e.getKeyText( e.getKeyCode()).equals(controls[2]))
            p3LeftPress = false;
        if( e.getKeyText( e.getKeyCode()).equals(controls[3]))
            p3RightPress = false;

        //BOTTOM PLAYER
        if( e.getKeyText( e.getKeyCode()).equals(controls[6]))
            p4LeftPress = false;
        if( e.getKeyText( e.getKeyCode()).equals(controls[7]))
            p4RightPress = false;


    }

    public void keyTyped(KeyEvent e){ /* Not needed yet. */ }

    // ***********************************
    //  Run()
    //    Handles movement and interactions
    //    between objects (paddles & ball)
    // ***********************************
    public void run(){

        // Player1/LeftSide
        if(p1LeftPress && players[0] == 0)
            if( (p1y - p1Speed) > 0) 
                p1y -= p1Speed;

        if(p1RightPress && players[0] == 0)
            if( (p1y + p1Speed + p1Height) < getWidth() )
                p1y += p1Speed;

        //Player2/RightSide
        if(p2LeftPress && players[0] == 0)
            if( (p2y - p2Speed) > 0) 
                p2y -= p2Speed;

        if(p2RightPress && players[0] == 0)
            if( (p2y + p2Speed + p2Height) < getWidth() )
                p2y += p2Speed;

        //Player3/TOP
        if(p3LeftPress && players[0] == 0)
            if( (p3x - p3Speed) > 0) 
                p3x -= p3Speed;

        if(p3RightPress && players[0] == 0)
            if( (p3x + p3Speed + p3Width) < getHeight() )
                p3x += p3Speed;

        //Player4/BOTTOM
        if(p4LeftPress && players[0] == 0)
            if( (p4x - p3Speed) > 0) 
                p4x -= p3Speed;

        if(p4RightPress && players[0] == 0)
            if( (p4x + p4Speed + p4Width) < getHeight())
                p4x += p4Speed;

        if(players[3] == 1)
            System.out.println("computer");


        //Check for boundaries.
        //NOTE:
        // x boundaries are from x to x+width-1
        // y boundaries are from y to y+height-1

        //Left Side
        if( (ballX+ballDX) < (p1x + p1Width)){                //If ball passes Left Paddle Position
            if (ballY+ballDY > (p1y + p1Height) 
                            || 
               (ballY+ballDY + diameter) < p1y)     //If ball Misses the paddle
            {    
                //Announce Winner, Score Points
                System.out.println("P1 Loses.");            //Game Blouses.
                roundOver();

            }else                                    //Else, were good, it hit.
            {
                ballDX = Math.abs(ballDX);
                rounds++;
            }
        }

        //Right Side
        if( (ballX + ballDX + diameter) > (p2x)){                 //If ball Right Passes Paddle Position
            if ( (ballY+ballDY) > (p2y + p2Height) 
                            ||
               (ballY+ballDY + diameter) < p2y)
            {
                //Announce Winner, Score Points
                System.out.println("P2 Loses.");            //Game Blouses.
                roundOver();

            }
            else{
                ballDX = -Math.abs(ballDX);
                rounds++;
            }
        }

        //Top
        if(wallTop)
        {   
            if(ballY < diameter/2) 
                ballDY = Math.abs(ballDY);
        }
        else if( ( (ballY+ballDY) < p3y ) ){
            if( (ballX+ballDX) > (p3x+p3Width)
                        ||
                (ballX+ballDX+diameter) < p3x)
            {
                //Announce Winner, Score Points
                System.out.println("P3 Loses.");
                roundOver();
            }
            else{    
                ballDY = Math.abs(ballDY);
                rounds++;
            }
        }
        

        //Bottom
        if(wallBottom)
        {    
            if(ballY > getHeight() - diameter/2)
                ballDY = -Math.abs(ballDY);
        }
        else if(ballY+ballDY > p4y){
            if( (ballX+ballDX >(p4x+p3Width))
                    ||
                (ballX+ballDX+diameter) < p4x)
            {
                System.out.println("P4 Loses.");
                roundOver();
            }
            else{
                ballDY = -Math.abs(ballDY);
                rounds++;
            }
        }


        //Sets new ball position.
        ballX += ballDX;
        ballY += ballDY;
        
        //Draw it.
        repaint();
    }

    // ***********************************
    // * roundOver
    // *  Resets paddles, ball, counter w/
    // *   start values/positions.
    // ***********************************
    public void roundOver(){
    
        //Reset ball
        ballX = 325;
        ballY = 325;
        
        //Reset P1 Position
        p1x = 25;
        p1y = 325;
 
        //Reset P2 Position
        p2x = 700 - 25 - 10;
        p2y = 325;

        //Reset P3 Position
        p3x = 325;
        p3y = 25;

        //Reset P4 Position
        p4x = 325;
        p4y = 700 - 25 - 10;

        //Reset Speed Up Counter
        rounds = 0;

	//Randomize Ball direction
	do{
	   ballDX = randX.nextInt(10) - 5;
	   ballDY = randY.nextInt(10) - 5;
	} while (ballDX == 0 || ballDY == 0);
//gameOver(0);
    }

    // ***********************************
    // * gameOver
    // *  Stops the game, brings up popup
    // *   asking to continue or end
    // ***********************************
    private void gameOver(int winner){
	//0 = no winner; 1-4 correspond to different players
	loop = true;
	paused = true;
	
	final JPanel end = new JPanel ();
	end.setLayout(new BoxLayout(end, BoxLayout.Y_AXIS));
	end.setSize(200,200);
	end.setBackground(new Color(255,255,255,255));

	//section that pauses everything
	final int[] holder = {p1Speed,p2Speed,p3Speed,p4Speed,ballDX,ballDY};
	p1Speed = 0;
	p2Speed = 0;
	p3Speed = 0;
	p4Speed = 0;
	ballDX = 0;
	ballDY = 0;

	String message;
	if (winner == 0)
		message = "No Winner!";
	else
		message = "Player " + winner + " wins!!";
	JLabel whoWins = new JLabel(message);
	whoWins.setAlignmentX(Component.CENTER_ALIGNMENT);

	JButton restart = new JButton("Restart");
	restart.setAlignmentX(Component.CENTER_ALIGNMENT);
	restart.addActionListener(new ActionListener(){
		public void actionPerformed( ActionEvent e){
			//reset score, speeds, call roundOver
    			p1Score = 0;
    			p2Score = 0;
    			p3Score = 0;
    			p4Score = 0;
			p1Speed = holder[0];
			p2Speed = holder[1];
			p3Speed = holder[2];
			p4Speed = holder[3];
			ballDX = randX.nextInt(10) - 5;
			ballDY = randY.nextInt(10) - 5;
			remove(end);
		}
	});

	JButton mainMenu = new JButton("Main Menu");
	mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
	mainMenu.addActionListener(new ActionListener(){
		public void actionPerformed( ActionEvent e){
			//quit to main menu
		}
	});

	JButton quit = new JButton("Quit");
	quit.setAlignmentX(Component.CENTER_ALIGNMENT);
	quit.addActionListener(new ActionListener(){
		public void actionPerformed( ActionEvent e){
			System.exit(0);
		}
	});

	end.add(whoWins);
	end.add(restart);
	end.add(mainMenu);
	end.add(quit);
	add(end);
	end.revalidate();
	end.setLocation(250,250);
    }

    // ***********************************
    // * Pause
    // *  Resets paddles, ball, counter w/
    // *   start values/positions.
    // ***********************************
    public void pause(){
	paused = true;

	final JPanel pauseScreen = new JPanel ();
	pauseScreen.setLayout(new BoxLayout(pauseScreen, BoxLayout.Y_AXIS));
	pauseScreen.setLocation(250,250);
	pauseScreen.setSize(200,200);
	pauseScreen.setBackground(new Color(0,0,0,0));

	//section that pauses everything
	final int[] holder = {p1Speed,p2Speed,p3Speed,p4Speed,ballDX,ballDY};
	p1Speed = 0;
	p2Speed = 0;
	p3Speed = 0;
	p4Speed = 0;
	ballDX = 0;
	ballDY = 0;

	JButton resume = new JButton("Resume");
	resume.setAlignmentX(Component.CENTER_ALIGNMENT);
	resume.addActionListener(new ActionListener(){
		public void actionPerformed( ActionEvent e){
			//do something to resume
			p1Speed = holder[0];
			p2Speed = holder[1];
			p3Speed = holder[2];
			p4Speed = holder[3];
			ballDX = holder[4];
			ballDY = holder[5];	
			remove(pauseScreen);
			paused = false;
		}
	});

	JButton mainMenu = new JButton("Main Menu");
	mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
	mainMenu.addActionListener(new ActionListener(){
		public void actionPerformed( ActionEvent e){
			//quit to main menu
			gameOver(0);
			remove(pauseScreen);
		}
	});

	JButton quit = new JButton("Quit");
	quit.setAlignmentX(Component.CENTER_ALIGNMENT);
	quit.addActionListener(new ActionListener(){
		public void actionPerformed( ActionEvent e){
			System.exit(0);
		}
	});

	pauseScreen.add(resume);
	pauseScreen.add(mainMenu);
	pauseScreen.add(quit);
	add(pauseScreen);
	revalidate();
    }
   
}
