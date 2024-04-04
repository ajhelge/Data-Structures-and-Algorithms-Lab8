package lab108.helgesonaj;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Data Structures & Algorithms 6th Edition
 * Goodrick, Tamassia, Goldwasser
 * 
 * An implementation of a ArrayList class.
 * 
 * 
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 *
 * @author Alec Helgeson, transcribed by
 * @version 3/16/2024
 */

public class ArrayList<E> implements List<E> {

  // ---------------- nested ArrayIterator class ----------------
  /**
   * A (nonstatic) inner class. Note well that each instance contains an implicit
   * reference to the containing list, allowing it to access the list's members.
   */
  private class ArrayIterator implements Iterator<E> {
    private int j = 0; // index of the next element to report
    private boolean removable = false; // can remove be called at this time?

    /**
     * Tests whether the iterator has a next object.
     * 
     * @return true if there are further objects, false otherwise
     */
    public boolean hasNext() {
      return j < size;
    } // size is field of outer instance

    /**
     * Returns the next object in the iterator.
     *
     * @return next object
     * @throws NoSuchElementException if there are no further elements
     */
    public E next() throws NoSuchElementException {
      if (j == size)
        throw new NoSuchElementException("No next element");
      removable = true; // this element can subsequently be removed
      return data[j++]; // post-increment j, so it is ready for future call to next
    }

    /**
     * Removes the element returned by most recent call to next.
     * 
     * @throws IllegalStateException if next has not yet been called
     * @throws IllegalStateException if remove was already called since recent next
     */
    public void remove() throws IllegalStateException {
      if (!removable)
        throw new IllegalStateException("nothing to remove");
      ArrayList.this.remove(j - 1); // that was the last one returned
      j--; // next element has shifted one cell to the left
      removable = false; // do not allow remove again until next is called
    }
  } // ------------ end of nested ArrayIterator class ------------

  /** Returns an iterator of the elements stored in the list. */
  public Iterator<E> iterator() {
    return new ArrayIterator(); // create a new instance of the inner class
  }

  // instance variables
  public static final int CAPACITY = 16; // default array capacity
  private E[] data; // generic array used for storage
  private int size = 0; // current number of elements
  // constructors

  public ArrayList() {
    this(CAPACITY);
  } // constructs list with default capacity

  public ArrayList(int capacity) { // constructs list with given capacity
    data = (E[]) new Object[capacity]; // safe cast; compiler may give warning
  }

  // public methods
  /** Returns the number of elements in the array list. */
  public int size() {
    return size;
  }

  /** Returns whether the array list is empty. */
  public boolean isEmpty() {
    return size == 0;
  }

  /** Returns (but does not remove) the element at index i. */
  public E get(int i) throws IndexOutOfBoundsException {
    checkIndex(i, size);
    return data[i];
  }

  /** Replaces the element at index i with e, and returns the replaced element. */
  public E set(int i, E e) throws IndexOutOfBoundsException {
    checkIndex(i, size);
    E temp = data[i];
    data[i] = e;
    return temp;
  }

  /**
   * Inserts element e to be at index i, shifting all subsequent elements later.
   */
  public void add(int i, E e) throws IndexOutOfBoundsException,
      IllegalStateException {
    checkIndex(i, size + 1);
    if (size == data.length) // not enough capacity
      throw new IllegalStateException("Array is full");
    for (int k = size - 1; k >= i; k--) // start by shifting rightmost
      data[k + 1] = data[k];
    data[i] = e; // ready to place the new element
    size++;
  }

  /**
   * Removes/returns the element at index i, shifting subsequent elements earlier.
   */
  public E remove(int i) throws IndexOutOfBoundsException {
    checkIndex(i, size);
    E temp = data[i];
    for (int k = i; k < size - 1; k++) // shift elements to fill hole
      data[k] = data[k + 1];
    data[size - 1] = null; // help garbage collection
    size--;
    return temp;
  }

  // utility method
  /** Checks whether the given index is in the range [0, n−1]. */
  protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
    if (i < 0 || i >= n)
      throw new IndexOutOfBoundsException("Illegal index: " + i);
  }

  @Override
  public boolean contains(Object o) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'contains'");
  }

  @Override
  public Object[] toArray() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toArray'");
  }

  @Override
  public <T> T[] toArray(T[] a) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'toArray'");
  }

  @Override
  public boolean add(E e) {
    try {
      add(size(), e);
    } catch (Exception ex) {
      return false;
    }
    return true;

  }

  @Override
  public boolean remove(Object o) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'remove'");
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'containsAll'");
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addAll'");
  }

  @Override
  public boolean addAll(int index, Collection<? extends E> c) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addAll'");
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'retainAll'");
  }

  @Override
  public void clear() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'clear'");
  }

  @Override
  public int indexOf(Object o) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'indexOf'");
  }

  @Override
  public int lastIndexOf(Object o) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'lastIndexOf'");
  }

  @Override
  public ListIterator<E> listIterator() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
  }

  @Override
  public ListIterator<E> listIterator(int index) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'listIterator'");
  }

  @Override
  public List<E> subList(int fromIndex, int toIndex) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'subList'");
  }
}
