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
import steering.Locomotion;
import steering.Steering;
import vector.Vector2D;

public class Flee implements Behavior {

	private Vector2D position;
	private Locomotion boid;

	public Flee(Locomotion boid, Vector2D position) {
		this.boid = boid;
		this.position = position;
	}
	
	@Override
	public Vector2D steeringForce() {
		return Steering.flee(boid, position);
	}

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public Locomotion getBoid() {
		return boid;
	}

	public void setBoid(Locomotion boid) {
		this.boid = boid;
	}

}
