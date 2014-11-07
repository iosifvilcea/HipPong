public class Computer extends Player{
	private boolean axis;	//used to calculate how the computer will move
				//if true, moves along the x axis(left & right)
				//if false, move along the y axis(up & down)

	//*********************************************************************
	//Constructor:
	//	Computer
	//
        //      Passed in values:
        //              int pos: Should hold that starting position for the 
        //                      paddle.
        //              int d: Should hold the delts(movement speed) for the
        //                      paddle.
        //              int s: Shoudl hold the length of the paddle in pixels.
        //
        //      Operations:
	//		This constructor calls the constructor from the Player
	//		class and passes it the values that where passed into
	//		it.
	//*********************************************************************
	public Computer(int pos, int d, int s){
		super(pos, d, s);
	}

	//*********************************************************************
	//Function:
	//	move
	//
	//	Passed in values:
	//		Ball ball: Should hold the ball object for the game.
	//
	//	Operations:
	//		This function moves the computer's paddle position
	//		in the direction of the ball.
	//*********************************************************************
	public void move(Ball ball){
		double ballPos; //will be used to hold the balls position

		//determines which axis of the ball's position it should check
		//	and gets the ball's position in that axis and stores it
		//	in ballPos
		if (axis){
			ballPos = ball.getCurX();
		}
		else{
			ballPos = ball.getCurY();
			
		}

		//moves the paddle according to if the ball is past it or
		//	or before it
		if (ballPos > getPosition())
			setPosition(true);
		else
			setPosition(false);
	}
}
