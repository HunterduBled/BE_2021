
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

/**
 * Remote control of an NXT car by means of the arrow keys 
 * and motor power entered by a slider in a GUI.
 * 
 * @author Ole Caprani
 * @version 17.01.15
 */
public class RemoteController implements KeyEventDispatcher 
{
	private final int leftArrow  = 37,
					  rightArrow = 39,
					  upArrow    = 38,
					  downArrow  = 40;
	
	private GUI gui;
	private RCcar rcCar;
	
	public RemoteController()
	{
		gui = new GUI();
		gui.add(this);
		rcCar = new RCcar();
		rcCar.aLive();
	}
		
	public boolean dispatchKeyEvent(KeyEvent e)
	{

		if ( e.getID() == KeyEvent.KEY_PRESSED)
		{
			int power = gui.getPower();
			
			switch ( e.getKeyCode() )
			{
			case leftArrow:   rcCar.spinLeft(power);
                          	  gui.displayCommand("Left " + power);
                              break;
			case rightArrow:  rcCar.spinRight(power);
                              gui.displayCommand("Right " + power);
                              break;
			case upArrow:	  rcCar.forward(power);
			                  gui.displayCommand("Forward " + power);
						      break;		
			case downArrow:   rcCar.backward(power);
		                      gui.displayCommand("Backward " + power);
		                      break;
		                      
		    default:          return false;
		    }
			
			return true;
		}
	    else
			if ( e.getID() == KeyEvent.KEY_RELEASED)
			{
				switch ( e.getKeyCode() )
				{
				case leftArrow:   
				case rightArrow:  
				case upArrow:	  		
				case downArrow:   rcCar.stop();
			                      gui.displayCommand("Stop");
			                      return true;
			                      
			    default:          return false;
			    }
			}
		
		return false;
	}
		
	public static void main(String [] args) 
	{		
		RemoteController rc = new RemoteController();		
	}
}