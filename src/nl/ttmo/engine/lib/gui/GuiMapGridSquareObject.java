package nl.ttmo.engine.lib.gui;

import nl.ttmo.engine.lib.world.Location;

/**
 * Represents the gui object of a map grid square
 * @author Maarten Slenter
 */
public interface GuiMapGridSquareObject extends GuiObject
{
	/**
	 * Initializes the gui object
	 * @param location The location of this map grid square
	 */
	public void initialize(Location location);

	/**
	 * Adds the gui object of a grid square to this gui object
	 * @param gridSquare The gui object to add
	 */
	public void addGuiGridSquare(GuiGridSquareObject gridSquare);
}
