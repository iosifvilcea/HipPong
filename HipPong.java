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
	
	//initialization max poinst to play game
	private static int maxPoints = 5;
	//initialization of the game options
	private static int[] players = {0,0,0,0}; //0 = human; 1 = computer; 2 = wall;
	//every pair is a player. first in pair is left/up. second in pair is right/down.
	//private static String[] Controls = {"Q","R","C","V","Open Bracket","Semicolen","M","Comma"};
	private static String[] Controls = {"W","S","A","D","Up","Down","Left","Right"};
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
		frame.setSize(1000, 500);
	        frame.setLocationRelativeTo(null); //Adds window in center of screen.
		frame.setResizable(true);
		frame.setBackground(Color.BLACK);
		
		//sets the constraints for spacing around the panels
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(50,0,0,50);
		
		//initialization of menu panels
		JPanel left = PlayerOptions(0);
		JPanel top = PlayerOptions(1);
		JPanel right = PlayerOptions(2);
		JPanel bottom = PlayerOptions(3);
		JPanel center = PlayersConfirm();
		
		//putting menus on frame with constraints and showing menu to user(s)
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		frame.add(left, gbc);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 5;
		frame.add(top, gbc);
		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		frame.add(right, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 5;
		frame.add(bottom, gbc);
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
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
		JPanel bottom = new JPanel();

		//menu.setBackground(Color.BLACK);
		//top.setBackground(Color.BLACK);

		//setting the layouts for everything so it is forced to display
		//	how I want it to display
		menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
		top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));

		//setting the returned's panel dimensions
		menu.setPreferredSize(new Dimension(200,60));
		menu.setMaximumSize(new Dimension(200,60));
		menu.setMinimumSize(new Dimension(200,60));
		
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
		JLabel mp = new JLabel("Max Points:");
		final JTextField points = new JTextField("5");
		JButton accept = new JButton("Play");
		accept.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					maxPoints = Integer.parseInt(points.getText());
				} catch (Exception excep) {
					
				} finally {
					PlayGame();
				}
			}
		});
		
		//accept.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//adding everything to the top and bottom half's
		top.add(easy);
		top.add(normal);
		top.add(hard);
		bottom.add(mp);
		bottom.add(points);
		bottom.add(accept);
		
		//adding everything to the panel that will be returned
		menu.add(top);
		menu.add(bottom);
		
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
		menu.setPreferredSize(new Dimension(350,50));
		menu.setMaximumSize(new Dimension(350,50));
		menu.setMinimumSize(new Dimension(350,50));
		
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
					System.out.println(player + " is " + players[player]);
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
		if (player != 0 && player != 2)
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
		boolean loop = false;
		//clearing frame
		frame.setVisible(false);
		frame.getContentPane().removeAll();

		//creating game and adding it to the frame
		Game game = new Game(players, Controls, difficulty, maxPoints, loop);
		frame.setResizable(true);
		frame.setSize(702,725);
		frame.setResizable(false);
	        frame.setLayout(new BorderLayout());
	        frame.add(game, BorderLayout.CENTER);
	        frame.setVisible(true);
		if (loop){
			frame.setVisible(false);
			frame.getContentPane().removeAll();
			main(new String[] {"",""});
		}
	}
	//*********************************************************************
	//getKey
	//	passed in:
	//		player: the player who's change is being requested
	//		left: left control or not
	//		button: reference to the button that was clicked
	//
	//	operation:
	//		creates a popup style jframe that listens for any keys
	//		hit by the player. It gets he key code, translates that
	//		into text and then sets the calling button text to the
	//		string of the new key as well as the control key for
	//		the corresponding player. The jframe is then distroyed.
	//		if closed out, the jframe wont do anything, and will
	//		close out.
	//*********************************************************************
	private static void getKey(final int player, final boolean left, final JButton button){
		//creation of popup frame
		final JFrame popup = new JFrame("New Key");
		popup.setSize(300,100);
		popup.add(new JLabel("Press the new Control Key"));
		popup.setVisible(true);

		//adding listeners to get new key and set the correct control to it
		popup.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e){
				//gets the entered key's string representation
				String holder = e.getKeyText( e.getKeyCode());

				//checks which of the players keys is getting reset
				if (left)
					Controls[player * 2] = holder;
				else
					Controls[(player * 2) + 1] = holder;
				
				//sets the buttons text to the new key
				button.setText(holder);
				//closes the jframe
				popup.dispose();
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {
				//gets the entered key's string representation
				String holder = e.getKeyText( e.getKeyCode());

				//checks which of the players keys is getting reset
				if (left)
					Controls[player * 2] = holder;
				else
					Controls[(player * 2) + 1] = holder;
				
				//sets the buttons text to the new key
				button.setText(holder);
				//closes the jframe
				popup.dispose();
			}
		});
	}
}
