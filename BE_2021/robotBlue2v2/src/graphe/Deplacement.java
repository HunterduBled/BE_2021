package graphe;


public class Deplacement {
  protected FormeCase fCases;
  protected TypeCase tCases;
  protected int coorX;
  protected int coorY;

public Deplacement(FormeCase fCases,TypeCase tCases,int coorX,int coorY) {
	  super();
	  this.fCases=fCases;
	  this.tCases=tCases;
	  this.coorX=coorX;
	  this.coorY=coorY;
  }
  public FormeCase getfCases() {
	return fCases;
  }
  public TypeCase gettCases() {
	return tCases;
  }
  public int getCoorX() {
	return coorX;
  }
  public int getCoorY() {
	return coorY;
  }
public void settCases(TypeCase tCases) {
	this.tCases = tCases;
}
  
}
