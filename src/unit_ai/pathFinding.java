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
                for (Coordinate neighbor : openStack.peek().getNeighbors()) {
                    //Do some stuff, not really sure
                    return openStack;
                }
            }
            closedStack.push(node);
        }
        return openStack;
    }
}
