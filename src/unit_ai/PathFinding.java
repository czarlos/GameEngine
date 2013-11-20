package unit_ai;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import utils.UnitUtilities;
import gameObject.GameUnit;
import grid.Coordinate;
import grid.Grid;
import grid.Tile;

public class PathFinding {
    
    /**
     * AutoMove moves a unit the appropriate amount of units forward
     * (the max number possible based on that unit's movement stats)
     * whenever it is called. It does so by finding the shortest distance
     * from the unit to the target and moving as close to that target as
     * possible.
     * Example usage: Whenever it is the AI's turn, if it can't attack, move
     * to closest target.
     * @param start
     * @param end
     * @param unit
     */
    public void autoMove (Node start, Node end, GameUnit unit) {
        int range = unit.getStat("movement");
        List<Node> path = findPath(start, end);
        Node newNode = path.get(range);
        unit.setGridPosition(newNode.getCoordinate());
    }
    
    /**
     * nodeGrid must be made from the grid and all of its neighbors must be put in place,
     * start and end must be in the list nodeGrid. A BFS was used and the paths formed are
     * contructed by storing "pointers" to the parents of each node visited, when the end
     * node is found, it simply follows the pointers back to the start node to determine the path.
     * @param start
     * @param end
     * @param nodeGrid
     * @return
     */
    public List<Node> findPath (Node start, Node end) {
        Queue<Node> queue = new LinkedList<Node>();
        List<Node> visited = new ArrayList<Node>();
        List<Node> path = new ArrayList<Node>();

        queue.add(start);
        
        while(!queue.isEmpty()) {
            Node workingNode = queue.poll();
            if (workingNode.equals(end)) {
                while(!workingNode.equals(start)) {
                    path.add(workingNode);
                    workingNode = workingNode.getParent();
                }
                return path;
            }
            else {
                if(!visited.contains(workingNode)) {
                    visited.add(workingNode);
                    for(Node neighbor : workingNode.getNeighbors()) {
                        if(!visited.contains(neighbor)) {
                            neighbor.setParent(workingNode);
                            queue.add(neighbor);  
                        }
                    }
                }
            }
        }
        return path;       
        
    }

    /**
     * Makes a list of all nodes on the graph from the grid. This list of nodes
     * does not contain neighbor data, and gets sent to the addNeighbors
     * method
     * 
     * @param grid
     * @return
     */
    public List<Node> coordinatesToNodes (Grid grid, GameUnit unit) {
        List<Node> nodeList = new ArrayList<Node>();
        for (int i = 0; i < grid.getTiles().length; i++) {
            for (int j = 0; j < grid.getTiles().length; j++) {
                if(grid.getTile(i, j).isPassable(unit))
                    nodeList.add(new Node(null, new Coordinate(i, j)));
            }
        }
        return nodeList;
    }

    /**
     * Adds a list of neighboring nodes to every node in the list of nodes in a grid.
     * 
     * @param nodeList
     */
    public void addNeighbors (List<Node> nodeList) {
        for (Node node : nodeList) {
            List<Node> nodeAdjacencyList = new ArrayList<>();
            for (Node otherNode : nodeList) {
                if (isNeighbor(node, otherNode) && !otherNode.equals(node)) {
                    nodeAdjacencyList.add(otherNode);
                }
            }
            node.setNeighbors(nodeAdjacencyList);
        }
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
