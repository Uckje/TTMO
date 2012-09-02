package nl.ttmo.client.gui.niftygui;

import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import nl.ttmo.client.gui.GuiConstants;
import nl.ttmo.client.gui.util.AssetManagerSingleton;
import nl.ttmo.client.threads.Main;

/**
 * Nifty gui controller for bar on the right
 * @author Maarten Slenter
 */
public class RightBarController implements ScreenController
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
	 * Constructor for this class. Sets up nifty and makes sure nifty gui is included in rendering.
	 * @param inputManager The JME InputManager
	 * @param audioRenderer The JME AudioRenderer
	 * @param viewPort The JME ViewPort
	 */
    public RightBarController(InputManager inputManager, AudioRenderer audioRenderer, ViewPort viewPort)
    {
		NiftyJmeDisplay niftyJmeDisplay = new NiftyJmeDisplay(AssetManagerSingleton.get(), inputManager, audioRenderer, viewPort);
        nifty = niftyJmeDisplay.getNifty();
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        nifty.fromXml("Interface/Gui/RightBar.xml", "start", this);

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
	 * Called when a placement button on the right is clicked. Will set the current type to be placed to the specified type
	 * @param type The new type for placement
	 */
	public void setCurrentType(String type)
	{
		screen.findNiftyControl("typeLabel", Label.class).setText(type);
		Main.getInstance().setCurrentType(type);
	}

	/**
	 * Exit function, when the client is shutting down
	 */
	public void exit()
	{
		nifty.exit();
	}
}
