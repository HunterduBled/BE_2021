
import lejos.nxt.*;

/**
 * Remote access to motor B and C of an NXT car using
 * iCommand-like classes in pccomm.jar.
 * 
 * @author Ole Caprani
 * @version 17.01.15
 */
public class RCcar 
{

	private MotorPort leftMotor  = new MotorPort(2);
	private MotorPort rightMotor = new MotorPort(1);
	
	public RCcar()
	{
		
	}
		
	public void forward( int power)
	{	
		leftMotor.controlMotor(power, BasicMotorPort.FORWARD);
		rightMotor.controlMotor(power, BasicMotorPort.FORWARD);	
	}
	
	public void backward( int power)
	{	
		leftMotor.controlMotor(power, BasicMotorPort.BACKWARD);
		rightMotor.controlMotor(power, BasicMotorPort.BACKWARD);	
	}
	
	public void spinLeft( int power)
	{	
		leftMotor.controlMotor(power, BasicMotorPort.BACKWARD);
		rightMotor.controlMotor(power, BasicMotorPort.FORWARD);	
	}
	
	public void spinRight( int power)
	{	
		leftMotor.controlMotor(power, BasicMotorPort.FORWARD);
		rightMotor.controlMotor(power, BasicMotorPort.BACKWARD);	
	}
	
	public void stop()
	{	
		leftMotor.controlMotor(0, BasicMotorPort.STOP);
		rightMotor.controlMotor(0, BasicMotorPort.STOP);	
	}
	
	public void aLive()
	{
		Sound.beep();
	}
}