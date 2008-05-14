package demo;

import geom.CenteredEllipse;
import geom.CenteredRectangle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Stack;

import org.jdesktop.jdic.screensaver.SimpleScreensaver;

import steering.LinearDecayArrivalFn;
import steering.SimpleLocomotion;
import steering.Steering;
import vector.V3;
import vector.Vector3D;

public class ArriveBehindDemo extends SimpleScreensaver {

	private SimpleLocomotion car0;
	private SimpleLocomotion car1;
	
	private Stack<AffineTransform> transformStack = new Stack<AffineTransform>();
	private Image buffer;

	private double carLength = 40.0;
	private double followDist = 50.0;
	
	private Vector3D target0 = new Vector3D(250.0, 250.0, 0.0);
	private Vector3D target1 = new Vector3D(350.0, 350.0, 0.0);
	
	private LinearDecayArrivalFn arrivalFn;
	
	public ArriveBehindDemo() {
		arrivalFn = new LinearDecayArrivalFn(10.0, 10.0, 400.0);
		
		Vector3D initPos = new Vector3D(100.0, 100.0, 0.0);
		Vector3D initVelo = new Vector3D(1.0, 1.0, 0.0);
		
		car0 = new SimpleLocomotion(1.0, initPos, initVelo, 0.2, 5.0);
				
		initPos = new Vector3D(20.0, 20.0, 0.0);
		initVelo = new Vector3D(1.0, 1.0, 0.0);
		car1 = new SimpleLocomotion(1.0, initPos, initVelo, 0.2, 5.0);
	}
	
	@Override
	public void paint(Graphics g) {
		Component c = getContext().getComponent();
		
		// create offscreen image buffer to draw to
		if(buffer == null)
			buffer = c.createImage(c.getWidth(), c.getHeight());
		Graphics2D g2 = (Graphics2D) buffer.getGraphics();
		// clear image
		c.paint(g2);
				
		Vector3D backwards = V3.mult(-1, car0.getOrientation()[SimpleLocomotion.FORWARD]);
		Vector3D behind = V3.add(car0.position(), V3.resizeTo(followDist, backwards));
		
		// draw goals
		drawTarget(g2, target0.x, target0.y, 20.0, Color.RED);
		drawTarget(g2, target1.x, target1.y, 20.0, Color.GREEN);
		drawTarget(g2, behind.x, behind.y, 20.0, Color.BLUE);
		
		// draw cars and driver
		drawVehicle(g2, car0, Color.RED);
		drawVehicle(g2, car1, Color.GREEN);
		
		// steer first car
		Vector3D steeringForce = Steering.arrive(car0, target0, 10.0, 100.0, arrivalFn);
		car0.steer(steeringForce);
		car0.move();

		// steer second car
		steeringForce = Steering.arriveBehind(car1, target1, 10.0, 100.0, arrivalFn,
				car0, followDist, 100.0, arrivalFn);
		car1.steer(steeringForce);
		car1.move();

		// draw the buffered image to the screen
		g.drawImage(buffer, 0, 0, null);
	}

	private void drawTarget(Graphics2D g2, double x, double y, double size, Color color) {
		transformStack.push(g2.getTransform());
		g2.translate(x, y);
		g2.setColor(color);
		g2.draw(new CenteredEllipse(0.0, 0.0, size, size));
		g2.setTransform(transformStack.pop());
	}
	
	private void drawVehicle(Graphics2D g2, SimpleLocomotion car, Color color) {
		Shape vehicle = new CenteredRectangle(0.0, 0.0, carLength, carLength / 2);
		Shape driver = new CenteredEllipse(0.0, 0.0, 9.0, 9.0);
		
		g2.setColor(color);
		
		// position car
		transformStack.push(g2.getTransform());
		g2.translate(car.position().x, car.position().y);
		double angle = V3.angle(car.getOrientation()[SimpleLocomotion.FORWARD]);
		g2.rotate(angle);
		
		// position and draw the driver
		transformStack.push(g2.getTransform());
		g2.translate(10, -5);
		g2.fill(driver);
		g2.setTransform(transformStack.pop());
		
		// draw the vehicle
		g2.draw(vehicle);
		g2.setTransform(transformStack.pop());		
	}
}
