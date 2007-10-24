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
public class V {

	/**
	 * Add vector v1 to vector v2
	 */
    public static Vector2D add(Vector2D v1, Vector2D v2) {
        Vector2D vector = new Vector2D();
        vector.x = v1.x + v2.x;
        vector.y = v1.y + v2.y;

        return vector;
    }

    /**
     * Subtract vector v2 from vector v1
     */
    public static Vector2D sub(Vector2D v1, Vector2D v2) {
        Vector2D vector = new Vector2D();
        vector.x = v1.x - v2.x;
        vector.y = v1.y - v2.y;

        return vector;
    }

    /**
     * Scale vector v1 by a constant double value
     */
    public static Vector2D mult(double by, Vector2D v1) {
        Vector2D vector = new Vector2D();
        vector.x = v1.x * by;
        vector.y = v1.y * by;

        return vector;
    }

    /**
     * Calculate the length of the given vector
     */
    public static double magnitude(Vector2D v1) {
        return Math.sqrt(v1.x * v1.x + v1.y * v1.y);
    }

    /**
     * Returns a vector of length 1.0 with the same direction as the given vector
     */
    public static Vector2D unitOf(Vector2D v1) {
        double magnitude = magnitude(v1);

        return mult((1/magnitude), v1);
    }

    /**
     * Calculates the dot product of two vectors 
     */
    public static double dot(Vector2D v1, Vector2D v2) {
        return (v1.x * v2.x + v1.y * v2.y);
    }
        
    /**
     * Return a random vector with components no larger than the given bounds
     */
    public static Vector2D randomVector(double xbound, double ybound) {
		return new Vector2D((Math.random() * xbound), (Math.random() * ybound));
	}
    
    /**
     * Projects one vector onto another
     */
    public static Vector2D project(Vector2D vector, Vector2D onto) {
    	double hypotenuse = magnitude(vector);
    	
    	double adjacent = hypotenuse * Math.cos(angle(onto, vector));
    	
    	return mult(adjacent, unitOf(onto));
    }

	public static double angle(Vector2D v1, Vector2D v2) {
		return Math.acos(dot(unitOf(v1), unitOf(v2)));
	}
}
