package steering.containers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import vector.Vector2D;


public class CircleTest {

	private Circle container;

	@Before
	public void setUp() {
		container = new Circle(new Vector2D(0.0, 0.0), 5.0);
	}
	
	@Test
	public void testProjection() {
		Vector2D projection = container.project(new Vector2D(1.0, 0.0));
		assertEquals(new Vector2D(5.0, 0.0), projection);
	}
	
	@Test
	public void testOutsideContainer() {
		assertFalse(container.within(new Vector2D(6.0, 0.0)));
	}
	
	@Test
	public void testInsideContainer() {
		assertTrue(container.within(new Vector2D(3.0, 0.0)));
	}
}
