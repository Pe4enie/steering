package steering;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import vector.V3;
import vector.Vector3D;


public class SimpleLocomotionTest {

	private static final double MAX_SPEED = 5.0;
	private SimpleLocomotion simple;
	
	@Before
	public void setUp() {
		Vector3D xaxis = new Vector3D(1.0, 0.0, 0.0);
		Vector3D yaxis = new Vector3D(0.0, 1.0, 0.0);
		Vector3D zaxis = new Vector3D(0.0, 0.0, 1.0);
		
		simple = new SimpleLocomotion(1.0, new Vector3D(0.0, 0.0, 0.0), 
				new Vector3D(0.0, 0.0, 0.0), 5.0, MAX_SPEED, 
				new Vector3D[]{xaxis, yaxis, zaxis});
	}
	
	@Test
	public void testSetVelocity() {
		simple.setVelocity(new Vector3D(1.0, 1.0, 1.0));
		assertEquals(1.0, simple.velocity().x);
		assertEquals(1.0, simple.velocity().y);
		assertEquals(1.0, simple.velocity().z);
	}
	
	@Test
	public void testSetVelocityHigherThanMax() {
		simple.setVelocity(new Vector3D(30.0, 30.0, 30.0));
		double magnitude = V3.magnitude(simple.velocity());
		assertEquals(MAX_SPEED, magnitude);
	}
	
	@Test
	public void testSteer() {
		simple.steer(new Vector3D(1.0, 1.0, 1.0));
		assertEquals(1.0, simple.velocity().x);
		assertEquals(1.0, simple.velocity().y);
		assertEquals(1.0, simple.velocity().z);
	}
	
	@Test
	public void testOverSteer() {
		simple.steer(new Vector3D(30.0, 30.0, 30.0));
		double magnitude = V3.magnitude(simple.velocity());
		assertEquals(MAX_SPEED, magnitude);
	}
	
	@Test
	public void testOrient() {
		simple.setVelocity(new Vector3D(-1.0, 0.0, 0.0));
		assertEquals(-1.0, simple.velocity().x);
		simple.orient();
		Vector3D[] orientation = simple.getOrientation();
		assertEquals(-1.0, orientation[SimpleLocomotion.FORWARD].x);
		assertEquals(-1.0, orientation[SimpleLocomotion.SIDE].y);
		assertEquals(1.0, orientation[SimpleLocomotion.UP].z);
	}
	
	@Test
	public void testMove() {
		simple.setVelocity(new Vector3D(1.0, 1.0, 1.0));
		simple.move();
		assertEquals(1.0, simple.position().x);
		assertEquals(1.0, simple.position().y);
		assertEquals(1.0, simple.position().z);		
	}
}
