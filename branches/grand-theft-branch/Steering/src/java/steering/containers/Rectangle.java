package steering.containers;

import steering.Container;
import vector.V;
import vector.Vector2D;

public class Rectangle implements Container {

	private static final Vector2D horiz = new Vector2D(1.0, 0.0);
	private static final Vector2D vert = new Vector2D(0.0, 1.0);
	
	private double width;
	private double height;
	
	private boolean horizProject;

	public Rectangle(double width, double height) {
		this.horizProject = true;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public Vector2D project(Vector2D vector) {
		if(horizProject)
			return V.project(vector, horiz);
		return V.project(vector, vert);
	}

	@Override
	public boolean within(Vector2D point) {
		if(point.x <= width && point.x >= 0)
			if(point.y <= height && point.y >= 0)
				return true;
		return false;
	}

	public void setProjection(boolean horiz) {
		this.horizProject = horiz;
	}	
}
