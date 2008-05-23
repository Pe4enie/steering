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

import vector.Plane.Isxn;

/**
 * Vector class useful for manipulating and 
 * representing positions and vectors in 2 dimensional space
 * 
 * @author Nate Young
 */
public class Vector3D {

    public double x;
    public double y;
    public double z;
	
    public Isxn isxn = Isxn.NONE;
    
    public Vector3D() {	}

    public Vector3D(double x, double y, double z) {
    	this.x = x;
    	this.y = y;
    	this.z = z;
    }

	public Vector3D(double x, double y, double z, Isxn isxn) {
		this(x, y, z);
		this.isxn = isxn;
	}
}
