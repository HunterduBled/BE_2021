package robotBlue2;


import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import affichage.Telecommande;
import lejos.pc.comm.NXTCommException;
import graphe.*;


public class Main {

	/**
	 * @param node 
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void eviterRencontreRobotIARobotJoueur(Robot robotIA,Robot robotJ) throws InterruptedException{
		boolean eloigner=false;
		int timer=0;
		for (Map.Entry<Node, Integer> nodePoidsIA : robotIA.getNodeCourante().getAdjacentNodes().entrySet()){
			for (Map.Entry<Node, Integer> nodePoidsJ : robotJ.getNodeCourante().getAdjacentNodes().entrySet()){
				if ((nodePoidsIA.getKey().equals(nodePoidsJ.getKey())) || robotJ.getNodeCourante().equals(robotIA.getNodeCourante()) || nodePoidsIA.getKey().equals(robotJ.getNodeCourante()) || nodePoidsJ.getKey().equals(robotIA.getNodeCourante()) ){
					eloigner=true;
				}
			}
			
		}
		while(eloigner && timer<5){
			for (Map.Entry<Node, Integer> nodePoidsIA : robotIA.getNodeCourante().getAdjacentNodes().entrySet()){
				for (Map.Entry<Node, Integer> nodePoidsJ : robotJ.getNodeCourante().getAdjacentNodes().entrySet()){
					if ((nodePoidsIA.getKey().equals(nodePoidsJ.getKey())) || robotJ.getNodeCourante().equals(robotIA.getNodeCourante()) || nodePoidsIA.getKey().equals(robotJ.getNodeCourante()) || nodePoidsJ.getKey().equals(robotIA.getNodeCourante())){
						eloigner=true;
						break;
					}
					else{
						eloigner=false;
					}
				}
				
			}
//			TimeUnit.SECONDS.sleep(2);
//			timer++;
		}
//		if (timer<5){
//			return false;
//		}
//		else{
//			for (Map.Entry<Node, Integer> nodePoids : robotIA.getNodeCourante().getAdjacentNodes().entrySet()){
//				if ((nodePoids.getKey().equals(robotJ.getNodeCourante())) || robotJ.getNodeCourante().equals(robotIA.getNodeCourante())){
//						nodePoids.setValue(1000);
//					}
//			}
//			return true;
//		}
		
	}
	
	
	public static boolean deplacerRobot(Graph graph,Node n,Robot robotIA,Robot robotJ) throws IOException, InterruptedException{
		LinkedList<Node> shortestPathTemp = (LinkedList<Node>) n.getShortestPath();
		shortestPathTemp.addLast(n);
		System.out.println("--- Parcours de "+shortestPathTemp.get(0).getName()+" vers "+n.getName()+" ---");
		
		if (robotIA.demiTour(shortestPathTemp.get(0), shortestPathTemp.get(1))){
			shortestPathTemp.removeFirst();
			TimeUnit.SECONDS.sleep(7);
			robotIA.setNodeCourante(shortestPathTemp.get(0));
		}
		
		while (shortestPathTemp.size()>1 && (shortestPathTemp.getLast().gettCases()!=TypeCase.RIEN)){
//			if (eviterRencontreRobotIARobotJoueur(robotIA,robotJ)){
//				break;
//			}
			eviterRencontreRobotIARobotJoueur(robotIA,robotJ);
			System.out.println("- On part de la case "+shortestPathTemp.get(0).getName()+" vers la case "+shortestPathTemp.get(1).getName());
			System.out.println("Type de la case arrive "+shortestPathTemp.getLast().gettCases());
			if (shortestPathTemp.get(0).gettCases() == TypeCase.HOPITAL && robotIA.getNbVictimes() > 0) {
				System.out.println("La ou les  victime(s) a(ont) bien été amené(s) à l'hopital "+shortestPathTemp.get(0).getName());
				robotIA.setNbVictimes(0);
			}
			if (shortestPathTemp.get(0).getfCases()==FormeCase.SB ||shortestPathTemp.get(0).getfCases()==FormeCase.SH ||shortestPathTemp.get(0).getfCases()==FormeCase.SG ||shortestPathTemp.get(0).getfCases()==FormeCase.SD){
				robotIA.deplacementSlip(shortestPathTemp.get(0), shortestPathTemp.get(1));
			}
			else{
				robotIA.deplacement(shortestPathTemp.get(0), shortestPathTemp.get(1));
			}
			TimeUnit.SECONDS.sleep(7);
			shortestPathTemp.removeFirst();	
			robotIA.setNodeCourante(shortestPathTemp.get(0));
		}
		if (shortestPathTemp.size()>1){
			return false;
		}
		else{
			return true;
		}
	}
	
	public static void SelectionDirection(Graph graph,TypeCase cases,Robot robotIA,Robot robotJ) throws IOException, InterruptedException{
		int cheminPlusCourt=Integer.MAX_VALUE;;
		Node nodeTemp = null;
		if (cases==TypeCase.VICTIME){
			for(Node n : graph.getNodes()){
				if(n.gettCases() == cases && n.getShortestPath().size()<cheminPlusCourt){
					nodeTemp=n;
					cheminPlusCourt= n.getShortestPath().size();
				}
			}
			if ((deplacerRobot(graph,nodeTemp,robotIA,robotJ))){
				System.out.println("La victime a bien été récupérée sur la case "+nodeTemp.getName());
				nodeTemp.settCases(TypeCase.RIEN);
				robotIA.setNbVictimes(robotIA.getNbVictimes()+1);
			}
			else{
				System.out.println("La victime a été récupérée par le robot joueur "+nodeTemp.getName());
			}
		}
		else if (cases==TypeCase.HOPITAL){
			for(Node n : graph.getNodes()){
				if(n.gettCases() == cases && n.getShortestPath().size()<cheminPlusCourt){
					nodeTemp=n;
					cheminPlusCourt= n.getShortestPath().size();
				}
			}
			if (deplacerRobot(graph,nodeTemp,robotIA,robotJ)){
				System.out.println("La ou les  victime(s) a(ont) bien été amené(s) à l'hopital "+nodeTemp.getName());
			}
		}
		graph.reset(graph);
		graph.calculateShortestPathFromSource(graph,robotIA.getNodeCourante());
	}
	
	public static boolean Victime(Graph graph){
		boolean victimeASoigner=false;
		for(Node n : graph.getNodes()){
			if(n.gettCases() == TypeCase.VICTIME){
				victimeASoigner=true;
			}
		}
		return victimeASoigner;
	}
		
	public static void main(String[] args) throws IOException, NXTCommException, InterruptedException {
		// TODO Auto-generated method stub
		//final Robot robot= new Robot("FUBUKI",0,2); //nom,orientation,capacitéVictime
//		JPanel jp = new JPanel();
//		JFrame frame = new JFrame ("MyPanel");
		//Telecommande telecommande1= new Telecommande(robot);
		//robot.connexion();
//		final DifferentialPilot pilote = new DifferentialPilot(4.96f,13.0f,robot.getMG(),robot.getMD(),false);
		
		Node nodeH = new Node("nodeH",FormeCase.VHD,TypeCase.VICTIME,0,1);
		Node nodeD = new Node("nodeD",FormeCase.L,TypeCase.RIEN,0,2);
		Node nodeA = new Node("nodeA",FormeCase.VBD,TypeCase.VICTIME,0,3);
		Node nodeL = new Node("nodeL",FormeCase.VHD,TypeCase.RIEN,1,0);
		Node nodeI = new Node("nodeI",FormeCase.SG,TypeCase.HOPITAL,1,1);
		Node nodeE = new Node("nodeE",FormeCase.L,TypeCase.RIEN,1,2);
		Node nodeB = new Node("nodeB",FormeCase.SB,TypeCase.RIEN,1,3);
		Node nodeM = new Node("nodeM",FormeCase.SH,TypeCase.RIEN,2,0);
		Node nodeJ = new Node("nodeJ",FormeCase.L,TypeCase.RIEN,2,1);
		Node nodeF = new Node("nodeF",FormeCase.VHD,TypeCase.RIEN,2,2);
		Node nodeC = new Node("nodeC",FormeCase.SB,TypeCase.RIEN,2,3);
		Node nodeN = new Node("nodeN",FormeCase.VHG,TypeCase.VICTIME,3,0);
		Node nodeK = new Node("nodeK",FormeCase.SD,TypeCase.RIEN,3,1);
		Node nodeZ = new Node("nodeZ",FormeCase.L,TypeCase.RIEN,4,1);
		Node nodeG = new Node("nodeG",FormeCase.SG,TypeCase.HOPITAL,3,2);
		Node nodeO = new Node("nodeO",FormeCase.VBG,TypeCase.VICTIME,3,3);
		
		nodeH.addDestination(nodeD, 1);
		nodeH.addDestination(nodeI, 1);
		nodeD.addDestination(nodeH, 1);
		nodeD.addDestination(nodeA, 1);
		nodeA.addDestination(nodeD, 1);
		nodeA.addDestination(nodeB, 1);
		nodeL.addDestination(nodeI, 1);
		nodeL.addDestination(nodeM, 1);
		nodeI.addDestination(nodeL, 1);
		nodeI.addDestination(nodeH, 1);
		nodeI.addDestination(nodeE, 1);
		nodeE.addDestination(nodeI, 1);
		nodeE.addDestination(nodeB, 1);
		nodeB.addDestination(nodeE, 1);
		nodeB.addDestination(nodeA, 1);
		nodeB.addDestination(nodeC, 1);
		nodeM.addDestination(nodeL, 1);
		nodeM.addDestination(nodeN, 1);
		nodeM.addDestination(nodeJ, 1);
		nodeJ.addDestination(nodeM, 1);
		nodeF.addDestination(nodeC, 1);
		nodeF.addDestination(nodeG, 1);
		nodeC.addDestination(nodeF, 1);
		nodeC.addDestination(nodeB, 1);
		nodeC.addDestination(nodeO, 1);
		nodeN.addDestination(nodeM, 1);
		nodeN.addDestination(nodeK, 1);
		nodeK.addDestination(nodeN, 1);
		nodeK.addDestination(nodeG, 1);
		nodeK.addDestination(nodeZ, 1);
		nodeZ.addDestination(nodeK, 1);
		nodeG.addDestination(nodeK, 1);
		nodeG.addDestination(nodeF, 1);
		nodeG.addDestination(nodeO, 1);
		nodeO.addDestination(nodeC, 1);
		nodeO.addDestination(nodeG, 1);
			
		Graph graph = new Graph();
		
		graph.addNode(nodeA);
		graph.addNode(nodeB);
		graph.addNode(nodeC);
		graph.addNode(nodeD);
		graph.addNode(nodeE);
		graph.addNode(nodeF);
		graph.addNode(nodeG);
		graph.addNode(nodeH);
		graph.addNode(nodeI);
		graph.addNode(nodeJ);
		graph.addNode(nodeK);
		graph.addNode(nodeL);
		graph.addNode(nodeM);
		graph.addNode(nodeN);
		graph.addNode(nodeO);
		
		Node nodeDepart = nodeJ;
		final Robot robotIA= new Robot("FUBUKI",6,2,nodeJ);
		final Robot robotJ= new Robot("Say my n",0,2,nodeG); //nom,orientation,capacitéVictime,nodeDépart
		Telecommande telecommande1= new Telecommande(robotJ);
		robotJ.connexion();
		robotIA.connexion();
	
		graph.calculateShortestPathFromSource(graph,nodeDepart);
		telecommande1.getFrame().setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		telecommande1.getFrame().getContentPane().add (telecommande1.getJp());
		telecommande1.getFrame().pack();
        telecommande1.getFrame().setVisible (true);
		
		while (Victime(graph)){
			while(robotIA.getNbVictimes()<robotIA.getTailleCamion() && Victime(graph)){
				SelectionDirection(graph,TypeCase.VICTIME,robotIA,robotJ);
			}
			if (robotIA.getNbVictimes()==robotIA.getTailleCamion()){
				System.out.println("Le camion est plein , il doit aller déposer les victimes");
			}
			robotIA.setNbVictimes(0);
			SelectionDirection(graph,TypeCase.HOPITAL,robotIA,robotJ);
		}
				
	
	
		
		System.out.println("\" FUBUKI \" a fini son service, la Capsule Corp n'est en rien responsable des dommages occasionnés durant le transport");
	}
}
