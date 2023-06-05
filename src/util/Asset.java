package util;

/**
 * The Asset class represents an asset with a unique identifier, name, and path.
 */
@SuppressWarnings("unused")
public class Asset {
    private static int uniqueId = 100;

    private final int id;

    /**
     * Constructs a new Asset object with the given name and path.
     *
     * @param name The name of the asset.
     * @param path The path of the asset.
     */
    public Asset(String name, String path) {
        this.id = ++uniqueId;
        this.name = name;
        this.path = path;
    }

    /**
     * Returns the unique identifier of the asset.
     *
     * @return The unique identifier.
     */
    public final int getID() {
        return this.id;
    }

    private final String name;

    /**
     * Returns the name of the asset.
     *
     * @return The name of the asset.
     */
    public final String getName() {
        return this.name;
    }

    /**
     * Checks if the asset has the same name as the specified string.
     *
     * @param other The string to compare with.
     * @return true if the asset name equals the specified string, false otherwise.
     */
    public final boolean isName(final String other) {
        return this.name.equals(other);
    }

    private final String path;

    /**
     * Returns the path of the asset.
     *
     * @return The path of the asset.
     */
    public final String getPath() {
        return this.path;
    }

    /**
     * Checks if the asset has the same path as the specified string.
     *
     * @param other The string to compare with.
     * @return true if the asset path equals the specified string, false otherwise.
     */
    public final boolean isPath(final String other) {
        return this.path.equals(other);
    }

    /**
     * Returns a string representation of the asset.
     *
     * @return A string representation of the asset.
     */
    @Override
    public String toString() {
        return "Asset -> [" + this.id + "] [" + this.name + "] [" + this.path + "]";
    }
}
