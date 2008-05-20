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
 * Class implementing methods for Steering Behavior.
 * 
 * Apologies to Craig Reynolds.  This class is boldfaced
 * theft of (and probably a poorly implemented attempt at) his ideas 
 * described in his paper "Steering Behaviors For Autonomous Characters"
 * 
 * @author Nate Young
 */
public class Steering {
    
    public static Vector3D seek(SimpleLocomotion seeker, Vector3D desired, 
    		double speed) {
    	Vector3D desiredVelocity = 
    		V3.resizeTo(speed, V3.sub(desired, seeker.position()));
    	return V3.sub(desiredVelocity, seeker.velocity());
    }
    
    public static Vector3D flee(SimpleLocomotion fleer, Vector3D target, double speed) {
    	return V3.mult(-1, seek(fleer, target, speed));
    }

    public static Vector3D pursue(SimpleLocomotion seeker, SimpleLocomotion quarry, double speed, int t) {
    	Vector3D future = V3.add(quarry.position(), V3.mult(t, quarry.velocity()));
    	return seek(seeker, future, speed);
    }
    
    public static Vector3D evade(SimpleLocomotion fleer, SimpleLocomotion pursuer, double speed, int t) {
    	return V3.mult(-1, pursue(fleer, pursuer, speed, t));
    }
    
    /**
     * Return a steering vector that reduces the seeker's velocity
     * as it approaches the given destination
     */
    public static Vector3D arrive(SimpleLocomotion seeker, Vector3D destination, 
    		double speed, double slowRadius, ArrivalFunction fn) {
    	double distance = V3.magnitude(V3.sub(destination, seeker.position()));
    	if(distance < slowRadius)
    		return Steering.seek(seeker, destination, fn.speedOfArrival(distance));
    	return Steering.seek(seeker, destination, speed);
    }

    public static Vector3D arriveBehind(SimpleLocomotion seeker, Vector3D destination,
    		double speed, double slowRadius, ArrivalFunction fn, 
    		SimpleLocomotion ahead, double followDist, double followRadius, 
    		ArrivalFunction followFn) {
    	Vector3D backwards = V3.mult(-1, ahead.getOrientation()[SimpleLocomotion.FORWARD]);
    	Vector3D behind = V3.add(ahead.position(), V3.resizeTo(followDist, backwards));
    	double distance = V3.magnitude(V3.sub(ahead.position(), seeker.position()));
    	if(distance < followDist * 2)
    		return arrive(seeker, behind, speed, followRadius, followFn);
    	return arrive(seeker, destination, speed, slowRadius, fn);
    }
    
	public static Vector3D followPath(SimpleLocomotion seeker, Vector3D[] path, double pathRadius, double vFactor, double speed) {
		//future position from vFactor, velocity and position
		Vector3D futurePos = V3.add(seeker.position(), V3.mult(vFactor, seeker.velocity()));
		//project onto path
		Vector3D projection = V3.projectPolyline(futurePos, path);
		if(projection == null || V3.magnitude(V3.sub(projection, seeker.position())) < pathRadius) {
			return new Vector3D(0.0, 0.0, 0.0);
		}
		return Steering.seek(seeker, projection, speed);
	}

	public static Vector3D followPath(SimpleLocomotion seeker, Path path,
			double switchDist, double vFactor, double speed) {
		double distFrom = V3.magnitude(V3.sub(path.curr(), seeker.position()));
		if(distFrom < switchDist) {
			path.next();
		}
		
		Vector3D futurePos = V3.add(seeker.position(), V3.mult(vFactor, seeker.velocity()));
		Vector3D segment = V3.sub(path.prev(), path.curr());
		Vector3D projection = V3.project(futurePos, segment);
		Vector3D adjust = V3.sub(projection, futurePos);
		
		if(V3.magnitude(adjust) < path.getRadius()) {
			return V3.add(seek(seeker, path.curr(), speed), adjust);
		}
		
		return seek(seeker, path.curr(), speed);
	}
}