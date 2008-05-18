package demo;

import geom.CenteredEllipse;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.Random;

import physics.Physics;
import saver.ScreensaverBase;
import steering.Orb;
import vector.V3;
import vector.Vector3D;

public class CollisionDemo extends ScreensaverBase {

	private double mass = 1.0;
	private double maxForce = 100.0;
	private double maxSpeed = 100.0;
	private double radius = 40.0;
	
	private double xoffset = 200.0;
	private double yoffset = 100.0;
	
	private Shape circle = new CenteredEllipse(0.0, 0.0, radius * 2, radius * 2);
	
	private Orb green, blue;
	
	@Override
	public void init() {
		super.init();
	
		Random random = new Random();
		
		Vector3D initPos;
		
		double x = random.nextDouble() * (width - 2 * radius) + radius;
		double y = random.nextDouble() * (height - 2 * radius) + radius;
		
		initPos = new Vector3D(x, y, 0.0);
		Vector3D initVelo = new Vector3D(1.0, 1.0, 0.0);
		green = new Orb(mass, initPos, initVelo, radius, maxForce, maxSpeed);

		x = random.nextDouble() * (width - 2 * radius) + radius;
		y = random.nextDouble() * (height - 2 * radius) + radius;
		
		initPos = new Vector3D(x, y, 0.0);
		initVelo = new Vector3D(-1.0, 1.0, 0.0);
		blue = new Orb(mass, initPos, initVelo, radius, maxForce, maxSpeed);
	}
	
	@Override
	public void render(Graphics2D g2) {
		g2.setColor(Color.GREEN);
		fillAt(g2, circle, green.position().x + xoffset, green.position().y + yoffset);
		g2.setColor(Color.BLUE);
		fillAt(g2, circle, blue.position().x + xoffset, blue.position().y + yoffset);
		
		// check for collision with balls
		if(intersecting(green, blue)) {
			Vector3D[] results = 
				Physics.collide(green.mass(), green.position(), green.velocity(),
							    blue.mass(), blue.position(), blue.velocity());
			
			green.setVelocity(results[0]);
			blue.setVelocity(results[1]);
		}
		collideWithWall(green);
		collideWithWall(blue);
		
		green.move();
		blue.move();
	}

	private void collideWithWall(Orb orb) {
		// check for collision with walls
		double x = orb.position().x + xoffset;
		double y = orb.position().y + yoffset;
		
		if((x - orb.radius() < 0) || (x + orb.radius() > width)) {
			orb.setVelocity(new Vector3D(-1 * orb.velocity().x, orb.velocity().y, 0.0));
		}
		if((y - orb.radius() < 0) || (y + orb.radius() > height)) {
			orb.setVelocity(new Vector3D(orb.velocity().x, -1 * orb.velocity().y, 0.0));
		}
	}

	private boolean intersecting(Orb ball, Orb other) {
		double distance = V3.magnitude(V3.sub(ball.position(), other.position()));
		return distance < (ball.radius() + other.radius());
	}

}
