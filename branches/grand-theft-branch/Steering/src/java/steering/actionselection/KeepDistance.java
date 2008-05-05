package steering.actionselection;

import steering.ActionSelection;
import steering.Behavior;
import steering.Locomotion;
import steering.behaviors.Flee;
import vector.V;
import vector.Vector2D;

public class KeepDistance implements ActionSelection {

	private double radius;
	private Flee flee;
	
	public KeepDistance(Locomotion boid, Vector2D undesired, double radius) {
		flee = new Flee(boid, undesired);
		this.radius = radius;
	}
	
	@Override
	public Behavior selectAction() {
		if(V.magnitude(V.sub(flee.getPosition(), flee.getBoid().position())) > radius) {
			return new Behavior() {
				@Override
				public Vector2D steeringForce() {
					return new Vector2D(0.0, 0.0);
				}
			};
		}
		return flee;
	}
}