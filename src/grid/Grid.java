package grid;

import engine.GameObject;

public class Grid {
	
	private int rows, cols;
	private int[][] myGrid;
	
	public Grid(int xDimension, int yDimension){
		rows = xDimension;
		cols = yDimension;
		myGrid = new int[xDimension][yDimension];
	}
	
	public GameObject getGameObject(int x, int y){
		return null;
		
	}
}
