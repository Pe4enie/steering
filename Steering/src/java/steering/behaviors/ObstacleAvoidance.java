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

import java.util.Collection;

import steering.Behavior;
import steering.Locomotion;
import steering.Steering;
import vector.Vector2D;

public class ObstacleAvoidance implements Behavior {

	private Collection<Locomotion> others;
	private double intersectDistance;
	private Locomotion boid;

	public ObstacleAvoidance(Locomotion boid, double intersectDistance, Collection<Locomotion> others) {
		this.boid = boid;
		this.intersectDistance = intersectDistance;
		this.others = others;
	}
	
	@Override
	public Vector2D steeringForce() {
		return Steering.avoidObstacle(boid, intersectDistance, others);
	}

	public Collection<Locomotion> getOthers() {
		return others;
	}

	public void setOthers(Collection<Locomotion> others) {
		this.others = others;
	}

	public double getIntersectDistance() {
		return intersectDistance;
	}

	public void setIntersectDistance(double intersectDistance) {
		this.intersectDistance = intersectDistance;
	}

	public Locomotion getBoid() {
		return boid;
	}

	public void setBoid(Locomotion boid) {
		this.boid = boid;
	}

}
