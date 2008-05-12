package demo;

import geom.CenteredEllipse;
import geom.CenteredRectangle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.Stack;

import org.jdesktop.jdic.screensaver.SimpleScreensaver;

import steering.SimpleLocomotion;
import steering.SimplePath;
import steering.Steering;
import vector.V3;
import vector.Vector3D;

public class PathFollowingDemo2 extends SimpleScreensaver {

	private SimpleLocomotion car;
	
	private SimplePath path;
	private Vector3D[] points;
	private double radius = 5.0;
	
	private double switchDist = 5.0;
	
	private Stack<AffineTransform> transformStack = new Stack<AffineTransform>();
	
	public PathFollowingDemo2() {
		points = new Vector3D[] { 
				new Vector3D(75.0, 350.0, 0.0),
				new Vector3D(275.0, 300.0, 0.0),
				new Vector3D(375.0, 100.0, 0.0),
				new Vector3D(550.0, 25.0, 0.0),
		};
		path = new SimplePath(points, radius);
		
		Vector3D initPos = new Vector3D(40.0, 350.0, 0.0);
		Vector3D initVelo = new Vector3D(0.0, 0.0, 0.0);
		
		Vector3D forward = new Vector3D(1.0, 0.0, 0.0);
		Vector3D up = new Vector3D(0.0, 0.0, 1.0);
		Vector3D side = V3.cross(forward, up);
		Vector3D[] orientation = new Vector3D[] { forward, side, up };

		car = new SimpleLocomotion(1.0, initPos, initVelo, 0.1, 2.0, orientation);
	}
	
	@Override
	public void paint(Graphics g) {
		Component c = getContext().getComponent();
		c.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		// draw path spine
		drawPolyline(g2, points);
		
		// draw car and driver
		drawVehicle(g2, car);
		
		// select point to steer towards
		double distance = V3.magnitude(V3.sub(car.position(), path.curr()));
		if(distance < switchDist) {
			path.next();
		}
		
		// steer the car
		Vector3D steeringForce = Steering.seek(car, path.curr(), 10.0);
		car.steer(steeringForce);
		car.move();
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
			point1 = path[i];
			point2 = path[i - 1];
			line = new Line2D.Double(point1.x, point1.y, point2.x, point2.y);			
			g2.draw(line);
		}
	}
}
