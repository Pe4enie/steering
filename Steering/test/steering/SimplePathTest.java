package steering;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import vector.Vector3D;


public class SimplePathTest {

	private SimplePath simplePath;
	private Vector3D[] path;
	
	@Before
	public void setUp() {
		path = new Vector3D[] {
			new Vector3D(1.0, 2.0, 3.0),
			new Vector3D(5.0, 4.0, 3.0)
		};
		simplePath = new SimplePath(path, 5.0);
	}
	
	@Test
	public void testFirstCurrCallReturnsFirstVert() {
		Vector3D vert = simplePath.curr();
		
		assertEquals(path[0].x, vert.x);
		assertEquals(path[0].y, vert.y);
		assertEquals(path[0].z, vert.z);
	}
	
	@Test
	public void testNextReturnsNextVert() {
		Vector3D vert = simplePath.next();
		
		assertEquals(path[1].x, vert.x);
		assertEquals(path[1].y, vert.y);
		assertEquals(path[1].z, vert.z);
	}
	
	@Test
	public void testNextSetsCurrVert() {
		Vector3D vert0 = simplePath.next();
		Vector3D vert1 = simplePath.curr();
		
		assertEquals(vert0.x, vert1.x);
		assertEquals(vert0.y, vert1.y);
		assertEquals(vert0.z, vert1.z);
	}
	
	@Test
	public void testPrevVert() {
		Vector3D vert0 = simplePath.curr();
		simplePath.next();
		Vector3D vert1 = simplePath.prev();
		
		assertEquals(vert0.x, vert1.x);
		assertEquals(vert0.y, vert1.y);
		assertEquals(vert0.z, vert1.z);
	}
}
