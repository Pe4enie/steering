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

import vector.V;
import vector.Vector2D;

/**
 * Class implementing a simple approach to agent locomotion
 * 
 * Apologies to Craig Reynolds. This class is boldfaced theft of (and probably
 * a poorly implemented attempt at) his ideas described in his paper "Steering
 * Behaviors For Autonomous Characters"
 * 
 * @author Nate Young
 */
public class SimpleLocomotion implements Locomotion {

	// these are default values, your code should set them to better values
	private double maxForce = 1.5; // maximum amount of change in velocity
	private double maxSpeed = 1.5; // maximum allowable velocity									

	private double mass;
	private Vector2D position;
	private Vector2D velocity;

	public SimpleLocomotion(double mass, Vector2D pos, Vector2D v) {
		this.mass = mass;
		this.position = pos;
		this.velocity = v;
	}

	@Override
	public double mass() {
		return mass;
	}

	@Override
	public Vector2D position() {
		return position;
	}

	@Override
	public Vector2D velocity() {
		return velocity;
	}

	@Override
	public void steer(Vector2D steeringForce) {
		Vector2D force = clip(maxForce, steeringForce);
		Vector2D acceleration = V.mult((1 / mass), force);
		setVelocity(V.add(velocity, acceleration));
	}

	private static Vector2D clip(double max, Vector2D vector) {
		if (V.magnitude(vector) > max) {
			return V.mult(max, V.unitOf(vector));
		}
		return vector;
	}

	@Override
	public void move() {
		position = V.add(velocity, position);
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = clip(maxSpeed, velocity);
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public double getMaxForce() {
		return maxForce;
	}

	public void setMaxForce(double maxForce) {
		this.maxForce = maxForce;
	}
}