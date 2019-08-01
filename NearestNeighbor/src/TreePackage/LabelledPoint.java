package TreePackage;
import TreePackage.TwoDimensionTree.Point;

/**
 * A Point with a label associated. Could represent any object with
 * coordinates, such as a city, and object drawn on a computer screen,
 * or something else.
 * 
 * 
 * @author Jacob Schrum
 * Last Modified: 5/28/17
 */
public class LabelledPoint extends Point {
	// Label associated with this Point
	private String label;

	/**
	 * Create instance with the given label and x/y coordinates
	 * @param label String label
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	public LabelledPoint(String label, double x, double y) {
		super(x, y);
		this.label = label;
	}

	/**
	 * Get the label for the Point
	 * @return String label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * LabelledPoints are only equal if they are at the same location
	 * and have the same label.
	 */
	public boolean equals(Object obj) {
		if(obj instanceof LabelledPoint) {
			LabelledPoint other = (LabelledPoint) obj;
			return super.equals(obj) && label.equals(other.label);
		}
		return false;
	}
}
