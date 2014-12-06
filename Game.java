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
    private boolean paused = false;
    private boolean hole = false;

    private int maxPoints = 5;
    private int lastPaddle = 0;
    private int playerScore[] = {0, 0, 0, 0, 0};

    // *********************
    // Computer Attributes
    // *********************
    private int trackDist = 275;
    private int compSpeed = 4;

    // *********************
    // Wall Attributes
    // *********************
    private boolean wallTop = false;
    private boolean wallBottom = false;

    // *********************
    // BlackHole Attributes
    // *********************
    private int roundBoost = 0;

    // *********************
    // Ball Attributes
    // *********************
    //Ball Position.
    private int ballX = 250;
    private int ballY = 250;
    
    //Ball Position Change
    private int ballDX = -3;
    private int ballDY = 5;
    private int ballMinSpeed = 4;
    private int rounds = 0;

    //Ball Size
    private int diameter = 20;

    //Ball Direction Randomizer
    private Random randX = new Random (System.currentTimeMillis());
    private Random randY = new Random (System.currentTimeMillis() + 100);

    // *********************
    // Player Attributes
    // *********************

    //General
    private int padelSpeed = 5;
    
    //Note: Real width: 698, height: 675

    //LEFT
    private int p1x = 25;
    private int p1y = 325;
    private int p1Width = 10;
    private int p1Height = 50;
    private int p1Score = 0;

    private boolean p1LeftPress = false;
    private boolean p1RightPress = false;

    //RIGHT
    private int p2x = 700 - 25 - 10;
    private int p2y = 325;
    private int p2Width = 10;
    private int p2Height = 50;
    private int p2Score = 0;

    private boolean p2LeftPress = false;
    private boolean p2RightPress = false;

    //TOP
    private int p3x = 325;
    private int p3y = 25;
    private int p3Width = 50;
    private int p3Height = 10;
    private int p3Score = 0;

    private boolean p3LeftPress = false;
    private boolean p3RightPress = false;

    //BOTTOM
    private int p4x = 325;
    private int p4y = 700 - 25 - 10;
    private int p4Width = 50;
    private int p4Height = 10;
    private int p4Score = 0;

    private boolean p4LeftPress = false;
    private boolean p4RightPress = false;


    // ********************
    //  Constructor
    // ********************
    public Game(int[] p, String[] c, int d, int pl){
        //setting in passed in values
	players = new int[4];
    for(int i = 0; i < 4; i++){
		players[i] = p[i];
	}

	controls = c;
	difficulty = d;
	maxPoints = pl;

        if(p[1] == 2)
            wallTop = true;
        if(p[3] == 2)
            wallBottom = true;

	//set computer difficulty
	if (difficulty == 0){
	    trackDist = 200;
	    ballMinSpeed = 3;
	}
	else if (difficulty == 1){
  	    trackDist = 275;
	    compSpeed = 5;
	    ballMinSpeed = 4;
	}
	else {
	    trackDist = 350;
	    compSpeed = 6;
	    ballMinSpeed = 5;
	}

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
        
        if(!wallBottom)
            g2d.fillRect(p4x, p4y, p4Width, p4Height);    //Create p4 Paddle
        
        
        // *
        // * Draw Scoreboard
        // *
        
        //Draw BlackHole
        Point2D center = new Point2D.Float(350,350);
        Point2D focus = new Point2D.Float(350, 350);
        float radius = 200;
        float[] dist = {0.1f, .7f};
        Color[] colors = {Color.BLACK, new Color(0,0,0,0)};
        RadialGradientPaint p = new RadialGradientPaint(center, radius, focus, dist, colors, 
                                                        MultipleGradientPaint.CycleMethod.NO_CYCLE);
        g2d.setPaint(p);
        g2d.fill(new Ellipse2D.Double(200,200,290,290));

        //BlackHole Center
        center = new Point2D.Float(350,350);
        focus = new Point2D.Float(350, 350);
        radius = 100;
        RadialGradientPaint p2 = new RadialGradientPaint(center, radius, focus, dist, colors, 
                                                        MultipleGradientPaint.CycleMethod.NO_CYCLE);
        g2d.setPaint(p2);       
        g2d.fill(new Ellipse2D.Double(295,295,100,100));
        
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
      
        g2d.drawString(Integer.toString(playerScore[1]), 267, 360);
        g2d.drawString(Integer.toString(playerScore[2]), 398, 360);
        
        if(!wallTop)
            g2d.drawString(Integer.toString(playerScore[3]), 332, 300);
        
        if(!wallBottom)
            g2d.drawString(Integer.toString(playerScore[4]), 332, 420);
    }

    // ***********************************
    //  Starts the Game when constructor
    //   is called.
    // ***********************************
    public void actionPerformed(ActionEvent e){
        run();
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
               end(0);
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
            if( (p1y - padelSpeed) > 0) 
                p1y -= padelSpeed;

        if(p1RightPress && players[0] == 0)
            if( (p1y + padelSpeed + p1Height) < getWidth() )
                p1y += padelSpeed;

        if(players[0] == 1)
	    if (ballX < trackDist){
		if (ballY < (p1y + (p1Width / 4)))
		    p1y -= compSpeed;
		else if (ballY > (p1y + (3 * p1Width / 4)))
		    p1y += compSpeed;
	    }

        //Player2/RightSide
        if(p2LeftPress && players[2] == 0)
            if( (p2y - padelSpeed) > 0) 
                p2y -= padelSpeed;

        if(p2RightPress && players[2] == 0)
            if( (p2y + padelSpeed + p2Height) < getWidth() )
                p2y += padelSpeed;

        if(players[2] == 1)
	    if (ballX > trackDist){
		if (ballY < (p2y + (p2Width / 4)))
		    p2y -= compSpeed;
		else if (ballY > (p2y + (3 * p2Width / 4)))
		    p2y += compSpeed;
	    }

        //Player3/TOP
        if(p3LeftPress && players[1] == 0)
            if( (p3x - padelSpeed) > 0) 
                p3x -= padelSpeed;

        if(p3RightPress && players[1] == 0)
            if( (p3x + padelSpeed + p3Width) < getHeight() )
                p3x += padelSpeed;

        if(players[1] == 1)
	    if (ballY < trackDist){
		if (ballX < (p3x + (p3Width / 4)))
		    p3x -= compSpeed;
		else if (ballX > (p3x + (3 * p3Width / 4)))
		    p3x += compSpeed;
	    }


        //Player4/BOTTOM
        if(p4LeftPress && players[3] == 0)
            if( (p4x - padelSpeed) > 0) 
                p4x -= padelSpeed;

        if(p4RightPress && players[3] == 0)
            if( (p4x + padelSpeed + p4Width) < getHeight())
                p4x += padelSpeed;

        if(players[3] == 1)
	    if (ballY > trackDist){
		if (ballX < (p4x + (p4Width / 4)))
		    p4x -= compSpeed;
		else if (ballX > (p4x + (3 * p4Width / 4)))
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
                playerScore[lastPaddle] += 1;
                roundOver();

            }else                                    //Else, were good, it hit.
            {
                ballDX = Math.abs(ballDX);
                lastPaddle = 1;
                rounds++;
		ballSpeedUp();
            }
        }

        //Right Side
        if( (ballX + ballDX + diameter) > (p2x)){                 //If ball Right Passes Paddle Position
            if ( (newBallPosY) > (p2y + p2Height) || (newBallPosY + diameter) < p2y)
            {
                //Announce Winner, Score Points
                playerScore[lastPaddle] += 1;
                roundOver();

            }
            else{
                ballDX = -Math.abs(ballDX);
                lastPaddle = 2;
                rounds++;
		ballSpeedUp();
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
                playerScore[lastPaddle] += 1;
                roundOver();
            }
            else{    
                ballDY = Math.abs(ballDY);
                lastPaddle = 3;
                rounds++;
		ballSpeedUp();
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
                playerScore[lastPaddle] += 1;
                roundOver();
            }
            else{
                ballDY = -Math.abs(ballDY);
                lastPaddle = 4;
                rounds++;
		ballSpeedUp();
            }
        }

	
        //Black Hole
        if(!hole && difficulty != 0){
            if( (newBallPosX > 305 && newBallPosX < 345) &&
                (newBallPosY > 305 && newBallPosY < 345) &&
                (lastPaddle != 0) )
            {//Randomize Ball direction
		do{
	   		ballDX = randX.nextInt(40) - 20;
	   		ballDY = randY.nextInt(40) - 20;
		} while (ballSpeedCheck());
            }
        }
        else
            hole = true;

        //Sets new ball position.
        ballX += ballDX;
        ballY += ballDY;

        //Draw it.
        repaint();
    }

    // ***********************************
    // * ballSpeedCheck
    // *  makes sure ball's deltas are
    // *   not too slow
    // ***********************************
    private boolean ballSpeedCheck(){
	double bspeed = Math.sqrt(Math.pow(ballDX, 2) + Math.pow(ballDY, 2));
	if (bspeed <= (ballMinSpeed + roundBoost) ||
		bspeed >= (ballMinSpeed + roundBoost + 1) ||
		ballDX == 0 || ballDY == 0) {
		return true;
	}
		return false;
    }

    // ***********************************
    // * ballSpeedUp
    // *  increments the ball speed given
    // *   right conditions
    // ***********************************
    private void ballSpeedUp(){
	if ((difficulty == 1 && rounds != 0 && rounds%5 == 0) ||
	    (difficulty == 2 && rounds != 0 && rounds%3 == 0)){
		roundBoost++;
		if(ballDX > 0)
			ballDX++;
		else
			ballDX--;
		if(ballDY > 0)
			ballDY++;
		else
			ballDY--;
	}
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
	roundBoost = 0;

        //Reset last touched
        lastPaddle = 0;

	//Randomize Ball direction
	do{
	   ballDX = randX.nextInt(10) - 5;
	   ballDY = randY.nextInt(10) - 5;
	} while (ballSpeedCheck());
       
        //Check if GameOver
        for(int i=1; i<playerScore.length; i++)
            if(playerScore[i] >= maxPoints)
                end(i);
    }

    // ***********************************
    // * end
    // *  pauses the game, declares winner
    // *   0 not passed in, makes popup
    // *   with options of what to do next
    // ***********************************
    private void end(int winner){
	paused = true;
	
	//section that pauses everything
	final int[] holder = {padelSpeed,ballDX,ballDY};
	padelSpeed = 0;
	ballDX = 0;
	ballDY = 0;

	final JFrame endframe = new JFrame("Game Over");
	endframe.setLocationRelativeTo(this);
	endframe.setSize(170,200);
	endframe.setLayout(new BoxLayout(endframe.getContentPane(), BoxLayout.Y_AXIS));
    	endframe.getContentPane().setBackground(Color.BLACK);
	endframe.addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent evt){
			padelSpeed = holder[0];
			ballDX = holder[1];
			ballDY = holder[2];
			paused = false;
			endframe.dispose();
		}
	});

	String message;
	if (winner == 0)
		message = "No Winner!";
	else
		message = "Player " + winner + " wins!!";
	JLabel whoWins = new JLabel(message);
	whoWins.setAlignmentX(Component.CENTER_ALIGNMENT);
    	whoWins.setForeground(Color.WHITE);

	JButton restart = new JButton("Restart");
	
    	restart.setOpaque(false);
    	restart.setContentAreaFilled(false);
    	restart.setBorderPainted(false);
    	restart.setForeground(Color.WHITE);
    
   	restart.setAlignmentX(Component.CENTER_ALIGNMENT);
	restart.addActionListener(new ActionListener(){
		public void actionPerformed( ActionEvent e){
			//reset score, speeds, call roundOver
            		for(int i=0; i<playerScore.length; i++)
                		playerScore[i] = 0;

           		padelSpeed = holder[0];
			do{
				ballDX = randX.nextInt(10) - 5;
				ballDY = randY.nextInt(10) - 5;
			}while (ballSpeedCheck());
			paused = false;
			endframe.dispose();
		}
	});

	final JButton resume = new JButton("Resume");

    	resume.setOpaque(false);
    	resume.setContentAreaFilled(false);
    	resume.setBorderPainted(false);
    	resume.setForeground(Color.WHITE);

	resume.setAlignmentX(Component.CENTER_ALIGNMENT);
	resume.addActionListener(new ActionListener(){
		public void actionPerformed( ActionEvent e){
			//do something to resume
			padelSpeed = holder[0];
			ballDX = holder[1];
			ballDY = holder[2];
			paused = false;
			endframe.dispose();
		}
	});

	JButton mainMenu = new JButton("Main Menu");
	
    	mainMenu.setOpaque(false);
    	mainMenu.setContentAreaFilled(false);
    	mainMenu.setBorderPainted(false);
    	mainMenu.setForeground(Color.WHITE);
    
    	mainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
	mainMenu.addActionListener(new ActionListener(){
		public void actionPerformed( ActionEvent e){
			//quit to main menu
			endframe.dispose();
			HipPong.reset();
		}
	});

	JButton quit = new JButton("Quit");
	
    	quit.setOpaque(false);
    	quit.setContentAreaFilled(false);
    	quit.setBorderPainted(false);
    	quit.setForeground(Color.WHITE);

    	quit.setAlignmentX(Component.CENTER_ALIGNMENT);
	quit.addActionListener(new ActionListener(){
		public void actionPerformed( ActionEvent e){
			//terminate the game
			System.exit(0);
		}
	});
	if (winner != 0){
		endframe.add(whoWins);
		endframe.add(Box.createRigidArea(new Dimension(0,5)));
		endframe.add(restart);
	} else
		endframe.add(resume);
	endframe.add(Box.createRigidArea(new Dimension(0,5)));
	endframe.add(mainMenu);
	endframe.add(Box.createRigidArea(new Dimension(0,5)));
	endframe.add(quit);
	//endframe.pack();
    	endframe.setLocationRelativeTo(null);
	endframe.setVisible(true);
    }
   
}
