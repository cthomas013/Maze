
package falstad;

import generation.MazeConfiguration;
import generation.MazeContainer;
import generation.MazeFactory;
import generation.Order;
import generation.Order.Builder;

/**
 * 
 * This class has the task of creating an OrderStub that can be used in the MazeFactoryTest
 * to test the order method in MazeFactory.
 * 
 * @author corythomas
 *
 */

public class StubOrder extends MazeController implements Order {
	
	/**
	 * This is the constructor for the StubOrder class.
	 * It sets all the fields to their default values.
	 * The default builder is DFS and the default skill level is 0.
	 */
	public StubOrder() {
		super() ;
		setBuilder(Order.Builder.DFS); 
		setPerfect(true);
		setSkillLevel(0);
	}
	/**
	 * 
	 * @param skillLevel
	 * @param builder
	 * This constructor takes a skill level parameter and a builder parameter and sets the 
	 * builder and skill level fields to the parameters.
	 */
	public StubOrder(int skillLevel, Builder builder) {
		super() ;
		setBuilder(builder) ;
		setPerfect(true);
		setSkillLevel(skillLevel);
	}

	/**
	 * @return skill
	 * This will return the skill level for the maze.
	 */
	@Override
	public int getSkillLevel() {
		return super.skill;
	}

	/**
	 * @return builder
	 * This will return the type of builder algorithm that will be used to generate the maze.
	 */
	@Override
	public Builder getBuilder() {
		return super.builder;
	}

	/**
	 * @return perfect
	 * This will tell whether the maze is perfect or not. A perfect maze has no loops
	 * and every cell is accessible from every other cell in the maze.
	 */
	@Override
	public boolean isPerfect() {
		return super.perfect;
	}

	/**
	 * This method will allow access to the maze configuration.
	 */
	@Override
	public void deliver(MazeConfiguration mazeConfig) {
		super.deliver(mazeConfig);
	}

	/**
	 * This calls the updateProgress method in the superclass so that the current percentage
	 * of the maze that has been generated will show up on the maze game screen.
	 */
	@Override
	public void updateProgress(int percentage) {
		super.updateProgress(percentage);	
	}
	/**
	 * @return mazeConfiguration
	 * This method will return the mazeConfiguration from the field in the superclass.
	 */
	public MazeConfiguration getMazeConfiguration() {
		return super.getMazeConfiguration();
	}

}
