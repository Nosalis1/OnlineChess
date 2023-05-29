package util;

public class Vector {
    // Public fields for X and Y coordinates
    public int X, Y;

    // Default constructor initializes X and Y to 0
    public Vector() {
        this.X = this.Y = 0;
    }

    // Constructor that initializes X and Y to the provided scalar value
    public Vector(final int scalar) {
        this.X = this.Y = scalar;
    }

    // Constructor that initializes X and Y with the provided values
    public Vector(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    // Add the components of another vector to this vector
    public void add(final Vector other) {
        this.X += other.X;
        this.Y += other.Y;
    }

    // Add the components of another vector to this vector
    public void add(final int x,final int y) {
        this.X += x;
        this.Y += y;
    }

    // Subtract the components of another vector from this vector
    public void subtract(final Vector other) {
        this.X -= other.X;
        this.Y -= other.Y;
    }

    // Multiply this vector by a scalar value
    public void multiply(final int scalar) {
        this.X *= scalar;
        this.Y *= scalar;
    }

    // Divide this vector by a scalar value
    public void divide(final int scalar) {
        this.X /= scalar;
        this.Y /= scalar;
    }

    // Override the toString() method to provide a string representation of the
    // vector
    @Override
    public String toString() {
        return "Vector{ X = " + X + " Y = " + Y + "}";
    }

    // Static final vectors for common values
    public static final Vector ZERO = new Vector(0);
    public static final Vector ONE = new Vector(1);
    public static final Vector MAX = new Vector(Integer.MAX_VALUE);
    public static final Vector MIN = new Vector(Integer.MIN_VALUE);

    // Check if the vector's components are within the specified bounds
    public boolean inBounds(final int min, final int max) {
        return (X > min && X < max) && (Y > min && Y < max);
    }

    // Clamp the vector's components to be within the specified bounds
    public void clamp(final int min, final int max) {
        if (inBounds(min, max))
            return;

        X = X < min ? min : (Math.min(X, max));
        Y = Y < min ? min : (Math.min(Y, max));
    }

    // Perform linear interpolation between two vectors
    public static Vector lerp(final Vector start, final Vector end, final int t) {
        end.subtract(start);

        start.add(end);
        return start;
    }

    // Rotate the vector by swapping its X and Y components
    public void rotate() {
        int i = X;
        X = Y;
        Y = i;
    }
}

