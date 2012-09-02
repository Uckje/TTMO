package nl.ttmo.lib.gui;

import nl.ttmo.lib.world.Location;

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
