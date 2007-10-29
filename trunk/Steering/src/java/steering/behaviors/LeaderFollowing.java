package steering.behaviors;

import steering.Behavior;
import steering.Locomotion;
import steering.Neighborhood;
import steering.Steering;
import vector.Vector2D;

public class LeaderFollowing implements Behavior {

	
	private Neighborhood neighborhood;
	private double offset;
	private Locomotion leader;
	private Locomotion boid;
	private double distance;

	public LeaderFollowing(Locomotion boid, Locomotion leader, double offset, Neighborhood neighborhood, double distance) {
		this.boid = boid;
		this.leader = leader;
		this.offset = offset;
		this.neighborhood = neighborhood;
		this.distance = distance;
	}
	
	@Override
	public Vector2D steeringForce() {
		return Steering.followLeader(boid, leader, offset, neighborhood, distance);
	}

	public Neighborhood getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(Neighborhood neighborhood) {
		this.neighborhood = neighborhood;
	}

	public double getOffset() {
		return offset;
	}

	public void setOffset(double offset) {
		this.offset = offset;
	}

	public Locomotion getLeader() {
		return leader;
	}

	public void setLeader(Locomotion leader) {
		this.leader = leader;
	}

	public Locomotion getBoid() {
		return boid;
	}

	public void setBoid(Locomotion boid) {
		this.boid = boid;
	}

}
