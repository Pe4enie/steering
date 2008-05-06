package geom;

import java.awt.geom.Rectangle2D;

public class CenteredRectangle extends Rectangle2D.Double {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4071376210711608247L;

	public CenteredRectangle() {
		super();
	}
	
	public CenteredRectangle(double x, double y, double w, double h) {
		super(x - w / 2, y - h / 2, w, h);
	}	
}
