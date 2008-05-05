package steering;

public class LinearDecayArrivalFn implements ArrivalFunction {
    
    private double shutoff;
    private double prime;
    private double radius;
    
    /**
     * 
     * @param prime   the speed to travel at full distance from target 
     * @param shutoff distance at which fn should return 0 speed
     * @param radius radius of slowDown distance
     */
    public LinearDecayArrivalFn(double shutoff, double prime, double radius) {
    	this.shutoff = shutoff;
    	this.prime = prime;
    	this.radius = radius;
    }

    public double speedOfArrival(double distance) {
    	if(distance < this.shutoff)
    		return 0;
    	return this.prime / this.radius * distance;
    }
}