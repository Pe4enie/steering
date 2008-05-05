package steering;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;


public class ExponentialDecayArrivalFnTest {

	private ArrivalFunction fn;
	private double lambda;
	private double shutoff;
	private double radius;
	private double prime;
	
	@Before
	public void setUp() {
		this.lambda = 2.3;
		this.shutoff = 0.25;
		this.prime = 5.0;
		this.radius = 5.0;
		fn = new ExponentialDecayArrivalFn(lambda, shutoff, prime, radius);
	}
	
	@Test
	public void testZeroAtZero() {
		assertEquals(0.0, fn.speedOfArrival(0.0));
	}
	
	@Test
	public void testZeroInsideShutoff() {
		assertEquals(0.0, fn.speedOfArrival(0.1));
	}
	
	@Test
	public void testPrimeAtRadius() {
		assertEquals(5.0, fn.speedOfArrival(radius));
	}
	
	@Test
	// test that fn produces negative acceleration 
	// (1st derivative is negative i.e. slope decreases with decreasing distance)
	public void testExponentialDecay() {
		double distance0 = 3.0;
		double distance1 = 2.0;
		double distance2 = 1.0;
		
		double speed0 = fn.speedOfArrival(distance0);
		double speed1 = fn.speedOfArrival(distance1);
		double speed2 = fn.speedOfArrival(distance2);
		
		double slope0 = speed1 / speed0;
		double slope1 = speed2 / speed1;
		
		assertTrue(slope1 > slope0);
	}
}
