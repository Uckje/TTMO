package nl.ttmo.lib.game.routing;

import java.util.ArrayList;

import nl.ttmo.lib.tree.TreeNode;

/**
 * Represents a route between two connection points through an object
 * The class is useable as a binary tree node, to make finding the shortest route between two map object the easiest possible
 * @author Maarten Slenter
 */
public class Route implements TreeNode<Route>
{
	/**
	 * The first connection point
	 */
    private ConnectionPoint endpoint1;

	/**
	 * The second connection point
	 */
    private ConnectionPoint endpoint2;

	/**
	 * A Route object containing a shorter route. If there is none, this is null
	 */
	private Route shorterRoute;

	/**
	 * A Route object containing a route of equal length. If there is none, this is null
	 */
	private Route equalRoute;

	/**
	 * A route object containing a longer route
	 */
	private Route longerRoute;

	/**
	 * The distance of this route
	 */
    private double distance;

    public Route(ConnectionPoint point1, ConnectionPoint point2)
    {
		this.endpoint1 = point1;
		this.endpoint2 = point2;

		this.distance = calculateDistance(point1, point2);
    }

	/**
	 * Calculates the distance between two connection points
	 * @param point1 The first connection point
	 * @param point2 The second connection point
	 * @return The distance between the two connection points
	 */
    public static double calculateDistance(ConnectionPoint point1, ConnectionPoint point2)
    {
		return Math.abs(point1.getX() - point2.getX()) + Math.abs(point1.getZ() - point2.getZ());
    }

    public double getDistance() {return this.distance;}

	/**
	 * If there is a shorter route, it will call its getShortestDistance. Else it returns its own distance, as it will be the shortest
	 * @return The shortest distance as seen from this node
	 */
	public double getShortestDistance()
	{
		if(shorterRoute == null)
		{
			return this.distance;
		}
		else
		{
			return shorterRoute.getShortestDistance();
		}
	}

	/**
	 * If there is a longer route, it will call its getLongestDistance. Else it returns its own distance, as it will be the longest
	 * @return The longest distance as seen from this node
	 */
	public double getLongestDistance()
	{
		if(longerRoute == null)
		{
			return this.distance;
		}
		else
		{
			return longerRoute.getLongestDistance();
		}
	}

	/**
	 * Adds the supplied Route object to the tree.
	 * Checks if the supplied route is longer, shorter or of equal distance as this route and puts it in the corresponding variable
	 * @param routeObject The route object to add
	 */
	@Override
	public void add(Route routeObject)
	{
		if(routeObject.distance < this.distance)
		{
			if(shorterRoute == null)
			{
				shorterRoute = routeObject;
			}
			else
			{
				shorterRoute.add(routeObject);
			}
		}
		else if(routeObject.distance > this.distance)
		{
			if(longerRoute == null)
			{
				longerRoute = routeObject;
			}
			else
			{
				longerRoute.add(routeObject);
			}
		}
		else
		{
			if(equalRoute == null)
			{
				equalRoute = routeObject;
			}
			else
			{
				equalRoute.add(routeObject);
			}
		}
	}

	/**
	 * Removes the supplied object from the tree.
	 * Currently a non supported method, because grid square deletion is currently impossible
	 * @param object The object to remove
	 */
	@Override
	public void remove(Route routeObject)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Builds an ArrayList with all the routes in this tree
	 * @param store The array list to store the route objects in
	 */
	public void buildList(ArrayList<Route> store)
	{
		store.add(this);

		if(shorterRoute != null)
		{
			shorterRoute.buildList(store);
		}

		if(longerRoute != null)
		{
			longerRoute.buildList(store);
		}
	}
}
