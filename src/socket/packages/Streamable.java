package socket.packages;

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
     */
    void unpack(String buffer) throws socket.exceptions.InvalidBufferFormatException;
}