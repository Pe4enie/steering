package vector;

public class Plane {

	public enum Isxn { LINE, POINT, NONE };
	
	private Vector3D normal;
	private double distance;
	
	public Plane(Vector3D n, double d) {
		initPlane(n, d);
	}

	public Plane(Vector3D point0, Vector3D point1, Vector3D point2) {
		Vector3D n = V3.cross(V3.sub(point1, point0), V3.sub(point2, point0));
		double d = -1 * V3.dot(normal, point0);
		initPlane(n, d);
	}
	
	private void initPlane(Vector3D n, double d) {
		if(V3.magnitude(n) == 1) {
			this.normal = n;
			this.distance = d;
		} else {
			this.distance = d / V3.magnitude(n);
			this.normal = V3.unitOf(n);
		}
	}
	
	public boolean isParallelTo(Vector3D v) {
		return V3.dot(this.normal, v) == 0;
	}
	
	public Vector3D intersectsAt(Vector3D point, Vector3D direction) {
		if(isParallelTo(direction)) {
			if(distanceFrom(point) == 0) {
				return new Vector3D(direction.x, direction.y, direction.z, Plane.Isxn.LINE);
			}
		}
		double x1, y1, z1, w1;
		double x2, y2, z2, w2;
		x1 = this.normal.x; y1 = this.normal.y; z1 = this.normal.z; w1 = this.distance;
		x2 = point.x; y2 = point.y; z2 = point.z; w2 = 1;
		
		double numer = -1 * (x1*x2 + y1*y2 + z1*z2 + w1*w2);
		
		x2 = direction.x; y2 = direction.y; z2 = direction.z; w2 = 0;
		double denom = x1*x2 + y1*y2 + z1*z2 + w1*w2;
		
		double t = numer / denom;
		return V3.add(point, V3.mult(t, direction));
	}

	public double distanceFrom(Vector3D point) {
		double x1, y1, z1, w1;
		double x2, y2, z2, w2;
		
		x1 = this.normal.x; y1 = this.normal.y; z1 = this.normal.z; w1 = this.distance;
		x2 = point.x; y2 = point.y; z2 = point.z; w2 = 1;
		
		return x1*x2 + y1*y2 + z1*z2 + w1*w2;
	}
}
