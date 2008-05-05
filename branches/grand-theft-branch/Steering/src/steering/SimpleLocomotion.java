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

import vector.V3;
import vector.Vector3D;

/**
 * Class implementing a simple approach to agent locomotion
 * 
 * Apologies to Craig Reynolds. This class is boldfaced theft of (and probably
 * a poorly implemented attempt at) his ideas described in his paper "Steering
 * Behaviors For Autonomous Characters"
 * 
 * @author Nate Young
 */
public class SimpleLocomotion {

    public static final int FORWARD = 0;
    public static final int SIDE = 1;
    public static final int UP = 2;

    private double maxForce; // maximum amount of change in velocity
    private double maxSpeed; // maximum allowable velocity

    private double mass;
    private Vector3D position;
    private Vector3D velocity;
    private Vector3D[] orientation;

    public SimpleLocomotion(double mass, Vector3D pos, Vector3D v, 
    		double maxForce, double maxSpeed) {
    	this.mass = mass;
    	this.position = pos;
    	this.velocity = v;
    	this.maxForce = maxForce;
    	this.maxSpeed = maxSpeed;
    	this.orientation = new Vector3D[] { null, null, new Vector3D(0.0, 0.0, 1.0) };
    	orient();
    }
    
    public SimpleLocomotion(double mass, Vector3D pos, Vector3D v, 
    		double maxForce, double maxSpeed, Vector3D[] orientation) {
    	this.mass = mass;
    	this.position = pos;
    	this.velocity = v;
    	this.maxForce = maxForce;
    	this.maxSpeed = maxSpeed;
    	this.orientation = orientation;
    }

    public double mass() {
    	return mass;
    }

    public Vector3D position() {
    	return position;
    }

    public Vector3D velocity() {
    	return velocity;
    }

    public void steer(Vector3D steeringForce) {
    	Vector3D force = clip(maxForce, steeringForce);
    	Vector3D acceleration = V3.mult((1 / mass), force);
    	setVelocity(V3.add(velocity, acceleration));
    	orient();
    }

    private static Vector3D clip(double max, Vector3D vector) {
    	if (V3.magnitude(vector) > max) {
    		return V3.resizeTo(max, vector);
    	}
    	return vector;
    }

    public void orient() {
    	orientation[FORWARD] = V3.unitOf(this.velocity);
    	Vector3D approxUp = V3.unitOf(orientation[UP]);
    	orientation[SIDE] = V3.cross(orientation[FORWARD], approxUp);
    	orientation[UP] = V3.cross(orientation[FORWARD], orientation[SIDE]);
    }

    public void move() {
    	this.position = V3.add(velocity, position);
    }

    public void setMass(double mass) {
    	this.mass = mass;
    }

    public void setPosition(Vector3D position) {
    	this.position = position;
    }

    public void setVelocity(Vector3D velocity) {
    	this.velocity = clip(maxSpeed, velocity);
    }

    public double getMaxSpeed() {
    	return this.maxSpeed;
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

	public Vector3D[] getOrientation() {
		return orientation;
	}
}