package nl.ttmo.lib.game.world.gridSquares;

import nl.ttmo.lib.gui.GuiGridSquareObject;
import nl.ttmo.lib.world.Location;

/**
 * Represents a city (C) type grid square
 * @author Maarten Slenter
 */
public class GridSquareC extends GridSquare
{
	/**
	 * No argument constructor, for serialization and dynamic instantiation purposes only
	 */
    public GridSquareC()
    {}

    public GridSquareC(Location location, GuiGridSquareObject guiGridSquare)
    {
		initialize(location, guiGridSquare);
    }

	/**
	 * Initializes this grid square if it was dynamically instantiated
	 * @param location The location of this grid square
	 * @param guiGridSquare The gui object of this grid square
	 */
    @Override
    public void initialize(Location location, GuiGridSquareObject guiGridSquare)
    {
		super.initialize(location, guiGridSquare);
		value = 100;
    }

    @Override
    protected String getGridSquareType()
    {
		return "C";
    }
}
