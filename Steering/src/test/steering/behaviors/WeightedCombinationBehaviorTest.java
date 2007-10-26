package steering.behaviors;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import steering.SimpleLocomotion;
import steering.Steering;
import vector.Vector2D;


public class WeightedCombinationBehaviorTest {

	private WeightedCombinationBehavior behavior;
	private Seek seek;
	private SimpleLocomotion boid;
	private Vector2D position;
	private Flee flee;

	@Before
	public void setUp() {
		behavior = new WeightedCombinationBehavior();

		position = new Vector2D(2.0, 2.0);
		boid = new SimpleLocomotion(1.0, new Vector2D(1.0, 1.0), new Vector2D(1.0, 1.0));
		seek = new Seek(boid, position);
		
		flee = new Flee(boid, position);
	}
	
	@Test
	public void testSingleBehavior() {
		behavior.addBehavior(seek);
		Vector2D steering = behavior.steeringForce();
		assertEquals(Steering.seek(boid, position), steering);
	}
	
	@Test
	public void testMultipleBehaviorsNoWeights() {
		behavior.addBehavior(seek);
		behavior.addBehavior(flee);
		Vector2D steering = behavior.steeringForce();
		assertEquals(new Vector2D(0.0, 0.0), steering);
	}
	
	@Test
	public void testMultipleBehaviorsWithWeights() {
		behavior.addBehavior(seek, 0.75);
		behavior.addBehavior(flee, 0.25);
		Vector2D steering = behavior.steeringForce();
		assertEquals(new Vector2D(0.5, 0.5), steering);
	}	
}
