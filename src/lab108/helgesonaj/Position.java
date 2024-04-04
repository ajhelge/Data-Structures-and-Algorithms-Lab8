package lab108.helgesonaj;

/**
 * Data Structures & Algorithms 6th Edition 
 * Goodrick, Tamassia, Goldwasser
 * 
 * An implementation of a Position interface.
 * 
 * 
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 *
 * @author Alec Helgeson, transcribed by
 * @version 3/16/2024
 */

public interface Position<E> {
    /**
     * Returns the element stored at this position.
     *
     * @return the stored element
     * @throws IllegalStateException if position no longer valid
     */
    E getElement() throws IllegalStateException;
}
