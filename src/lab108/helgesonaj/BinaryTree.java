package lab108.helgesonaj;

/**
 * Data Structures & Algorithms 6th Edition
 * Goodrick, Tamassia, Goldwasser
 * 
 * An implementation of a BinaryTree class.
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
 * An interface for a binary tree, in which each node has at most two children.
 */
public interface BinaryTree<E> extends Tree<E> {
    /** Returns the Position of p's left child (or null if no child exists). */
    Position<E> left(Position<E> p) throws IllegalArgumentException;

    /** Returns the Position of p's right child (or null if no child exists). */
    Position<E> right(Position<E> p) throws IllegalArgumentException;

    /** Returns the Position of p's sibling (or null if no sibling exists). */
    Position<E> sibling(Position<E> p) throws IllegalArgumentException;
}