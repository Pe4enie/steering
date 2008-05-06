package geom;

import java.awt.geom.Ellipse2D;

public class CenteredEllipse extends Ellipse2D.Double {

	private static final long serialVersionUID = 956360345720387972L;

	/**
	 * Constructs a new Ellipse centered at 0,0 with size 0,0
	 */
	public CenteredEllipse() {
		super();
	}
	
	/**
	 * Constructs and initializes an Ellipse2D.Double from the specified cooridinates
	 */
	public CenteredEllipse(double x, double y, double w, double h) {
		super(x - w / 2, y - h / 2, w, h);
	}
	
}
