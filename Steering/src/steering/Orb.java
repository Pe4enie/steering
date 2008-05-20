package steering;

import vector.V3;
import vector.Vector3D;

public class Orb extends SimpleLocomotion {

	protected double radius;

	public boolean collided;
	public int rebound = 0;
	
	public Orb(double mass, Vector3D pos, Vector3D v, 
			double radius, double maxForce, double maxSpeed) {
		super(mass, pos, v, maxForce, maxSpeed);
		this.radius = radius;
	}
	
	public double radius() {
		return radius;
	}

	public void collide(Orb other) {
		Vector3D lineOfForce = V3.sub(other.position(), this.position());
		
		Vector3D along1 = V3.project(velocity, lineOfForce);
		Vector3D along2 = V3.project(other.velocity, lineOfForce);
		
		Vector3D tangent = V3.sub(velocity, along1);
		
		Vector3D vprime = elasticCollision(mass, along1, other.mass(), along2);
		
		Vector3D result = V3.add(vprime, tangent);
		
		this.velocity = result;
	}
	
	public static Vector3D elasticCollision(double mass1, Vector3D velo1,
			double mass2, Vector3D velo2) {
		return V3.mult(1 / (mass1 + mass2),
				       V3.add(V3.mult((mass1 - mass2), velo1),
				    		  V3.mult(2 * mass2, velo2)));
	}
}
