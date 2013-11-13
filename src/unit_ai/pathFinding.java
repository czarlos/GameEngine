package unit_ai;

import grid.Coordinate;

public class pathFinding {
    
    public void aStar (Coordinate start, Coordinate end) {
        
    }
    //At initialization add the starting location to the open list and empty the closed list

    //While there are still more possible next steps in the open list and we haven’t found the target:
        //Select the most likely next step (based on both the heuristic and path costs)
        //Remove it from the open list and add it to the closed
        //Consider each neighbor of the step. For each neighbor:
            //Calculate the path cost of reaching the neighbor
            //If the cost is less than the cost known for this location then remove it from the open or 
                //closed lists (since we’ve now found a better route)
            //If the location isn’t in either the open or closed list then record the costs for 
                //the location and add it to the open list (this means it’ll be considered in the next search).
                    //Record how we got to this location
}
