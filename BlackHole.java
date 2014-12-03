//1 thing let to do. 1) make sure that spit function works as intended

//*********************************************************************
//class:
//	BlackHole
//	
//	Use:
//		This class is used to store all the variables
//		pertinant to the BlackHole obsticle that is used in
//		the game HipPong.
//*********************************************************************
import java.util.Random;

public class BlackHole{
	//Random variable that will be used to randomize where the ball will
	//	be spit out to and at what speed
	Random rand;
	
	//Declaration of all variables
	//cur variables hold the Blackhole's current position, used to draw it
	private double curX = 0;
	private double curY = 0;

	//max variables hold the size of the play field. They are used to
	//	perform the change on the ball's dir variables to make sure
	//	they are within reasonable bounds.
	private int maxX = 0;
	private int maxY = 0;
	
	//size hold the blackhole's radius size in pixels, can only be set in
	//	constructor
	private int size = 1;

	//ballSpVar holds a vaibale that will affect what the dirX and dirY of
	//	the ball will be set to when it is expelled
	private int ballSpVar;

	//*********************************************************************
	//Constructor:
	//	Ball
	//	
	//	Passed in Variables:
	//		int Size: Should hold the Balls radius size in pixels.
	//		
	//		int MaxX: Should hold the play fieldd x size in pixels.
	//
	//		int MaxY: Should holf the play fields y size in pixels.
	//
	//	Operations:
	//		Sets size, maxX, and maxY to their matching passed in
	//		values, and curX and curY are set to random values
	//		within the center 50% of the play field.
	//*********************************************************************
	public BlackHole(int Size, int MaxX, int MaxY){
		//intializes rand with the seed of the current time.
		rand = new Random(System.currentTimeMillis());
		//this is used to set and min and max possible initial ball
		//	speeds.
		ballSpVar = rand.nextInt(5);

		//setting variables to passed in counterpart.
		size = Size;
		maxX = MaxX;
		maxY = MaxY;
	}

	

	//*********************************************************************
	//Function:
	//	spit
	//
	//	Passed in Variables:
	//		Ball arg: Should be the Game's Ball class.
	//	
	//	Operations:
	//		This function changes the balls direction so some
	//		random set of doubles.
	//*********************************************************************
	public void spit( /*Ball arg */ ){
		//setting direction variables to random numbers with mins
		//arg.setDirX (rand.nextInt(maxX/ballSpVar) + (maxX/ballSpVar));
		//arg.setDirY (rand.nextInt(maxY/ballSpVar) + (maxY/ballSpVar));
	}
}
