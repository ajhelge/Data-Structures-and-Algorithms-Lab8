package lab108.helgesonaj;

/**
 * Data Structures & Algorithms 6th Edition 
 * Goodrick, Tamassia, Goldwasser
 * 
 * An implementation of a simple Queue interface.
 * 
 * 
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 *
 * @author Alec Helgeson, transcribed by
 * @version 3/16/2024
 */

public interface Queue<E> {
    /** Returns the number of elements in the queue. */
    int size();

    /** Tests whether the queue is empty. */
    boolean isEmpty();

    /** Inserts an element at the rear of the queue. */
    void enqueue(E e);

    /** Returns, but does not remove, the first element of the queue (null if empty). */
    E first();

    /** Removes and returns the first element of the queue (null if empty). */
    E dequeue();
}
