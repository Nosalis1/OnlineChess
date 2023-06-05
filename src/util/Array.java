package util;

/**

 An array data structure implementation.

 @param <T> The type of elements stored in the array
 */
@SuppressWarnings("unused")
public class Array<T> {

    /**
     * Constructs an empty array.
     */
    public Array() {
        clear();
    }

    /**
     * Constructs an array with the specified size.
     *
     * @param size The size of the array
     */
    public Array(final int size) {
        this.recreate(size);
    }

    /**
     * Constructs an array from the given array.
     *
     * @param array The array to be used
     */
    public Array(T[] array) {
        this.setArray(array);
    }

    /**
     * Constructs a copy of the given array.
     *
     * @param array The array to be copied
     */
    public Array(Array<T> array) {
        this.setArray(array.copy());
    }

    private T[] array;

    /**
     * Returns the underlying array.
     *
     * @return The array
     */
    public T[] getArray() {
        return this.array;
    }

    private void setArray(T[] array) {
        this.array = array;
    }

    /**
     * Returns the size of the array.
     *
     * @return The size of the array
     */
    public final int size() {
        return this.array.length;
    }

    /**
     * Checks if the array is empty.
     *
     * @return true if the array is empty, false otherwise
     */
    public final boolean isEmpty() {
        return this.array == null || this.array.length == 0;
    }

    /**
     * Replaces the array with the given array.
     *
     * @param array The new array to replace the existing one
     */
    public void replace(T[] array) {
        this.setArray(array);
    }

    /**
     * Creates a copy of the array.
     *
     * @return A copy of the array
     */
    @SuppressWarnings("unchecked")
    public T[] copy() {
        T[] copy = (T[]) new Object[this.array.length];
        for (int i = 0; i < this.array.length; i++)
            copy[i] = this.get(i);

        return copy;
    }

    @SuppressWarnings("unchecked")
    private void recreate(final int size) {
        this.array = (T[]) new Object[size];
    }

    /**
     * Resizes the array to the specified size.
     *
     * @param newSize The new size of the array
     */
    public void resize(final int newSize) {
        T[] oldArray = this.copy();

        this.recreate(newSize);

        System.arraycopy(oldArray, 0, this.array, 0, Math.min(oldArray.length, newSize));
    }

    /**
     * Shifts all elements in the array one position to the left.
     */
    public void shiftLeft() {
        T temp = this.get(0);

        for (int i = 0; i < this.array.length - 1; i++)
            this.set(i, this.get(i + 1));

        this.set(this.array.length - 1, temp);
    }

    /**
     * Shifts all elements in the array one position to the right.
     */
    public void shiftRight() {
        T temp = this.get(this.array.length - 1);

        for (int i = this.array.length - 1; i > 0; i--)
            this.set(i, this.get(i - 1));

        this.set(0, temp);
    }

    private boolean checkIndex(final int index) {
        return index < 0 || index >= this.array.length;
    }

    /**
     * Sets the element at the specified index in the array.
     *
     * @param index The index of the element to be set
     * @param item  The item to be set at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void set(final int index, T item) {
        if (checkIndex(index))
            throw new IndexOutOfBoundsException(index);

        this.array[index] = item;
    }

    /**
     * Adds an item at the specified index in the array.
     *
     * @param index The index at which the item should be added
     * @param item  The item to be added
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void add(final int index, T item) {
        this.resize(this.array.length + 1);
        if (checkIndex(index))
            throw new IndexOutOfBoundsException(index);
        this.set(this.array.length - 1, item);
        this.swap(this.array.length - 1, index);
    }

    /**
     * Adds an item to the end of the array.
     *
     * @param item The item to be added
     */
    public void add(T item) {
        this.add(this.array.length, item);
    }

    /**
     * Adds all the items from the given array to the end of the array.
     *
     * @param array The array of items to be added
     */
    public void add(T[] array) {
        this.resize(this.array.length + array.length);
        for (int i = this.array.length - array.length, j = 0; i < this.array.length; i++, j++) {
            this.set(i, array[j]);
        }
    }

    /**
     * Inserts an item at the specified index in the array, shifting the existing elements to the right.
     *
     * @param index The index at which the item should be inserted
     * @param item  The item to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
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
     * Inserts all the items from the given array at the specified index in the array,
     * <p>
     * shifting the existing elements to the right.
     *
     * @param index The index at which the items should be inserted
     * @param array The array of items to be inserted
     * @throws IndexOutOfBoundsException if the index is out of range
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
     * Returns the item at the specified index in the array.
     *
     * @param index The index of the item to be retrieved
     * @return The item at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public T get(final int index) {
        if (checkIndex(index))
            throw new IndexOutOfBoundsException(index);
        return this.array[index];
    }

    /**
     * Returns the last item in the array.
     * @return The last item in array
     */
    public T getLast() {
        if (this.isEmpty())
            return null;
        return this.get(this.array.length - 1);
    }

    /**
     * Removes the item at the specified index from the array, shifting the remaining elements to the left.
     *
     * @param index The index of the item to be removed
     * @throws IndexOutOfBoundsException if the index is out of range
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
     * Removes the first occurrence of the specified item from the array, shifting the remaining elements to the left.
     *
     * @param item The item to be removed
     */
    public void remove(T item) {
        this.remove(findIndexOf(item));
    }

    /**
     * Clears the array, removing all elements.
     */
    public void clear() {
        this.recreate(0);
    }

    /**
     * Returns the index of the first occurrence of the specified item in the array.
     *
     * @param item The item to search for
     * @return The index of the item, or -1 if the item is not found
     */
    public final int findIndexOf(T item) {
        if (this.contains(item))
            for (int i = 0; i < this.array.length; i++)
                if (this.get(i) == item)
                    return i;
        return -1;
    }

    /**
     * Checks if the array contains the specified item.
     *
     * @param item The item to be checked
     * @return true if the item is found in the array, false otherwise
     */
    public final boolean contains(T item) {
        for (T tempItem : this.array)
            if (tempItem == item)
                return true;
        return false;
    }

    /**
     * Swaps the positions of two elements in the array.
     *
     * @param index       The index of the first element to be swapped
     * @param destination The index of the second element to be swapped
     * @throws IndexOutOfBoundsException if the index or destination is out of range
     */
    public void swap(final int index, final int destination) {
        if (checkIndex(index) || checkIndex(destination))
            throw new IndexOutOfBoundsException(index);

        if (index == destination)
            return;

        T temp = this.get(index);
        this.set(index, get(destination));
        this.set(destination, temp);
    }

    /**
     * Performs the specified action on each element of the array.
     *
     * @param action The action to be performed on each element
     */
    public void foreach(util.events.Action<T> action) {
        for (T item : this.array)
            action.run(item);
    }
}