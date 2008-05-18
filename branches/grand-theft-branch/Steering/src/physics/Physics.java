package physics;

import vector.V3;
import vector.Vector3D;

public class Physics {

	public static Vector3D[] collide(double mass1, Vector3D center1,
			Vector3D velo1, double mass2, Vector3D center2, Vector3D velo2) {
		Vector3D[] result = new Vector3D[2];
		
		Vector3D lineOfForce = V3.sub(center2, center1);
		
		Vector3D along1 = V3.project(velo1, lineOfForce);
		Vector3D along2 = V3.project(velo2, lineOfForce);
		
		Vector3D tangent1 = V3.sub(velo1, along1);
		Vector3D tangent2 = V3.sub(velo2, along2);

		Vector3D vprime1 = elasticCollision(mass1, along1, mass2, along2);
		Vector3D vprime2 = elasticCollision(mass2, along2, mass1, along1);
		
		result[0] = V3.add(vprime1, tangent1);
		result[1] = V3.add(vprime2, tangent2);
		
		return result;
	}

	public static Vector3D elasticCollision(double mass1, Vector3D velo1,
			double mass2, Vector3D velo2) {
		return
		V3.mult(1 / (mass1 + mass2),
				V3.add(V3.mult((mass1 - mass2), velo1),
					   V3.mult(2 * mass2, velo2)));
	}
}
