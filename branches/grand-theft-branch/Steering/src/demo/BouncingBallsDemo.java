package demo;

import geom.CenteredEllipse;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import saver.ScreensaverBase;
import steering.SimpleLocomotion;
import vector.V3;
import vector.Vector3D;

public class BouncingBallsDemo extends ScreensaverBase {

	private double ballMass = 1.0;
	private double ballMaxForce = 1.0;
	private double ballMaxSpeed = 20.0;
	private double ballRadius = 20.0;
	
	private int numberOfBalls = 5;
	private List<SimpleLocomotion> balls;
	
	
	private CenteredEllipse circle = new CenteredEllipse(0.0, 0.0, ballRadius * 2, ballRadius * 2);
	
	@Override
	public void init() {
		super.init();
		
		int width = getContext().getComponent().getWidth();
		int height = getContext().getComponent().getHeight();
		
		double max = Math.sqrt(ballMaxSpeed / 3);
		
		Random random = new Random();
		Vector3D initPos; 
		Vector3D initVelo;
		
		balls = new ArrayList<SimpleLocomotion>(numberOfBalls);
		for(int i = 0; i < numberOfBalls; i++) {
			initPos = new Vector3D(width * random.nextDouble(), height * random.nextDouble(), 0.0);
			initVelo = new Vector3D(max * random.nextDouble(), max * random.nextDouble(), max * random.nextDouble());
			SimpleLocomotion ball = new SimpleLocomotion(ballMass, initPos, initVelo, ballMaxForce, ballMaxSpeed);
			balls.add(ball);
		}
	}
	
	@Override
	public void render(Graphics2D g2) {
		collideWithBalls(balls, ballRadius, ballMass);
		for(SimpleLocomotion ball : balls) {
			g2.setColor(Color.GREEN);		
			fillAt(g2, circle, ball.position().x, ball.position().y);
			
			collideWithWall(ball, ballRadius, getContext().getComponent().getWidth(),
											  getContext().getComponent().getHeight());
			ball.move();
		}
	}
	
	private static void collideWithWall(SimpleLocomotion ball, double radius, double width, double height) {
		double x = ball.position().x;
		double y = ball.position().y;
		
		if((x - radius < 0) || (x + radius > width)) {
			ball.setVelocity(new Vector3D(-1 * ball.velocity().x, ball.velocity().y, 0.0));
		}
		if((y - radius < 0) || (y + radius > height)) {
			ball.setVelocity(new Vector3D(ball.velocity().x, -1 * ball.velocity().y, 0.0));
		}			
	}
	
	private static void collideWithBalls(List<SimpleLocomotion> balls, double radius, double ballMass) {
		List<SimpleLocomotion> collided = new ArrayList<SimpleLocomotion>();
		for(int index = 0; index < balls.size(); index++) {
			for(int i = 0; i < balls.size(); i++) {
				if(i == index)
					continue;
				if(intersecting(balls.get(index), balls.get(i), radius)) {
					System.out.println("Collision!");
					collided.add(balls.remove(i));
					collided.add(balls.remove(index));
					index = -1; // reset index
					break;
				}
			}
		}
		
		//collide all pairs of balls in collided
		for(int i = 0; i < collided.size(); i += 2) {
			collide(balls.get(i), balls.get(i + 1), ballMass, ballMass);
		}
		
		//recombine list
		balls.addAll(collided);
	}

	private static boolean intersecting(SimpleLocomotion ball, SimpleLocomotion other, double radius) {
		double distance = V3.magnitude(V3.sub(other.position(), ball.position()));
		return distance < (radius * 2);
	}
	
	private static void collide(SimpleLocomotion one, SimpleLocomotion two, double mass1, double mass2) {
		Vector3D lineOfForce = V3.sub(two.position(), one.position());
		Vector3D along1 = V3.project(one.velocity(), lineOfForce);
		Vector3D tangent1 = V3.sub(one.velocity(), along1);
		
		lineOfForce = V3.mult(-1, lineOfForce);
		Vector3D along2 = V3.project(two.velocity(), lineOfForce);
		Vector3D tangent2 = V3.sub(two.velocity(), along2);
		
		double v1 = elasticCollision(mass1, V3.magnitude(along1),
									 mass2, V3.magnitude(along2));
		double v2 = elasticCollision(mass2, V3.magnitude(along2),
									 mass1, V3.magnitude(along1));
		
		along1 = V3.resizeTo(v1, along1);
		along2 = V3.resizeTo(v2, along2);
		
		one.setVelocity(V3.add(along1, tangent1));
		two.setVelocity(V3.add(along2, tangent2));
	}

	private static double elasticCollision(double mass1, double velo1, double mass2, double velo2) {
		return ((velo1 * (mass1 - mass2)) + 2 * mass2 * velo2) / (mass1 + mass2);
	}
}
