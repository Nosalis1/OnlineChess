package socket.packages;

/**
 * The Streamable interface defines the contract for objects that can be packed and unpacked
 * into/from a string representation for streaming purposes.
 */
public interface Streamable {
    /**
     * Pack the data of the object into a string representation.
     *
     * @return The packed string representation of the object
     */
    String pack();

    /**
     * Unpack the data from the provided buffer and populate the object.
     *
     * @param buffer The buffer containing the data to unpack
     * @throws socket.exceptions.InvalidBufferFormatException if the provided buffer has an invalid format
     */
    void unpack(String buffer) throws socket.exceptions.InvalidBufferFormatException;
}