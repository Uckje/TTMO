package nl.ttmo.lib.game.world.gridSquares;

import nl.ttmo.lib.game.mapObjects.MapObject;
import nl.ttmo.lib.gui.Displayable;
import nl.ttmo.lib.gui.GuiGridSquareObject;
import nl.ttmo.lib.world.Location;

/**
 * Represents a grid square
 * @author Maarten Slenter
 */
public class GridSquare implements Displayable
{
	/**
	 * The type of this grid square
	 */
    public final String gridSquareType = getGridSquareType();

	/**
	 * The map object this grid square belongs to
	 */
	protected MapObject mapObject;

	/**
	 * The value of this grid square
	 */
    protected double value = 0;

	/**
	 * The location of this grid square
	 */
    protected Location location;

	/**
	 * The gui object of this grid square
	 */
    protected GuiGridSquareObject guiGridSquare;

	/**
	 * No argument constructor, for serialization and dynamic instantiation only
	 */
    public GridSquare()
    {}

    public GridSquare(Location location, GuiGridSquareObject guiGridSquare)
    {
        initialize(location, guiGridSquare);
    }

	/**
	 * Initializes this grid square if it was dynamically instantiated
	 * @param location The location of this grid square
	 * @param guiGridSquare The gui object of this grid square
	 */
    public void initialize(Location location, GuiGridSquareObject guiGridSquare)
    {
		this.location = location;
		this.guiGridSquare = guiGridSquare;
    }

    public double getValue()
    {
		return value;
    }

	@Override
	public GuiGridSquareObject getGuiObject()
	{
		return guiGridSquare;
	}

	public MapObject getMapObject()
	{
		return mapObject;
	}

	/**
	 * @return True if this grid square has a map object, false otherwise
	 */
	public boolean hasMapObject()
	{
		return mapObject != null;
	}

	public void setMapObject(MapObject mapObject)
	{
		this.mapObject = mapObject;
	}

    protected String getGridSquareType()
    {
		return "G";
    }

	/**
	 * Compares the supplied type to this grid square's type
	 * @param typeToCheck The type to compare against
	 * @return True if this grid square is the type that was supplied, false otherwise
	 */
	public boolean isType(String typeToCheck)
	{
		return (gridSquareType.equals(typeToCheck));
	}
}
