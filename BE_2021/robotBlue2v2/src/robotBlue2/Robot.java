package robotBlue2;


import lejos.nxt.remote.AsciizCodec;
import lejos.nxt.remote.NXTCommand;
//import lejos.nxt.remote.NXTProtocol;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import graphe.FormeCase;
import graphe.Node;


public class Robot{
	NXTComm nxtComm=null;
	NXTInfo[] nxtInfo=null;
	NXTCommand nxtCommand;
	String nameRobot;
	int orientation;
	int nbVictimes;
	int tailleCamion;
	Node nodeCourante;
	static byte[] avancer;
    static byte[] demitour;
    static byte[] slipgauche;
    static byte[] slipdroit;
    static byte[] wait;
	
    public Robot(String nameRobot,int orientation,int tailleCamion,Node nodeCourante) {
		this.nameRobot=nameRobot;
		this.orientation=orientation;
		this.tailleCamion=tailleCamion;
		this.nodeCourante=nodeCourante;
	}
    
	public Node getNodeCourante() {
		return nodeCourante;
	}
	public void setNodeCourante(Node nodeCourante) {
		this.nodeCourante = nodeCourante;
	}
	public byte[] getAvancer() {
		return avancer;
	}
	public byte[] getDemitour() {
		return demitour;
	}
	public byte[] getSlipgauche() {
		return slipgauche;
	}
	public byte[] getSlipdroit() {
		return slipdroit;
	}
	public byte[] getWait() {
		return wait;
	}
	public NXTComm getNxtComm() {
		return nxtComm;
	}
	public void setNxtComm(NXTComm nxtComm) {
		this.nxtComm = nxtComm;
	}
	public String getRobotName(){
		return this.nameRobot;
	}
	public NXTCommand getNXTCommand(){
		return this.nxtCommand;
	}
	public int getOrientation() {
		return orientation;
	}
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
	public int getNbVictimes() {
		return nbVictimes;
	}
	public void setNbVictimes(int nbVictimes) {
		this.nbVictimes = nbVictimes;
	}
	public int getTailleCamion() {
		return tailleCamion;
	}
	public static void init() throws UnsupportedEncodingException{
    	avancer = new byte[22];
    	avancer[0]=(byte)0x00;
    	avancer[1]=(byte)0x00;
        System.arraycopy(AsciizCodec.encode("av.rxe"), 0, avancer, 2, AsciizCodec.encode("av.rxe").length);
        
        demitour = new byte[22];
        demitour[0]=(byte)0x00;
        demitour[1]=(byte)0x00;
        System.arraycopy(AsciizCodec.encode("dt.rxe"), 0, demitour, 2, AsciizCodec.encode("dt.rxe").length);
        
        slipgauche = new byte[22];
        slipgauche[0]=(byte)0x00;
        slipgauche[1]=(byte)0x00;
        System.arraycopy(AsciizCodec.encode("sg.rxe"), 0, slipgauche, 2, AsciizCodec.encode("sg.rxe").length);
        
        slipdroit = new byte[22];
        slipdroit[0]=(byte)0x00;
        slipdroit[1]=(byte)0x00;
        System.arraycopy(AsciizCodec.encode("sd.rxe"), 0, slipdroit, 2, AsciizCodec.encode("sd.rxe").length);
        
        wait = new byte[2];
        wait[0]=(byte)0x00;
        wait[1]=(byte)0x11;
        
    }
	
	public void connexion() throws NXTCommException, InterruptedException, UnsupportedEncodingException{
		this.nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
		this.nxtInfo = nxtComm.search(this.nameRobot);
	    if (this.nxtInfo.length == 0) {
	        System.out.println("No nxt found");
	        System.exit(1);
	    }
	    this.nxtComm.open(this.nxtInfo[0]);
	    System.out.println("Connecté à "+ this.nxtInfo[0].name+" avec addresse " +this.nxtInfo[0].deviceAddress);
	    nxtCommand = new NXTCommand(nxtComm);
	    init();
	}
	
	public boolean demiTour(Node n0, Node n1) throws IOException{
		if (n0.getCoorX()>n1.getCoorX() && this.orientation==3){
			System.out.println("- On part de la case "+n0.getName()+" vers la case "+n1.getName());
			System.out.println("Faire demi-tour");
			nxtComm.sendRequest(demitour,3);
			this.orientation=(this.orientation+6)%12;
			return true;
		}
		else if (n0.getCoorX()<n1.getCoorX() && this.orientation==9 ){
			System.out.println("- On part de la case "+n0.getName()+" vers la case "+n1.getName());
			System.out.println("Faire demi-tour");
			nxtComm.sendRequest(demitour,3);
			this.orientation=(this.orientation+6)%12;
			return true;
		}
		else if (n0.getCoorY()>n1.getCoorY() && this.orientation==0 ){
			System.out.println("- On part de la case "+n0.getName()+" vers la case "+n1.getName());
			System.out.println("Faire demi-tour");
			nxtComm.sendRequest(demitour,3);
			this.orientation=(this.orientation+6)%12;
			return true;
		}
		else if (n0.getCoorY()<n1.getCoorY() && this.orientation==6){
			System.out.println("- On part de la case "+n0.getName()+" vers la case "+n1.getName());
			System.out.println("Faire demi-tour");
			nxtComm.sendRequest(demitour,3);
			this.orientation=(this.orientation+6)%12;
			return true;
		}
		else{
			return false;
		}
	}

	public void deplacement(Node n0, Node n1) throws IOException{
		if (n0.getCoorX()-n1.getCoorX()==0 && n0.getCoorY()-n1.getCoorY()==-1 && this.orientation==0){
			nxtComm.sendRequest(avancer,3);
			System.out.println("Avancer");
		}
		else if (n0.getCoorX()-n1.getCoorX()==1 && n0.getCoorY()-n1.getCoorY()==0 && this.orientation==0){
			this.orientation=9;
			nxtComm.sendRequest(avancer,3);
			System.out.println("Gauche");
		}
		else if (n0.getCoorX()-n1.getCoorX()==-1 && n0.getCoorY()-n1.getCoorY()==0 && this.orientation==0){
			this.orientation=(this.orientation+3)%12;
			nxtComm.sendRequest(avancer,3);
			System.out.println("Droite");
		}
		else if (n0.getCoorX()-n1.getCoorX()==0 && n0.getCoorY()-n1.getCoorY()==-1 && this.orientation==3){
			this.orientation=(this.orientation-3)%12;
			nxtComm.sendRequest(avancer,3);
			System.out.println("Gauche");
		}
		else if (n0.getCoorX()-n1.getCoorX()==-1 && n0.getCoorY()-n1.getCoorY()==0 && this.orientation==3){
			nxtComm.sendRequest(avancer,3);
			System.out.println("Avancer");
		}
		else if (n0.getCoorX()-n1.getCoorX()==0 && n0.getCoorY()-n1.getCoorY()==1 && this.orientation==3){
			this.orientation=(this.orientation+3)%12;
			nxtComm.sendRequest(avancer,3);
			System.out.println("Droite");
		}
		else if (n0.getCoorX()-n1.getCoorX()==0 && n0.getCoorY()-n1.getCoorY()==1 && this.orientation==6){
			nxtComm.sendRequest(avancer,3);
			System.out.println("Avancer");
		}
		else if (n0.getCoorX()-n1.getCoorX()==1 && n0.getCoorY()-n1.getCoorY()==0 && this.orientation==6){
			this.orientation=(this.orientation+3)%12;
			nxtComm.sendRequest(avancer,3);
			System.out.println("Droite");
		}
		else if (n0.getCoorX()-n1.getCoorX()==-1 && n0.getCoorY()-n1.getCoorY()==0 && this.orientation==6){
			this.orientation=(this.orientation-3)%12;
			nxtComm.sendRequest(avancer,3);
			System.out.println("Gauche");
		}
		else if (n0.getCoorX()-n1.getCoorX()==0 && n0.getCoorY()-n1.getCoorY()==-1 && this.orientation==9){
			this.orientation=(this.orientation+3)%12;
			nxtComm.sendRequest(avancer,3);
			System.out.println("Droite");
		}
		else if (n0.getCoorX()-n1.getCoorX()==1 && n0.getCoorY()-n1.getCoorY()==0 && this.orientation==9){
			nxtComm.sendRequest(avancer,3);
			System.out.println("Avancer");
		}
		else if (n0.getCoorX()-n1.getCoorX()==0 && n0.getCoorY()-n1.getCoorY()==1 && this.orientation==9){
			this.orientation=(this.orientation-3)%12;
			nxtComm.sendRequest(avancer,3);
			System.out.println("Gauche");
		}
		else{
			System.out.println("Il y a une erreur");
		}
	}
	
	public void deplacementSlip(Node n0, Node n1) throws IOException{
		if (n0.getCoorX()-n1.getCoorX()==0 && n0.getCoorY()-n1.getCoorY()==-1 && this.orientation==0){
			if (n0.getfCases()==FormeCase.SG){
				nxtComm.sendRequest(slipdroit,3);
				System.out.println("Avancer SlipD");
			}
			else{
				nxtComm.sendRequest(slipgauche,3);
				System.out.println("Avancer SlipG");
			}
			System.out.println("1");
		}
		else if (n0.getCoorX()-n1.getCoorX()==1 && n0.getCoorY()-n1.getCoorY()==0 && this.orientation==0){
			this.orientation=9;
			nxtComm.sendRequest(slipgauche,3);
			System.out.println("Gauche Slip");
		}
		else if (n0.getCoorX()-n1.getCoorX()==-1 && n0.getCoorY()-n1.getCoorY()==0 && this.orientation==0){
			this.orientation=(this.orientation+3)%12;
			nxtComm.sendRequest(slipdroit,3);
			System.out.println("Droite Slip");
		}
		else if (n0.getCoorX()-n1.getCoorX()==0 && n0.getCoorY()-n1.getCoorY()==-1 && this.orientation==3){
			this.orientation=(this.orientation-3)%12;
			nxtComm.sendRequest(slipgauche,3);
			System.out.println("Gauche Slip");
		}
		else if (n0.getCoorX()-n1.getCoorX()==-1 && n0.getCoorY()-n1.getCoorY()==0 && this.orientation==3){
			if (n0.getfCases()==FormeCase.SH){
				nxtComm.sendRequest(slipdroit,3);
				System.out.println("Avancer SlipD");
			}
			else {
				nxtComm.sendRequest(slipgauche,3);
				System.out.println("Avancer SlipG");
			}
			System.out.println("2");
		}
		else if (n0.getCoorX()-n1.getCoorX()==0 && n0.getCoorY()-n1.getCoorY()==1 && this.orientation==3){
			this.orientation=(this.orientation+3)%12;
			nxtComm.sendRequest(slipdroit,3);
			System.out.println("Droite Slip");
		}
		else if (n0.getCoorX()-n1.getCoorX()==0 && n0.getCoorY()-n1.getCoorY()==1 && this.orientation==6){
			if (n0.getfCases()==FormeCase.SG){
				nxtComm.sendRequest(slipgauche,3);
				System.out.println("Avancer SlipG");
			}
			else{
				nxtComm.sendRequest(slipdroit,3);
				System.out.println("Avancer SlipD");
			}
			System.out.println("3");
		}
		else if (n0.getCoorX()-n1.getCoorX()==1 && n0.getCoorY()-n1.getCoorY()==0 && this.orientation==6){
			this.orientation=(this.orientation+3)%12;
			nxtComm.sendRequest(slipdroit,3);
			System.out.println("Droite Slip");
		}
		else if (n0.getCoorX()-n1.getCoorX()==-1 && n0.getCoorY()-n1.getCoorY()==0 && this.orientation==6){
			this.orientation=(this.orientation-3)%12;
			nxtComm.sendRequest(slipgauche,3);
			System.out.println("Gauche Slip");
		}
		else if (n0.getCoorX()-n1.getCoorX()==0 && n0.getCoorY()-n1.getCoorY()==-1 && this.orientation==9){
			this.orientation=(this.orientation+3)%12;
			nxtComm.sendRequest(slipdroit,3);
			System.out.println("Droite Slip");
		}
		else if (n0.getCoorX()-n1.getCoorX()==1 && n0.getCoorY()-n1.getCoorY()==0 && this.orientation==9){
			if (n0.getfCases()==FormeCase.SH){
				nxtComm.sendRequest(slipgauche,3);
				System.out.println("Avancer SlipG");
			}
			else {
				nxtComm.sendRequest(slipdroit,3);
				System.out.println("Avancer SlipD");
			}
			System.out.println("4");
		}
		else if (n0.getCoorX()-n1.getCoorX()==0 && n0.getCoorY()-n1.getCoorY()==1 && this.orientation==9){
			this.orientation=(this.orientation-3)%12;
			nxtComm.sendRequest(slipgauche,3);
			System.out.println("Gauche Slip");
		}
		else{
			System.out.println("Il y a une erreur");
		}
	}
	
	public void deplacementManuel(String deplacement) throws IOException{
		int axeX;
		int axeY;
		Node nodeTemporaire=this.nodeCourante;
		if (deplacement.equals("avancer")){
			if (this.orientation==0){
				axeX=this.nodeCourante.getCoorX();
				axeY=this.nodeCourante.getCoorY()+1;
				for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
					if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
						nodeTemporaire=nodePoids.getKey();
					}
				}
				if (nodeTemporaire.equals(this.nodeCourante)){
					System.out.println("Vous ne pouvez pas réaliser cette action , vous allez quitter le circuit");
				}
				else{
					if (this.nodeCourante.getfCases()==FormeCase.SG){
						nxtComm.sendRequest(slipdroit,3);
					}
					else if (this.nodeCourante.getfCases()==FormeCase.SD){
						nxtComm.sendRequest(slipgauche,3);
					}
					else{
						nxtComm.sendRequest(avancer,3);
					}
					System.out.println("avancer");
				}
			}
			else if (this.orientation==6){
				axeX=this.nodeCourante.getCoorX();
				axeY=this.nodeCourante.getCoorY()-1;
				for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
					if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
						nodeTemporaire=nodePoids.getKey();
					}
				}
				if (nodeTemporaire.equals(this.nodeCourante)){
					System.out.println("Vous ne pouvez pas réaliser cette action , vous allez quitter le circuit");
				}
				else{
					if (this.nodeCourante.getfCases()==FormeCase.SG){
						nxtComm.sendRequest(slipgauche,3);
					}
					else if (this.nodeCourante.getfCases()==FormeCase.SD){
						nxtComm.sendRequest(slipdroit,3);
					}
					else{
						nxtComm.sendRequest(avancer,3);
					}
					System.out.println("avancer");
				}
			}
			else if (this.orientation==3){
				axeX=this.nodeCourante.getCoorX()+1;
				axeY=this.nodeCourante.getCoorY();
				for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
					if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
						nodeTemporaire=nodePoids.getKey();
					}
				}
				if (nodeTemporaire.equals(this.nodeCourante)){
					System.out.println("Vous ne pouvez pas réaliser cette action , vous allez quitter le circuit");
				}
				else{
					if (this.nodeCourante.getfCases()==FormeCase.SH){
						nxtComm.sendRequest(slipdroit,3);
					}
					else if (this.nodeCourante.getfCases()==FormeCase.SB){
						nxtComm.sendRequest(slipgauche,3);
					}
					else{
						nxtComm.sendRequest(avancer,3);
					}
					System.out.println("avancer");
				}
			}
			else if (this.orientation==9){
				axeX=this.nodeCourante.getCoorX()-1;
				axeY=this.nodeCourante.getCoorY();
				for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
					if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
						nodeTemporaire=nodePoids.getKey();
					}
				}
				if (nodeTemporaire.equals(this.nodeCourante)){
					System.out.println("Vous ne pouvez pas réaliser cette action , vous allez quitter le circuit");
				}
				else{
					if (this.nodeCourante.getfCases()==FormeCase.SH){
						nxtComm.sendRequest(slipgauche,3);
					}
					else if (this.nodeCourante.getfCases()==FormeCase.SB){
						nxtComm.sendRequest(slipdroit,3);
					}
					else{
						nxtComm.sendRequest(avancer,3);
					}
				}
			}
			
		}
		else if (deplacement.equals("gauche")){
			if (this.orientation==6){
				if (this.nodeCourante.getfCases()==FormeCase.VHD || this.nodeCourante.getfCases()==FormeCase.SD || this.nodeCourante.getfCases()==FormeCase.SH){
					axeX=this.nodeCourante.getCoorX()+1;
					axeY=this.nodeCourante.getCoorY();
					for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
						if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
							nodeTemporaire=nodePoids.getKey();
						}
					}
					if (nodeTemporaire.equals(this.nodeCourante)){
						System.out.println("Vous ne pouvez pas réaliser cette action , vous allez quitter le circuit");
					}
					else{
						if (this.nodeCourante.getfCases()==FormeCase.VHD){
							nxtComm.sendRequest(avancer,3);
							System.out.println("Gauche");
						}
						else{
							nxtComm.sendRequest(slipgauche,3);
							System.out.println("GaucheS");
						}
						this.orientation=3;
					}
				}
			}
			else if (this.orientation==3){
				if (this.nodeCourante.getfCases()==FormeCase.VHG || this.nodeCourante.getfCases()==FormeCase.SG || this.nodeCourante.getfCases()==FormeCase.SH){
					axeX=this.nodeCourante.getCoorX();
					axeY=this.nodeCourante.getCoorY()+1;
					for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
						if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
							nodeTemporaire=nodePoids.getKey();
						}
					}
					if (nodeTemporaire.equals(this.nodeCourante)){
						System.out.println("Vous ne pouvez pas réaliser cette action , vous allez quitter le circuit");
					}
					else{
						if (this.nodeCourante.getfCases()==FormeCase.VHG){
							nxtComm.sendRequest(avancer,3);
							System.out.println("Gauche");
						}
						else{
							nxtComm.sendRequest(slipgauche,3);
							System.out.println("GaucheS");
						}
						this.orientation=0;
					}
				}
			}
			else if (this.orientation==0){
				if (this.nodeCourante.getfCases()==FormeCase.VBG || this.nodeCourante.getfCases()==FormeCase.SG || this.nodeCourante.getfCases()==FormeCase.SB){
					axeX=this.nodeCourante.getCoorX()-1;
					axeY=this.nodeCourante.getCoorY();
					for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
						if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
							nodeTemporaire=nodePoids.getKey();
						}
					}
					if (nodeTemporaire.equals(this.nodeCourante)){
						System.out.println("Vous ne pouvez pas réaliser cette action , vous allez quitter le circuit");
					}
					else{
						if (this.nodeCourante.getfCases()==FormeCase.VBG){
							nxtComm.sendRequest(avancer,3);
							System.out.println("Gauche");
						}
						else{
							nxtComm.sendRequest(slipgauche,3);
							System.out.println("GaucheS");
						}
						this.orientation=9;
					}
				}
			}
			else if (this.orientation==9){
				if (this.nodeCourante.getfCases()==FormeCase.VBD || this.nodeCourante.getfCases()==FormeCase.SD || this.nodeCourante.getfCases()==FormeCase.SB){
					axeX=this.nodeCourante.getCoorX();
					axeY=this.nodeCourante.getCoorY()-1;
					for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
						if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
							nodeTemporaire=nodePoids.getKey();
						}
					}
					if (nodeTemporaire.equals(this.nodeCourante)){
						System.out.println("Vous ne pouvez pas réaliser cette action , vous allez quitter le circuit");
					}
					else{
						if (this.nodeCourante.getfCases()==FormeCase.VBD){
							nxtComm.sendRequest(avancer,3);
							System.out.println("Gauche");
							
						}
						else{
							nxtComm.sendRequest(slipgauche,3);
							System.out.println("GaucheS");
						}
						this.orientation=6;
					}
				}
			}	
		}
		else if (deplacement.equals("droite")){
			if (this.orientation==6){
				if (this.nodeCourante.getfCases()==FormeCase.VHG || this.nodeCourante.getfCases()==FormeCase.SG || this.nodeCourante.getfCases()==FormeCase.SH){
					axeX=this.nodeCourante.getCoorX()-1;
					axeY=this.nodeCourante.getCoorY();
					for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
						if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
							nodeTemporaire=nodePoids.getKey();
						}
					}
					if (nodeTemporaire.equals(this.nodeCourante)){
						System.out.println("Vous ne pouvez pas réaliser cette action , vous allez quitter le circuit");
					}
					else{
						if (this.nodeCourante.getfCases()==FormeCase.VHG){
							nxtComm.sendRequest(avancer,3);
							System.out.println("Droite");
						}
						else{
							nxtComm.sendRequest(slipdroit,3);
							System.out.println("DroiteS");
						}
						this.orientation=9;
					}
				}
			}
			else if (this.orientation==3){
				if (this.nodeCourante.getfCases()==FormeCase.VBG || this.nodeCourante.getfCases()==FormeCase.SG || this.nodeCourante.getfCases()==FormeCase.SB){
					axeX=this.nodeCourante.getCoorX();
					axeY=this.nodeCourante.getCoorY()-1;
					for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
						if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
							nodeTemporaire=nodePoids.getKey();
						}
					}
					if (nodeTemporaire.equals(this.nodeCourante)){
						System.out.println("Vous ne pouvez pas réaliser cette action , vous allez quitter le circuit");
					}
					else{
						if (this.nodeCourante.getfCases()==FormeCase.VBG){
							nxtComm.sendRequest(avancer,3);
							System.out.println("Droite");
						}
						else{
							nxtComm.sendRequest(slipdroit,3);
							System.out.println("DroiteS");
						}
						this.orientation=6;
					}
				}
			}
			else if (this.orientation==0){
				if (this.nodeCourante.getfCases()==FormeCase.VBD || this.nodeCourante.getfCases()==FormeCase.SD || this.nodeCourante.getfCases()==FormeCase.SB){
					axeX=this.nodeCourante.getCoorX()+1;
					axeY=this.nodeCourante.getCoorY();
					for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
						if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
							nodeTemporaire=nodePoids.getKey();
						}
					}
					if (nodeTemporaire.equals(this.nodeCourante)){
						System.out.println("Vous ne pouvez pas réaliser cette action , vous allez quitter le circuit");
					}
					else{
						if (this.nodeCourante.getfCases()==FormeCase.VBD){
							nxtComm.sendRequest(avancer,3);
							System.out.println("Droite");
						}
						else{
							nxtComm.sendRequest(slipdroit,3);
							System.out.println("DroiteS");
						}
						this.orientation=3;
					}
				}
			}
			else if (this.orientation==9){
				if (this.nodeCourante.getfCases()==FormeCase.VHD || this.nodeCourante.getfCases()==FormeCase.SD || this.nodeCourante.getfCases()==FormeCase.SH){
					axeX=this.nodeCourante.getCoorX();
					axeY=this.nodeCourante.getCoorY()+1;
					for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
						if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
							nodeTemporaire=nodePoids.getKey();
						}
					}
					if (nodeTemporaire.equals(this.nodeCourante)){
						System.out.println("Vous ne pouvez pas réaliser cette action , vous allez quitter le circuit");
					}
					else{
						if (this.nodeCourante.getfCases()==FormeCase.VHD){
							nxtComm.sendRequest(avancer,3);
							System.out.println("Droite");
						}
						else{
							nxtComm.sendRequest(slipdroit,3);
							System.out.println("DroiteS");
						}
						this.orientation=0;
					}
				}
			}	
		}
		else if (deplacement.equals("demitour")){
			nxtComm.sendRequest(demitour,3);
			System.out.println("demitour");
			switch(this.orientation){
				case 0:
					axeX=this.nodeCourante.getCoorX();
					axeY=this.nodeCourante.getCoorY()-1;
					for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
						if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
							nodeTemporaire=nodePoids.getKey();
						}
					}
					break;
				case 3:
					axeX=this.nodeCourante.getCoorX()-1;
					axeY=this.nodeCourante.getCoorY();
					for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
						if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
							nodeTemporaire=nodePoids.getKey();
						}
					}
					break;
				case 6:
					axeX=this.nodeCourante.getCoorX();
					axeY=this.nodeCourante.getCoorY()+1;
					for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
						if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
							nodeTemporaire=nodePoids.getKey();
						}
					}
					break;
				case 9:
					axeX=this.nodeCourante.getCoorX()+1;
					axeY=this.nodeCourante.getCoorY();
					for (Map.Entry<Node, Integer> nodePoids : this.nodeCourante.getAdjacentNodes().entrySet()){
						if ((nodePoids.getKey().getCoorX()==axeX) && (nodePoids.getKey().getCoorY()==axeY)){
							nodeTemporaire=nodePoids.getKey();
						}
					}
					break;
			}	
			this.orientation=(this.orientation+6)%12;
		}
		this.nodeCourante=nodeTemporaire;
	}	
}
	