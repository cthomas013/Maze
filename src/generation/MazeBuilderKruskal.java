package generation;

import java.util.ArrayList;
import generation.Wall;

/**
 *
 * This class has the responsibility to generate a maze of given width and height
 * The MazeBuilder implements Runnable such that it can be run a separate thread.
 * The MazeFactory has a MazeBuilder and handles the thread management.
 * 
 * The maze will be generated using Kruskal's algorithm.
 * Kruskal's algorithm works by creating a minimal spanning tree by selecting an edge with the 
 * lowest possible weight and then using that edge to connect two trees in the forest.
 * For the purpose of this class we will use a random generator to decide on the edges because
 * all of them will have the same weight.
 * 
 * @author corythomas
 *
 */
public class MazeBuilderKruskal extends MazeBuilder implements Runnable {
	
	public int[][] values;
	
	public MazeBuilderKruskal() {
		super();
		System.out.println("MazeBuilderKruskal uses Kruskal's algorithm to generate maze.");
	}
	
	public MazeBuilderKruskal(boolean det) {
		super(det);
		System.out.println("MazeBuilderKruskal uses Kruskal's algorithm to generate maze.");
	}
	
	/**
	 * This method generates pathways into the maze by using Kruskal's algorithm to generate a minimal spanning tree for an undirected graph.
	 * The cells are the nodes of the graph and the spanning tree. An edge represents that one can move from one cell to an adjacent cell.
	 * So an edge implies that its nodes are adjacent cells in the maze and that there is no wall separating these cells in the maze. 
	 **/
	@Override
	public void generatePathways() {
		
		final ArrayList<Wall> wallCandidates = new ArrayList<Wall>();
		CardinalDirection[] direction = new CardinalDirection[2];
		
		direction[0] = CardinalDirection.East;
		direction[1] = CardinalDirection.South;
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				Wall wall = new Wall(i, j, CardinalDirection.East);
				for (CardinalDirection cd : direction) {
					wall.setWall(i, j, cd);
					
					if (cells.canGo(wall)) {
						wallCandidates.add(new Wall(i, j, cd));
					}
				}
			}
		}

		// create an array list to hold the values in the cells
		this.values = new int[width][height];
		int count = 1;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				this.values[x][y] = count;
				count++;
			}
		}
		
		// iterate through the list of walls until it becomes empty randomly choosing a wall to see if you can remove it
		while (!wallCandidates.isEmpty()) {
			// get current wall from the exractWall method
			Wall currentWall = extractWallFromCandidateSetRandomly(wallCandidates);
							
				// check to see if the neighbors are in the same set
				if (this.values[currentWall.getX()][currentWall.getY()] != this.values[currentWall.getNeighborX()][currentWall.getNeighborY()]) {
					cells.deleteWall(currentWall);
					// loop through the values to change all of the ones with the same old value to the new value
					// assign a current value variable 
					int currVal = this.values[currentWall.getX()][currentWall.getY()];
					for (int i = 0; i < width; i++) {
						for (int j = 0; j < height; j++) {
							if (this.values[i][j] == currVal) {
								this.values[i][j] = this.values[currentWall.getNeighborX()][currentWall.getNeighborY()];
							}
						}
					}
					
				}
		}
	}
	/**
	 * Pick a random position in the list of candidates, remove the candidate from the list and return it
	 * @param candidates
	 * @return candidate from the list, randomly chosen
	 */
	private Wall extractWallFromCandidateSetRandomly(final ArrayList<Wall> candidates) {
		return candidates.remove(random.nextIntWithinInterval(0, candidates.size()-1)); 
	}
	

}
