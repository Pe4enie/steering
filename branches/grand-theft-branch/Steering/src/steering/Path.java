package steering;

import vector.Vector3D;

public interface Path {

	public Vector3D curr();
	public Vector3D next();
	public Vector3D prev();
	public double getRadius();
}
