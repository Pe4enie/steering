package steering.containers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import vector.Vector2D;


public class RectangleTest {

	private Rectangle rectangle;

	@Before
	public void setUp() {
		rectangle = new Rectangle(5.0, 5.0);
	}
	
	@Test
	public void testDefaultProjectsHorizontally() {
		Vector2D projection = rectangle.project(new Vector2D(1.0, 1.0));
		assertEquals(new Vector2D(1.0, 0.0), projection);
	}
	
	@Test
	public void testCanProjectVertically() {
		rectangle.setProjection(false);
		Vector2D projection = rectangle.project(new Vector2D(1.0, 1.0));
		assertEquals(new Vector2D(0.0, 1.0), projection);		
	}
	
	@Test
	public void testOutsideContainer() {
		assertFalse(rectangle.within(new Vector2D(-1.0, 3.0)));
		assertFalse(rectangle.within(new Vector2D(6.0, 3.0)));
		assertFalse(rectangle.within(new Vector2D(3.0, -1.0)));
		assertFalse(rectangle.within(new Vector2D(3.0, 6.0)));
	}
	
	@Test
	public void testInsideContainer() {
		assertTrue(rectangle.within(new Vector2D(3.0, 3.0)));
	}
}
