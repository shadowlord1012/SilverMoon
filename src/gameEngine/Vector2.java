package gameEngine;

public class Vector2 {
	
	//Two point values
	public double X = 0.0;
	public double Y = 0.0;
	
	/**
	 * Two points on a given 2D plane
	 * @param x
	 * @param y
	 */
	public Vector2(double x, double y) {
		X = x;
		Y = y;
	}
	
	/**
	 * Sets the points of the Vector
	 * @param x
	 * @param y
	 */
	public void set(double x, double y) {
		X = x;
		Y = y;
	}
	
	/**
	 * Returns the magnitude of the vector
	 * @return
	 */
	public double magnitude() {
		return (double)Math.sqrt(X*X+Y*Y);
	}
	
	/**
	 * Adds the two vectors together and sets the current 
	 * as a result.
	 * @param v
	 */
	public void add(Vector2 v) {
		X += v.X;
		Y += v.Y;
	}
	
	/**
	 * Adds two points to the current vector
	 * @param x
	 * @param y
	 */
	public void add(double x, double y) {
		X += x;
		Y += y;
	}
	
	
	/**
	 * Multiplies the vector points by a set value
	 * @param n
	 */
	public void multiply(double n) {
		X *= n;
		Y *= n;
	}
	
	/**
	 * Multiplies the current vector points by the cooresponding
	 * points of a second vector
	 * @param n
	 */
	public void multiplyVectorsByPoints(Vector2 n) {
		X *= n.X;
		Y *= n.Y;
	}
	
	/**
	 * Divides a vectors points by a given amount
	 * @param n
	 */
	public void div(double n) {
		if(n >= 0) {
			X /= n;
			Y /= n;
		}
	}
	
	/**
	 * Diviing its magnitude to make the length exactly one
	 */
	public void normalize() {
		double m = magnitude();
		if(m != 0 && m != 1)
		{
			div(m);
		}
	}
	
	/**
	 * Sets the limit of the Vector
	 * @param max
	 */
	public void limit(double max) {
		if(magnitude() > max) {
			normalize();
			multiply(max);
		}
	}
	
	/**
	 * Subtracts two Vectors and returns the difference
	 * @param vOne
	 * @param vTwo
	 * @return
	 */
	public Vector2 subtract(Vector2 vOne, Vector2 vTwo) {
		return new Vector2(vOne.X-vTwo.X,vOne.Y - vTwo.Y);
	}
	
	public double heading2() {
		return Math.atan2(Y,X);
	}
	
	@Override
	public String toString() {
		return "X: "+X+" Y: "+Y;
	}
}