package nl.ttmo.engine.lib.gui;

import nl.ttmo.engine.lib.world.Location;

/**
 * Represents the gui object of a grid square
 * @author Maarten Slenter
 */
public interface GuiGridSquareObject extends GuiObject
{
	/**
	 * Initializes the gui object
	 * @param location The location of this grid square
	 */
	public void initialize(Location location);
}
