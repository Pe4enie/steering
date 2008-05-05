package steering;

import static org.junit.Assert.assertEquals;

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
	public void testExponentialDecay() {
		double distance0 = 2.0;
		double distance1 = 1.0;
		
		double speed0 = fn.speedOfArrival(distance0);
		double speed1 = fn.speedOfArrival(distance1);
		
		double speedRatio = speed1 / speed0;
		
		assertEquals(Math.exp(-1 * lambda), speedRatio);
	}
}
