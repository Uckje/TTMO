package nl.ttmo.engine.lib.gui;

/**
 * Represents a gui object factory, a class that can generate gui objects for game objects
 * @author Maarten Slenter
 */
public interface GuiObjectFactory
{
	/**
	 * Creates the gui object for the supplied game class
	 * @param gameClass The Class object belonging to a game class
	 * @return The created gui object
	 */
	public GuiObject create(Class<? extends Displayable> gameClass, boolean content);
}
