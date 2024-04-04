package lab108.helgesonaj;

import java.util.Iterator;
import java.lang.Iterable;

/**
 * Data Structures & Algorithms 6th Edition
 * Goodrick, Tamassia, Goldwasser
 * 
 * An implementation of a Tree Interface.
 * 
 * 
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 *
 * @author Alec Helgeson, transcribed by
 * @version 3/16/2024
 */

/**
 * An interface for a tree where nodes can have an arbitrary number of children.
 */
public interface Tree<E> extends Iterable<E> {
    Position<E> root();

    Position<E> parent(Position<E> p) throws IllegalArgumentException;

    Iterable<Position<E>> children(Position<E> p)
            throws IllegalArgumentException;

    int numChildren(Position<E> p) throws IllegalArgumentException;

    boolean isInternal(Position<E> p) throws IllegalArgumentException;

    boolean isExternal(Position<E> p) throws IllegalArgumentException;

    boolean isRoot(Position<E> p) throws IllegalArgumentException;

    int size();

    boolean isEmpty();

    Iterator<E> iterator();

    Iterable<Position<E>> positions();
}
