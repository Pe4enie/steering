package steering.actionselection;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import steering.Locomotion;
import steering.SimpleLocomotion;
import steering.behaviors.Flee;
import vector.Vector2D;


public class KeepDistanceTest {

	private Locomotion boid;
	
	@Before
	public void setUp() {
		boid = new SimpleLocomotion(1.0, new Vector2D(1.0, 1.0), new Vector2D(1.0, 1.0));
	}
	
	@Test
	public void testFleeWithinRadius() {
		Vector2D point = new Vector2D(0.0, 0.0);
		KeepDistance selector = new KeepDistance(boid, point, 2.0);
		Flee flee = new Flee(boid, point);
		Vector2D steer = selector.selectAction().steeringForce();
		assertEquals(flee.steeringForce(), steer);
	}
	
	@Test
	public void testNoSteerOutsideRadius() {
		Vector2D point = new Vector2D(0.0, 0.0);
		KeepDistance selector = new KeepDistance(boid, point, 0.1);
		Vector2D steer = selector.selectAction().steeringForce();
		assertEquals(new Vector2D(0.0, 0.0), steer);		
	}
	
}
