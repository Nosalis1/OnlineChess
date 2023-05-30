package util;

import socket.packages.Streamable;

public class Vector implements Streamable {
    // Public fields for X and Y coordinates
    public int X, Y;

    /**
     * Default constructor that initializes X and Y to 0.
     */
    public Vector() {
        this.X = this.Y = 0;
    }

    /**
     * Constructor that initializes X and Y to the provided scalar value.
     *
     * @param scalar the scalar value to initialize X and Y
     */
    public Vector(final int scalar) {
        this.X = this.Y = scalar;
    }

    /**
     * Constructor that initializes X and Y with the provided values.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public Vector(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    /**
     * Add the components of another vector to this vector.
     *
     * @param other the vector to add
     */
    public void add(final Vector other) {
        this.X += other.X;
        this.Y += other.Y;
    }

    /**
     * Add the specified values to the components of this vector.
     *
     * @param x the value to add to the X coordinate
     * @param y the value to add to the Y coordinate
     */
    public void add(final int x, final int y) {
        this.X += x;
        this.Y += y;
    }

    /**
     * Subtract the components of another vector from this vector.
     *
     * @param other the vector to subtract
     */
    public void subtract(final Vector other) {
        this.X -= other.X;
        this.Y -= other.Y;
    }

    /**
     * Multiply this vector by a scalar value.
     *
     * @param scalar the scalar value to multiply by
     */
    public void multiply(final int scalar) {
        this.X *= scalar;
        this.Y *= scalar;
    }

    /**
     * Divide this vector by a scalar value.
     *
     * @param scalar the scalar value to divide by
     */
    public void divide(final int scalar) {
        this.X /= scalar;
        this.Y /= scalar;
    }

    /**
     * Returns a string representation of the vector.
     *
     * @return a string representation of the vector
     */
    @Override
    public String toString() {
        return "Vector{ X = " + X + ", Y = " + Y + "}";
    }

    // Static final vectors for common values
    public static final Vector ZERO = new Vector(0);
    public static final Vector ONE = new Vector(1);
    public static final Vector MAX = new Vector(Integer.MAX_VALUE);
    public static final Vector MIN = new Vector(Integer.MIN_VALUE);

    /**
     * Checks if the vector's components are within the specified bounds.
     *
     * @param min the minimum bound
     * @param max the maximum bound
     * @return true if the vector is within the bounds, false otherwise
     */
    public boolean inBounds(final int min, final int max) {
        return (X > min && X < max) && (Y > min && Y < max);
    }

    /**
     * Clamps the vector's components to be within the specified bounds.
     *
     * @param min the minimum bound
     * @param max the maximum bound
     */
    public void clamp(final int min, final int max) {
        if (inBounds(min, max))
            return;

        X = X < min ? min : (Math.min(X, max));
        Y = Y < min ? min : (Math.min(Y, max));
    }

    /**
     * Performs linear interpolation between two vectors.
     *
     * @param start the starting vector
     * @param end   the ending vector
     * @param t     the interpolation value (0 to 100)
     * @return the interpolated vector
     */
    public static Vector lerp(final Vector start, final Vector end, final int t) {
        end.subtract(start);

        start.add(end);
        return start;
    }

    /**
     * Rotates the vector by swapping its X and Y components.
     */
    public void rotate() {
        int i = X;
        X = Y;
        Y = i;
    }

    @Override
    public String pack() {
        return X + "," + Y;
    }

    @Override
    public void unapck(String buffer) {
        String[] values = buffer.split(",");
        if (values.length == 2) {
            this.X = Integer.parseInt(values[0]);
            this.Y = Integer.parseInt(values[1]);
        } else {
            throw new IllegalArgumentException("Invalid buffer format");
        }
    }

}