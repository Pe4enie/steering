// Copyright (c) 2007 Nate H. Young All rights reserved. Use is
// subject to license terms.

// This file is part of Steering.
//
// Steering is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// Steering is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Steering; if not, write to the Free Software
// Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
package steering;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import vector.Vector2D;


public class SimpleLocomotionTest {

	SimpleLocomotion locomotion;
	
	@Before
	public void setUp() {
		locomotion = new SimpleLocomotion(1.0, new Vector2D(0.0, 0.0), new Vector2D(0.0, 0.0));
		locomotion.setMaxSpeed(Math.sqrt(8.0));
		locomotion.setMaxForce(4.0);
	}
	
	@Test
	public void testSetVelocityBelowMaxSpeed() {
		Vector2D newVelocity = new Vector2D(1.0, 1.0);
		locomotion.setVelocity(newVelocity);
		assertEquals(newVelocity, locomotion.velocity());
	}

	@Test
	public void testSetVelocityAboveMaxSpeed() {
		locomotion.setVelocity(new Vector2D(5.0, 5.0));
		assertEquals(new Vector2D(2.0, 2.0), locomotion.velocity());
	}
	
	@Test
	public void testSteeringForceLessThanMax() {
		locomotion.setVelocity(new Vector2D(1.0, 1.0));
		locomotion.steer(new Vector2D(1.0, -1.0));
		assertEquals(new Vector2D(2.0, 0.0), locomotion.velocity());
	}
	
	@Test
	public void testSteeringForceMoreThanMax() {
		locomotion.setMaxSpeed(100.0);
		locomotion.setVelocity(new Vector2D(4.0, 4.0));
		locomotion.steer(new Vector2D(4.0, -4.0));
		assertEquals(new Vector2D(4.0 + Math.sqrt(8.0), 4.0 - Math.sqrt(8.0)), locomotion.velocity());
	}
	
	@Test
	public void testMove() {
		locomotion.move();
		assertEquals(new Vector2D(0.0, 0.0), locomotion.position());
		
		locomotion.setVelocity(new Vector2D(1.0, 1.0));
		locomotion.move();
		assertEquals(new Vector2D(1.0, 1.0), locomotion.position());
	}
}
