package steering.behaviors;

import java.util.ArrayList;
import java.util.Collection;

import steering.Behavior;
import vector.V;
import vector.Vector2D;

public class WeightedCombinationBehavior implements Behavior {

	public class WeightedBehavior {
		private double weight;
		private Behavior behavior;
		public WeightedBehavior(Behavior behavior, double weight) {
			this.behavior = behavior;
			this.weight = weight;
		}
		public double getWeight() {
			return weight;
		}
		public void setWeight(double weight) {
			this.weight = weight;
		}
		public Behavior getBehavior() {
			return behavior;
		}
		public void setBehavior(Behavior behavior) {
			this.behavior = behavior;
		}
	}
	
	private Collection<WeightedBehavior> behaviors = new ArrayList<WeightedBehavior>();
	
	@Override
	public Vector2D steeringForce() {
		Vector2D steering = new Vector2D(0.0, 0.0);
		for(WeightedBehavior behavior : this.behaviors) {
			Vector2D component = behavior.getBehavior().steeringForce();
			steering = V.add(V.mult(behavior.getWeight(), component), steering);
		}
		return steering;
	}

	public void addBehavior(Behavior behavior) {
		double averageWeight = 1.0 / (behaviors.size() + 1);
		for(WeightedBehavior oldBehavior : this.behaviors) {
			oldBehavior.setWeight(averageWeight);
		}
		this.behaviors.add(new WeightedBehavior(behavior, averageWeight));
	}
	
	public void addBehavior(Behavior behavior, double weight) {
		behaviors.add(new WeightedBehavior(behavior, weight));
	}
	
	public Collection<WeightedBehavior> getBehaviors() {
		return behaviors;
	}

	public void setBehaviors(Collection<WeightedBehavior> behaviors) {
		this.behaviors = behaviors;
	}

}
