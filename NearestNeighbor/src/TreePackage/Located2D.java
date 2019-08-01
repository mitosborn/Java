package TreePackage;

/**
 * Interface for objects that have x/y coordinates in 2D space.
 * 
 * @author Jacob Schrum
 * Last modified: 5/27/17
 */
public interface Located2D {
	/**
	 * Get the x-coordinate of this object
	 * @return x-coordinate
	 */
	public double getX();
	/**
	 * Get the y-coordinate of this object
	 * @return y-coordinate
	 */
	public double getY();
}
