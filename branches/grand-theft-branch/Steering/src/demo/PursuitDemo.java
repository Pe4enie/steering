package demo;

import geom.CenteredEllipse;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import physics.Physics;
import saver.ScreensaverBase;
import steering.Orb;
import steering.Steering;
import vector.V3;
import vector.Vector3D;

public class PursuitDemo extends ScreensaverBase {

	private double mass = 1.0;
	private double maxForce = 100.0;
	private double maxSpeed = 100.0;
	private double radius = 30.0;

	private Shape circle = new CenteredEllipse(0.0, 0.0, radius * 2, radius * 2);

	private Orb ball;
	private Orb seeker;

	@Override
	public void init() {
		super.init();

		Vector3D initVelo;

		List<Vector3D> initPos = randomStartingPositions(2, width, height);

		initVelo = new Vector3D(2.0, 2.0, 0.0);
		ball = new Orb(mass, initPos.get(0), initVelo, radius, maxForce,
				maxSpeed);

		initVelo = new Vector3D(1.0, 1.0, 0.0);
		seeker = new Orb(mass, initPos.get(1), initVelo, radius, maxForce,
				maxSpeed);
	}

	private List<Vector3D> randomStartingPositions(int num, double width,
			double height) {
		List<Vector3D> vectors = new ArrayList<Vector3D>(num);

		Random random = new Random();
		double x, y;

		for (int i = 0; i < num; i++) {
			x = random.nextDouble() * (width - 2 * radius) + radius;
			y = random.nextDouble() * (height - 2 * radius) + radius;

			Vector3D vector = new Vector3D(x, y, 0);

			boolean overlapping = false;
			do {
				overlapping = false;
				for (Vector3D other : vectors) {
					double distance = V3.distance(vector, other);
					if (distance < 2 * radius) {
						overlapping = true;
						x = random.nextDouble() * (width - 2 * radius) + radius;
						y = random.nextDouble() * (height - 2 * radius)
								+ radius;
						vector = new Vector3D(x, y, 0.0);
						break;
					}
				}
			} while (overlapping);

			vectors.add(vector);
		}
		return vectors;
	}

	@Override
	public void render(Graphics2D g2) {
		// draw ball
		Point2D topLeft = new Point2D.Double(-radius, -radius);
		Point2D center = new Point2D.Double(radius, radius);
		g2
				.setPaint(new GradientPaint(topLeft, Color.WHITE, center,
						Color.GREEN));
		fillAt(g2, circle, ball.position().x, ball.position().y);

		// draw seeker
		topLeft = new Point2D.Double(-radius, -radius);
		center = new Point2D.Double(radius, radius);
		g2.setPaint(new GradientPaint(topLeft, Color.WHITE, center, Color.RED));
		fillAt(g2, circle, seeker.position().x, seeker.position().y);

		// check for collision with each other
		if (intersecting(ball, seeker)) {
			Vector3D[] result = Physics.collide(ball.mass(), ball.position(),
					ball.velocity(), seeker.mass(), seeker.position(), seeker
							.velocity());

			ball.setVelocity(result[0]);
			seeker.setVelocity(result[1]);
			
			seeker.rebound = 30 * 2;
		}

		// check for collision with walls
		collideWithWall(ball);
		collideWithWall(seeker);

		// update position
		ball.move();
		if(seeker.rebound <= 0) {
			Vector3D steering = Steering.pursue(seeker, ball, 1.0, 30);
			seeker.steer(steering);
		}
		seeker.move();
		seeker.rebound -= 1;
	}

	private void collideWithWall(Orb orb) {
		// check for collision with walls
		double x = orb.position().x;
		double y = orb.position().y;

		if ((x - orb.radius() < 0) || (x + orb.radius() > width)) {
			orb.setVelocity(new Vector3D(-1 * orb.velocity().x,
					orb.velocity().y, 0.0));
		}
		if ((y - orb.radius() < 0) || (y + orb.radius() > height)) {
			orb.setVelocity(new Vector3D(orb.velocity().x, -1
					* orb.velocity().y, 0.0));
		}
	}

	private boolean intersecting(Orb ball, Orb other) {
		double distance = V3.magnitude(V3
				.sub(ball.position(), other.position()));
		return distance < (ball.radius() + other.radius());
	}

}
