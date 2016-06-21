package generation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import falstad.StubOrder;
import generation.Order.Builder;
import generation.MazeConfiguration;

/**
 * This class has the responsibility of testing the MazeFactory class.
 * It will test all of the methods in the class, as well as some additional properties of the maze
 * to make sure that MazeBuilder is running correctly
 * 
 * @author corythomas
 *
 */

public class MazeFactoryTest {
	
	private boolean deterministic = true;
	private MazeFactory mf1;
	private MazeFactory mf2;
	private Order stub1;
	private Order stub2;
	private MazeConfiguration mazeConfiguration;
	private Cells cells;
	private Distance distance;
	
	
	/**
	 * This method helps to set up all of the fields that will be needed for the tests.
	 * Two MazeFactory objects were initialized, one to test each of the different constructors, and two stubs were
	 * created, one for each of the constructors.
	 */
	@Before
	public void setUp() {
		mf1 = new MazeFactory();
		mf2 = new MazeFactory(deterministic);
		stub1 = new StubOrder();
		stub2 = new StubOrder(3, Order.Builder.Prim);
	}
	
	/**
	 * This method will test to make sure that the constructor for MazeFactory creates an object.
	 */
	@Test
	public void testMazeFactory() {
		assertNotNull(mf1);
	}
	
	/**
	 * This test makes sure that the constructor that allows for a boolean input creates an object.
	 */
	@Test
	public void testMazeFactoryBoolean() {
		assertNotNull(mf2);
	}

	/**
	 * This will test the order method in the MazeFactory java class.
	 */
	@Test
	public void testOrder() {
		// make sure the order method returns true when a valid input is entered
		assertTrue(mf1.order(stub1));
		
		// test that it does not start another thread until the other one is finished
		mf1.order(stub1);
		assertFalse(mf1.order(stub2));
		
	}
	
	/**
	 * This method tests both of the constructors for the StubOrder class to make sure they are creating StubOrder objects.
	 * It also makes sure that the get methods of the class are working properly.
	 */
	@Test
	public void testStubOrder() {
		// test the StubOrder constructor when no arguments are given
		assertEquals(0, stub1.getSkillLevel());
		assertEquals(Builder.DFS, stub1.getBuilder());
		assertEquals(true, stub1.isPerfect());
		
		// test the StubOrder constructor when arguments are given
		assertEquals(3, stub2.getSkillLevel());
		assertEquals(Builder.Prim, stub2.getBuilder());
		assertEquals(true, stub2.isPerfect());
	}

	/**
	 * This tests the cancel method in the MazeFactory class to make sure it cancels the thread and sets
	 * the variables to null.
	 */
	@Test
	public void testCancel() {
		// make sure both the builder and the currentOrder are set to null
		mf1.order(stub1);
		mf1.cancel();
		
		assertNull(mf1.builder);
		assertNull(mf1.currentOrder);
		
	}

	/**
	 * This method tests to make sure that when the waitTillDelivered method from the MazeFactory class is called
	 * everything else is not done until the thread finishes and returns the values.
	 */
	@Test
	public void testWaitTillDelivered() {
		// make sure both the builder and the currentOrder are set to null
		mf1.order(stub1);
		mf1.waitTillDelivered();
		
		assertNull(mf1.builder);
		assertNull(mf1.currentOrder);
	}
	
	/**
	 * This test makes sure that the maze has exactly one exit and that that exit is located on the border of the maze.
	 */
	@Test
	public void testIsOneBorderExit() {
		mf1.order(stub1);
		mf1.waitTillDelivered();
		
		mazeConfiguration = ((StubOrder) stub1).getMazeConfiguration();
		cells = mazeConfiguration.getMazecells();
		// test to see if there is only one exit position in the maze
		// do this by looping through every cell in the maze
		// if the cell is an exit add it to a count variable, which should be 1 when done
		// also make sure the exit position is on a border
		boolean isBorder = false;
		int count = 0;
		for (int i=0; i < cells.width; i++) {
			for (int j=0; j < cells.height; j++) {
				if (cells.isExitPosition(i, j)) {
					if ((i - 1 < 0) || (i + 1 >= cells.width) || (j - 1 < 0) || (j + 1 >= cells.height)) {
					isBorder = true;	
					count++;
					}
				}
			}
		}
		assertEquals(1, count);
		assertTrue(isBorder);
	}
	
	/**
	 * This test makes sure that the maze is perfect, meaning there are no loops and that you can access one cell from any other
	 * cell in the maze.
	 */
	@Test
	public void testIsPerfect() {
		mf1.order(stub1);
		mf1.waitTillDelivered();
		
		mazeConfiguration = ((StubOrder) stub1).getMazeConfiguration();
		cells = mazeConfiguration.getMazecells();
		
		// test to see if the maze is a perfect maze
		// if it is, then there are no loops and you can access any point from anywhere else in the maze
		distance = mazeConfiguration.getMazedists();
		
		// set the boolean isPerfect to true
		// then go through every cell to see if any of the distances are equal to zero 
		// if they are, change the isPerfect value to false
		boolean isPerfect = true;
		for (int i = 0; i < distance.width; i++) {
			for (int j = 0; j < distance.height; j++) {
				if (distance.getDistance(i, j) == 0) {
					isPerfect = false;
				}
			}
		}
		
		assertTrue(isPerfect);
	}

}
