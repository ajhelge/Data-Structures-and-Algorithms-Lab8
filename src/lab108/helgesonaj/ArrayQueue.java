package lab108.helgesonaj;

/**
 * Data Structures & Algorithms 6th Edition 
 * Goodrick, Tamassia, Goldwasser
 * 
 * An implementation of a ArrayQueue class.
 * 
 * 
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 *
 * @author Alec Helgeson, transcribed by
 * @version 3/16/2024
 */

/** Implementation of the queue ADT using a fixed-length array. */
public class ArrayQueue<E> implements Queue<E> {
    // instance variables
    private E[] data; // generic array used for storage
    private int f = 0; // index of the front element
    private int sz = 0; // current number of elements
    private static final int CAPACITY = 10;

    // constructors
    public ArrayQueue() {
        this(CAPACITY);
    } // constructs queue with default capacity

    public ArrayQueue(int capacity) { // constructs queue with given capacity
        data = (E[]) new Object[capacity]; // safe cast; compiler may give warning
    }

    // methods
    /** Returns the number of elements in the queue. */
    public int size() {
        return sz;
    }

    /** Tests whether the queue is empty. */
    public boolean isEmpty() {
        return (sz == 0);
    }

    /** Inserts an element at the rear of the queue. */
    public void enqueue(E e) throws IllegalStateException {
        if (sz == data.length)
            throw new IllegalStateException("Queue is full");
        int avail = (f + sz) % data.length; // use modular arithmetic
        data[avail] = e;
        sz++;
    }

    /**
     * Returns, but does not remove, the first element of the queue (null if empty).
     */
    public E first() {
        if (isEmpty())
            return null;
        return data[f];
    }

    /** Removes and returns the first element of the queue (null if empty). */
    public E dequeue() {
        if (isEmpty())
            return null;
        E answer = data[f];
        data[f] = null; // dereference to help garbage collection
        f = (f + 1) % data.length;
        sz--;
        return answer;
    }

    /** Creates a deep copy of this ArrayQueue for reference */
    public ArrayQueue<E> copy(){
        if(isEmpty()){
            return null;
        }
        int size = this.size();
        ArrayQueue<E> copy = new ArrayQueue<>(size);
        for(int i = 0 ; i < size; i++){
            copy.enqueue(data[i]);
        }
        return copy;
    }
}
