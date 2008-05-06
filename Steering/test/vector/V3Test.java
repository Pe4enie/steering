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
	public void testAngle() {
		Vector3D origin = new Vector3D(0.0, 0.0, 0.0);
		assertEquals(Math.toRadians(45), V3.angle(origin, new Vector3D(1.0, 1.0, 0.0)));
		assertEquals(Math.toRadians(90), V3.angle(origin, new Vector3D(0.0, 1.0, 0.0)));
		assertEquals(Math.toRadians(135), V3.angle(origin, new Vector3D(-1.0, 1.0, 0.0)));
		assertEquals(Math.toRadians(180), V3.angle(origin, new Vector3D(-1.0, 0.0, 0.0)));
		assertEquals(Math.toRadians(-90), V3.angle(origin, new Vector3D(0.0, -1.0, 0.0)));
	}
	
	@Test
	public void testResizeTo() {
		Vector3D v = V3.resizeTo(2.0, new Vector3D(5.0, 5.0, 5.0)); 
		double magnitude = V3.magnitude(v);
		assertEquals(2.0, magnitude);
	}
	
	@Test
	public void testProjectDirection() {
		Vector3D vector = new Vector3D(5.0, 12.0, -4.0);
		Vector3D onto = new Vector3D(3.0, 2.0, -1.0);
		
		Vector3D projection = V3.project(vector, onto);

		assertEquals(V3.unitOf(onto).x, V3.unitOf(projection).x);
		assertEquals(V3.unitOf(onto).y, V3.unitOf(projection).y);
		assertEquals(V3.unitOf(onto).z, V3.unitOf(projection).z);
	}
	
	@Test
	public void testProjectDistance() {
		Vector3D vector = new Vector3D(5.0, 5.0, 0.0);
		Vector3D onto = new Vector3D(12.0, 0.0, 0.0);
		
		Vector3D projection = V3.project(vector, onto);
		
		assertEquals(5.0, V3.magnitude(projection));
	}
	
	@Test
	public void testNotBetween() {
		assertFalse(V3.between(3.0, -1.0, 1.0));
	}
	
	@Test
	public void testBetween() {
		assertTrue(V3.between(1.0, 0.0, 3.0));
		assertTrue(V3.between(-2.0, -3.0, 0.0));
	}
	
	@Test
	public void testBetweenSameEndpoints() {
		assertTrue(V3.between(5.0, 5.0, 5.0));
	}
	
	@Test
	public void testBetweenOnEndpoint() {
		assertTrue(V3.between(1.0, 1.0, 4.0));
	}
	
	@Test
	public void testPolylineProjectOff() {
		Vector3D point = new Vector3D(1.0, 1.0, 0.0);
		Vector3D[] polyline = new Vector3D[] { 
				new Vector3D(-4.0, 6.0, 0.0),
				new Vector3D(0.0, 4.0, 0.0)
		};

		Vector3D segment = V3.sub(polyline[1], polyline[0]);
		Vector3D proj = V3.project(point, segment); 
		
		System.out.println("(" + proj.x + ", " + proj.y + ", " + proj.z + ")");
		
		Vector3D project = V3.projectPolyline(point, polyline);
		
		assertNull(project);
	}
	
	@Test
	public void testPolylineProjectOn() {
		Vector3D point = new Vector3D(1.0, 1.0, 0.0);
		Vector3D[] polyline = new Vector3D[] { 
				new Vector3D(0.0, -3.0, 0.0),
				new Vector3D(3.0, 0.0, 0.0)
		};
				
		Vector3D project = V3.projectPolyline(point, polyline);
		
		assertNotNull(project);
	}

	@Test
	public void testPolylineProjectOneSegmentNotOn() {
		Vector3D point = new Vector3D(1.0, 1.0, 0.0);
		Vector3D[] polyline = new Vector3D[] { 
				new Vector3D(-4.0, 6.0, 0.0),
				new Vector3D(0.0, 4.0, 0.0),
				new Vector3D(0.0, -3.0, 0.0),
				new Vector3D(3.0, 0.0, 0.0)
		};

		Vector3D segment = V3.sub(polyline[1], polyline[0]);
		Vector3D proj = V3.project(point, segment); 
		
		System.out.println("(" + proj.x + ", " + proj.y + ", " + proj.z + ")");
		
		Vector3D project = V3.projectPolyline(point, polyline);
		
		assertNotNull(project);
	}
	
	@Test
	public void testPolylineProjectPickMin() {
		Vector3D point = new Vector3D(1.0, 1.0, 0.0);
		Vector3D[] polyline = new Vector3D[] { 
				new Vector3D(-4.0, 6.0, 0.0),
				new Vector3D(0.0, 4.0, 0.0),
				new Vector3D(0.5, 0.0, 0.0),
				new Vector3D(0.0, -3.0, 0.0),
				new Vector3D(3.0, 0.0, 0.0)
		};

		Vector3D project = V3.projectPolyline(point, polyline);
		
		assertTrue(project.y > 0);
	}

	@Test
	public void testPolylineProjectNullIfNone() {
		Vector3D point = new Vector3D(1.0, 1.0, 0.0);
		Vector3D[] polyline = new Vector3D[] { 
				new Vector3D(-4.0, 6.0, 0.0),
				new Vector3D(0.0, 4.0, 0.0),
				new Vector3D(2.0, 8.0, 0.0)
		};

		Vector3D project = V3.projectPolyline(point, polyline);
		
		assertNull(project);
	}
}
