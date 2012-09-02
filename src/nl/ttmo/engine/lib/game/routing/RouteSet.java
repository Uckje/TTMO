package nl.ttmo.engine.lib.game.routing;

import java.util.ArrayList;

import nl.ttmo.engine.lib.tree.Tree;

/**
 * Represents a set or routes between two map objects
 * @author Maarten Slenter
 */
public class RouteSet extends Tree<Route>
{
	/**
	 * The main node of the binary tree of routes
	 */
	Route mainNode;

	public RouteSet()
	{}

	public RouteSet(ConnectionPoint point1, ConnectionPoint point2)
	{
		mainNode = new Route(point1, point2);
	}

	/**
	 * Adds routes from each of the points in points1 to point2
	 * @param points1 A set of connection points with the first map object
	 * @param point2 The connection point with the second object
	 */
	public RouteSet(ArrayList<ConnectionPoint> points1, ConnectionPoint point2)
	{
		mainNode = new Route(points1.get(0), point2);

		for(int i = 1; i <= points1.size() - 1; i++)
		{
			mainNode.add(new Route(points1.get(i), point2));
		}
	}

	/**
	 * Adds routes from each of the points in points1 to the points in points2
	 * @param points1 A set of connection points with the first map object
	 * @param points2 A set of connection points with the second map object
	 */
	public RouteSet(ArrayList<ConnectionPoint> points1, ArrayList<ConnectionPoint> points2)
	{
		mainNode = new Route(points1.get(0), points2.get(0));

		for(int i = 1; i <= points2.size() - 1; i++)
		{
			mainNode.add(new Route(points1.get(0), points2.get(i)));
		}

		for(int i = 1; i <= points1.size() - 1; i++)
		{
			for(int j = 1; j <= points2.size() - 1; j++)
			{
				mainNode.add(new Route(points1.get(i), points2.get(j)));
			}
		}
	}

	public RouteSet(Route routeObject)
	{
		mainNode = routeObject;
	}

	public RouteSet(ArrayList<Route> routeObjects)
	{
		mainNode = routeObjects.get(0);

		for(int i = 1; i <= routeObjects.size() - 1; i++)
		{
			mainNode.add(routeObjects.get(i));
		}
	}

	/**
	 * Adds a route to the tree
	 * @param routeObject The route object to add
	 */
	@Override
	public void add(Route routeObject)
	{
		if(mainNode != null)
		{
			mainNode.add(routeObject);
		}
		else
		{
			mainNode = routeObject;
		}
	}

	/**
	 * Adds routes from each of the points in points1 to point2
	 * @param points1 A set of connection points with the first map object
	 * @param point2 The connection point with the second map object
	 */
	public void add(ArrayList<ConnectionPoint> points1, ConnectionPoint point2)
	{
		for(ConnectionPoint point1 : points1)
		{
			if(mainNode == null)
			{
				mainNode = new Route(point1, point2);
			}
			else
			{
				mainNode.add(new Route(point1, point2));
			}
		}
	}

	/**
	 * Adds routes from each of the points in points1 to the points in points2
	 * @param points1 A set of connection points with the first map object
	 * @param points2 A set of connection points with the second map object
	 */
	public void add(ArrayList<ConnectionPoint> points1, ArrayList<ConnectionPoint> points2)
	{
		for(ConnectionPoint point1 : points1)
		{
			for(ConnectionPoint point2 : points2)
			{
				if(mainNode != null)
				{
					mainNode.add(new Route(point1, point2));
				}
				else
				{
					mainNode = new Route(point1, point2);
				}
			}
		}
	}

	/**
	 * Adds all the routes from the supplied route set
	 * @param routeSet The route set to add the routes from
	 */
	public void addAll(RouteSet routeSet)
	{
		for(Route route : routeSet.buildList())
		{
			this.add(route);
		}
	}

	/**
	 * Removes the supplied route object from this tree
	 * @param routeObject The route object to remove
	 */
	public void remove(Route routeObject)
	{
		mainNode.remove(routeObject);
	}

	/**
	 * Finds the shortest distance in the tree and returns it
	 * @return The shortest distance in this tree
	 */
	public double getShortestDistance()
	{
		return mainNode.getShortestDistance();
	}

	/**
	 * Finds the longest distance in the tree and returns it
	 * @return The longest distance in this tree
	 */
	public double getLongestDistance()
	{
		return mainNode.getLongestDistance();
	}

	/**
	 * @return True if this tree is empty, false otherwise
	 */
	public boolean isEmpty()
	{
		return (mainNode == null);
	}

	/**
	 * Builds an ArrayList with all the route objects in this tree in it
	 * @return An ArrayList with all the route objects in this tree
	 */
	public ArrayList<Route> buildList()
	{
		ArrayList<Route> store = new ArrayList<Route>();
		mainNode.buildList(store);
		return store;
	}
}
