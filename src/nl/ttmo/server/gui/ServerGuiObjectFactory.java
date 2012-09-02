package nl.ttmo.server.gui;

import nl.ttmo.lib.gui.Displayable;
import nl.ttmo.lib.gui.GuiObject;
import nl.ttmo.lib.gui.GuiObjectFactory;

/**
 * Gui object factory for the server.
 * Since the server doesn't have a gui, this wil always return null
 * @author Maarten Slenter
 */
public class ServerGuiObjectFactory implements GuiObjectFactory
{
	/**
	 * Dummy method, since the server doesn't have a gui.
	 * Always returns null
	 * @param gameClass
	 * @return Null
	 */
	public GuiObject create(Class<? extends Displayable> gameClass)
	{
		return null;
	}
}
