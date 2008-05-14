package demo;

import geom.CenteredEllipse;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import saver.ScreensaverBase;
import steering.SimpleLocomotion;
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
		for(SimpleLocomotion ball : balls) {
			g2.setColor(Color.GREEN);		
			fillAt(g2, circle, ball.position().x, ball.position().y);
			
			ball.move();
			collideWithWall(ball, ballRadius, getContext().getComponent().getWidth(),
											  getContext().getComponent().getHeight());
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
}
