package vector;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class V3Test {

	private Vector3D v1;
	private Vector3D v2;
	
	@Before
	public void setUp() {
		v1 = new Vector3D(1.0, 2.0, 3.0);
		v2 = new Vector3D(3.0, 4.0, 5.0);
	}
	
	@Test
	public void testAdd() {
		Vector3D v = V3.add(v1, v2);
		assertEquals(4.0, v.x);
		assertEquals(6.0, v.y);
		assertEquals(8.0, v.z);
	}
	
	@Test
	public void testSub() {
		Vector3D v = V3.sub(v2, v1);
		assertEquals(2.0, v.x);
		assertEquals(2.0, v.y);
		assertEquals(2.0, v.z);
	}
	
	@Test
	public void testMult() {
		Vector3D v = V3.mult(2.0, v1);
		assertEquals(2.0, v.x);
		assertEquals(4.0, v.y);
		assertEquals(6.0, v.z);		
	}
	
	@Test
	public void testMagnitude() {
		double mag = V3.magnitude(v1);
		assertEquals(Math.sqrt(14.0), mag);
	}
	
	@Test
	public void testUnitOf() {
		Vector3D v = V3.unitOf(new Vector3D(1.0, 1.0, 1.0));
		assertEquals(Math.sqrt(3.0) / 3.0, v.x);
		assertEquals(Math.sqrt(3.0) / 3.0, v.y);
		assertEquals(Math.sqrt(3.0) / 3.0, v.z);
	}
	
	@Test
	public void testUnitOfNegativeVector() {
		Vector3D v = V3.unitOf(new Vector3D(-1.0, 0.0, 0.0));
		assertEquals(-1.0, v.x);
		assertEquals(0.0, v.y);
		assertEquals(0.0, v.z);
	}
	
	@Test
	public void testDot() {
		double dot = V3.dot(v1, v2);
		assertEquals(26.0, dot);
	}
	
	@Test 
	public void testCross() {
		Vector3D xaxis = new Vector3D(1.0, 0.0, 0.0);
		Vector3D yaxis = new Vector3D(0.0, 1.0, 0.0);
		Vector3D zaxis = new Vector3D(0.0, 0.0, 1.0);
		
		Vector3D cross = V3.cross(xaxis, yaxis);
		
		assertEquals(zaxis.x, cross.x);
		assertEquals(zaxis.y, cross.y);
		assertEquals(zaxis.z, cross.z);		
	}
	
	@Test
	public void testResizeTo() {
		Vector3D v = V3.resizeTo(2.0, new Vector3D(5.0, 5.0, 5.0)); 
		double magnitude = V3.magnitude(v);
		assertEquals(2.0, magnitude);
	}
}
