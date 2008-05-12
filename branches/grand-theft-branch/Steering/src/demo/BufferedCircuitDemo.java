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
import java.awt.geom.Line2D;
import java.util.Stack;

import org.jdesktop.jdic.screensaver.SimpleScreensaver;

import steering.CircularPath;
import steering.SimpleLocomotion;
import steering.Steering;
import vector.V3;
import vector.Vector3D;

public class BufferedCircuitDemo extends SimpleScreensaver {

	private SimpleLocomotion car;
	
	private CircularPath path;
	private Vector3D[] points;
	private double radius = 10.0;
	private double switchDist = 7.0;
	
	private Stack<AffineTransform> transformStack = new Stack<AffineTransform>();

	private Image buffer;
	
	public BufferedCircuitDemo() {
		points = new Vector3D[] { 
				new Vector3D(150.0, 50.0, 0.0),
				new Vector3D(475.0, 50.0, 0.0),
				new Vector3D(550.0, 100.0, 0.0),
				new Vector3D(550.0, 300.0, 0.0),				
				new Vector3D(500.0, 375.0, 0.0),				
				new Vector3D(300.0, 450.0, 0.0),				
				new Vector3D(175.0, 430.0, 0.0),
				new Vector3D(75.0, 350.0, 0.0),
				new Vector3D(75.0, 150.0, 0.0)
		};
		path = new CircularPath(points, radius);
		path.next();
		
		Vector3D initPos = new Vector3D(200.0, 70.0, 0.0);
		Vector3D initVelo = new Vector3D(2.0, 0.0, 0.0);
		
		car = new SimpleLocomotion(1.0, initPos, initVelo, 0.2, 5.0);
	}
	
	@Override
	public void paint(Graphics g) {
		Component c = getContext().getComponent();
		
		if(buffer == null)
			buffer = c.createImage(c.getWidth(), c.getHeight());
		Graphics2D g2 = (Graphics2D) buffer.getGraphics();
		// clear image
		c.paint(g2);
		
		// draw path spine
		drawPolyline(g2, points);

		// draw car and driver
		drawVehicle(g2, car);
		
		// steer the car
		Vector3D steeringForce = Steering.followPath(car, path, switchDist, 2.0, 7.0);
		car.steer(steeringForce);
		car.move();
		
		// draw the buffered image to the screen
		g.drawImage(buffer, 0, 0, null);
	}

	private void drawVehicle(Graphics2D g2, SimpleLocomotion car) {
		Shape vehicle = new CenteredRectangle(0.0, 0.0, 40, 20);
		Shape driver = new CenteredEllipse(0.0, 0.0, 9.0, 9.0);
		
		g2.setColor(Color.RED);
		
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

	private void drawPolyline(Graphics2D g2, Vector3D[] path) {
		g2.setColor(Color.BLUE);
		
		Shape line;
		Vector3D point1, point2;
		
		for(int i = 1; i < path.length; i++) {
			point1 = path[i - 1];
			point2 = path[i];
			line = new Line2D.Double(point1.x, point1.y, point2.x, point2.y);
			g2.draw(line);
		}
		point1 = path[path.length - 1];
		point2 = path[0];
		line = new Line2D.Double(point1.x, point1.y, point2.x, point2.y);
		g2.draw(line);
	}
}
