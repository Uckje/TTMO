package nl.ttmo.engine.lib.gui;

/**
 * Represents a displayable game object, i.e. a game object that can have a gui object
 * @author Maarten Slenter
 */
public interface Displayable
{
	/**
	 * @return The gui object that belongs to this game object
	 */
	public GuiObject getGuiObject();
}
