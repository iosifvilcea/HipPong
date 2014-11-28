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
//		is function painting the field, ball, BlackHole, computers
//		and players constantly. It also takes in input from the
//		and translates it the the movement of the paddles. Lastly,
//		it runs all the updates for the movement of all the objects.
//
//		TO DO:
//			finish writing what needs to go in the menus
//			figure how to get info back from panels
//			write the actual game code
//*****************************************************************************
public class HipPong{
	//initialization of the frame with Boarder Layout
	private static JFrame frame = new JFrame("HipPong");
	
	//initialization of the game options
	private static int[] players = {0,0,0,0}; //0 = human; 1 = computer; 2 = wall;
	//every pair is a player. first in pair is left/up. second in pair is right/down.
	private static char[] Controls = {'q','a','c','v','[',';','m',','};
	private static int difficulty = 1; //0 = easy; 1 = normal; 2 = hard;
	
	public static void main(String[] args){
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new GridBagLayout());
	    frame.setSize(1400, 900);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(100,10,100,10);
		
		//initialization of menu panels
		JPanel left = PlayerOptions(players[0]);
		JPanel top = PlayerOptions(players[1]);
		JPanel right = PlayerOptions(players[2]);
		JPanel bottom = PlayerOptions(players[3]);
		JPanel center = PlayersConfirm();
		
		//putting menus on frame with constraints and showing menu to user(s)
		gbc.gridx = 0;
		gbc.gridy = 2;
		frame.add(left, gbc);
		gbc.gridx = 3;
		gbc.gridy = 0;
	    frame.add(top, gbc);
		gbc.gridx = 5;
		gbc.gridy = 2;
		frame.add(right, gbc);
		gbc.gridx = 3;
		gbc.gridy = 4;
		frame.add(bottom, gbc);
		gbc.gridx = 3;
		gbc.gridy = 2;
		frame.add(center, gbc);
        frame.setVisible(true);
	}
	
	private static JPanel PlayersConfirm(){
		JPanel menu = new JPanel();
		JPanel top = new JPanel();
		JPanel bottom = new JPanel();
		
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.X_AXIS));

		menu.setPreferredSize(new Dimension(400,50));
		menu.setMaximumSize(new Dimension(400,50));
		menu.setMinimumSize(new Dimension(400,50));
		
		JRadioButton easy = new JRadioButton("Easy",false);
		JRadioButton normal = new JRadioButton("Normal",true);
		JRadioButton hard = new JRadioButton("Hard",false);
		ButtonGroup playerType = new ButtonGroup();
		playerType.add(easy);
		playerType.add(normal);
		playerType.add(hard);
		
		easy.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if (e.getStateChange() == ItemEvent.SELECTED){
					difficulty = 0;
				}
			}
		});
		normal.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if (e.getStateChange() == ItemEvent.SELECTED){
					difficulty = 1;
				}
			}
		});
		hard.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if (e.getStateChange() == ItemEvent.SELECTED){
					difficulty = 2;
				}
			}
		});
		
		JButton accept = new JButton("Play");
		accept.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		accept.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		top.add(easy);
		top.add(normal);
		top.add(hard);
		
		menu.add(top);
		menu.add(accept);
		
		return menu;
	}
	
    private static JPanel PlayerOptions(final int player){
		JPanel menu = new JPanel();
		JPanel top = new JPanel();
		JPanel bottom = new JPanel();
		
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
		bottom.setLayout(new BoxLayout(bottom,BoxLayout.X_AXIS));

		menu.setPreferredSize(new Dimension(400,50));
		menu.setMaximumSize(new Dimension(400,50));
		menu.setMinimumSize(new Dimension(400,50));
		
		JRadioButton human = new JRadioButton("Human",true);
		JRadioButton computer = new JRadioButton("Computer",false);
		JRadioButton wall = new JRadioButton("Wall",false);
		ButtonGroup playerType = new ButtonGroup();
		playerType.add(human);
		playerType.add(computer);
		playerType.add(wall);
		
		human.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if (e.getStateChange() == ItemEvent.SELECTED){
					players[player] = 0;
					System.out.println("Player = " + players[0]);
				}
			}
		});
		computer.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if (e.getStateChange() == ItemEvent.SELECTED){
					players[player] = 1;
					System.out.println("Player = " + players[0]);
				}
			}
		});
		wall.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if (e.getStateChange() == ItemEvent.SELECTED){
					players[player] = 2;
					System.out.println("Player = " + players[0]);
				}
			}
		});
		
		JLabel left = new JLabel("Left:");
		JLabel right = new JLabel("Right:");
		final JTextField leftControl = new JTextField(String.valueOf(Controls[player * 2]), 2);
		final JTextField rightControl = new JTextField(String.valueOf(Controls[(player * 2) + 1]), 2);
		
		leftControl.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Controls[player * 2] = leftControl.getText().charAt(0);
				System.out.println("left key: " + Controls[player * 2]);
			}
		});
		rightControl.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Controls[(player * 2) + 1] = rightControl.getText().charAt(0);
				System.out.println("right key: " + Controls[(player * 2) + 1]);
			}
		});
		
		//leftControl.addItemListener
		
		top.add(human);
		top.add(computer);
		top.add(wall);
		bottom.add(left);
		bottom.add(leftControl);
		bottom.add(right);
		bottom.add(rightControl);
		
		menu.add(top);
		menu.add(bottom);
		
		return menu;
	}
}

//*****************************************************************************
//Class:
//	Menu
//
//	Operation:
//		This class will paint all the choices of the menu for all players.
//		These choices include movement keys, human, computer, or
//		wall(maybe, if implemented);
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

