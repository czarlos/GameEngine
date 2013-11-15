package unit_ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import utils.UnitUtilities;
import grid.Coordinate;
import grid.Grid;
import grid.Tile;


public class PathFinding {

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
        for(int i=0; i <grid.getMyTiles().length; i++) {
            for (int j=0; j<grid.getMyTiles().length; j++) {
                nodeList.add(new Node(null, new Coordinate(i, j)));
            }
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
    public boolean isNeighbor (Node node, Node otherNode) {
        double delta =
                UnitUtilities.calculateLength(node.getCoordinate(), otherNode.getCoordinate());
        if (delta <= Math.sqrt(2)) { return true; }
        return false;
    }
}
