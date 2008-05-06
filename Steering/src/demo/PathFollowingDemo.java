package demo;

import geom.CenteredRectangle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

import org.jdesktop.jdic.screensaver.SimpleScreensaver;

import steering.SimpleLocomotion;
import steering.Steering;
import vector.V3;
import vector.Vector3D;

public class PathFollowingDemo extends SimpleScreensaver {

	private Vector3D[] path;
	private SimpleLocomotion car;
	
	private Shape vehicle = new CenteredRectangle(0.0, 0.0, 40, 20);
	
	public PathFollowingDemo() {
		path = new Vector3D[] { 
				new Vector3D(150.0, 50.0, 0.0),
				new Vector3D(75.0, 150.0, 0.0),
				new Vector3D(75.0, 350.0, 0.0),
				new Vector3D(175.0, 430.0, 0.0),
				new Vector3D(300.0, 450.0, 0.0),
				new Vector3D(500.0, 375.0, 0.0),
				new Vector3D(550.0, 300.0, 0.0),
				new Vector3D(550.0, 100.0, 0.0),
				new Vector3D(475.0, 50.0, 0.0),
				new Vector3D(150.0, 50.0, 0.0)
		};
		
		Vector3D initPos = new Vector3D(200.0, 70.0, 0.0);
		Vector3D initVelo = new Vector3D(2.0, 0.0, 0.0);
		
		car = new SimpleLocomotion(1.0, initPos, initVelo, 0.1, 15.0);
	}
	
	@Override
	public void paint(Graphics g) {
		Component c = getContext().getComponent();
		c.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		AffineTransform base = g2.getTransform();
		
		g2.setColor(Color.BLUE);
	
		// draw path spine
		drawPolyline(g2, path);

		// draw car
		g2.translate(car.position().x, car.position().y);
		double angle = V3.angle(car.getOrientation()[SimpleLocomotion.FORWARD]);
		g2.rotate(angle);
		g2.setColor(Color.RED);
		g2.draw(vehicle);
		g2.setTransform(base);
		
		// steer the car
		Vector3D steeringForce = Steering.followPath(car, path, 15.0, 3.2, 7.0);
		car.steer(steeringForce);
		car.move();
	}

	private void drawPolyline(Graphics2D g2, Vector3D[] path) {
		Shape line;
		Vector3D point1, point2;
		
		for(int i = 1; i < path.length; i++) {
			point1 = path[i - 1];
			point2 = path[i];
			line = new Line2D.Double(point1.x, point1.y, point2.x, point2.y);			
			g2.draw(line);
		}
	}
}
