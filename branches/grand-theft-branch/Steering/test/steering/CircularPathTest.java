package steering;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import vector.Vector3D;


public class CircularPathTest {

	private CircularPath path;
	
	@Before
	public void setUp() {
		Vector3D[] points = new Vector3D[] {
				new Vector3D(1.0, 2.0, 3.0),
				new Vector3D(5.0, 4.0, 3.0)
		};
		path = new CircularPath(points, 5.0);
	}
	
	@Test
	public void testNextAfterLastIsFirst() {
		Vector3D first = path.curr();
		path.next();
		Vector3D afterLast = path.next();
		
		assertEquals(first.x, afterLast.x);
		assertEquals(first.y, afterLast.y);
		assertEquals(first.z, afterLast.z);
	}
	
	@Test
	public void testPrevBeforeFirstIsLast() {
		Vector3D last = path.next();
		path.next();
		Vector3D beforeFirst = path.prev();
		
		assertEquals(last.x, beforeFirst.x);
		assertEquals(last.y, beforeFirst.y);
		assertEquals(last.z, beforeFirst.z);	
	}
}
