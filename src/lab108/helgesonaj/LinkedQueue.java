package lab108.helgesonaj;

/**
 * Data Structures & Algorithms 6th Edition 
 * Goodrick, Tamassia, Goldwasser
 * 
 * An implementation of a LinkedQueue class.
 * 
 * 
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 *
 * @author Alec Helgeson, transcribed by
 * @version 3/16/2024
 */

/** Realization of a FIFO queue as an adaptation of a SinglyLinkedList. */
public class LinkedQueue<E> implements Queue<E> {
    private SinglyLinkedList<E> list = new SinglyLinkedList<>(); // an empty list

    public LinkedQueue() {
    } // new queue relies on the initially empty list

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void enqueue(E element) {
        list.addLast(element);
    }

    public E first() {
        return list.first();
    }

    public E dequeue() {
        return list.removeFirst();
    }
}
