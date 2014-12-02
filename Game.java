// ********************************************************
// * Game Class
// *    Actual game play is drawn/handled here.
// *
// * Note: 
// *
// * Credits:
// *   Ball boundaries used from
// *    ~/myers/cop3252/notes/examples/gui/Ball.java
// ********************************************************

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.awt.event.KeyListener;

public class Game extends JPanel implements ActionListener, KeyListener{
    
    // *********************
    // Ball Attributes
    // *********************
    //Ball Position.
    private int ballX = 250;
    private int ballY = 250;
    
    //Ball Position Change
    private int ballDX = -2;
    private int ballDY = 5;
    
    //Ball Size
    private int diameter = 20;

    // *********************
    // Player Attributes
    // *********************

    //LEFT
    private int p1x = 25;
    private int p1y = 250;
    private int p1Width = 10;
    private int p1Height = 50;
    private int p1Speed = 5;

    private boolean p1LeftPress = false;
    private boolean p1RightPress = false;

    //RIGHT
    private int p2x = 465;
    private int p2y = 250;
    private int p2Width = 10;
    private int p2Height = 50;
    private int p2Speed = 5;

    private boolean p2LeftPress = false;
    private boolean p2RightPress = false;


    // ********************
    //  Constructor
    // ********************
    public Game(){
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
        
        //BOTTOM PLAYER
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
            if( (p1y + p1Speed + p1Height) < getHeight())
                p1y += p1Speed;

        //Player2/RightSide
        if(p2LeftPress)
            if( (p2y - p2Speed) > 0) 
                p2y -= p1Speed;

        if(p2RightPress)
            if( (p2y + p2Speed + p2Height) < getHeight())
                p2y += p2Speed;


        //Check for boundaries.
        //Left Side
        if(ballX < (p1x + p1Width)){                //If ball passes Left Paddle Position
            if (ballY+ballDY > (p1y + p1Height) || (ballY+ballDY + diameter) < p1y)     //If ball Misses the paddle
            {    
                //Announce Winner, Score Points
                System.out.println("P1 Loses.");            //Game Blouses.
               
                //Reset Paddles
                p1x = 25;
                p2x = 465;

                //Reset Ball Position
                ballX = 250;
                ballY = 250;

            }else                                    //Else, were good, it hit.
                ballDX = Math.abs(ballDX);
        }

        //Right Side
        if(ballX > (p2x + p2Width)){                 //If ball Right Passes Paddle Position
            if (ballY+ballDY > (p2y + p2Height) || (ballY+ballDY + diameter) < p2y)
            {
                //Announce Winner, Score Points
                System.out.println("P2 Loses.");            //Game Blouses.
               
                //Reset Paddles
                p1x = 25;
                p2x = 465;

                //Reset Ball Position
                ballX = 250;
                ballY = 250;

            }else
                ballDX = -Math.abs(ballDX);
        }

        //Top
        if(ballY < diameter/2){
            ballDY = Math.abs(ballDY);
        }

        //Bottom
        if(ballY > getHeight()- diameter/2){
            ballDY = -Math.abs(ballDY);
        }


        //Sets new ball position.
        ballX += ballDX;
        ballY += ballDY;
        
        //Draw it.
        repaint();
    }
}
