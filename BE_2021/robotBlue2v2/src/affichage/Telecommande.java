package affichage;

import graphe.FormeCase;
import graphe.TypeCase;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import robotBlue2.Robot;

public class Telecommande {
	
	private JButton jcompA;
    private JButton jcompDT;
    private JButton jcompG;
    private JButton jcompD;
    private JButton jcompR;
    private JButton jcompDE;
    JPanel jp=new JPanel();
    JFrame frame= new JFrame ("MyPanel");
    private Robot robot;
    
    public Telecommande(final Robot robot){
    	this.robot=robot;
    	//construct components
    	
    	jcompG = new JButton ("Gauche");
        jcompDT = new JButton ("Demi-Tour");
        jcompA = new JButton ("Avancer");
        jcompD = new JButton ("Droite");
        jcompR = new JButton ("Récupérer");
        jcompDE = new JButton ("Déposer");
    	//adjust size and set layout
    	jp.setPreferredSize (new Dimension (400, 296));
    	jp.setLayout (null);

    	//add components
    	jp.add (jcompA);
    	jp.add (jcompD);
    	jp.add (jcompG);
    	jp.add (jcompDT);
    	jp.add (jcompR);
    	jp.add (jcompDE);

    	//set component bounds (only needed by Absolute Positioning)
    	jcompG.setBounds (40, 80, 100, 55);
    	jcompD.setBounds (250, 80, 100, 55);
    	jcompA.setBounds (145, 25, 100, 55);
    	jcompDT.setBounds (145, 135, 100, 55);
    	jcompR.setBounds (10, 235, 175, 55);
    	jcompDE.setBounds (210, 235, 175, 55);
    	
    	griserBouton();
    	
    	jcompA.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	        	   try {
	        		   System.out.println(robot.getNodeCourante().getName());
	        		   robot.deplacementManuel("avancer");
	        		   System.out.println("orientation du robot est "+robot.getOrientation());
	        		   System.out.println(robot.getNodeCourante().getName());
	        		   griserBouton();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	           }
	        });
    	jcompG.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	        	   try {
	        		   System.out.println(robot.getNodeCourante().getName());
	        		   robot.deplacementManuel("gauche");
	        		   System.out.println("orientation du robot est "+robot.getOrientation());
	        		   System.out.println(robot.getNodeCourante().getName());
	        		   griserBouton();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	           }
	        });
     jcompD.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	        	   try {
	        		   System.out.println(robot.getNodeCourante().getName());
	        		   robot.deplacementManuel("droite");
	        		   System.out.println("orientation du robot est "+robot.getOrientation());
	        		   System.out.println(robot.getNodeCourante().getName());
	        		   griserBouton();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	           }
	        });
     jcompDT.addActionListener(new ActionListener() {
	           public void actionPerformed(ActionEvent e) {
	        	   try {
	        		   System.out.println(robot.getNodeCourante().getName());
	        		   robot.deplacementManuel("demitour");
	        		   System.out.println("orientation du robot est "+robot.getOrientation());
	        		   System.out.println(robot.getNodeCourante().getName());
	        		   griserBouton();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	           }
	        });
     jcompR.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
      	   System.out.println(robot.getNodeCourante().getName());
      	   ramasserVictimes();
		   System.out.println("orientation du robot est "+robot.getOrientation());
		   System.out.println(robot.getNodeCourante().getName());
		   griserBouton();
		   
         }
      });
     jcompDE.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
      	   System.out.println(robot.getNodeCourante().getName());
		   deposerVictimes();
		   System.out.println("orientation du robot est "+robot.getOrientation());
		   System.out.println(robot.getNodeCourante().getName());
		   griserBouton();
         }
      });
    }
    
    public Robot getRobot() {
		return robot;
	}
    
    
    public JPanel getJp() {
		return jp;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void griserBouton(){
    	if (this.robot.getNodeCourante().getfCases()==FormeCase.L){
    		jcompG.setEnabled(false);
        	jcompD.setEnabled(false);
        	jcompA.setEnabled(true);
    	}
    	else if (this.robot.getNodeCourante().getfCases()==FormeCase.VBG){
    		if (this.robot.getOrientation()==0){
    			jcompD.setEnabled(false);
        		jcompA.setEnabled(false);
        		jcompG.setEnabled(true);
    		}
    		else if (this.robot.getOrientation()==3){
    			jcompG.setEnabled(false);
        		jcompA.setEnabled(false);
        		jcompD.setEnabled(true);
    		}
    		else{
    			System.out.println("Il y a une erreur");
    		}
    	}
    	else if (this.robot.getNodeCourante().getfCases()==FormeCase.VBD){
    		if (this.robot.getOrientation()==0){
    			jcompG.setEnabled(false);
        		jcompA.setEnabled(false);
        		jcompD.setEnabled(true);
    		}
    		else if (this.robot.getOrientation()==9){
    			jcompD.setEnabled(false);
        		jcompA.setEnabled(false);
        		jcompG.setEnabled(true);
    		}
    		else{
    			System.out.println("Il y a une erreur");
    		}
    	}
    	else if (this.robot.getNodeCourante().getfCases()==FormeCase.VHD){
    		if (this.robot.getOrientation()==6){
    			jcompD.setEnabled(false);
        		jcompA.setEnabled(false);
        		jcompG.setEnabled(true);
    		}
    		else if (this.robot.getOrientation()==9){
    			jcompG.setEnabled(false);
        		jcompA.setEnabled(false);
        		jcompD.setEnabled(true);
    		}
    		else{
    			System.out.println("Il y a une erreur");
    		}
    	}
    	else if (this.robot.getNodeCourante().getfCases()==FormeCase.VHG){
    		if (this.robot.getOrientation()==6){
    			jcompG.setEnabled(false);
        		jcompA.setEnabled(false);
        		jcompD.setEnabled(true);
    		}
    		else if (this.robot.getOrientation()==3){
    			jcompD.setEnabled(false);
        		jcompA.setEnabled(false);
        		jcompG.setEnabled(true);
    		}
    		else{
    			System.out.println("Il y a une erreur");
    		}
    	}
    	else if (this.robot.getNodeCourante().getfCases()==FormeCase.SH){
    		if (this.robot.getOrientation()==6){
    			jcompG.setEnabled(true);
        		jcompA.setEnabled(false);
        		jcompD.setEnabled(true);
    		}
    		else if (this.robot.getOrientation()==3){
    			jcompG.setEnabled(true);
        		jcompA.setEnabled(true);
        		jcompD.setEnabled(false);
    		}
    		else if (this.robot.getOrientation()==9){
    			jcompG.setEnabled(false);
        		jcompA.setEnabled(true);
        		jcompD.setEnabled(true);
    		}
    		else{
    			System.out.println("il y a une erreur");
    		}
    	}
    	else if (this.robot.getNodeCourante().getfCases()==FormeCase.SB){
    		if (this.robot.getOrientation()==0){
    			jcompG.setEnabled(true);
        		jcompA.setEnabled(false);
        		jcompD.setEnabled(true);
    		}
    		else if (this.robot.getOrientation()==3){
    			jcompG.setEnabled(false);
        		jcompA.setEnabled(true);
        		jcompD.setEnabled(true);
    		}
    		else if (this.robot.getOrientation()==9){
    			jcompG.setEnabled(true);
        		jcompA.setEnabled(true);
        		jcompD.setEnabled(false);
    		}
    		else{
    			System.out.println("Il y a une erreur");
    		}
    	}
    	else if (this.robot.getNodeCourante().getfCases()==FormeCase.SG){
    		if (this.robot.getOrientation()==0){
    			jcompG.setEnabled(true);
        		jcompA.setEnabled(true);
        		jcompD.setEnabled(false);
    		}
    		else if (this.robot.getOrientation()==3){
    			jcompG.setEnabled(true);
        		jcompA.setEnabled(false);
        		jcompD.setEnabled(true);
    		}
    		else if (this.robot.getOrientation()==6){
    			jcompG.setEnabled(false);
        		jcompA.setEnabled(true);
        		jcompD.setEnabled(true);
    		}
    		else{
    			System.out.println("Il y a une erreur");
    		}
    	}
    	else if (this.robot.getNodeCourante().getfCases()==FormeCase.SD){
    		if (this.robot.getOrientation()==0){
    			jcompG.setEnabled(false);
        		jcompA.setEnabled(true);
        		jcompD.setEnabled(true);
    		}
    		else if (this.robot.getOrientation()==9){
    			jcompG.setEnabled(true);
        		jcompA.setEnabled(false);
        		jcompD.setEnabled(true);
    		}
    		else if (this.robot.getOrientation()==6){
    			jcompG.setEnabled(true);
        		jcompA.setEnabled(true);
        		jcompD.setEnabled(false);
    		}
    		else{
    			System.out.println("Il y a une erreur");
    		}
    	}
    	else{
    		System.out.println("Il y a une erreur");
    	}
    	if (this.robot.getNodeCourante().gettCases()==TypeCase.VICTIME){
    		jcompR.setEnabled(true);
    		jcompDE.setEnabled(false);
    	}
    	else if (this.robot.getNodeCourante().gettCases()==TypeCase.HOPITAL){
    		jcompDE.setEnabled(true);
    		jcompR.setEnabled(false);
    	}
    	else{
    		jcompR.setEnabled(false);
    		jcompDE.setEnabled(false);
    	}
    }
	public void ramasserVictimes(){
		if (this.robot.getNodeCourante().gettCases()==TypeCase.VICTIME && (this.robot.getTailleCamion()>this.robot.getNbVictimes())){
			robot.setNbVictimes(robot.getNbVictimes()+1);
			robot.getNodeCourante().settCases(TypeCase.RIEN);
			System.out.println("Le robot joueur à ramasser une victime");	
		}
		else{
			System.out.println("Le camion du robot joueur est plein , il ne peut pas ramasser la victime");
		}
	}
	public void deposerVictimes(){
		if (this.robot.getNodeCourante().gettCases()==TypeCase.HOPITAL && (this.robot.getNbVictimes()>0)){
			robot.setNbVictimes(0);
			System.out.println("Le robot joueur à déposer une ou les  victime(s)");	
		}
		else{
			System.out.println("Le camion du robot est vide , aucune victime ne peut être déposé");
		}
	}
	
	

}
