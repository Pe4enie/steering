package steering;

public class ExponentialDecayArrivalFn implements ArrivalFunction{
    
    private double lambda;
    private double shutoff;
	private double prime;
	private double radius;

    public ExponentialDecayArrivalFn(double lambda, double shutoff,
			double prime, double radius) {
    	this.lambda = lambda;
    	this.shutoff = shutoff;
    	this.prime = prime;
    	this.radius = radius;
	}

	public double speedOfArrival(double distance) {
    	if(distance < this.shutoff)
    		return 0;
		double fromRadius = radius - distance;
    	return this.prime * Math.exp((-1 * this.lambda) * fromRadius);
    }
}