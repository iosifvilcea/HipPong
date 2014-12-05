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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.MultipleGradientPaint.CycleMethod;
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
    private int lastPaddle = 0;
    private int playerScore[] = {0, 0, 0, 0, 0};

    // *********************
    // Computer Attributes
    // *********************
    private int trackDist = 275;
    private int compSpeed = 3;

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
	players = new int[4];
    for(int i = 0; i < 4; i++){
		players[i] = p[i];
	}

	controls = c;
	difficulty = d;
	maxPoints = pl;
	loop = l;

        if(p[1] == 2)
            wallTop = true;
        if(p[3] == 2)
            wallBottom = true;

	//set computer difficulty
	if (difficulty == 0)
	    trackDist = 200;
	else if (difficulty == 1)
  	    trackDist = 275;
	else
	    trackDist = 350;

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
        Graphics2D g2d = (Graphics2D)g;
        
        //Smooth Edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);                    //Set Component Colors
        g2d.fillOval(ballX,ballY,diameter,diameter);  //Create Ball
        g2d.fillRect(p1x, p1y, p1Width, p1Height);    //Create P1 Paddle
        g2d.fillRect(p2x, p2y, p2Width, p2Height);    //Create P2 Paddle 
        
        if(!wallTop)
            g2d.fillRect(p3x, p3y, p3Width, p3Height);    //Create P3 Paddle
        else
            ;   //Don't draw anything.

        if(!wallBottom)
            g2d.fillRect(p4x, p4y, p4Width, p4Height);    //Create p4 Paddle
        else
            ; //Don't draw anything.
        
        
        // *
        // * Draw Scoreboard
        // *
        
        //Draw BlackHole
        Point2D center = new Point2D.Float(350,350);
        Point2D focus = new Point2D.Float(350, 350);
        float radius = 200;
        float[] dist = {0.1f, .7f};
        Color[] colors = {Color.BLACK, new Color(0,0,0,0)};
        RadialGradientPaint p = new RadialGradientPaint(center, radius, focus, dist, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE);

        g2d.setPaint(p);
        g2d.fill(new Ellipse2D.Double(200,200,290,290));

        //Dashed Lines
        g2d.setColor(Color.WHITE);
        final float dash[] = {10.0f};
        final BasicStroke dashed = new BasicStroke(2.0f, BasicStroke.CAP_ROUND,
                                                         BasicStroke.JOIN_MITER,
                                                         10.0f, dash, 0.0f);
        g2d.setStroke(dashed);
        g2d.draw(new Line2D.Double(280,280,420,420));
        g2d.draw(new Line2D.Double(420,270,270,420));


        //Actual Score Font

        g2d.setColor(Color.WHITE);
        
        Font font = new Font("Serif", Font.PLAIN, 40);
        g2d.setFont(font);
       
        g2d.drawString(Integer.toString(playerScore[1]), 398, 360);
        g2d.drawString(Integer.toString(playerScore[2]), 267, 360);
        g2d.drawString(Integer.toString(playerScore[3]), 332, 300);
        g2d.drawString(Integer.toString(playerScore[4]), 332, 420);
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
        */
    }

    // ***********************************
    // Handles KeyEvents
    // ***********************************
    public void keyPressed(KeyEvent e){
        //LEFT PLAYER
        if( e.getKeyText( e.getKeyCode() ).equals( controls[0] ) )
            p1LeftPress = true;
        if( e.getKeyText( e.getKeyCode() ).equals( controls[1] ) )
            p1RightPress = true;

        //RIGHT PLAYER
        if( e.getKeyText( e.getKeyCode() ).equals( controls[4] ) )
            p2LeftPress = true;
        if( e.getKeyText( e.getKeyCode() ).equals( controls[5] ) )
            p2RightPress = true;

        //TOP PLAYER
        if( e.getKeyText( e.getKeyCode() ).equals( controls[2] ) )
            p3LeftPress = true;
        if( e.getKeyText( e.getKeyCode() ).equals( controls[3] ) )
            p3RightPress = true;
       
        //BOTTOM PLAYER
        if( e.getKeyText( e.getKeyCode() ).equals( controls[6] ) )
            p4LeftPress = true;
        if( e.getKeyText( e.getKeyCode() ).equals( controls[7] ) )
            p4RightPress = true;

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
	    if (!paused)
               pause();
	}

    }

    public void keyReleased(KeyEvent e){
        //LEFT PLAYER
        if( e.getKeyText( e.getKeyCode() ).equals( controls[0] ) )
            p1LeftPress = false;
        if( e.getKeyText( e.getKeyCode() ).equals( controls[1] ) )
            p1RightPress = false;

        //RIGHT PLAYER
        if( e.getKeyText( e.getKeyCode() ).equals( controls[4] ) )
            p2LeftPress = false;
        if( e.getKeyText( e.getKeyCode() ).equals( controls[5] ) )
            p2RightPress = false;

        //TOP PLAYER
        if( e.getKeyText( e.getKeyCode() ).equals( controls[2] ) )
            p3LeftPress = false;
        if( e.getKeyText( e.getKeyCode() ).equals( controls[3] ) )
            p3RightPress = false;
       
        //BOTTOM PLAYER
        if( e.getKeyText( e.getKeyCode() ).equals( controls[6] ) )
            p4LeftPress = false;
        if( e.getKeyText( e.getKeyCode() ).equals( controls[7] ) )
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

        if(players[0] == 1)
	    if (ballX < 400){
		if (p1y < ballY)
		    p1y += compSpeed;
		else if (p1y > ballY)
		    p1y -= compSpeed;
	    }

        //Player2/RightSide
        if(p2LeftPress && players[2] == 0)
            if( (p2y - p2Speed) > 0) 
                p2y -= p2Speed;

        if(p2RightPress && players[2] == 0)
            if( (p2y + p2Speed + p2Height) < getWidth() )
                p2y += p2Speed;

        if(players[2] == 1)
	    if (ballX > 400){
		if (p2y < ballY)
		    p2y += compSpeed;
		else if (p2y > ballY)
		    p2y -= compSpeed;
	    }

        //Player3/TOP
        if(p3LeftPress && players[1] == 0)
            if( (p3x - p3Speed) > 0) 
                p3x -= p3Speed;

        if(p3RightPress && players[1] == 0)
            if( (p3x + p3Speed + p3Width) < getHeight() )
                p3x += p3Speed;

        if(players[1] == 1)
	    if (ballY < 400){
		if (p3x > ballX)
		    p3x -= compSpeed;
		else if (p3x < ballX)
		    p3x += compSpeed;
	    }


        //Player4/BOTTOM
        if(p4LeftPress && players[3] == 0)
            if( (p4x - p4Speed) > 0) 
                p4x -= p4Speed;

        if(p4RightPress && players[3] == 0)
            if( (p4x + p4Speed + p4Width) < getHeight())
                p4x += p4Speed;

        if(players[3] == 1)
	    if (ballY > 400){
		if (p4x > ballX)
		    p4x -= compSpeed;
		else if (p4x < ballX)
		    p4x += compSpeed;
	    }


        //Check for boundaries.
        //NOTE:
        // x boundaries are from x to x+width-1
        // y boundaries are from y to y+height-1
        int newBallPosX = ballX + ballDX;
        int newBallPosY = ballY + ballDY;

        //Left Side
        if( (newBallPosX) < (p1x + p1Width)){                //If ball passes Left Paddle Position
            if (newBallPosY > (p1y + p1Height) || (newBallPosY + diameter) < p1y)     //If ball Misses the paddle
            {    
                //Announce Winner, Score Points
                System.out.println("P1 Loses.");            //Game Blouses.
                playerScore[lastPaddle] += 1;
                roundOver();

            }else                                    //Else, were good, it hit.
            {
                ballDX = Math.abs(ballDX);
                lastPaddle = 1;
                rounds++;
            }
        }

        //Right Side
        if( (ballX + ballDX + diameter) > (p2x)){                 //If ball Right Passes Paddle Position
            if ( (newBallPosY) > (p2y + p2Height) || (newBallPosY + diameter) < p2y)
            {
                //Announce Winner, Score Points
                System.out.println("P2 Loses.");            //Game Blouses.
                playerScore[lastPaddle] += 1;
                roundOver();

            }
            else{
                ballDX = -Math.abs(ballDX);
                lastPaddle = 2;
                rounds++;
            }
        }

        //Top
        if(wallTop)
        {   
            if(ballY < diameter/2) 
                ballDY = Math.abs(ballDY);
        }
        else if( ( (newBallPosY) < p3y ) ){
            if( (newBallPosX) > (p3x+p3Width) || (newBallPosX+diameter) < p3x)
            {
                //Announce Winner, Score Points
                System.out.println("P3 Loses.");
                playerScore[lastPaddle] += 1;
                roundOver();
            }
            else{    
                ballDY = Math.abs(ballDY);
                lastPaddle = 3;
                rounds++;
            }
        }
        

        //Bottom
        if(wallBottom)
        {    
            if(newBallPosY > getHeight() - diameter/2)
                ballDY = -Math.abs(ballDY);
        }
        else if( (newBallPosY) > (p4y-p4Height) ){  //If it passes the paddle
            if( (newBallPosX+diameter) < p4x || (newBallPosX) > (p4x+p4Width) )
            {
                System.out.println("P4 Loses.");
                playerScore[lastPaddle] += 1;
                roundOver();
            }
            else{
                ballDY = -Math.abs(ballDY);
                lastPaddle = 4;
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

        //Reset last touched
        lastPaddle = 0;
       
        //Check if GameOver
        for(int i=1; i<playerScore.length; i++)
            if(playerScore[i] >= maxPoints)
                gameOver(playerScore[i]);

	//Randomize Ball direction
	do{
	   ballDX = randX.nextInt(10) - 5;
	   ballDY = randY.nextInt(10) - 5;
	} while (ballDX == 0 || ballDY == 0);
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
            for(int i=0; i<playerScore.length; i++)
                playerScore[i] = 0;

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
			HipPong.reset();
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

	final JButton resume = new JButton("Resume");
	resume.setAlignmentX(Component.CENTER_ALIGNMENT);
	final ActionListener res = new ActionListener(){
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
	};
	resume.addActionListener(res);

	final JButton quit = new JButton("Quit");
	quit.setAlignmentX(Component.CENTER_ALIGNMENT);
	final ActionListener qui = new ActionListener(){
		public void actionPerformed( ActionEvent e){
			System.exit(0);
		}
	};
	quit.addActionListener(qui);

	final JButton mainMenu = new JButton("Main Menu");
	mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
	mainMenu.addActionListener(new ActionListener(){
		public void actionPerformed( ActionEvent e){
			//quit to main menu
			HipPong.reset();
		}
	});

	pauseScreen.add(resume);
	pauseScreen.add(mainMenu);
	pauseScreen.add(quit);
	add(pauseScreen);
	revalidate();
    }
   
}
