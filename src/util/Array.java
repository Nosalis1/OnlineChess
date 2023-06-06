package util;

/**
 * An array data structure that allows dynamic resizing and various operations.
 *
 * @param <T> the type of elements in the array
 */
@SuppressWarnings("unused")
public class Array<T> {

    /**
     * Creates an empty array.
     */
    public Array() {
        clear();
    }

    /**
     * Creates an array with the specified initial size.
     *
     * @param size the initial size of the array
     */
    public Array(final int size) {
        this.recreate(size);
    }

    /**
     * Creates an array from the given array.
     *
     * @param array the array to initialize the new instance with
     */
    public Array(T[] array) {
        this.setArray(array);
    }

    /**
     * Creates a copy of the given array.
     *
     * @param array the array to copy
     */
    public Array(Array<T> array) {
        this.setArray(array.copy());
    }

    private T[] array;

    /**
     * Returns the underlying array.
     *
     * @return the array
     */
    public T[] getArray() {
        return this.array;
    }

    private void setArray(T[] array) {
        this.array = array;
    }

    /**
     * Returns the number of elements in the array.
     *
     * @return the number of elements
     */
    public final int size() {
        return this.array.length;
    }

    /**
     * Checks if the array is empty.
     *
     * @return {@code true} if the array is empty, {@code false} otherwise
     */
    public final boolean isEmpty() {
        return this.array == null || this.array.length == 0;
    }

    /**
     * Replaces the contents of the array with the given array.
     *
     * @param array the array to replace the contents with
     */
    public void replace(T[] array) {
        this.setArray(array);
    }

    /**
     * Creates a copy of the array.
     *
     * @return a copy of the array
     */
    @SuppressWarnings("unchecked")
    public T[] copy() {
        T[] copy = (T[]) new Object[this.array.length];
        for (int i = 0; i < this.array.length; i++)
            copy[i] = this.get(i);

        return copy;
    }

    /**
     * Resizes the array to the specified new size.
     * If the new size is smaller than the current size, elements beyond the new size will be truncated.
     * If the new size is larger than the current size, the additional elements will be set to null.
     *
     * @param newSize the new size of the array
     */
    @SuppressWarnings("unchecked")
    private void recreate(final int newSize) {
        this.array = (T[]) new Object[newSize];
    }

    /**
     * Resizes the array to the specified new size.
     * If the new size is smaller than the current size, elements beyond the new size will be truncated.
     * If the new size is larger than the current size, the additional elements will be set to null.
     *
     * @param newSize the new size of the array
     */
    public void resize(final int newSize) {
        T[] oldArray = this.copy();

        this.recreate(newSize);

        System.arraycopy(oldArray, 0, this.array, 0, Math.min(oldArray.length, newSize));
    }

    /**
     * Shifts the elements of the array to the left by one position.
     * The first element becomes the last element.
     */
    public void shiftLeft() {
        T temp = this.get(0);

        for (int i = 0; i < this.array.length - 1; i++)
            this.set(i, this.get(i + 1));

        this.set(this.array.length - 1, temp);
    }

    /**
     * Shifts the elements of the array to the right by one position.
     * The last element becomes the first element.
     */
    public void shiftRight() {
        T temp = this.get(this.array.length - 1);

        for (int i = this.array.length - 1; i > 0; i--)
            this.set(i, this.get(i - 1));

        this.set(0, temp);
    }

    /**
     * Checks if the given index is valid.
     *
     * @param index the index to check
     * @return {@code true} if the index is invalid, {@code false} otherwise
     */
    private boolean checkIndex(final int index) {
        return index < 0 || index >= this.array.length;
    }

    /**
     * Sets the element at the specified index in the array.
     *
     * @param index the index at which to set the element
     * @param item  the element to be set
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void set(final int index, T item) {
        if (checkIndex(index))
            throw new IndexOutOfBoundsException(index);

        this.array[index] = item;
    }

    /**
     * Inserts the element at the specified index in the array, shifting the existing elements to the right.
     *
     * @param index the index at which to insert the element
     * @param item  the element to be inserted
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void add(final int index, T item) {
        this.resize(this.array.length + 1);
        if (checkIndex(index))
            throw new IndexOutOfBoundsException(index);
        this.set(this.array.length - 1, item);
        this.swap(this.array.length - 1, index);
    }

    /**
     * Appends the element to the end of the array.
     *
     * @param item the element to be added
     */
    public void add(T item) {
        this.add(this.array.length, item);
    }

    /**
     * Appends the elements of the given array to the end of the array.
     *
     * @param array the array of elements to be added
     */
    public void add(T[] array) {
        this.resize(this.array.length + array.length);
        for (int i = this.array.length - array.length, j = 0; i < this.array.length; i++, j++) {
            this.set(i, array[j]);
        }
    }

    /**
     * Inserts the element at the specified index in the array, shifting the existing elements to the right.
     *
     * @param index the index at which to insert the element
     * @param item  the element to be inserted
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void insert(final int index, T item) {
        if (checkIndex(index))
            throw new IndexOutOfBoundsException(index);
        this.resize(this.array.length + 1);
        for (int i = this.array.length - 1; i > index; i--)
            this.set(i, this.array[i - 1]);

        this.set(index, item);
    }

    /**
     * Inserts the elements of the given array at the specified index in the array, shifting the existing elements to the right.
     *
     * @param index the index at which to insert the elements
     * @param array the array of elements to be inserted
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void insert(final int index, T[] array) {
        if (checkIndex(index))
            throw new IndexOutOfBoundsException(index);

        this.resize(this.array.length + array.length);
        for (int n = 0; n < array.length; n++)
            for (int i = this.array.length - 1; i > index; i--)
                this.set(i, this.array[i - 1]);
        for (int i = 0; i < array.length; i++)
            this.set(index + i, array[i]);
    }

    /**
     * Returns the element at the specified index in the array.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public T get(final int index) {
        if (checkIndex(index))
            throw new IndexOutOfBoundsException(index);
        return this.array[index];
    }

    /**
     * Returns the first element in the array.
     *
     * @return the first element in the array, or {@code null} if the array is empty
     */
    public T getFirst() {
        if (this.isEmpty())
            return null;
        return this.get(0);
    }

    /**
     * Returns the last element in the array.
     *
     * @return the last element in the array, or {@code null} if the array is empty
     */
    public T getLast() {
        if (this.isEmpty())
            return null;
        return this.get(this.array.length - 1);
    }

    /**
     * Removes the element at the specified index in the array, shifting the remaining elements to the left.
     *
     * @param index the index of the element to remove
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public void remove(final int index) {
        if (checkIndex(index))
            throw new IndexOutOfBoundsException(index);

        this.set(index, null);

        for (int i = index; i < this.array.length - 1; i++)
            this.set(i, this.get(i + 1));

        this.resize(this.array.length - 1);
    }

    /**
     * Removes the first occurrence of the specified element from the array.
     *
     * @param item the element to be removed
     */
    public void remove(T item) {
        this.remove(findIndexOf(item));
    }

    /**
     * Clears the array by setting its size to 0.
     */
    public void clear() {
        this.recreate(0);
    }

    /**
     * Finds the index of the first occurrence of the specified element in the array.
     *
     * @param item the element to search for
     * @return the index of the first occurrence of the element, or -1 if the element is not found
     */
    public final int findIndexOf(T item) {
        if (this.contains(item))
            for (int i = 0; i < this.array.length; i++)
                if (this.get(i) == item)
                    return i;
        return -1;
    }

    /**
     * Checks if the array contains the specified element.
     *
     * @param item the element to search for
     * @return {@code true} if the element is found, {@code false} otherwise
     */
    public final boolean contains(T item) {
        for (T tempItem : this.array)
            if (tempItem == item)
                return true;
        return false;
    }

    /**
     * Swaps the elements at the specified indices in the array.
     *
     * @param index       the index of the first element to swap
     * @param destination the index of the second element to swap
     * @throws IndexOutOfBoundsException if either index is invalid
     */
    public void swap(final int index, final int destination) {
        if (checkIndex(index) || checkIndex(destination))
            throw new IndexOutOfBoundsException("Invalid index or destination");

        T temp = this.get(index);
        this.set(index, this.get(destination));
        this.set(destination, temp);
    }

    /**
     * Applies the specified action to each element in the array.
     *
     * @param action the action to be applied to each element
     */
    public void forEach(util.events.Action<T> action) {
        for (T item : this.array) {
            action.run(item);
        }
    }
}