package unit_ai;

import grid.Coordinate;
import java.util.List;

/**
 * Nodes are used as data structures to make pathfinding easier by holding more
 * data than just coordinates. However the integral piece of this data structure
 * is the coordinate, as this is what the path is being made from.
 * 
 * @author carlosreyes
 * 
 */
public class Node {
	private List<Node> myNeighbors;
	private Node myParent;
	private int myLength;
	private int myDistanceToGoal;
	private Coordinate myCoordinate;

	public Node(List<Node> neighbors, Coordinate coordinate) {
		this.myNeighbors = neighbors;
		this.myCoordinate = coordinate;
	}

	public List<Node> getNeighbors() {
		return myNeighbors;
	}

	public void setNeighbors(List<Node> myNeighbors) {
		this.myNeighbors = myNeighbors;
	}

	public Node getParent() {
		return myParent;
	}

	public void setParent(Node myParent) {
		this.myParent = myParent;
	}

	public int getLength() {
		return myLength;
	}

	public void setLength(int myLength) {
		this.myLength = myLength;
	}

	public int getDistanceToGoal() {
		return myDistanceToGoal;
	}

	public void setDistanceToGoal(int myDistanceToGoal) {
		this.myDistanceToGoal = myDistanceToGoal;
	}

	public Coordinate getCoordinate() {
		return myCoordinate;
	}

	public void setCoordinate(Coordinate myCoordinate) {
		this.myCoordinate = myCoordinate;
	}

}
