package falstad;

import falstad.Robot.Direction;
import falstad.Robot.Turn;
import generation.CardinalDirection;
import generation.Distance;
import generation.Order;

/**
 * Class: Wizard
 * 
 * Responsibilities: This class has the responsibility of driving to the exit like the WallFollower. But, unlike the WallFollower
 * the Wizard drives to the exit by moving to the cell that has the least distance to the exit. Therefore it will basically show the
 * best solution to solve the maze.
 * 
 * Collaborators: BasicRobot, Robot, RobotDriver, CardinalDirection, MazeController, MazeConfiguration
 * 
 * @author corythomas
 *
 */

public class Wizard implements RobotDriver {
	
	protected int[][] board;
	protected BasicRobot robot;
	protected Distance distance;
	protected int pathLength;
	protected MazeController maze;
	protected CardinalDirection cd;
	protected int[] neighbor;
	
	public Wizard() {
		this.board = null;
		this.robot = null;
		this.distance = null;
		this.pathLength = 0;
		this.maze = new MazeController();
		this.neighbor = new int[2];
	}
	
	public Wizard(Order.Builder builder) {
		this.board = null;
		this.robot = null;
		this.distance = null;
		this.pathLength = 0;
		this.maze = new MazeController(builder);
		this.neighbor = new int[2];
	}


	@Override
	public void setRobot(Robot r) {
		this.robot = (BasicRobot) r;
		this.robot.setMaze(this.maze);
	}

	@Override
	public void setDimensions(int width, int height) {
		this.board = new int[width][height];
	}

	@Override
	public void setDistance(Distance distance) {
		this.distance = distance;
	}

	@Override
	public boolean drive2Exit() throws Exception {
		while (!this.robot.isAtGoal()) {
			if (!this.robot.hasStopped) {
			
				this.neighbor = this.robot.maze.mazeConfig.getNeighborCloserToExit(this.robot.currentPosition[0], 
						this.robot.currentPosition[1]);
//				switch (this.robot.direction) {
//				case North:
//					if (this.neighbor[1] > this.robot.currentPosition[1]) {
//						this.robot.rotate(Turn.AROUND);
//					}
//					if (this.neighbor[0] < this.robot.currentPosition[0]) {
//						this.robot.rotate(Turn.RIGHT);
//					}
//					if (this.neighbor[0] > this.robot.currentPosition[0]) {
//						this.robot.rotate(Turn.LEFT);
//					}
//				case South:
//					if (this.neighbor[1] < this.robot.currentPosition[1]) {
//						this.robot.rotate(Turn.AROUND);
//					}
//					if (this.neighbor[0] > this.robot.currentPosition[0]) {
//						this.robot.rotate(Turn.RIGHT);
//					}
//					if (this.neighbor[0] < this.robot.currentPosition[0]) {
//						this.robot.rotate(Turn.LEFT);
//					}
//				case East:
//					if (this.neighbor[0] < this.robot.currentPosition[0]) {
//						this.robot.rotate(Turn.AROUND);
//					}
//					if (this.neighbor[1] > this.robot.currentPosition[1]) {
//						this.robot.rotate(Turn.RIGHT);
//					}
//					if (this.neighbor[1] < this.robot.currentPosition[1]) {
//						this.robot.rotate(Turn.LEFT);
//					}
//				case West:
//					if (this.neighbor[0] > this.robot.currentPosition[0]) {
//						this.robot.rotate(Turn.AROUND);
//					}
//					if (this.neighbor[1] > this.robot.currentPosition[1]) {
//						this.robot.rotate(Turn.RIGHT);
//					}
//					if (this.neighbor[1] < this.robot.currentPosition[1]) {
//						this.robot.rotate(Turn.LEFT);
//					}
//				}
				this.robot.move2Position(this.neighbor[0], this.neighbor[1]);
				this.robot.maze.notifyViewerRedraw();
				Thread.sleep(500);
				pathLength++;
			}
			else {
				return false;
			}
		}
		if (this.robot.canSeeGoal(Direction.LEFT)) {
			( (BasicRobot) robot).maze.state = Constants.StateGUI.STATE_FINISH;
			( (BasicRobot) robot).maze.notifyViewerRedraw();
		}
		else if (this.robot.canSeeGoal(Direction.RIGHT)) {
			( (BasicRobot) robot).maze.state = Constants.StateGUI.STATE_FINISH;
			( (BasicRobot) robot).maze.notifyViewerRedraw();
		}
		else if (this.robot.canSeeGoal(Direction.BACKWARD)) {
			( (BasicRobot) robot).maze.state = Constants.StateGUI.STATE_FINISH;
			( (BasicRobot) robot).maze.notifyViewerRedraw();
		}
		else if (this.robot.canSeeGoal(Direction.FORWARD)) {
			( (BasicRobot) robot).maze.state = Constants.StateGUI.STATE_FINISH;
			( (BasicRobot) robot).maze.notifyViewerRedraw();
		}
			
		return true;
	}

	@Override
	public float getEnergyConsumption() {
		return 2500 - this.robot.batteryLevel;
	}

	@Override
	public int getPathLength() {
		return this.pathLength;
	}
	
	/**
	 * This method will allow you to set the pathLength to what is given
	 * @param length is the new pathLength
	 */
	public void setPathLength(int length) {
		this.pathLength = length;
	}

}
