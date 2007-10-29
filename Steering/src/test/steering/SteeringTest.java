// Copyright (c) 2007 Nate H. Young All rights reserved. Use is
// subject to license terms.

// This file is part of Steering.
//
// Steering is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// Steering is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Steering; if not, write to the Free Software
// Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
package steering;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import vector.Vector2D;


public class SteeringTest {

	private Locomotion boid;
	private Locomotion otherboid;
	private Container mockContainer;
	private Neighborhood singleBoidMockNeighborhood;
	private Neighborhood emptyMockNeighborhood;
	private Neighborhood fullMockNeighborhood;
	
	@Before
	public void setUp() {
		boid = new SimpleLocomotion(1.0, new Vector2D(1.0, 1.0), new Vector2D(1.0, 1.0));
		otherboid = new SimpleLocomotion(1.0, new Vector2D(0.0, 1.0), new Vector2D(1.0, 1.0));
		
		mockContainer = new Container() {
			@Override
			public boolean within(Vector2D point) {
				if(point.x > 2)
					return false;
				return true;
			}

			@Override
			public Vector2D project(Vector2D vector) {
				return new Vector2D(2.0, 2.0);
			}			
		};

		emptyMockNeighborhood = new Neighborhood() {
			@Override
			public Collection<Locomotion> findNearby(Locomotion boid) {
				return new ArrayList<Locomotion>();
			}
			@Override
			public double getRadius() {
				return 1.0;
			}
		};
		
		singleBoidMockNeighborhood = new Neighborhood() {
			@Override
			public Collection<Locomotion> findNearby(Locomotion boid) {
				Locomotion boid1 = new SimpleLocomotion(1.0, new Vector2D(2.0, 1.0), new Vector2D(-1.0, 1.0));
				return Arrays.asList(boid1);
			}
			@Override
			public double getRadius() {
				return 1.0;
			}
		};

		fullMockNeighborhood = new Neighborhood() {
			@Override
			public Collection<Locomotion> findNearby(Locomotion boid) {
				Locomotion boid1 = new SimpleLocomotion(1.0, new Vector2D(2.0, 1.0), new Vector2D(-1.0, 1.0));
				Locomotion boid2 = new SimpleLocomotion(1.0, new Vector2D(-3.0, 3.0), new Vector2D(-1.0, 1.5));
				Locomotion boid3 = new SimpleLocomotion(1.0, new Vector2D(0.0, -5.0), new Vector2D(-1.0, 0.5));
				return Arrays.asList(boid1, boid2, boid3);
			}
			@Override
			public double getRadius() {
				return 1.0;
			}
		};
	}
	
	@Test
	public void testSeek() {
		Vector2D steer = Steering.seek(boid, new Vector2D(0.0, 0.0));
		assertEquals(new Vector2D(-1.0, -1.0), steer);
	}
	
	@Test
	public void testFlee() {
		Vector2D steer = Steering.flee(boid, new Vector2D(0.0, 0.0));
		assertEquals(new Vector2D(1.0, 1.0), steer);
	}
	
	@Test
	public void testPursuit() {
		Vector2D steer = Steering.pursue(boid, otherboid);
		assertEquals(new Vector2D(0.0, 1.0), steer);
	}
	
	@Test
	public void testEvasion() {
		Vector2D steer = Steering.evade(boid, otherboid);
		assertEquals(new Vector2D(0.0, -1.0), steer);		
	}
	
	@Test
	public void testArrivalNoCoefficient() {
		Vector2D steer = Steering.arrive(boid, new Vector2D(0.0, 0.0));
		assertEquals(new Vector2D(-1.0, -1.0), steer);
	}
	
	@Test
	public void testArrivalWithCoefficient() {
		Vector2D steer = Steering.arrive(boid, new Vector2D(0.0, 0.0), 2.0);
		assertEquals(new Vector2D(-2.0, -2.0), steer);
	}
	
	@Test
	public void testNoIntersection() {
		Collection<Locomotion> flock = Arrays.asList(boid);
		double radius = 0.5;
		Locomotion avoider = new SimpleLocomotion(1.0, new Vector2D(0.0, 0.0), new Vector2D(1.0, 0.0));
		Vector2D steer = Steering.avoidObstacle(avoider, radius, flock);
		assertEquals(new Vector2D(0.0, 0.0), steer);
	}
	
	@Test
	public void testSingleIntersection() {
		Collection<Locomotion> flock = Arrays.asList(boid);
		double radius = 2.0;
		Locomotion avoider = new SimpleLocomotion(1.0, new Vector2D(0.0, 0.0), new Vector2D(2.0, 0.0));
		Vector2D steer = Steering.avoidObstacle(avoider, radius, flock);
		assertEquals(new Vector2D(0.0, -2.0), steer);		
	}
	
	@Test
	public void testMultipleIntersectionAvoidsClosest() {
		Locomotion obstacle1 = new SimpleLocomotion(1.0, new Vector2D(1.0, 1.0), new Vector2D(0.0, 0.0));
		Locomotion obstacle2 = new SimpleLocomotion(1.0, new Vector2D(2.0, -1.0), new Vector2D(0.0, 0.0));
		double radius = 2.0;
		Locomotion avoider = new SimpleLocomotion(1.0, new Vector2D(0.0, 0.0), new Vector2D(3.0, 0.0));
		Collection<Locomotion> flock = Arrays.asList(obstacle1, obstacle2);
		Vector2D steer = Steering.avoidObstacle(avoider, radius, flock);
		assertEquals(new Vector2D(0.0, -3.0), steer);
	}
	
	@Test
	public void testContainmentWithinRegion() {
		boid.steer(new Vector2D(0.0, 15.0));
		Vector2D steer = Steering.contain(boid, mockContainer, 1.0);
		assertEquals(new Vector2D(0.0, 0.0), steer);
	}
	
	@Test
	public void testContainmentOutsideRegion() {
		Locomotion outofbounder = new SimpleLocomotion(1.0, new Vector2D(0.0, 0.0), new Vector2D(3.0, 2.0));
		Vector2D steer = Steering.contain(outofbounder, mockContainer, 1.0);
		assertEquals(new Vector2D(1.0, 2.0), steer);
	}
	
	@Test
	public void testSeparationNoNeighbors() {
		Vector2D steer = Steering.separate(boid, emptyMockNeighborhood);
		assertEquals(new Vector2D(0.0, 0.0), steer);
	}
	
	@Test
	public void testSeparationOneNeighbor() {
		Vector2D steer = Steering.separate(boid, singleBoidMockNeighborhood);
		assertEquals(new Vector2D(-1.0, 0.0), steer);
	}
	
	@Test
	public void testSeparationMultipleNeighbors() {
		Vector2D steer = Steering.separate(boid, fullMockNeighborhood);
		assertEquals(new Vector2D(4.0, 4.0), steer);		
	}
	
	@Test
	public void testCohesionNoNeighbors() {
		Vector2D steer = Steering.cohesion(boid, emptyMockNeighborhood);
		assertEquals(new Vector2D(0.0, 0.0), steer);
	}
	
	@Test
	public void testCohesionOneNeighbor() {
		Vector2D steer = Steering.cohesion(boid, singleBoidMockNeighborhood);
		assertEquals(new Vector2D(1.0, 0.0), steer);
	}
	
	@Test
	public void testCohesionMultipleNeighbors() {
		Vector2D steer = Steering.cohesion(boid, fullMockNeighborhood);
		assertEquals(new Vector2D((double)-4/3, (double)-4/3), steer);
	}

	@Test
	public void testAlignmentNoNeighbors() {
		Vector2D steer = Steering.align(boid, emptyMockNeighborhood);
		assertEquals(new Vector2D(0.0, 0.0), steer);
	}
	
	@Test
	public void testAlignmentOneNeighbor() {
		Vector2D steer = Steering.align(boid, singleBoidMockNeighborhood);
		assertEquals(new Vector2D(-2.0, 0.0), steer);
	}
	
	@Test
	public void testAlignmentMultipleNeighbors() {
		Vector2D steer = Steering.align(boid, fullMockNeighborhood);
		assertEquals(new Vector2D(-2.0, 0.0), steer);
	}
	
	@Test
	public void testFollowLeader() {
		Locomotion follower = new SimpleLocomotion(1.0, new Vector2D(-1.0, -1.0), new Vector2D());
		Locomotion leader = new SimpleLocomotion(1.0, new Vector2D(1.0, 1.0), new Vector2D(0.0, 1.0));
		
		Vector2D steer = Steering.followLeader(follower, leader, 1.0, emptyMockNeighborhood, 1.0);
		
		assertEquals(new Vector2D(2.0, 1.0), steer);
	}
	
	@Test
	public void testFollowLeaderInLeadersWay() {
		Locomotion follower = new SimpleLocomotion(1.0, new Vector2D(1.0, 1.0), new Vector2D());
		Locomotion leader = new SimpleLocomotion(1.0, new Vector2D(0.0, 0.0), new Vector2D(0.0, 2.0));
		
		Vector2D steer = Steering.followLeader(follower, leader, 1.0, emptyMockNeighborhood, 2.0);
		
		assertEquals(new Vector2D(2.0, 0.0), steer);
	}
	
	@Test
	public void testFollowLeaderWithCrowd() {
		Locomotion leader = new SimpleLocomotion(1.0, new Vector2D(-4.0, -4.0), new Vector2D(-1.0, -1.0));
		
		Vector2D steer = Steering.followLeader(boid, leader, Math.sqrt(2.0), fullMockNeighborhood, 1.0);
		
		assertEquals(new Vector2D(0.0, 0.0), steer);
	}
}