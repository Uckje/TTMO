package nl.ttmo.content.game.gridSquares;

import nl.ttmo.engine.lib.game.world.gridSquares.GridSquare;
import nl.ttmo.engine.lib.gui.GuiGridSquareObject;
import nl.ttmo.engine.lib.world.Location;

/**
 * Represents a road (R) type grid square
 * @author Maarten Slenter
 */
public class GridSquareR extends GridSquare
{
	/**
	 * No argument constructor, for serialization and dynamic instantiation purposes only
	 */
    public GridSquareR()
    {}

    public GridSquareR(Location location, GuiGridSquareObject guiGridSquare)
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
		value = 25;
    }

    @Override
    protected String getGridSquareType()
    {
		return "R";
    }
}
