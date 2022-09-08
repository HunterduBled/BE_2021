import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;

import lejos.nxt.remote.NXTCommand;
import lejos.nxt.remote.NXTProtocol;
import lejos.nxt.remote.RemoteMotor;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import lejos.robotics.navigation.DifferentialPilot;

public class Robot {
  public static void main (String[] args) throws NXTCommException, InterruptedException, IOException {
	  //light on, black = 700
	  //light on, white = 450-550
	  //light off, black = 850
	  //light off, white = 850
    NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
    NXTInfo[] nxtInfo = nxtComm.search("FUBUKI");
    if (nxtInfo.length == 0) {
    	System.out.println("No nxt found");
    	System.exit(1);
    }
    nxtComm.open(nxtInfo[0]);
    final RemoteMotor rmLeft = new RemoteMotor(new NXTCommand(nxtComm), 1);
    final RemoteMotor rmRight = new RemoteMotor(new NXTCommand(nxtComm), 2);
    final NXTCommand nxtCommand = new NXTCommand(nxtComm);
    final DifferentialPilot pilote = new DifferentialPilot(4.96f, 13.0f, rmLeft, rmRight, false);
    nxtCommand.setInputMode(1, NXTProtocol.LIGHT_ACTIVE, NXTProtocol.RAWMODE);
    nxtCommand.setInputMode(2, NXTProtocol.LIGHT_ACTIVE, NXTProtocol.RAWMODE);
    pilote.setTravelSpeed(10);
    
    System.out.println("Connecté à"+ nxtInfo[0].name + "avec addresse"  + nxtInfo[0].deviceAddress);

    Thread tournerDroite = new Thread() {

		@Override
		public void run() {
			while(true) {
				try {
					if ((nxtCommand.getInputValues(1).rawADValue < 650)) {
						pilote.steer(60);
					} else if (nxtCommand.getInputValues(1).rawADValue < 500) {
						pilote.stop();
					}
					Thread.sleep(50);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    };
    
    Thread tournerGauche = new Thread() {

		@Override
		public void run() {
			while(true)  {
				try {
					if (nxtCommand.getInputValues(2).rawADValue < 650) {
						pilote.steer(-60);
					} else if (nxtCommand.getInputValues(2).rawADValue < 500) {
						pilote.stop();
					}
					Thread.sleep(50);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
    };
    tournerDroite.start();
    tournerGauche.start();

    
    JFrame f = new JFrame();
    f.addKeyListener(new Deplacements(rmLeft, rmRight, nxtCommand));
    f.setVisible(true);
    f.setSize(new Dimension(500, 500)); 
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    
    f.addWindowListener(new java.awt.event.WindowAdapter() {
    	@Override
    	public void windowClosing(java.awt.event.WindowEvent wE) {
    		try {
				nxtCommand.setInputMode(2, NXTProtocol.LIGHT_INACTIVE, NXTProtocol.RAWMODE);
				nxtCommand.setInputMode(1, NXTProtocol.LIGHT_INACTIVE, NXTProtocol.RAWMODE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		System.exit(1);
    	}
    });
    
    
  }
}
