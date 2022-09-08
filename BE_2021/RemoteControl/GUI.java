import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GUI implements ChangeListener
{
	private JFrame frame;
	private JLabel motorLabel;
	private JSlider motorPower;
	private int power = 50;
	private JTextField text;
	private KeyboardFocusManager kfm;
	
	public GUI() 
	{
		kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		frame = new JFrame();
		frame.setSize(500, 150);
		frame.setLayout(new BorderLayout());
		
		motorLabel = new JLabel("Motor power", JLabel.CENTER);
		motorPower = new JSlider(JSlider.HORIZONTAL,0,100,50);
		motorPower.setMajorTickSpacing(10);
		motorPower.setMinorTickSpacing(1);
		motorPower.setPaintTicks(true);
		motorPower.setPaintLabels(true);
		motorPower.addChangeListener(this);
		
		text  = new JTextField(20);
		text.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		frame.add(motorLabel, BorderLayout.NORTH);
		frame.add(motorPower, BorderLayout.CENTER);
		frame.add(text, BorderLayout.SOUTH);		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/* Listen to slider */
	public void stateChanged(ChangeEvent e)
	{
		JSlider source = (JSlider)e.getSource();
		if ( ! source.getValueIsAdjusting() )
		{
			power = (int)source.getValue();
		}
	}
	
	public int getPower()
	{
		return power;
	}
	
	public void add(KeyEventDispatcher listener)
	{
		kfm.addKeyEventDispatcher(listener);
	}
	
	public void displayCommand(String command)
	{
		text.setText(command);
	}

}
