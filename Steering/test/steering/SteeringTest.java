package steering;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import vector.V3;
import vector.Vector3D;


public class SteeringTest {

	private SimpleLocomotion seeker;
	private Vector3D velocity;
	private Vector3D desired;

	@Before
	public void setUp() {
		velocity = new Vector3D(1.0, 1.0, 1.0);
		seeker = 
			new SimpleLocomotion(1.0, new Vector3D(0.0, 0.0, 0.0), velocity, 5.0, 5.0);
		desired = new Vector3D(6.0, -2.0, 3.0);
	}
	
	@Test
	public void testSeekInRightDirection() {		
		Vector3D steering = Steering.seek(seeker, desired, 2.0);
		
		Vector3D steerDir = new Vector3D(5.0, -3.0, 2.0);
		assertEquals(V3.unitOf(steerDir).x, V3.unitOf(steering).x);
		assertEquals(V3.unitOf(steerDir).y, V3.unitOf(steering).y);
		assertEquals(V3.unitOf(steerDir).z, V3.unitOf(steering).z);
	}
	
	@Test
	public void testSeekToRightSpeed() {
		Vector3D steering = Steering.seek(seeker, desired, 2.0);
		assertEquals(2.0, V3.magnitude(V3.add(velocity, steering)), 0.000001);
	}
	
	@Test
	public void testArriveOutsideRadius() {
		double distance = V3.magnitude(desired);
		double slowRadius = distance - 2.0;
		
		ArrivalFunction fn = new LinearDecayArrivalFn(0.05, 2.0, slowRadius);
		
		Vector3D arrive = Steering.arrive(seeker, desired, 
				2.0, slowRadius, fn);
		Vector3D seek = Steering.seek(seeker, desired, 2.0);
		
		assertEquals(seek.x, arrive.x);
		assertEquals(seek.y, arrive.y);
		assertEquals(seek.z, arrive.z);
	}
	
	@Test
	public void testArriveInsideRadius() {
		double distance = V3.magnitude(desired);
		double slowRadius = distance + 2.0;
		
		ArrivalFunction fn = new LinearDecayArrivalFn(0.05, 2.0, slowRadius);
		
		Vector3D arrive = Steering.arrive(seeker, desired, 2.0, slowRadius, fn);
		Vector3D seek = Steering.seek(seeker, desired, fn.speedOfArrival(distance));
		
		assertEquals(seek.x, arrive.x);
		assertEquals(seek.y, arrive.y);
		assertEquals(seek.z, arrive.z);		
	}

	@Test
	public void testSlowDownInsideRadius() {
		double distance = V3.magnitude(desired);
		double largeRadius = distance + 2.0;
		double smallRadius = distance - 2.0;
		
		ArrivalFunction fn0 = new LinearDecayArrivalFn(0.05, 2.0, smallRadius);
		ArrivalFunction fn1 = new LinearDecayArrivalFn(0.05, 2.0, largeRadius);
		
		Vector3D outside = Steering.arrive(seeker, desired, 2.0, smallRadius, fn0);
		Vector3D inside = Steering.arrive(seeker, desired, 2.0, largeRadius, fn1);
		
		double speedOutside = V3.magnitude(outside);
		double speedInside = V3.magnitude(inside);
		
		assertTrue(speedInside < speedOutside);
	}
	
	@Test
	public void testPathFollowInsidePathRadius() {
		seeker.setVelocity(new Vector3D(1.0, 1.0, 1.0));
		
		Vector3D[] path = new Vector3D[] { 
				new Vector3D(-4.0, 6.0, 0.0),
				new Vector3D(0.0, 4.0, 0.0),
				new Vector3D(0.5, 0.0, 0.0),
				new Vector3D(0.0, -3.0, 0.0),
				new Vector3D(3.0, 0.0, 0.0)
		};
		
		Vector3D steer = Steering.followPath(seeker, path, 5.0, 1.0, 2.0);
		
		assertEquals(0.0, steer.x);
		assertEquals(0.0, steer.y);
		assertEquals(0.0, steer.z);
	}
	
	@Test
	public void testPathFollowOutsidePathRadius() {
		seeker.setVelocity(new Vector3D(1.0, 1.0, 1.0));
		double vFactor = 5.0;
		Vector3D point = V3.add(seeker.position(), V3.mult(vFactor , seeker.velocity()));

		Vector3D[] path = new Vector3D[] { 
				new Vector3D(0.0, 0.0, 0.0),
				new Vector3D(0.0, 10.0, 0.0),
				new Vector3D(15.0, 15.0, 0.0),
		};
		
		Vector3D steer = Steering.followPath(seeker, path, 5.0, vFactor, 2.0);
		Vector3D seek = Steering.seek(seeker, V3.projectPolyline(point, path), 2.0);
		
		assertEquals(seek.x, steer.x);
		assertEquals(seek.y, steer.y);
		assertEquals(seek.z, steer.z);
	}
}
