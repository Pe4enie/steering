package steering;

import vector.Vector3D;

public class SimplePath implements Path {

	protected Vector3D[] vertices;
	protected int index;
	private double radius;
	
	public SimplePath(Vector3D[] vertices, double radius) {
		this.index = 0;
		this.vertices = vertices;
		this.radius = radius;
	}
	
	@Override
	public Vector3D curr() {
		return vertices[index];
	}

	@Override
	public double getRadius() {
		return radius;
	}

	@Override
	public Vector3D next() {
		index++;		
		return curr();
	}

	@Override
	public Vector3D prev() {
		return vertices[index - 1];
	}

	public int size() {
		return vertices.length;
	}

	public void reset() {
		index = 0;
	}
}
