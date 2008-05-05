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
 * Vector math and operations class.
 * 
 * @author Nate Young
 */
public class V3 {

    /**
     * Add vector v1 to vector v2
     */
    public static Vector3D add(Vector3D v1, Vector3D v2) {
        Vector3D vector = new Vector3D();
        vector.x = v1.x + v2.x;
        vector.y = v1.y + v2.y;
	vector.z = v1.z + v2.z;

        return vector;
    }

    /**
     * Subtract vector v2 from vector v1
     */
    public static Vector3D sub(Vector3D v1, Vector3D v2) {
        Vector3D vector = new Vector3D();
        vector.x = v1.x - v2.x;
        vector.y = v1.y - v2.y;
        vector.z = v1.z - v2.z;

        return vector;
    }

    /**
     * Scale vector v1 by a constant double value
     */
    public static Vector3D mult(double by, Vector3D v1) {
        Vector3D vector = new Vector3D();
        vector.x = v1.x * by;
        vector.y = v1.y * by;
	vector.z = v1.z * by;

        return vector;
    }

    /**
     * Calculate the length of the given vector
     */
    public static double magnitude(Vector3D v1) {
        return Math.sqrt(v1.x * v1.x + v1.y * v1.y + v1.z * v1.z);
    }

    /**
     * Returns a vector of length 1.0 with the same direction as the given vector
     */
    public static Vector3D unitOf(Vector3D v1) {
        double magnitude = magnitude(v1);

        return mult((1/magnitude), v1);
    }

    /**
     * Calculates the dot product of two vectors 
     */
    public static double dot(Vector3D v1, Vector3D v2) {
        return (v1.x * v2.x + v1.y * v2.y + v1.z * v2.z);
    }
     
    public static Vector3D cross(Vector3D v1, Vector3D v2) {
    	Vector3D vector = new Vector3D();
		vector.x = (v1.y * v2.z) - (v1.z * v2.y);
		vector.y = (v1.x * v2.z) - (v1.z * v2.x);
		vector.z = (v1.x * v2.y) - (v1.y * v2.x);

		return vector;
    }

    public static Vector3D resizeTo(double length, Vector3D vector) {
    	return V3.mult(length, V3.unitOf(vector));
    }
    
    ///**
    // * Projects one vector onto another
    // */
    //public static Vector3D project(Vector3D vector, Vector3D onto) {
    //	return mult((dot(vector, onto) / dot(onto, onto)), onto);
    //}
}
