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
package steering.behaviors;

import steering.Behavior;
import steering.Container;
import steering.Locomotion;
import steering.Steering;
import vector.Vector2D;

public class Containment implements Behavior {

	private double offset;
	private Container container;
	private Locomotion boid;

	public Containment(Locomotion boid, Container container, double offset) {
		this.boid = boid;
		this.container = container;
		this.offset = offset;
	}
	
	@Override
	public Vector2D steeringForce() {
		return Steering.contain(boid, container, offset);
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	public Locomotion getBoid() {
		return boid;
	}

	public void setBoid(Locomotion boid) {
		this.boid = boid;
	}

}
