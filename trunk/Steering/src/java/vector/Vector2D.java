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

package vector;

/**
 * Vector class useful for manipulating and 
 * representing positions and vectors in 2 dimensional space
 * 
 * @author Nate Young
 */
public class Vector2D {

	public double x;
	public double y;
	
	public Vector2D() {	}

	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	/**
	 * Determines equality to three decimal places
	 */
	public boolean equals(Object obj) {
		if(obj instanceof Vector2D) {
			Vector2D other = (Vector2D) obj;
			boolean result = (approxEqual(3, other.x, this.x) && approxEqual(3, other.y, this.y));
			return result;
		}
		return false;
	}
	
	private boolean approxEqual(int places, double value1, double value2) {
		double compare = Math.pow(10, -1 * places);
		return (Math.abs((float)value1 - (float)value2) < compare);	//FIXME: this narrowing cast is a BAAAD idea
	}
}
