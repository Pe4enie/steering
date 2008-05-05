package vector;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class Vector3DTest {

	@Test
	public void testDefaultConstructor() {
		Vector3D v = new Vector3D();
		v.x = 5.0;
		v.y = 4.0;
		v.z = 3.0;
		assertEquals(5.0, v.x);
		assertEquals(4.0, v.y);
		assertEquals(3.0, v.z);
	}
	
	@Test
	public void testInitialConstructor() {
		Vector3D v = new Vector3D(5.0, 4.0, 3.0);
		assertEquals(5.0, v.x);
		assertEquals(4.0, v.y);
		assertEquals(3.0, v.z);		
	}
}
