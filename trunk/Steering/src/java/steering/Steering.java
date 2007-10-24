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

import java.util.Collection;

import vector.V;
import vector.Vector2D;

/**
 * Class implementing methods for Steering Behavior.
 * 
 * Apologies to Craig Reynolds.  This class is boldfaced
 * theft of (and probably a poorly implemented attempt at) his ideas 
 * described in his paper "Steering Behaviors For Autonomous Characters"
 * 
 * @author Nate Young
 */
public class Steering {

	/**
	 * Return a steering vector that seeks the desired location
	 */
	public static Vector2D seek(Locomotion seeker, Vector2D desired) {
		return V.sub(desired, seeker.position());
	}
	
	/**
	 * Return a steering vector that flees the given location
	 */
	public static Vector2D flee(Locomotion fleer, Vector2D undesired) {
		return V.mult(-1, seek(fleer, undesired));
	}
	
	/**
	 * Return a steering vector that seeks the future location of the given quarry 
	 */
	public static Vector2D pursue(Locomotion pursuer, Locomotion quarry) {
		return seek(pursuer, V.add(quarry.position(), quarry.velocity()));
	}
	
	/**
	 * Return a steering vector fleeing from the given predator
	 */
	public static Vector2D evade(Locomotion fleer, Locomotion predator) {
		return flee(fleer, V.add(predator.position(), predator.velocity()));
	}
	
	/**
	 * Return a steering vector that reduces the seeker's velocity as it approaches 
	 * the given destination
	 */
	public static Vector2D arrive(Locomotion seeker, Vector2D destination) {
		return arrive(seeker, destination, 1.0);
	}
	
	/**
	 * Return a steering vector that reduces the seeker's velocity as it approaches 
	 * the given destination
	 * 
	 * @param speedCoefficient multiplies the speed at which the seeker approaches the destination
	 */
	public static Vector2D arrive(Locomotion seeker, Vector2D destination, double speedCoefficient) {
		//desired speed is directly proportional to distance from destination
		double desiredSpeed = speedCoefficient * V.magnitude(V.sub(destination, seeker.position()));
		
		return V.mult(desiredSpeed, V.unitOf(seek(seeker, destination)));
	}
	
	/**
	 * Returns a steering vector that aims to avoid the closest intersecting object in its path
	 * 
	 * @param others Other Locomotion objects to avoid if in the boid's path
	 * @param distance The distance from the boid's velocity vector in order to be considered in the boid's path
	 */
	public static Vector2D obstacleAvoidance(Locomotion boid, double distance, Collection<Locomotion> others) {
		double closest = V.magnitude(boid.velocity());
		Vector2D avoid = null;
		
		//calculate the closest intersection with boid's velocity vector
		for(Locomotion other : others) {
			Vector2D otherVector = V.sub(other.position(), boid.position());
			
			//point at which the other object *would* intersect *if* it does 
			Vector2D intersect = V.project(otherVector, boid.velocity());
			
			Vector2D ortho = V.sub(other.position(), intersect);
			
			//check for intersection and that its the closest
			if(V.magnitude(ortho) < distance && 
			   V.magnitude(intersect) < closest) {
				closest = V.magnitude(intersect);
				//to avoid, move perpendicularly away from the obstacle
				avoid = V.mult(-1 * V.magnitude(boid.velocity()), V.unitOf(ortho));
			}
		}
		
		//calculate avoidance
		if(avoid == null) {
			//nothing to avoid, so no steering change
			return new Vector2D(0, 0);
		}
		
		return avoid;
	}
}