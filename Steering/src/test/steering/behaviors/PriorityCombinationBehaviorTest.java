package steering.behaviors;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import steering.Behavior;
import steering.Container;
import steering.SimpleLocomotion;
import vector.Vector2D;


public class PriorityCombinationBehaviorTest {

	private Behavior containmentBehavior;
	private Container mockContainer;
	private SimpleLocomotion boid;
	private PriorityCombinationBehavior combo;
	private Behavior seekBehavior;

	@Before
	public void setUp() {
		boid = new SimpleLocomotion(1.0, new Vector2D(0.0, 0.0), new Vector2D(1.0, 1.0));
		boid.setMaxForce(100.0);
		boid.setMaxSpeed(100.0);

		mockContainer = new Container() {
			@Override
			public boolean within(Vector2D point) {
				if(point.x > 2)
					return false;
				return true;
			}
			@Override
			public Vector2D project(Vector2D vector) {
				return new Vector2D(2.0, 2.0);
			}
		};

		containmentBehavior = new Containment(boid, mockContainer, 1.0);
		seekBehavior = new Seek(boid, new Vector2D(0.0, -1.0));
		
		combo = new PriorityCombinationBehavior();
	}
	
	@Test
	public void testSingleBehavior() {
		List<Behavior> behaviors = Arrays.asList(containmentBehavior); 
		combo.setBehaviors(behaviors);
		
		Vector2D steering = combo.steeringForce();
		assertEquals(containmentBehavior.steeringForce(), steering);
	}

	@Test
	public void testFirstBehaviorTakesPriority() {
		boid.steer(new Vector2D(5.0, 0.0));
		List<Behavior> behaviors = Arrays.asList(containmentBehavior, seekBehavior); 
		combo.setBehaviors(behaviors);
		
		Vector2D steering = combo.steeringForce();
		System.out.println("(" + steering.x + ", " + steering.y + ")");
		Vector2D behaviorForce = containmentBehavior.steeringForce();
		System.out.println("(" + behaviorForce.x + ", " + behaviorForce.y + ")");
		assertEquals(behaviorForce, steering);
	}
	
	@Test
	public void testSecondBehaviorTakesPriority() {
		List<Behavior> behaviors = Arrays.asList(containmentBehavior, seekBehavior); 
		combo.setBehaviors(behaviors);
		
		Vector2D steering = combo.steeringForce();
		assertEquals(seekBehavior.steeringForce(), steering);
	}
}
