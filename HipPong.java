import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
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
//*****************************************************************************
public class HipPong{
	//initialization of the frame with Boarder Layout
	private static JFrame frame = new JFrame("HipPong");
	
	//initialization of the game options
	private static int[] players = {0,0,0,0}; //0 = human; 1 = computer; 2 = wall;
	//every pair is a player. first in pair is left/up. second in pair is right/down.
	private static String[] Controls = {"Q","R","C","V","Open Bracket","Semicolen","M","Comma"};
	private static int difficulty = 1; //0 = easy; 1 = normal; 2 = hard;
	
	//*************************************************************************
	//main
	//		passed in values:
	//			ignored!!
	//		operations:
	//			sets up the frame and panels for the games menu with the
	//			correct spacing
	//*************************************************************************
	public static void main(String[] args){
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		frame.setSize(1400, 900);
		frame.setResizable(false);
		frame.setBackground(Color.BLACK);
		
		//sets the constraints for spacing around the panels
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(100,10,100,10);
		
		//initialization of menu panels
		JPanel left = PlayerOptions(0);
		JPanel top = PlayerOptions(1);
		JPanel right = PlayerOptions(2);
		JPanel bottom = PlayerOptions(3);
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
	
	//*************************************************************************
	//PlayerConfirm
	//		returns:
	//			JPanel containing a single the games difficulty choice and
	//			an accept/play button to start the game
	//		operations:
	//			creates two horizontal boxlayouts of radiobuttons or normal
	//			buttons and puts everything in a vertial boxlayout. Then it
	//			returns everything to the calling object. The radiobuttons
	//			change the difficulty and the single normal button calls the
	//			game function which then starts the game
	//*************************************************************************
	private static JPanel PlayersConfirm(){
		JPanel menu = new JPanel();
		JPanel top = new JPanel();
		
		//menu.setBackground(Color.BLACK);
		//top.setBackground(Color.BLACK);
		
		//setting the layouts for everything so it is forced to display
		//	how I want it to display
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));

		//setting the returned's panel dimensions
		menu.setPreferredSize(new Dimension(400,50));
		menu.setMaximumSize(new Dimension(400,50));
		menu.setMinimumSize(new Dimension(400,50));
		
		//creating player type buttons and adding them to a group so
		// they are aware of each other
		JRadioButton easy = new JRadioButton("Easy",false);
		JRadioButton normal = new JRadioButton("Normal",true);
		JRadioButton hard = new JRadioButton("Hard",false);
		ButtonGroup playerType = new ButtonGroup();
		playerType.add(easy);
		playerType.add(normal);
		playerType.add(hard);
		
		//adding listeners so that when ever a buttons state changes to
		//	selected it updates the player type value
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
		
		//creating the accept/play button and setting to when pressed it
		//	calls the game class. then setting it to be in the center to the
		//	boxlayout
		JButton accept = new JButton("Play");
		accept.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				PlayGame();
			}
		});
		accept.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//adding everything to the top and bottom half's
		top.add(easy);
		top.add(normal);
		top.add(hard);
		
		//adding everything to the panel that will be returned
		menu.add(top);
		menu.add(accept);
		
		return menu;
	}
	
	//*************************************************************************
	//PlayerOptions
	//		passed in values:
	//			player: used to reference which player's settings change
	//		returns:
	//			JPanel containing a single player's choices for type, and
	//			controls
	//		operations:
	//			creates two horizontal boxlayouts of buttons or labels and
	//			text fields and puts everything in a vertial boxlayout. Then
	//			it returns everything to the calling object.
	//*************************************************************************
	private static JPanel PlayerOptions(final int player){
		JPanel menu = new JPanel();
		JPanel top = new JPanel();
		JPanel bottom = new JPanel();
		
		//menu.setBackground(Color.BLACK);
		//top.setBackground(Color.BLACK);
		//bottom.setBackground(Color.BLACK);
		
		//setting the layouts for everything so it is forced to display
		//	how I want it to display
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		top.setLayout(new BoxLayout(top,BoxLayout.X_AXIS));
		bottom.setLayout(new BoxLayout(bottom,BoxLayout.X_AXIS));

		//setting the returned's panel dimensions
		menu.setPreferredSize(new Dimension(400,50));
		menu.setMaximumSize(new Dimension(400,50));
		menu.setMinimumSize(new Dimension(400,50));
		
		//creating player type buttons and adding them to a group so
		// they are aware of each other
		JRadioButton human = new JRadioButton("Human",true);
		JRadioButton computer = new JRadioButton("Computer",false);
		JRadioButton wall = new JRadioButton("Wall",false);
		ButtonGroup playerType = new ButtonGroup();
		playerType.add(human);
		playerType.add(computer);
		playerType.add(wall);
		
		//adding listeners so that when ever a buttons state changes to
		//	selected it updates the player type value
		human.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if (e.getStateChange() == ItemEvent.SELECTED){
					players[player] = 0;
				}
			}
		});
		computer.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if (e.getStateChange() == ItemEvent.SELECTED){
					players[player] = 1;
				}
			}
		});
		wall.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				if (e.getStateChange() == ItemEvent.SELECTED){
					players[player] = 2;
				}
			}
		});
		
		//creates text labels and field for player controls
		JLabel left = new JLabel("Left:");
		JLabel right = new JLabel("Right:");
		final JButton leftControlKey = new JButton(String.valueOf(Controls[player * 2]));
		final JButton rightControlKey = new JButton(String.valueOf(Controls[(player * 2) + 1]));
		
		//creating the listeners for the buttons. when clicked it calls a dialog
		//	asking the user for a key and returns the first key typed
		leftControlKey.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				getKey(player, true, leftControlKey);
			}
		});
		rightControlKey.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				getKey(player, false, rightControlKey);
			}
		});
		
		//adding everything to the top and bottom half's
		top.add(human);
		top.add(computer);
		top.add(wall);
		bottom.add(left);
		bottom.add(leftControlKey);
		bottom.add(right);
		bottom.add(rightControlKey);
		
		//adding everything to the panel that will be returned
		menu.add(top);
		menu.add(bottom);
		
		return menu;
	}
	//*************************************************************************
	//Game
	//		called by the accept button. It clears the frame and adds a new
	//		game to it. when creating the game panel it passes in the recorded
	//		values of the menu screen.
	//*************************************************************************
    private static void PlayGame(){
		//clearing frame
		frame.setVisible(false);
		frame.getContentPane().removeAll();

		//creating game and adding it to the frame
		Game game = new Game(players, Controls, difficulty);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.setResizable(true);
		frame.setSize(1000,1000);
		//frame.setResizable(false);
        	frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
	}
	//*********************************************************************
	//getKey
	//
	//
	//
	//*********************************************************************
	private static void getKey(final int player, final boolean left, final JButton button){
		final boolean[] check = {false};
		final JFrame popup = new JFrame("New Key");
		popup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		popup.setSize(300,100);
		popup.add(new JLabel("Press the new Control Key"));
		popup.setVisible(true);
		popup.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e){
				String holder = e.getKeyText( e.getKeyCode());

				if (left)
					Controls[player * 2] = holder;
				else
					Controls[(player * 2) + 1] = holder;

				button.setText(holder);
				popup.dispose();
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {
				String holder = e.getKeyText( e.getKeyCode());

				if (left)
					Controls[player * 2] = holder;
				else
					Controls[(player * 2) + 1] = holder;

				button.setText(holder);
				popup.dispose();
			}
		});
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
