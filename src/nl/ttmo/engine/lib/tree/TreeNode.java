package nl.ttmo.engine.lib.tree;

/**
 * A node for a binary tree represented by Tree
 * @author Maarten Slenter
 */
public interface TreeNode<E>
{
	/**
	 * Adds an object to the tree
	 * @param object The object to add
	 */
	public void add(E object);

	/**
	 * Removes an object from the tree
	 * @param object The object to remove
	 */
	public void remove(E object);
}
