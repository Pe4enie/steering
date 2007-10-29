package steering.behaviors;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import steering.Locomotion;
import steering.Neighborhood;
import steering.SimpleLocomotion;
import steering.Steering;
import vector.V;
import vector.Vector2D;


public class FlockingTest {
	
	private Locomotion boid;
	
	private Neighborhood emptyMockNeighborhood;
	private Neighborhood singleBoidMockNeighborhood;
	private Neighborhood fullMockNeighborhood;
	
	@Before
	public void setUp() {
		boid = new SimpleLocomotion(1.0, new Vector2D(1.0, 1.0), new Vector2D(1.0, 1.0));
		
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
	public void testFlockingNoNeighbors() {
		Vector2D sep = Steering.separate(boid, emptyMockNeighborhood);
		Vector2D coh = Steering.cohesion(boid, emptyMockNeighborhood);
		Vector2D align = Steering.align(boid, emptyMockNeighborhood);

		Flocking flocking = new Flocking(boid, emptyMockNeighborhood, 1, 1, 1);
		Vector2D steer = flocking.steeringForce();
		assertEquals(V.add(sep, V.add(coh, align)), steer);
	}
	
	@Test
	public void testFlockingOneNeighbor() {
		Vector2D sep = Steering.separate(boid, singleBoidMockNeighborhood);
		Vector2D coh = Steering.cohesion(boid, singleBoidMockNeighborhood);
		Vector2D align = Steering.align(boid, singleBoidMockNeighborhood);
		
		Flocking flocking = new Flocking(boid, singleBoidMockNeighborhood, 1, 1, 1);
		Vector2D steer = flocking.steeringForce();
		assertEquals(V.add(sep, V.add(coh, align)), steer);
	}
	
	@Test
	public void testFlockingMultipleNeighbors() {
		Vector2D sep = Steering.separate(boid, fullMockNeighborhood);
		Vector2D coh = Steering.cohesion(boid, fullMockNeighborhood);
		Vector2D align = Steering.align(boid, fullMockNeighborhood);
		
		Flocking flocking = new Flocking(boid, fullMockNeighborhood, 1, 1, 1);
		Vector2D steer = flocking.steeringForce();
		assertEquals(V.add(sep, V.add(coh, align)), steer);
	}
	
	@Test
	public void testAutoScaleConstructor() {
		double sweight = 1.0 / 3.0;
		double aweight = 1.0 / 3.0;
		double cweight = 1.0 / 3.0;

		Vector2D sep = V.mult(sweight, Steering.separate(boid, fullMockNeighborhood));
		Vector2D coh = V.mult(aweight, Steering.cohesion(boid, fullMockNeighborhood));
		Vector2D align = V.mult(cweight, Steering.align(boid, fullMockNeighborhood));
		
		Flocking flocking = new Flocking(boid, fullMockNeighborhood);
		Vector2D steer = flocking.steeringForce();
		assertEquals(V.add(sep, V.add(coh, align)), steer);		
	}
	
	@Test
	public void testBehaviorsIndependantlyScalable() {
		double sweight = 0.5;
		double aweight = 2.0;
		double cweight = 1.5;

		Vector2D sep = V.mult(sweight, Steering.separate(boid, fullMockNeighborhood));
		Vector2D coh = V.mult(cweight, Steering.cohesion(boid, fullMockNeighborhood));
		Vector2D align = V.mult(aweight, Steering.align(boid, fullMockNeighborhood));
		
		Flocking flocking = new Flocking(boid, fullMockNeighborhood, sweight, aweight, cweight);
		Vector2D steer = flocking.steeringForce();
		assertEquals(V.add(sep, V.add(coh, align)), steer);
	}
	
	@Test
	public void testSeparateSettableNeighborhoods() {
		Vector2D sep = Steering.separate(boid, emptyMockNeighborhood);
		Vector2D coh = Steering.cohesion(boid, singleBoidMockNeighborhood);
		Vector2D align = Steering.align(boid, fullMockNeighborhood);
		
		Flocking flocking = new Flocking(boid, emptyMockNeighborhood, fullMockNeighborhood, singleBoidMockNeighborhood, 1, 1, 1);
		Vector2D steer = flocking.steeringForce();
		assertEquals(V.add(sep, V.add(coh, align)), steer);
	}
}
