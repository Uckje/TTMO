package nl.ttmo.engine.client.gui.world.util;

import nl.ttmo.engine.lib.game.world.GameMap;
import nl.ttmo.engine.lib.game.world.MapGridSquare;
import nl.ttmo.engine.lib.world.Location;

/**
 * Provides an interface to GameMap for the GuiGridsQuare classes
 * @author Maarten Slenter
 */
public class GuiGameMapUtilities
{
	/**
	 * Checks if the given location has the given type
	 */
	public static boolean hasTypeAt(Location location, String type)
	{
		return GameMap.getInstance().getMapGridSquare(location).hasType(type);
	}

	/**
	 * Checks if the given location <b>only</b> has the given type
	 */
	public static boolean hasOnlyTypeAt(Location location, String type)
	{
		String[] types = GameMap.getInstance().getMapGridSquare(location).getGridSquareTypes();
		return (types.length == 1 && types[0].equals(type));
	}

	/**
	 * Checks if the given location has the given type next to it
	 */
	public static boolean hasTypeNextTo(Location location, String type)
	{
		for(Location.Direction face : Location.Direction.values())
		{
			if(hasTypeInDirection(location, face, type))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if the given location has the given type in the given direction
	 */
	public static boolean hasTypeInDirection(Location location, Location.Direction direction, String type)
	{
		if(location.getFace(direction).isValid())
		{
			return GameMap.getInstance().getMapGridSquare(location.getFace(direction)).hasType(type);
		}
		else
		{
			return false;
		}
	}

	/**
	 * Counts the amount of times the given type is within radius of location
	 */
	public static int countTypeInArea(Location location, int radius, String type)
	{
		int count = 0;

		for(MapGridSquare mapGridSquare : GameMap.getInstance().getArea(location, radius))
		{
			if(mapGridSquare.hasType(type))
			{
				count++;
			}
		}

		return count;
	}

	/**
	 * Checks if the given type is withing radius of location
	 */
	public static boolean hasTypeInArea(Location location, int radius, String type)
	{
		for(MapGridSquare mapGridSquare : GameMap.getInstance().getArea(location, radius))
		{
			if(mapGridSquare.hasType(type))
			{
				return true;
			}
		}

		return false;
	}
}
