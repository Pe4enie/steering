package steering.behaviors;

import java.util.List;

import steering.Behavior;
import vector.V;
import vector.Vector2D;

public class PriorityCombinationBehavior implements Behavior {

	List<Behavior> behaviors;
	
	@Override
	public Vector2D steeringForce() {
		for(Behavior behavior : behaviors) {
			Vector2D steering = behavior.steeringForce();
			if(V.magnitude(steering) > 0)
				return steering;
		}
		return new Vector2D(0.0, 0.0);
	}

	public List<Behavior> getBehaviors() {
		return behaviors;
	}

	public void setBehaviors(List<Behavior> behaviors) {
		this.behaviors = behaviors;
	}

}
