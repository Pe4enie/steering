package steering.behaviors;

import steering.Behavior;
import steering.Locomotion;
import steering.Neighborhood;
import vector.V;
import vector.Vector2D;

public class Flocking implements Behavior {

	private Separation separation;
	private Alignment alignment;
	private Cohesion cohesion;
	
	private double separationWeight;
	private double alignmentWeight;
	private double cohesionWeight;
	
	public Flocking(Locomotion boid, Neighborhood hood) {
		this(boid, hood, hood, hood, 1.0 / 3.0, 1.0 / 3.0, 1.0 / 3.0);
	}
	
	public Flocking(Locomotion boid, Neighborhood hood, double sweight, double aweight, double cweight) {
		this(boid, hood, hood, hood, sweight, aweight, cweight);
	}
	
	public Flocking(Locomotion boid, Neighborhood shood, Neighborhood ahood, Neighborhood chood, double sweight, double aweight, double cweight) {
		this.separationWeight = sweight;
		this.alignmentWeight = aweight;
		this.cohesionWeight = cweight;
		
		separation = new Separation(boid, shood);
		alignment = new Alignment(boid, ahood);
		cohesion = new Cohesion(boid, chood);
	}
	
	@Override
	public Vector2D steeringForce() {
		Vector2D separationVector = V.mult(separationWeight, separation.steeringForce());
		Vector2D alignmentVector = V.mult(alignmentWeight, alignment.steeringForce());
		Vector2D cohesionVector = V.mult(cohesionWeight, cohesion.steeringForce());
		
		return V.add(separationVector, V.add(alignmentVector, cohesionVector));
	}

}
