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

import vector.Vector2D;

/**
 * Interface for agents implementing Locomotion
 * 
 * Apologies to Craig Reynolds.  This class is boldfaced
 * theft of (and probably a poorly implemented attempt at) his ideas 
 * described in his paper "Steering Behaviors For Autonomous Characters"
 * 
 * @author Nate Young
 */
public interface Locomotion {

	public Vector2D position();
	
	public double mass();
	
	public Vector2D velocity();
	
	public void steer(Vector2D steeringForce);

	public void move();	
}
