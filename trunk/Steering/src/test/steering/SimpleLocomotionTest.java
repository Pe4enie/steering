package steering;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import vector.Vector2D;


public class SimpleLocomotionTest {

	SimpleLocomotion locomotion;
	
	@Before
	public void setUp() {
		locomotion = new SimpleLocomotion(1.0, new Vector2D(0.0, 0.0), new Vector2D(0.0, 0.0));
		locomotion.setMaxSpeed(Math.sqrt(8.0));
		locomotion.setMaxForce(4.0);
	}
	
	@Test
	public void testSetVelocityBelowMaxSpeed() {
		Vector2D newVelocity = new Vector2D(1.0, 1.0);
		locomotion.setVelocity(newVelocity);
		assertEquals(newVelocity, locomotion.velocity());
	}

	@Test
	public void testSetVelocityAboveMaxSpeed() {
		locomotion.setVelocity(new Vector2D(5.0, 5.0));
		assertEquals(new Vector2D(2.0, 2.0), locomotion.velocity());
	}
	
	@Test
	public void testSteeringForceLessThanMax() {
		locomotion.setVelocity(new Vector2D(1.0, 1.0));
		locomotion.steer(new Vector2D(1.0, -1.0));
		assertEquals(new Vector2D(2.0, 0.0), locomotion.velocity());
	}
	
	@Test
	public void testSteeringForceMoreThanMax() {
		locomotion.setMaxSpeed(100.0);
		locomotion.setVelocity(new Vector2D(4.0, 4.0));
		locomotion.steer(new Vector2D(4.0, -4.0));
		assertEquals(new Vector2D(4.0 + Math.sqrt(8.0), 4.0 - Math.sqrt(8.0)), locomotion.velocity());
	}
	
	@Test
	public void testMove() {
		locomotion.move();
		assertEquals(new Vector2D(0.0, 0.0), locomotion.position());
		
		locomotion.setVelocity(new Vector2D(1.0, 1.0));
		locomotion.move();
		assertEquals(new Vector2D(1.0, 1.0), locomotion.position());
	}
}
