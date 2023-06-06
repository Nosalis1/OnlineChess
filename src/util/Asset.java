package util;

/**
 * Represents an asset with a unique ID, name, and path.
 */
@SuppressWarnings("unused")
public class Asset {
    private static int uniqueId = 100;
    private final int id;
    private final String name;
    private final String path;

    /**
     * Creates an Asset with the specified name and path.
     * @param name The name of the asset.
     * @param path The path of the asset.
     */
    public Asset(String name, String path) {
        this.id = ++uniqueId;
        this.name = name;
        this.path = path;
    }

    /**
     * Returns the ID of the asset.
     * @return The ID.
     */
    public int getID() {
        return this.id;
    }

    /**
     * Returns the name of the asset.
     * @return The name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Checks if the asset has the specified name.
     * @param other The name to compare.
     * @return True if the names match, false otherwise.
     */
    public boolean isName(final String other) {
        return this.name.equals(other);
    }

    /**
     * Returns the path of the asset.
     * @return The path.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Checks if the asset has the specified path.
     * @param other The path to compare.
     * @return True if the paths match, false otherwise.
     */
    public boolean isPath(final String other) {
        return this.path.equals(other);
    }

    @Override
    public String toString() {
        return "Asset -> [" + this.id + "] [" + this.name + "] [" + this.path + "]";
    }
}