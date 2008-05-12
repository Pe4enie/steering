package steering;

import vector.Vector3D;

public class CircularPath extends SimplePath {
	
	public CircularPath(Vector3D[] points, double radius) {
		super(points, radius);
	}
	
	public Vector3D next() {
		if(index + 1 >= size()) {
			index = -1;
		}
		return super.next();
	}
	
	public Vector3D prev() {
		if(index == 0) {
			return vertices[vertices.length - 1];
		}
		return super.prev();
	}
}
