package util;

/**
 * An array data structure implementation.
 * @param <T> The type of elements stored in the array.
 */
public class Array<T> {

    /**
     * The internal array to store elements.
     */
    private T[] array;

    /**
     * Constructs a new Array object with an initial size of 0.
     */
    public Array() {
        this.array = createNew(0);
    }

    /**
     * Returns the internal array.
     *
     * @return The internal array.
     */
    public final T[] getArray() {
        return this.array;
    }

    /**
     * Creates a new array of the specified size.
     *
     * @param size The size of the new array.
     * @return The newly created array.
     */
    @SuppressWarnings("unchecked")
    private T[] createNew(final int size) {
        return (T[]) new Object[size];
    }

    /**
     * Resizes the internal array to the specified size.
     *
     * @param newSize The new size of the array.
     */
    private void resize(final int newSize) {
        T[] newArray = createNew(newSize);

        final int SHORT_SIZE = Math.min(array.length, newSize);

        if (SHORT_SIZE >= 0) System.arraycopy(this.array, 0, newArray, 0, SHORT_SIZE);

        this.array = newArray;
    }

    /**
     * Returns the current size of the array.
     *
     * @return The size of the array.
     */
    public final int size() {
        return this.array.length;
    }

    /**
     * Checks if the array is empty.
     *
     * @return True if the array is empty, false otherwise.
     */
    public final boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Replaces the internal array with the provided array.
     *
     * @param array The array to replace the internal array with.
     */
    public void replace(T[] array) {
        this.array = array;
    }

    /**
     * Sets the element at the specified index in the array.
     *
     * @param index The index at which to set the element.
     * @param item  The item to be set at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public void set(final int index, T item) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException(index);
        this.array[index] = item;
    }

    /**
     * Adds an item to the end of the array.
     *
     * @param item The item to be added.
     */
    public void add(T item) {
        resize(size() + 1);
        this.array[size() - 1] = item;
    }

    /**
     * Adds multiple items to the end of the array.
     *
     * @param items The array of items to be added.
     */
    public void add(T[] items) {
        for (T item : items) {
            add(item);
        }
    }

    /**
     * Retrieves the element at the specified index in the array.
     *
     * @param index The index of the element to retrieve.
     * @return The element at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public T get(final int index) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException(index);
        return this.array[index];
    }

    /**
     * Checks if the array contains the specified item.
     *
     * @param item The item to search for in the array.
     * @return True if the item is found in the array, false otherwise.
     */
    public final boolean contains(T item) {
        for (T arrayItem : this.array) {
            if (arrayItem == item)
                return true;
        }
        return false;
    }

    /**
     * Finds the index of the specified item in the array.
     *
     * @param item The item to find the index of.
     * @return The index of the item in the array, or -1 if the item is not found.
     */
    public final int findIndexOf(T item) {
        if (contains(item)) {
            for (int i = 0; i < size(); i++) {
                if (this.array[i] == item)
                    return i;
            }
        }
        return -1;
    }

    /**
     * Removes the element at the specified index from the array.
     *
     * @param index The index of the element to remove.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public void remove(final int index) {
        if (index < 0 || index >= size())
            throw new IndexOutOfBoundsException(index);

        this.array[index] = null;

        for (int i = index; i < size(); i++)
            this.array[i] = this.array[i + 1];

        resize(size() - 1);
    }

    /**
     * Removes the specified item from the array.
     *
     * @param item The item to remove from the array.
     */
    public void remove(T item) {
        remove(findIndexOf(item));
    }

    /**
     * Clears the array by resizing it to size 0.
     */
    public void clear() {
        resize(0);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("GenericArray:\n");
        for (int i = 0; i < size(); i++) {
            str.append(this.array[i]).append("\n");
        }

        return str.toString();
    }
}