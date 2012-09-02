package nl.ttmo.client.gui.util;

import nl.ttmo.lib.ObjectFactory;
import nl.ttmo.lib.gui.Displayable;
import nl.ttmo.lib.gui.GuiObject;
import nl.ttmo.lib.gui.GuiObjectFactory;

/**
 * A factory for gui objects. Used to generate all the gui objects that the client needs
 * @author Maarten Slenter
 */
public class ClientGuiObjectFactory implements GuiObjectFactory
{
	/**
	 * Instantiates the gui object that corresponds to the supplied game class.
	 * The path to the gui object is built out of the name and path of the full class path to the game class.
	 * Two things are changed:
	 *	1. Everything after the game package to the end is used as the package name, with the gui packaged added to the beginning
	 *	2. Gui is added to the front of the class name
	 *
	 * For example:
	 * game.world.GameMap gets the gui object gui.world.GuiGameMap;
	 * @param gameClass The Class object that belongs to the game class
	 * @return The gui object belonging to the supplied game class
	 */
	public GuiObject create(Class<? extends Displayable> gameClass)
	{
		String completeName = gameClass.getName();
		String path = completeName.substring(completeName.lastIndexOf("game") + 5, completeName.lastIndexOf("."));
		String className = completeName.substring(completeName.lastIndexOf(".") + 1);
		GuiObject guiObject = (GuiObject) ObjectFactory.createObject("client", "gui." + path + ".Gui" + className);
		return guiObject;
	}
}
