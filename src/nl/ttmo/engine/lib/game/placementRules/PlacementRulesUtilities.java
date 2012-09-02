package nl.ttmo.engine.lib.game.placementRules;

import java.util.ArrayList;

import nl.ttmo.engine.lib.game.mapObjects.MapObject;
import nl.ttmo.engine.lib.game.routing.ConnectionPoint;
import nl.ttmo.engine.lib.game.routing.RouteSet;
import nl.ttmo.engine.lib.game.world.GameMap;
import nl.ttmo.engine.lib.game.world.MapGridSquare;
import nl.ttmo.engine.lib.world.Location;

/**
 * Utility class for the placement rules.
 * Contains various checks that are useful for checking placement rules
 * @author Maarten Slenter
 */
public class PlacementRulesUtilities
{
	/**
	 * Checks if the specified type is on top of the specified location
	 * @param baseLocation The location to check
	 * @param map The game map instance
	 * @param type The type to check for
	 * @return True if the type has been found at the location, false otherwise
	 */
	public static boolean checkIfOnTopOf(Location baseLocation, String type)
	{
		return GameMap.getInstance().getMapGridSquare(baseLocation).hasType(type);
	}

	/**
	 * Checks if the specified type is next to the specified location
	 * @param baseLocation The location to check
	 * @param map The game map instance
	 * @param type The type to check for
	 * @return True if the type has been found next to the location, false otherwise
	 */
	public static boolean checkIfNextTo(Location baseLocation, String type)
	{
		for(Location.Direction face : Location.Direction.values())
		{
			MapGridSquare mapGridSquare = GameMap.getInstance().getMapGridSquare(baseLocation.getFace(face));
			if(mapGridSquare.hasType(type))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if the specified type is within radius grid squares of the specified location
	 * @param baseLocation The location to check
	 * @param map The game map instance
	 * @param type The type to check for
	 * @param radius The radius
	 * @return True if the type has been found within radius of location, false otherwise
	 */
	public static boolean checkIfNear(Location baseLocation, String type, int radius)
	{
		ArrayList<MapGridSquare> mapGridSquares = GameMap.getInstance().getArea(baseLocation, radius);
		for(MapGridSquare gridSquare : mapGridSquares)
		{
			if(gridSquare.hasType(type))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if the specified type is reachable through another specified type from the specified location
	 * @param baseLocation The location to check
	 * @param map The game map instance
	 * @param type The type to check for
	 * @param typeThrough The type trough which the other type has to be reachable
	 * @return True if the type is reachable through the other type from location, false otherwise
	 */
	public static boolean checkIfThroughReachable(Location baseLocation, String type, String typeThrough)
	{
		return !(getThroughLocations(baseLocation, typeThrough).isEmpty());
	}

	/**
	 * Checks if the specified type is reachable through another specified type from the specified location within the specified distance
	 * @param baseLocation The location to check
	 * @param map The game map instance
	 * @param type The type to check for
	 * @param typeThrough The type trough which the other type has to be reachable
	 * @param distance The distance type can be from location trough typeThrough
	 * @return True if the type is reachable through the other type within distance of location, false otherwise
	 */
	public static boolean checkIfThroughNear(Location baseLocation, String type, String typeThrough, int distance)
	{
		return (getThroughLocations(baseLocation, typeThrough).getShortestDistance() <= distance);
	}

	/**
	 * Utility method for distance calculations, returns the set of routes through the specified type that are adjacent to the specified location
	 * @param baseLocation The location to check
	 * @param map The game map instance
	 * @param typeThrough The type through which the routes have to go
	 * @return The set of routes through the type from the location
	 */
	private static RouteSet getThroughLocations(Location baseLocation, String typeThrough)
	{
		RouteSet routeSet = new RouteSet();
		for(Location.Direction face : Location.Direction.values())
		{
			Location location = baseLocation.getFace(face);
			MapGridSquare mapGridSquare = GameMap.getInstance().getMapGridSquare(location);
			if(mapGridSquare.hasType(typeThrough))
			{
				MapObject mapObject = mapGridSquare.getGridSquare(typeThrough).getMapObject();
				routeSet.addAll(mapObject.routing.getRouteSet(new ConnectionPoint(baseLocation, location), typeThrough));
			}
		}

		return routeSet;
	}
}
