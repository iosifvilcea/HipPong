//1 things to finish. 1) random directions in constructor

//*********************************************************************
//class:
//	Ball
//	
//	Use:
//		This class is used to store all the variables
//		pertinant to the ball used in a game of HipPong.
//*********************************************************************
import java.util.Random;

public class Ball{
	//Declaration of all variables
	//cur variables hold the ball's current position, used to draw it
	private double curX = 0;
	private double curY = 0;
	
	//dir variables hold the direction of the balls movement, also used
	//	to adjust the balls velocity
	private double dirX = 0;
	private double dirY = 0;

	//max variables hold the size of the play field. They are used to
	//	perform checks the dir variables to make sure they are
	//	within bounds.
	private int maxX = 0;
	private int maxY = 0;
	
	//size hold the balls radius size in pixels, can only be set in
	//	constructor
	private int size = 1;

	//*********************************************************************
	//Constructor:
	//	Ball
	//	
	//	Passed in Variables:
	//		int Size: Should hold the Balls radius size in pixels.
	//		
	//		double centerX: Should hold the center's X for the
	//		play field.
	//		
	//		double centerY: Should hold the center's Y for the
	//		play field.
	//
	//		int MaxX: Should hold the play fieldd x size in pixels.
	//
	//		int MaxY: Should holf the play fields y size in pixels.
	//
	//	Operations:
	//		Sets size, curX, and curY to their matching passed in
	//		values, and sets dirx and dirY to random values to
	//		the ball start in the center and go off in a random
	//		direction.
	//*********************************************************************
	public void ball(int Size, double centerX, double centerY, int MaxX, int MaxY){
		//intializes rand with the seed of the current time.
		Random rand = new Random(System.currentTimeMillis());
		//this is used to set and min and max possible initial ball
		//	speeds.
		double ballSpVar = 5;

		//setting variables to passed in counterpart.
		size = Size;
		curX = centerX;
		curY = centerY;
		maxX = MaxX;
		maxY = MaxY;

		//setting direction variables to random numbers with mins
		dirX = rand.nextInt(maxX/ballSpVar) + (maxX/ballSpVar);
		dirY = rand.nextInt(maxY/ballSpVar) + (maxY/ballSpVar);
	}

	//*********************************************************************
	//Function Group:
	//	Set
	//	
	//	Passed in Varibales:
	//		double arg (for all except setSize)
	//
	//	Operations:
	//		This group of functions accept a passed in varibale
	//		arg amd make sure that arg is within the correct
	//		bounds for the specific ball variable. If arg checks
	//		out then the specific variable is set to equal arg.
	//*********************************************************************
	public void setCurX(double arg){
		if (arg >= 0)
			curX = arg;
	}

	public void setCurY(double arg){
		if (arg >= 0)
			curY = arg;
	}

	public void setDirX(double arg){
		if (arg >= 0)
			dirX = arg;
	}

	public void setDirY(double arg){
		if (arg >= 0)
			dirY = arg;
	}

	//*********************************************************************
	//Function Group:
	//	Get
	//	
	//	Operations:
	//		This group of functions returns the specific variable.
	//*********************************************************************
	public double getCurX(){
		return curX;
	}

	public double getCurY(){
		return curY;
	}

	public double getDirX(){
		return dirX;
	}

	public double getDirY(){
		return dirY;
	}

	public int getSize(){
		return size;
	}

}
