package unit_ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import utils.UnitUtilities;
import grid.Coordinate;
import grid.Grid;


public class PathFinding {

    /**
     * Nodes are used as data structures to make pathfinding easier by holding
     * more data than just coordinates. However the integral piece of this data
     * structure is the coordinate, as this is what the path is being made from.
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

        public Node (List<Node> neighbors, Coordinate coordinate) {
            this.myNeighbors = neighbors;
            this.myCoordinate = coordinate;
        }

        public List<Node> getNeighbors () {
            return myNeighbors;
        }

        public void setNeighbors (List<Node> myNeighbors) {
            this.myNeighbors = myNeighbors;
        }

        public Node getParent () {
            return myParent;
        }

        public void setParent (Node myParent) {
            this.myParent = myParent;
        }

        public int getLength () {
            return myLength;
        }

        public void setLength (int myLength) {
            this.myLength = myLength;
        }

        public int getDistanceToGoal () {
            return myDistanceToGoal;
        }

        public void setDistanceToGoal (int myDistanceToGoal) {
            this.myDistanceToGoal = myDistanceToGoal;
        }

        public Coordinate getCoordinate () {
            return myCoordinate;
        }

        public void setCoordinate (Coordinate myCoordinate) {
            this.myCoordinate = myCoordinate;
        }

    }

    public Stack<Node> aStar (Node start, Node end) {
        Stack<Node> openStack = new Stack<>();
        Stack<Node> closedStack = new Stack<>();
        openStack.add(start);

        for (Node node : openStack) {
            if (node.equals(end)) {
                return openStack;
            }
            else {
                closedStack.add(node);
                for (Node neighbor : node.getNeighbors()) {
                    if (closedStack.contains(neighbor) && node.getLength() < neighbor.getLength()) {
                        neighbor.setLength(node.getLength());
                        neighbor.setParent(node);
                    }
                    else if (openStack.contains(neighbor) &&
                             node.getLength() < neighbor.getLength()) {
                        neighbor.setLength(node.getLength());
                        neighbor.setParent(node);
                    }
                    else {
                        neighbor.setLength(node.getLength());
                        openStack.add(neighbor);
                    }
                }
            }
        }
        return openStack;
    }

    /**
     * Makes a list of all nodes on the graph from the grid. This list of nodes
     * does not contain neighbor data, and gets send to the addNeighbors
     * method to
     * 
     * @param grid
     * @return
     */
    public List<Node> coordinatesToNodes (Grid grid) {
        List<Node> nodeList = new ArrayList<Node>();
        for (Coordinate coord : grid.getTileMap().keySet()) {
            nodeList.add(new Node(null, coord));
        }
        return nodeList;
    }

    /**
     * Adds a list of neighboring nodes to every node in the list of nodes in a grid.
     * 
     * @param nodeList
     * @return
     */
    public List<Node> addNeighbors (List<Node> nodeList) {
        List<Node> nodeAdjacencyList = new ArrayList<>();
        for (Node node : nodeList) {
            for (Node otherNode : nodeList) {
                if (isNeighbor(node, otherNode)) {
                    nodeAdjacencyList.add(otherNode);
                }
            }
        }
        return nodeAdjacencyList;
    }

    /**
     * Determines whether or not a node is a neighbor of another node. Calls
     * a utility function calculate length which calculates the distance (delta)
     * between two coordinates. If this distance is less than the square root of two
     * we know that one node is next to another.
     * Note: This way counts diagonally positioned nodes as "next to" if this is not
     * diagonal nodes are not desired, simply change Math.sqrt(2) to 1.
     * 
     * @param node
     * @param otherNode
     * @return
     */
    private boolean isNeighbor (Node node, Node otherNode) {
        double delta =
                UnitUtilities.calculateLength(node.getCoordinate(), otherNode.getCoordinate());
        if (delta <= Math.sqrt(2)) { return true; }
        return false;
    }
}
