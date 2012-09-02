package nl.ttmo.engine.lib.tree;

import java.util.ArrayList;

/**
 * A basic binary tree implementation
 * @author Maarten Slenter
 */
public class Tree<E extends TreeNode<E>>
{
	/**
	 * The root node of this tree
	 */
	private E mainNode;

	public Tree()
	{}

	public Tree(E treeNode)
	{
		mainNode = treeNode;
	}

	public Tree(ArrayList<E> treeNodes)
	{
		mainNode = treeNodes.get(0);

		for(int i = 1; i <= treeNodes.size() - 1; i++)
		{
			mainNode.add(treeNodes.get(i));
		}
	}

	/**
	 * Adds a node to this tree
	 * @param object The node to add
	 */
	public void add(E object)
	{
		mainNode.add(object);
	}

	/**
	 * Removes a node from this tree
	 * @param object The node to remove
	 */
	public void remove(E object)
	{
		mainNode.remove(object);
	}
}
