package nl.ttmo.lib.game.world;

import java.util.Collection;
import java.util.HashMap;

import nl.ttmo.lib.game.world.gridSquares.GridSquare;
import nl.ttmo.lib.gui.Displayable;
import nl.ttmo.lib.gui.GuiMapGridSquareObject;
import nl.ttmo.lib.gui.GuiObjectFactory;
import nl.ttmo.lib.world.Location;

/**
 * Represents a map grid square, i.e. all grid squares at one location
 * @author Maarten Slenter
 */
public class MapGridSquare implements Displayable
{
	/**
	 * The location of this map grid square
	 */
	Location location;

	/**
	 * All grid squares that are part of this map grid square, organized by type
	 */
	HashMap<String, GridSquare> gridSquares = new HashMap<String, GridSquare>();

	/**
	 * The gui object of this map grid square
	 */
	GuiMapGridSquareObject guiMapGridSquare;

	public MapGridSquare(Location location, GuiObjectFactory guiObjectFactory)
	{
		this.location = location;
		this.guiMapGridSquare = (GuiMapGridSquareObject) guiObjectFactory.create(MapGridSquare.class);
	}

	/**
	 * Adds a grid square to this map grid square
	 * @param gridSquare The grid square to add
	 */
	public void addGridSquare(GridSquare gridSquare)
	{
		if(!hasType(gridSquare.gridSquareType))
		{
			gridSquares.put(gridSquare.gridSquareType, gridSquare);
		}

		if(guiMapGridSquare != null)
		{
			guiMapGridSquare.addGuiGridSquare(gridSquare.getGuiObject());
		}
	}

	/**
	 * @return True if this map grid square is empty, false otherwise
	 */
	public boolean isEmpty()
	{
		return gridSquares.isEmpty();
	}

	public Collection<GridSquare> getGridSquares()
	{
		return gridSquares.values();
	}

	public GridSquare getGridSquare(String type)
	{
		return gridSquares.get(type);
	}

	/**
	 * Checks if this map grid square has the specified type
	 * @param type The type to check
	 * @return True if this map grid square has the specified type, false otherwise
	 */
	public boolean hasType(String type)
	{
		return gridSquares.containsKey(type);
	}

	public Location getLocation()
	{
		return location;
	}

	public String[] getGridSquareTypes()
	{
		return gridSquares.keySet().toArray(new String[0]);
	}

	@Override
	public GuiMapGridSquareObject getGuiObject()
	{
		return guiMapGridSquare;
	}
}
