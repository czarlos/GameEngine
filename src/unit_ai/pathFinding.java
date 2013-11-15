package unit_ai;

import java.util.List;
import java.util.Stack;
import grid.Coordinate;

public class pathFinding {
    
    /**
     * Nodes are used as data structures to make pathfinding easier by holding
     * more data than just coordinates. However the integral piece of this data
     * structure is the coordinate, as this is what the path is being made from.
     * @author carlosreyes
     *
     */
    private class Node {
        private List<Node> myNeighbors;
        private Node myParent;
        private int myLength;
        private int myDistanceToGoal;
        private Coordinate myCoordinate;
        
        public Node(List<Node> neighbors, Coordinate coordinate) {
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
                    else if (openStack.contains(neighbor) && node.getLength() < neighbor.getLength()) {
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
}