package util;

/**
 * Represents a 2D vector.
 */
@SuppressWarnings("unused")
public class Vector implements socket.packages.Streamable {
    public int x;
    public int y;

    /**
     * Creates a new Vector instance with X and Y initialized to 0.
     */
    public Vector() {
        this.x = this.y = 0;
    }

    /**
     * Creates a new Vector instance with X and Y set to the given scalar value.
     *
     * @param scalar The scalar value to set X and Y.
     */
    public Vector(final int scalar) {
        this.x = this.y = scalar;
    }

    /**
     * Creates a new Vector instance with the given X and Y values.
     *
     * @param x The X value.
     * @param y The Y value.
     */
    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds the components of the given vector to this vector.
     *
     * @param other The vector to add.
     */
    public void add(final Vector other) {
        this.x += other.x;
        this.y += other.y;
    }

    /**
     * Adds the given values to the components of this vector.
     *
     * @param x The value to add to the X component.
     * @param y The value to add to the Y component.
     */
    public void add(final int x, final int y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Subtracts the components of the given vector from this vector.
     *
     * @param other The vector to subtract.
     */
    public void subtract(final Vector other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    /**
     * Multiplies the components of this vector by the given scalar value.
     *
     * @param scalar The scalar value to multiply.
     */
    public void multiply(final int scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    /**
     * Divides the components of this vector by the given scalar value.
     *
     * @param scalar The scalar value to divide.
     */
    public void divide(final int scalar) {
        this.x /= scalar;
        this.y /= scalar;
    }

    /**
     * Returns a string representation of this vector.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "Vector{ X = " + x + ", Y = " + y + "}";
    }

    /**
     * Returns a Vector instance representing the zero vector (0, 0).
     */
    public static final Vector ZERO = new Vector(0);

    /**
     * Returns a Vector instance representing the unit vector (1, 1).
     */
    public static final Vector ONE = new Vector(1);

    /**
     * Returns a Vector instance representing the maximum possible vector.
     */
    public static final Vector MAX = new Vector(Integer.MAX_VALUE);

    /**
     * Returns a Vector instance representing the minimum possible vector.
     */
    public static final Vector MIN = new Vector(Integer.MIN_VALUE);

    /**
     * Checks if the vector is within the bounds defined by the minimum and maximum values (exclusive).
     *
     * @param min The minimum value.
     * @param max The maximum value.
     * @return True if the vector is within the bounds, false otherwise.
     */
    public boolean inBounds(final int min, final int max) {
        return (x > min && x < max) && (y > min && y < max);
    }

    /**
     * Clamps the vector within the bounds defined by the minimum and maximum values.
     *
     * @param min The minimum value.
     * @param max The maximum value.
     */
    public void clamp(final int min, final int max) {
        if (inBounds(min, max))
            return;

        x = x < min ? min : (Math.min(x, max));
        y = y < min ? min : (Math.min(y, max));
    }

    /**
     * Performs linear interpolation between two vectors.
     *
     * @param start The starting vector.
     * @param end   The ending vector.
     * @param t     The interpolation parameter (between 0 and 1).
     * @return The interpolated vector.
     */
    public static Vector linearInterpolation(final Vector start, final Vector end, final int t) {
        int interpolatedX = start.x + (end.x - start.x) * t;
        int interpolatedY = start.y + (end.y - start.y) * t;
        return new Vector(interpolatedX, interpolatedY);
    }

    /**
     * Packs the vector into a string representation.
     *
     * @return The packed string.
     */
    @Override
    public String pack() {
        return x + "," + y;
    }

    /**
     * Unpacks the vector from a string representation.
     *
     * @param buffer The buffer containing the packed string.
     * @throws socket.exceptions.InvalidBufferFormatException if the buffer is in an invalid format.
     */
    @Override
    public void unpack(String buffer) throws socket.exceptions.InvalidBufferFormatException {
        String[] values = buffer.split(",");

        if (values.length == 2) {
            try {
                this.x = Integer.parseInt(values[0]);
                this.y = Integer.parseInt(values[1]);
            } catch (NumberFormatException e) {
                throw new socket.exceptions.InvalidBufferFormatException("Invalid buffer format. Expected comma-separated integers.", e);
            }
        } else {
            throw new socket.exceptions.InvalidBufferFormatException("Invalid buffer format. Expected two comma-separated values.");
        }
    }
}