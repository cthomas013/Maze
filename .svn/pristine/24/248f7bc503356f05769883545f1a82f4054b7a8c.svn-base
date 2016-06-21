package generation;

import static org.junit.Assert.*;
import falstad.StubOrder;
import generation.MazeFactory;


import org.junit.Before;
import org.junit.Test;

/**
 * This class will have the responsibility of testing the MazeBuilderKruskal class to make sure that its specific methods 
 * work properly.
 * 
 * @author corythomas
 *
 */

public class MazeBuilderKruskalTest {
	
	private MazeBuilderKruskal maze1;
	private MazeBuilderKruskal maze2;
	private MazeFactory mf;
	private Order stub;
	private MazeConfiguration mazeConfig;

	/**
	 * This is used to setup objects that will be needed for testing purposes.
	 * Two new MazeBuilderKruskal objects are created to test the constructor in the MazeBuilderKruskal class.
	 */
	@Before
	public void setUp() {
		maze1 = new MazeBuilderKruskal();
		maze2 = new MazeBuilderKruskal(true);
		mf = new MazeFactory();
		stub = new StubOrder(0, Order.Builder.Kruskal);
	}

	/**
	 * This test will test to make sure that the constructor in the MazeBuilderKruskal class is actually 
	 * creating a new object.
	 */
	@Test
	public void testMazeBuilderKruskal() {
		assertNotNull(maze1);
		assertNotNull(maze2);
	}
	
	/**
	 * This test will make sure that the generatePathways method is working properly. If it is
	 * working correctly, all the values of the array should be equal.
	 */
	@Test
	public void testGeneratePathways() {
		maze1.buildOrder(stub);
		maze1.run();
		
		// get a count variable to see how many different values there are in the cells
		int differentCount = 0;
		// get the current value that all should be equal to
		int rightVal = maze1.values[0][0];
		boolean allEqual = false;
		for (int i = 0; i < maze1.values.length; i++) {
			for (int j = 0; j < maze1.values.length; j++) {
				if (rightVal == maze1.values[i][j]) {
					allEqual = true;
				}
				else {
					allEqual = false;
					differentCount++;
				}
			}
		}
		// check to make sure the different counter is still 
		if (differentCount != 0) {
			allEqual = false;
		}
		assertTrue(allEqual);
	}
}
