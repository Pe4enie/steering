package steering;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class LinearDecayArrivalFnTest {

	private ArrivalFunction fn;
	private double shutoff;
	private double radius;
	private double prime;	
	
	@Before
	public void setUp() {
		shutoff = 0.25;
		prime = 5.0;
		radius = 5.0;
		fn = new LinearDecayArrivalFn(shutoff, prime, radius);
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
		assertEquals(prime, fn.speedOfArrival(radius));
	}
	
	@Test
	// test that 1st derivative is constant (slope of curve doesn't change)
	public void testLinearSlopeOfCurve() {
		double distance0 = 1.8;
		double distance1 = 2.4;
		double distance2 = 3.3;
		
		double speed0 = fn.speedOfArrival(distance0);
		double speed1 = fn.speedOfArrival(distance1);
		double speed2 = fn.speedOfArrival(distance2);
		
		double slope0 = speed1 / speed0;
		double slope1 = speed2 / speed1;
		
		assertEquals(slope0, slope1);
	}
}
