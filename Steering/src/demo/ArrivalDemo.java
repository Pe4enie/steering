package demo;

import geom.CenteredEllipse;
import geom.CenteredRectangle;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import org.jdesktop.jdic.screensaver.SimpleScreensaver;

import steering.LinearDecayArrivalFn;
import steering.SimpleLocomotion;
import steering.Steering;
import vector.V3;
import vector.Vector3D;

public class ArrivalDemo extends SimpleScreensaver {

	private Vector3D target = new Vector3D(250.0, 250.0, 0.0);

	private SimpleLocomotion car;
	private SimpleLocomotion car2;
	
	private Shape goal = new CenteredEllipse(0.0, 0.0, 100.0, 100.0);
	private Shape vehicle = new CenteredRectangle(0.0, 0.0, 40, 20);
	
	public ArrivalDemo() {
		Vector3D initPos = new Vector3D(500.0, 100.0, 0.0);	
		
		Vector3D forward = V3.sub(target, initPos);
		Vector3D up = new Vector3D(0.0, 0.0, 1.0);
		Vector3D side = V3.cross(forward, up);
		Vector3D[] orientation = new Vector3D[] { forward, side, up };
		
		car = new SimpleLocomotion(1.0, initPos, new Vector3D(0.0, 0.0, 0.0), 5.0, 5.0, orientation);

		initPos = new Vector3D(500.0, 250.0, 0.0);
		car2 = new SimpleLocomotion(1.0, initPos, new Vector3D(0.0, 10.0, 0.0), 0.1, 5.0);
	}
	
	@Override
	public void paint(Graphics g) {
		Component c = getContext().getComponent();
		c.paint(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		AffineTransform base = g2.getTransform();
		
		// draw the goal
		g2.translate(target.x, target.y);
		g2.setColor(Color.BLUE);
		g2.draw(goal);
		g2.setTransform(base);

		// draw the first car
		g2.translate(car.position().x, car.position().y);
		double angle = V3.angle(car.getOrientation()[SimpleLocomotion.FORWARD]);
		g2.rotate(angle);
		g2.setColor(Color.RED);
		g2.draw(vehicle);
		g2.setTransform(base);
		
		// steer the first car
		Vector3D steeringForce = Steering.arrive(car, target, 10.0, 100.0, new LinearDecayArrivalFn(10.0, 10.0, 100.0));
		car.steer(steeringForce);
		car.move();
		
		// draw the second car
		g2.translate(car2.position().x, car2.position().y);
		angle = V3.angle(car2.getOrientation()[SimpleLocomotion.FORWARD]);
		g2.rotate(angle);
		g2.setColor(Color.GREEN);
		g2.draw(vehicle);
		g2.setTransform(base);
		
		// steer the second car
		steeringForce = Steering.arrive(car2, target, 10.0, 100.0, new LinearDecayArrivalFn(10.0, 10.0, 100.0));
		car2.steer(steeringForce);
		car2.move();		
	}
}
