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

public class Game extends JPanel implements ActionListener, KeyListener{
    
    // *********************
    // Game Options
    // *********************
    private int[] players;
    private String[] controls;
    private int difficulty;
    private boolean loop;

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
    private int ballDX = -1;
    private int ballDY = 3;
    private int rounds = 0;

    //Ball Size
    private int diameter = 20;

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

    private boolean p1LeftPress = false;
    private boolean p1RightPress = false;

    //RIGHT
    private int p2x = 675;
    private int p2y = 325;
    private int p2Width = 10;
    private int p2Height = 50;
    private int p2Speed = 5;

    private boolean p2LeftPress = false;
    private boolean p2RightPress = false;

    //TOP
    private int p3x = 325;
    private int p3y = 25;
    private int p3Width = 50;
    private int p3Height = 10;
    private int p3Speed = 5;

    private boolean p3LeftPress = false;
    private boolean p3RightPress = false;

    //BOTTOM
    private int p4x = 325;
    private int p4y = 675;
    private int p4Width = 50;
    private int p4Height = 10;
    private int p4Speed = 5;

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
	loop = l;

        System.out.println("P3:" + p[2] + "P4:" + p[3]);
        if(p[2] == 2)
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
        if(rounds > 5 && rounds < 10)
        {
            ballDX = -2;
            ballDY = 4;
        }else if(rounds > 10 && rounds < 15)
        {
            ballDX = -3;
            ballDY = 5;
        }else if(rounds > 20)
        {
            ballDX = -5;
            ballDY = 7;
        }
        */

        run();

        System.out.println("pLeft:" + p2x + " y:" + p2y);
        //System.out.println("pTop:" + p3x + " y:" + p3y);
        System.out.println("w:" + getWidth() + " h:" + getHeight());
        //System.out.println("ballx:" + ballX + " y:" + ballY);
    }

    // ***********************************
    // Handles KeyEvents
    // ***********************************
    public void keyPressed(KeyEvent e){

        //LEFT PLAYER
        if( e.getKeyCode() == KeyEvent.VK_Q )
            p1LeftPress = true;
        
        if(e.getKeyCode() == KeyEvent.VK_A )
            p1RightPress = true;

        //RIGHT PLAYER
        if( e.getKeyCode() == KeyEvent.VK_M )
            p2LeftPress = true;
        if( e.getKeyCode() == KeyEvent.VK_COMMA )
            p2RightPress = true;

        //TOP PLAYER
        if( e.getKeyCode() == KeyEvent.VK_LEFT )
            p3LeftPress = true;
        if( e.getKeyCode() == KeyEvent.VK_RIGHT )
            p3RightPress = true;
       
        //BOTTOM PLAYER
        if( e.getKeyCode() == KeyEvent.VK_Z )
            p4LeftPress = true;
        if( e.getKeyCode() == KeyEvent.VK_X )
            p4RightPress = true;


    }

    public void keyReleased(KeyEvent e){
        //LEFT PLAYER
        if( e.getKeyCode() == KeyEvent.VK_Q ) 
            p1LeftPress = false;
        if( e.getKeyCode() == KeyEvent.VK_A ) 
            p1RightPress = false;

        //RIGHT PLAYER
        if( e.getKeyCode() == KeyEvent.VK_M )
            p2LeftPress = false;
        if( e.getKeyCode() == KeyEvent.VK_COMMA )
            p2RightPress = false;

        //TOP PLAYER
        if( e.getKeyCode() == KeyEvent.VK_LEFT )
            p3LeftPress = false;
        if( e.getKeyCode() == KeyEvent.VK_RIGHT )
            p3RightPress = false;

        //BOTTOM PLAYER
        if( e.getKeyCode() == KeyEvent.VK_Z )
            p4LeftPress = false;
        if( e.getKeyCode() == KeyEvent.VK_X )
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
        if(p1LeftPress)
            if( (p1y - p1Speed) > 0) 
                p1y -= p1Speed;

        if(p1RightPress)
            if( (p1y + p1Speed + p1Height) < getWidth() )
                p1y += p1Speed;

        //Player2/RightSide
        if(p2LeftPress)
            if( (p2y - p2Speed) > 0) 
                p2y -= p2Speed;

        if(p2RightPress)
            if( (p2y + p2Speed + p2Height) < getWidth() )
                p2y += p2Speed;

        //Player3/TOP
        if(p3LeftPress)
            if( (p3x - p3Speed) > 0) 
                p3x -= p3Speed;

        if(p3RightPress)
            if( (p3x + p3Speed + p3Width) < getHeight() )
                p3x += p3Speed;

        //Player4/BOTTOM
        if(p4LeftPress)
            if( (p4x - p3Speed) > 0) 
                p4x -= p3Speed;

        if(p4RightPress)
            if( (p4x + p4Speed + p4Width) < getHeight())
                p4x += p4Speed;



        //Check for boundaries.
        //Left Side
        if( (ballX+ballDX) < (p1x + p1Width)){                //If ball passes Left Paddle Position
            if (ballY+ballDY > (p1y + p1Height) 
                            || 
               (ballY+ballDY + diameter) < p1y)     //If ball Misses the paddle
            {    
                //Announce Winner, Score Points
                System.out.println("P1 Loses.");            //Game Blouses.

                gameOver();

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
            
                gameOver();

            }
            else{
                ballDX = -Math.abs(ballDX);
                rounds++;
            }
        }

        //Top
        if(ballY < diameter/2 ){
                ballDY = Math.abs(ballDY);
        }

        //Bottom
        if(ballY > getHeight() - diameter/2){
            ballDY = -Math.abs(ballDY);
        }


        //Sets new ball position.
        ballX += ballDX;
        ballY += ballDY;
        
        //Draw it.
        repaint();
    }

    // ***********************************
    // * GameOver
    // *  Resets paddles, ball, counter w/
    // *   start values/positions.
    // ***********************************
    public void gameOver(){
    
        //Reset ball
        ballX = 325;
        ballY = 325;
        
        //Reset P1 Position
        p1x = 25;
        p1y = 325;
 
        //Reset P2 Position
        p2x = 675;
        p2y = 325;

        //Reset P3 Position
        p3x = 325;
        p3y = 25;

        //Reset P4 Position
        p4x = 325;
        p4y = 675;

        //Reset Speed Up Counter
        rounds = 0;
    }
}
