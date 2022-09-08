package graphe;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class Node extends Deplacement {
	private String name;
    private LinkedList<Node> shortestPath = new LinkedList<Node>();
    private Integer distance = Integer.MAX_VALUE;
    private Map<Node,Integer> adjacentNodes = new HashMap<Node,Integer>();

    public Node(String name,FormeCase fCases,TypeCase tCases,int coorX,int coorY) {
    	super(fCases, tCases, coorX, coorY);
        this.name = name;
    }
    
    public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public Map<Node, Integer> getAdjacentNodes() {
		return adjacentNodes;
	}

	public Integer getDistance() {
		return distance;
	}

	public LinkedList<Node> getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(LinkedList<Node> shortestPath) {
		this.shortestPath = shortestPath;
	}

	public String getName() {
		return name;
	}

	public void addDestination(Node destination, Integer position) {
        adjacentNodes.put(destination, position);
    }	
}
