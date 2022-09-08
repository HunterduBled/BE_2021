import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import lejos.nxt.remote.NXTCommand;
import lejos.nxt.remote.NXTProtocol;
import lejos.nxt.remote.RemoteMotor;


public class Deplacements implements KeyListener {

	RemoteMotor rmA;
	RemoteMotor rmB;
	NXTCommand nxtCommand;
	boolean lumiereDroiteAllume = false;
	boolean lumiereGaucheAllume = false;
	
	public Deplacements(RemoteMotor rmA, RemoteMotor rmB, NXTCommand nxtCommand) {
		this.rmA = rmA;
		this.rmB = rmB;
		this.nxtCommand = nxtCommand;
	}
	
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		switch(keyCode) {
			case KeyEvent.VK_UP:
				rmA.forward();
				rmB.forward();
				break;
			case KeyEvent.VK_DOWN:
				rmA.stop(true);
				rmB.stop(true);
				break;
			case KeyEvent.VK_LEFT:
				//éteindre capteur droit
				break;
			case KeyEvent.VK_RIGHT:
				//éteindre capteur gauche
				break;
			case KeyEvent.VK_E:
				if (!lumiereDroiteAllume) {
					try {
						nxtCommand.setInputMode(1, NXTProtocol.LIGHT_ACTIVE, NXTProtocol.RAWMODE);
						lumiereDroiteAllume = true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						nxtCommand.setInputMode(1, NXTProtocol.LIGHT_INACTIVE, NXTProtocol.RAWMODE);
						lumiereDroiteAllume = false;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case KeyEvent.VK_A:
				if (!lumiereGaucheAllume) {
					try {
						nxtCommand.setInputMode(2, NXTProtocol.LIGHT_ACTIVE, NXTProtocol.RAWMODE);
						lumiereGaucheAllume = true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						nxtCommand.setInputMode(2, NXTProtocol.LIGHT_INACTIVE, NXTProtocol.RAWMODE);
						lumiereGaucheAllume = false;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				break;
			case KeyEvent.VK_H:
			try {
				System.out.println(nxtCommand.getInputValues(2).rawADValue);
				System.out.println(nxtCommand.getInputValues(1).rawADValue);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
