package steering.containers;

import steering.Container;
import vector.V;
import vector.Vector2D;

public class Circle implements Container {

	private Vector2D center;
	private double radius;

	public Circle(Vector2D center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	@Override
	public Vector2D project(Vector2D vector) {
		//project the vector onto the circle's radius
		return V.mult(radius, V.unitOf(vector));
	}

	@Override
	public boolean within(Vector2D point) {
		double distance = V.magnitude(V.sub(point, center));
		return (distance <= radius);
	}
}
