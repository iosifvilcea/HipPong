import java.lang.Math;

public class Player{
	private int position;	//position from the set 0 position or from center (to be decided)
	private int curScore;	//this players current score
	private int delta;	//used to hold the movement speed
	private int size;	//length of the padle

	//*********************************************************************
	//Constructor:
	//	Player
	//
	//	Passed in values:
	//		int pos: Should hold that starting position for the 
	//			paddle.
	//		int d: Should hold the delts(movement speed) for the
	//			paddle.
	//		int s: Shoudl hold the length of the paddle in pixels.
	//
	//	Operations:
	//		This constructor takes the passed in values and sets
	//		the corresponding values in the class, as well as,
	//		setting the curScore to 0. This should only be called
	//		at the begining of a game when a player is created.
	//*********************************************************************
	public Player(int pos, int d, int s){
		position = pos;
		curScore = 0;
		delta = d;
		size = s;
	}

	//*********************************************************************
	//Function:
	//	addScore
	//
	//	Operations:
	//		This fucntion increments the players current score
	//		by one. It is only by one beacuse a player should not
	//		be able to get multiple points at once when the ball
	//		goes past one of thier opponents paddles.
	//
	//	Remaining secuirty concerns:
	//		since this has to be public, it may be possible for 
	//		someone to add points to a player from outside the
	//		HipPong's main program.
	//*********************************************************************
	public void addScore(){
		curScore++;
	}

	//*********************************************************************
	//Function:
	//	getPosition
	//
	//	Operations:
	//		This fucntion returns the paddle's current postion.
	//*********************************************************************
	public int getPosition(){
		return position;
	}

	//*********************************************************************
	//Function:
	//	getScore
	//
	//	Operations:
	//		This fucntion returns the player's current score.
	//*********************************************************************
	public int getScore(){
		return curScore;
	}

	//*********************************************************************
	//Function:
	//	setPosition
	//
	//	Passed in Variables:
	//		boolean chg: will be used to determin if position will
	//			be increamented or decremented
	//
	//	Operations:
	//		This fucntion increaments the paddle's postion by
	//		delta (the paddle's movement speed) if true is passed
	//		into chg, decrements otherwise.
	//
	//	Remaining security concerns:
	//		since this function is public, it may be possible for
	//		someone to run a program to change the paddle's
	//		position faster then a human is capable of doing or
	//		change another players position without the other
	//		player intending to do so.
	//*********************************************************************
	public void setPosition(boolean chg){
		if (chg)
			position += delta;
		else
			position -= delta;
	}
}
