package falstad;

import static org.junit.Assert.*;
import generation.CardinalDirection;

import org.junit.Before;
import org.junit.Test;

import falstad.Robot.Direction;
import falstad.Robot.Turn;
import generation.Cells;
import generation.MazeConfiguration;
import generation.MazeFactory;
import generation.Order;

/**
 * This class will have the responsibility of testing the BasicRobot class to make sure that its specific methods 
 * work properly.
 * 
 * @author corythomas
 *
 */

public class BasicRobotTest {

	private BasicRobot robot;
	private MazeController maze;
	private CardinalDirection cd;
	private Cells cells;
	private MazeConfiguration mazeConfig;
	private MazeFactory mf;
	private Order stub;
	
	/**
	 * This method will set up all the objects that will be needed and used in the test cases
	 */
	@Before
	public void setUp() {
		robot = new BasicRobot();
		maze = new MazeController();
		cd = CardinalDirection.East;
		mf = new MazeFactory();
		stub = new StubOrder();
	}

	/**
	 * This will test the constructor for the BasicRobot class to make sure 
	 * it is creating a robot object and that it is setting everything to null.
	 */
	@Test
	public void testBasicRobot() {
		assertNotNull(robot);
		
		assertNull(robot.cells);
		assertNull(robot.maze);
		assertFalse(robot.hasStopped);
		assertEquals(0, robot.currentPosition[0]);
		assertEquals(0, robot.currentPosition[1]);
	}
	
	/**
	 * This will make sure that the get methods work for the BasicRobot class.
	 */
	@Test
	public void testGetMethods() {
		int[] posArr = robot.currentPosition;
		assertEquals(posArr, robot.getCurrentPosition());
		
		this.robot.setMaze(this.maze);
		cd = robot.direction;
		assertEquals(cd, robot.getCurrentDirection());
		
		float battery = robot.batteryLevel;
		assertEquals(battery, robot.getBatteryLevel(), battery);
		assertEquals(12, robot.getEnergyForFullRotation(), 12);
		assertEquals(5, robot.getEnergyForStepForward(), 5);
		
		assertTrue(robot.hasDistanceSensor(Direction.FORWARD));
		assertTrue(robot.hasDistanceSensor(Direction.BACKWARD));
		assertTrue(robot.hasDistanceSensor(Direction.LEFT));
		assertTrue(robot.hasDistanceSensor(Direction.RIGHT));
		assertTrue(robot.hasRoomSensor());
		
		float batteryLev = 250;
		robot.setBatteryLevel(batteryLev);
		assertEquals(batteryLev, robot.getBatteryLevel(), batteryLev);
		
		int[] pos = new int[2];
		pos[0] = 3;
		pos[1] = 2;
		this.robot.setCurrentPosition(pos);
		assertEquals(3, this.robot.currentPosition[0]);
		assertEquals(2, this.robot.currentPosition[1]);
	}
	
	/** 
	 * This method will test the rotate method to make sure that it is doing what it 
	 * is supposed to do.
	 */
	@Test
	public void testRotate() {
		this.robot.setMaze(this.maze);
		
		this.cd = robot.direction;
		this.robot.rotate(Turn.RIGHT);
		assertEquals(cd.rotateClockwise(), this.robot.direction);
		
		this.cd = robot.direction;
		this.robot.rotate(Turn.LEFT);
		assertEquals(cd.rotateCounterClockwise(), this.robot.direction);
		
		this.cd = robot.direction;
		this.robot.rotate(Turn.AROUND);
		assertEquals(cd.oppositeDirection(), this.robot.direction);
		
		this.cd = robot.direction;
		this.robot.setBatteryLevel(2);
		this.robot.rotate(Turn.RIGHT);
		assertTrue(this.robot.hasStopped());
		
		this.cd = robot.direction;
		this.robot.setBatteryLevel(2);
		this.robot.rotate(Turn.LEFT);
		assertTrue(this.robot.hasStopped());
		
		this.cd = robot.direction;
		this.robot.setBatteryLevel(2);
		this.robot.rotate(Turn.AROUND);
		assertTrue(this.robot.hasStopped());
	}
	
	/**
	 * This method will test to make sure the BasicRobot object can know
	 * if it is at the exit position
	 */
	@Test
	public void testIsAtGoal() {
		mf.order(stub);
		mf.waitTillDelivered();
		mazeConfig = ( (StubOrder) stub).getMazeConfiguration();
		cells = mazeConfig.getMazecells();
		robot.setMazeCells(cells);
		
		int exitX = 0;
		int exitY = 0;
		for (int i = 0; i < cells.width; i++) {
			for (int j = 0; j < cells.height; j++) {
				if (cells.isExitPosition(i, j)) {
					exitX = i;
					exitY = j;
				}
			}
		}
		this.robot.currentPosition[0] = exitX;
		this.robot.currentPosition[1] = exitY;
		assertTrue(this.robot.isAtGoal());
	}
	
	/**
	 * This method will test to see is a BasicRobot object can tell if it is in a room
	 */
	@Test
	public void testIsInRoom() {
		mf.order(stub);
		mf.waitTillDelivered();
		mazeConfig = ( (StubOrder) stub).getMazeConfiguration();
		cells = mazeConfig.getMazecells();
		robot.setMazeCells(cells);
		
		int roomX = 0;
		int roomY = 0;
		for (int i = 0; i < cells.width; i++) {
			for (int j = 0; j < cells.height; j++) {
				if (cells.isInRoom(i, j)) {
					roomX = i;
					roomY = j;
				}
			}
		}
		this.robot.currentPosition[0] = roomX;
		this.robot.currentPosition[1] = roomY;
		if (roomX != 0 && roomY != 0) {
			assertTrue(this.robot.isInsideRoom());
		}
		else {
			assertFalse(this.robot.isInsideRoom());
		}
	}
	
	/**
	 * This method will test to see if a BasicRobot object can sense the distance to a barrier
	 */
	@Test
	public void testDistanceToObstacle() {
		mf.order(stub);
		mf.waitTillDelivered();
		mazeConfig = ( (StubOrder) stub).getMazeConfiguration();
		cells = mazeConfig.getMazecells();
		robot.setMaze(this.maze);
		robot.setMazeCells(cells);
		
		this.cd = this.robot.getCurrentDirection();
		
		if (cells.hasWall(this.robot.currentPosition[0], this.robot.currentPosition[1], 
				this.cd)) {
			assertEquals(0, this.robot.distanceToObstacle(Direction.FORWARD));
		}
		if (cells.hasWall(this.robot.currentPosition[0], this.robot.currentPosition[1], 
				this.cd.rotateClockwise())) {
			assertEquals(0, this.robot.distanceToObstacle(Direction.RIGHT));
		}
		if (cells.hasWall(this.robot.currentPosition[0], this.robot.currentPosition[1],
				this.cd.rotateCounterClockwise())) {
			assertEquals(0, this.robot.distanceToObstacle(Direction.LEFT));
		}
		if (cells.hasWall(this.robot.currentPosition[0], this.robot.currentPosition[1], 
				this.cd.oppositeDirection())) {
			assertEquals(0, this.robot.distanceToObstacle(Direction.BACKWARD));
		}
	}
	
	/**
	 * This method will test the move2Positon method in the BasicRobot class.
	 */
	@Test
	public void testMove2Position() {
		mf.order(stub);
		mf.waitTillDelivered();
		mazeConfig = ( (StubOrder) stub).getMazeConfiguration();
		cells = mazeConfig.getMazecells();
		robot.setMaze(this.maze);
		robot.setMazeCells(cells);
		
		assertEquals(0, this.robot.currentPosition[0]);
		assertEquals(0, this.robot.currentPosition[1]);
		
		this.robot.move2Position(2, 2);
		assertEquals(2, this.robot.currentPosition[0]);
		assertEquals(2, this.robot.currentPosition[1]);
		assertEquals(2495, this.robot.batteryLevel, 2495);
		
		assertEquals(2, this.robot.maze.getCurrentPosition()[0]);
		assertEquals(2, this.robot.maze.getCurrentPosition()[1]);
	}
	
	/**
	 * This method will test to make sure the move2Position method changes the hasStopped field to 
	 * true if there is not enough battery to make a move.
	 */
	@Test
	public void testMove2PositionNotEnoughBattery() {
		mf.order(stub);
		mf.waitTillDelivered();
		mazeConfig = ( (StubOrder) stub).getMazeConfiguration();
		cells = mazeConfig.getMazecells();
		robot.setMaze(this.maze);
		robot.setMazeCells(cells);
		
		this.robot.setBatteryLevel(4);
		this.robot.move2Position(3, 2);
		assertTrue(this.robot.hasStopped);
	}
	
	/**
	 * This method will test the move method to make sure it is working properly
	 */
	@Test
	public void testMove() {
		mf.order(stub);
		mf.waitTillDelivered();
		mazeConfig = ( (StubOrder) stub).getMazeConfiguration();
		cells = mazeConfig.getMazecells();
		robot.setMaze(this.maze);
		robot.setMazeCells(cells);
						
		while (!this.robot.isAtGoal()) {
			int[] pos = new int[2];
			pos = this.robot.getCurrentPosition();
			
			if (this.robot.distanceToObstacle(Direction.LEFT) == 0 && this.robot.distanceToObstacle(Direction.FORWARD) > 0) {
				this.robot.move(1, false);

			}
			else {
				
				if (this.robot.distanceToObstacle(Direction.LEFT) > 0) {
					this.robot.rotate(Turn.LEFT);
					this.robot.move(1, false);

				}
				
				else if (this.robot.distanceToObstacle(Direction.RIGHT) > 0) {
					this.robot.rotate(Turn.RIGHT);
					this.robot.move(1, false);
				}
				
				else {
					this.robot.rotate(Turn.AROUND);
					this.robot.move(1, false);
				}
			}
			this.cd = this.robot.getCurrentDirection();
			
			
			switch (this.cd) {
			case North:
				assertEquals(pos[0], this.robot.currentPosition[0]);
				assertEquals(pos[1] - 1, this.robot.currentPosition[1]);
				break;
			case South:
				assertEquals(pos[0], this.robot.currentPosition[0]);
				assertEquals(pos[1] + 1, this.robot.currentPosition[1]);
				break;
			case East:
				assertEquals(pos[0] + 1, this.robot.currentPosition[0]);
				assertEquals(pos[1], this.robot.currentPosition[1]);
				break;
			case West:
				assertEquals(pos[0] - 1, this.robot.currentPosition[0]);
				assertEquals(pos[1], this.robot.currentPosition[1]);
				break;
			}
		}
	}
		/**
		 * This method will test to make sure the move method sets the hasStopped field to true
		 * if it does not have enough battery to make a move
		 */
		@Test
		public void testMoveNotEnoughBattery() {
		
			mf.order(stub);
			mf.waitTillDelivered();
			mazeConfig = ( (StubOrder) stub).getMazeConfiguration();
			cells = mazeConfig.getMazecells();
			robot.setMaze(this.maze);
			robot.setMazeCells(cells);
			
			this.robot.setBatteryLevel(3);
			this.robot.move(2, false);
			assertTrue(this.robot.hasStopped);
		}
		
		/** 
		 * This method will test to make sure the move method sets the hasStopped field to true
		 * if it cannot make the desired number of moves because it has run into a wall.
		 */
		@ Test
		public void testMoveRunsIntoWall() {
			mf.order(stub);
			mf.waitTillDelivered();
			mazeConfig = ( (StubOrder) stub).getMazeConfiguration();
			cells = mazeConfig.getMazecells();
			robot.setMaze(this.maze);
			robot.setMazeCells(cells);
			
			this.robot.move(this.robot.distanceToObstacle(Direction.FORWARD) + 1, false);
			assertTrue(this.robot.hasStopped);
		}
		
		/**
		 * This method will test to make sure the canSeeGoal method returns the correct value
		 */
		@Test
		public void testCanSeeGoal() {
			mf.order(stub);
			mf.waitTillDelivered();
			mazeConfig = ( (StubOrder) stub).getMazeConfiguration();
			cells = mazeConfig.getMazecells();
			robot.setMaze(this.maze);
			robot.setMazeCells(cells);
			
			if (!this.robot.canSeeGoal(Direction.LEFT)) {
				assertFalse(this.robot.canSeeGoal(Direction.LEFT));
			}
			if (!this.robot.canSeeGoal(Direction.FORWARD)) {
				assertFalse(this.robot.canSeeGoal(Direction.FORWARD));
			}
			if (!this.robot.canSeeGoal(Direction.BACKWARD)) {
				assertFalse(this.robot.canSeeGoal(Direction.BACKWARD));
			}
			if (!this.robot.canSeeGoal(Direction.RIGHT)) {
				assertFalse(this.robot.canSeeGoal(Direction.RIGHT));
			}
			
			int exitX = 0;
			int exitY = 0;
			for (int i = 0; i < cells.width; i++) {
				for (int j = 0; j < cells.height; j++) {
					if (cells.isExitPosition(i, j)) {
						exitX = i;
						exitY = j;	
					}
				}
			}
			
			int[] pos = new int[2];
			pos[0] = exitX;
			pos[1] = exitY;
			this.robot.setCurrentPosition(pos);
			
			if (this.robot.canSeeGoal(Direction.LEFT)) {
				assertTrue(this.robot.canSeeGoal(Direction.LEFT));
			}
			if (this.robot.canSeeGoal(Direction.FORWARD)) {
				assertTrue(this.robot.canSeeGoal(Direction.FORWARD));
			}
			if (this.robot.canSeeGoal(Direction.BACKWARD)) {
				assertTrue(this.robot.canSeeGoal(Direction.BACKWARD));
			}
			if (this.robot.canSeeGoal(Direction.RIGHT)) {
				assertTrue(this.robot.canSeeGoal(Direction.RIGHT));
			}
		}


		
	}

