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
    private int p1x = 25;
    private int p1y = 250;
    private int p1Width = 10;
    private int p1Height = 50;
    private int p1Speed = 5;

    private boolean p1LeftPress = false;
    private boolean p1RightPress = false;
   
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
        if( "Q".equals( e.getKeyText( e.getKeyCode() ) ) ){
            p1LeftPress = true;
        }
        if( "A".equals( e.getKeyText( e.getKeyCode() ) ) )
            p1RightPress = true;
    }
    public void keyReleased(KeyEvent e){
	    p1LeftPress = false;
	    p1RightPress = false;
    }

    public void keyTyped(KeyEvent e){}

    // ***********************************
    //  Run()
    //    Handles movement and interactions
    //    between objects (paddles & ball)
    // ***********************************
    public void run(){

        if(p1LeftPress){
            System.out.print("Left Pressed.\n");
            if(p1y - p1Speed > 0) 
                p1y -= p1Speed;
        }

        if(p1RightPress){
            System.out.print("Right Pressed.\n"); 
            if(p1y + p1Speed + p1Height < getHeight())
                p1y += p1Speed;
        }


        //Check for boundaries.
        //Left Side
        if(ballX < (p1x + p1Width)){                //If ball Passes Paddle Position
            if (ballY+ballDY > (p1y + p1Height) || 
               (ballY+ballDY + diameter) < p1y)     //If ball Misses the paddle
                System.out.println("Miss.");            //Game Blouses.
            else                                    //Else, were good, it hit.
                ballDX = Math.abs(ballDX);
        }

        //Right Side
        if(ballX > getWidth() - diameter/2){
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
