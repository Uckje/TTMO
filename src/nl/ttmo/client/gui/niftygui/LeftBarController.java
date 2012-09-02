package nl.ttmo.client.gui.niftygui;

import com.jme3.input.InputManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.renderer.ViewPort;
import com.jme3.niftygui.NiftyJmeDisplay;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import nl.ttmo.client.gui.GuiConstants;
import nl.ttmo.client.gui.niftygui.listBox.TTMOResourceEntry;
import nl.ttmo.client.gui.util.AssetManagerSingleton;
import nl.ttmo.client.threads.Main;

import nl.ttmo.messages.client.ResourceUpdateMessage;

/**
 * Nifty gui controller for the bar on the left (containing score and resources)
 * @author Maarten Slenter
 */
public class LeftBarController implements ScreenController
{
	/**
	 * The Nifty object that belongs to this controller
	 */
    Nifty nifty;

	/**
	 * The nifty gui Screen object that belongs to this controller
	 */
	Screen screen;

	/**
	 * A list of all the resources this player has
	 */
	HashMap<String, Integer> resources = new HashMap<String, Integer>();

	/**
	 * A list of the resource entries corresponding to a certain resource
	 */
	HashMap<String, TTMOResourceEntry> resourceEntries = new HashMap<String, TTMOResourceEntry>();

	/**
	 * Constructor for this class. Sets up nifty and makes sure nifty gui is included in rendering.
	 * @param inputManager The JME InputManager
	 * @param audioRenderer The JME AudioRenderer
	 * @param viewPort The JME ViewPort
	 */
    public LeftBarController(InputManager inputManager, AudioRenderer audioRenderer, ViewPort viewPort)
    {
		NiftyJmeDisplay niftyJmeDisplay = new NiftyJmeDisplay(AssetManagerSingleton.get(), inputManager, audioRenderer, viewPort);
        nifty = niftyJmeDisplay.getNifty();
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("Interface/Definitions/TTMOControlDefinitions.xml");
        nifty.fromXml("Interface/Gui/LeftBar.xml", "start", this);

        viewPort.addProcessor(niftyJmeDisplay);
    }

	/**
	 * Binds the controller to a Nifty instance and Screen
	 * @param nifty The Nifty object
	 * @param screen The nifty gui Screen object
	 */
    public void bind(Nifty nifty, Screen screen)
    {
		this.screen = screen;
    }

    public void onStartScreen()
    {

    }

    public void onEndScreen()
    {

    }

	/**
	 * Utility method for getting the current font.
	 * @return The current font
	 */
	public static String getFont()
	{
		return GuiConstants.font;
	}

	/**
	 * Update the resources in the gui
	 * @param message The message which contains the resources updates
	 */
	public void updateResources(ResourceUpdateMessage message)
	{
		ListBox<TTMOResourceEntry> resourcesListBox = screen.findNiftyControl("resourcesListBox", ListBox.class);

		HashMap<String, Integer> newResources = message.getResources();
		for(Map.Entry<String, Boolean> entry : getResourceChanges(newResources.keySet()).entrySet())
		{
			if(entry.getValue())
			{
				TTMOResourceEntry resourceEntry = new TTMOResourceEntry(entry.getKey(), newResources.get(entry.getKey()));
				resourceEntries.put(entry.getKey(), resourceEntry);
			}
			else
			{
				resourcesListBox.removeItem(resourceEntries.get(entry.getKey()));
				resourceEntries.remove(entry.getKey());
			}
		}

		for(Map.Entry<String, Integer> entry : newResources.entrySet())
		{
			TTMOResourceEntry resourceEntry = resourceEntries.get(entry.getKey());
			resourceEntry.setResourceAmount(entry.getValue());
			resourcesListBox.removeItem(resourceEntry);
			resourcesListBox.addItem(resourceEntry);
		}

		this.resources = newResources;
	}

	/**
	 * Update the score in the gui
	 * @param score The new score
	 */
	public void updateScore(double score)
	{
		screen.findNiftyControl("scoreLabel", Label.class).setText("Score: " + Math.floor(score));
	}

	/**
	 * Generate a list of which resources have been added or deleted with the new resource update.
	 * @param newResources The list with the received resources.
	 * @return A HashMap containing all the resources affected as key, with a true (added) or false (deleted) as value
	 */
	private HashMap<String, Boolean> getResourceChanges(Set<String> newResources)
	{
		HashMap<String, Boolean> changes = new HashMap<String, Boolean>();
		Set<String> oldResources = resources.keySet();
		for(String string : newResources)
		{
			if(!oldResources.contains(string))
			{
				changes.put(string, Boolean.TRUE);
			}
		}

		for(String string : oldResources)
		{
			if(!newResources.contains(string))
			{
				changes.put(string, Boolean.FALSE);
			}
		}

		return changes;
	}

	/**
	 * Exit function, when the client is shutting down
	 */
	public void exit()
	{
		nifty.exit();
	}
}