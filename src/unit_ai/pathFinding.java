package unit_ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import grid.Coordinate;

public class pathFinding {
    
    public Stack<Coordinate> aStar (Coordinate start, Coordinate end) {
        Stack<Coordinate> openStack = new Stack<>();
        Stack<Coordinate> closedStack = new Stack<>();
        openStack.add(start);
        
        
        for (Coordinate node : openStack) {
            if (node.equals(end)) {
                return openStack;
            }
            else {
                closedStack.add(node);
                for (Coordinate neighbor : node.getNeighbors()) {
                    if (closedStack.contains(neighbor)) {
                        
                    }
                    else if (openStack.contains(neighbor)) {
                        
                    }
                    else {
                        
                    }
                }
                
                
//                move the current node to the closed list and consider all of its neighbors
//                for (each neighbor) {
//                    if (this neighbor is in the closed list and our current g value is lower) {
//                        update the neighbor with the new, lower, g value 
//                        change the neighbor's parent to our current node
//                    }
//                    else if (this neighbor is in the open list and our current g value is lower) {
//                        update the neighbor with the new, lower, g value 
//                        change the neighbor's parent to our current node
//                    }
//                    else this neighbor is not in either the open or closed list {
//                        add the neighbor to the open list and set its g value
//                    }
            }
        }
        return openStack;
    }
}
