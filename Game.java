import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements ActionListener{
    
    private int ballX = 250;
    private int ballY = 250;
    private int ballDX = -1;
    private int ballDY = 3;
    private int diameter = 20;
    
    public Game(){
        setBackground(Color.BLACK);

        // 60fps
        Timer refresh = new Timer(1000/60, this);
        refresh.start();
    }

    //Paint Ball
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillOval(ballX,ballY,diameter,diameter);
    }

    public void actionPerformed(ActionEvent e){
        run();
    }

    public void run(){
        //int nextBallA = ballX + ballDX;
        //int nextBallB = ballX + ballDX + diameter;
        //int nextBallC = ballY + ballDY;
        //int nextBallD = ballY + ballDY + diameter;

        //ballX += ballDX;
        //ballY += ballDY;

        //TESTING THINGS:
        if(ballX < diameter/2) ballDX = Math.abs(ballDX);
        if(ballX > getWidth() - diameter/2) ballDX = -Math.abs(ballDX);
        if(ballY < diameter/2) ballDY = Math.abs(ballDY);
        if(ballY > getHeight()- diameter/2) ballDY = -Math.abs(ballDY);

        ballX += ballDX;
        ballY += ballDY;
        
        repaint();
    }
}
