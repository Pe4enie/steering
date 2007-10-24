package vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;


public class VTest {

	private Vector2D v1;
	private Vector2D v2;
	
	@Before
	public void setUp() {
		v1 = new Vector2D(1.0, 2.0);
		v2 = new Vector2D(2.0, 3.0);
	}

	@Test
	public void testEquals() {
		assertFalse(v1.equals(v2));
		Vector2D v3 = new Vector2D(1.0, 2.0);
		assertFalse(v1 == v3);
		assertEquals(v1, v3);
	}
	
	@Test
	public void testApproxEquals() {
		Vector2D vector1 = new Vector2D(1.0, 1.0);
		Vector2D threeDecimal = new Vector2D(1.0001, 1.0001);
		Vector2D twoDecimal = new Vector2D(1.001, 1.001);
		
		assertEquals(vector1, threeDecimal);
		assertFalse(vector1.equals(twoDecimal));
	}
	
	@Test
	public void testAdd() {
		assertEquals(new Vector2D(3.0, 5.0), V.add(v1, v2));
	}
	
	@Test
	public void testSub() {
		assertEquals(new Vector2D(1.0, 1.0), V.sub(v2, v1));
	}
	
	@Test
	public void testMult() {
		assertEquals(new Vector2D(2.0, 4.0), V.mult(2.0, v1));
	}
	
	@Test
	public void testMagnitude() {
		assertEquals(5.0, V.magnitude(new Vector2D(3.0, 4.0)));
	}
	
	@Test
	public void testUnitOf() {
		assertEquals(new Vector2D(0.0, 1.0), V.unitOf(new Vector2D(0.0, 2.0)));
	}
	
	@Test
	public void testDotProduct() {
		assertEquals(8.0, V.dot(v1, v2));
	}

	@Test
	public void testProject() {
		//testing with a 30-60-90 triangle
		//whose sides are n, n*sqrt(3) & 2n (yay basic trig!)
		double n = 3.0;

		Vector2D vector = new Vector2D(0.0, 2 * n);
		Vector2D onto = new Vector2D(5 * n * Math.sqrt(3), 5 * n);

		Vector2D projection = new Vector2D(n / 2 * Math.sqrt(3), n / 2);
		Vector2D actualProjection = V.project(vector, onto);
		
		assertEquals(projection, actualProjection);
	}
	
	@Test
	public void testAngle() {
		double n = 3.0;
		
		Vector2D vector1 = new Vector2D(0.0, 2 * n);
		Vector2D vector2 = new Vector2D(5 * n * Math.sqrt(3), 5 * n);
		
		double radians = 60.0 * Math.PI / 180;
		
		assertEquals(radians, V.angle(vector1, vector2));
	}
}