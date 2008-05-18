package saver;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Stack;

import org.jdesktop.jdic.screensaver.SimpleScreensaver;

public abstract class ScreensaverBase extends SimpleScreensaver {

	protected Stack<AffineTransform> transformStack = new Stack<AffineTransform>();
	
	protected double width;
	protected double height;
	
	private Image buffer;
	
	@Override
	public void init() {
		super.init();
		
		width = getContext().getComponent().getWidth();
		height = getContext().getComponent().getHeight();
	}
	
	@Override
	public void paint(Graphics g) {
		Component c = getContext().getComponent();
		
		if(buffer == null)
			buffer = c.createImage(c.getWidth(), c.getHeight());
		Graphics2D g2 = (Graphics2D) buffer.getGraphics();
		c.paint(g2);
		
		render(g2);
		
		g.drawImage(buffer, 0, 0, null);
	}

	public abstract void render(Graphics2D g2);
	
	public void drawAt(Graphics2D g2, Shape shape, double x, double y) {
		transformStack.push(g2.getTransform());
		g2.translate(x, y);
		g2.draw(shape);
		g2.setTransform(transformStack.pop());
	}

	public void fillAt(Graphics2D g2, Shape shape, double x, double y) {
		transformStack.push(g2.getTransform());
		g2.translate(x, y);
		g2.fill(shape);
		g2.setTransform(transformStack.pop());
	}
}
