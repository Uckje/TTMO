package nl.ttmo.engine.client.gui.niftygui;

import com.jme3.audio.AudioRenderer;
import com.jme3.input.InputManager;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.mapping.DefaultInputMapping;
import de.lessvoid.nifty.screen.KeyInputHandler;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import nl.ttmo.engine.client.gui.GuiConstants;
import nl.ttmo.engine.client.gui.util.AssetManagerSingleton;
import nl.ttmo.engine.client.threads.Main;

/**
 * NiftyGui controller for the initial connect screen
 * @author Maarten Slenter
 */
public class ConnectController implements ScreenController
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
	 * @param app The Main object instance
	 * @param inputManager The JME InputManager
	 * @param audioRenderer The JME AudioRenderer
	 * @param viewPort The JME ViewPort
	 */
    public ConnectController(final InputManager inputManager, final AudioRenderer audioRenderer, final ViewPort viewPort)
    {
		NiftyJmeDisplay niftyJmeDisplay = new NiftyJmeDisplay(AssetManagerSingleton.get(), inputManager, audioRenderer, viewPort);
		nifty = niftyJmeDisplay.getNifty();
		nifty.loadStyleFile("nifty-default-styles.xml");
		nifty.loadControlFile("nifty-default-controls.xml");
		nifty.fromXml("Interface/Gui/ConnectScreen.xml", "start", this);

		viewPort.addProcessor(niftyJmeDisplay);
		inputManager.setCursorVisible(true);
    }

	/**
	 * Binds the controller to a Nifty instance and Screen
	 * @param nifty The Nifty object
	 * @param screen The nifty gui Screen object
	 */
    public void bind(Nifty nifty, Screen screen)
    {
		this.nifty = nifty;
		this.screen = screen;
		screen.addKeyboardInputHandler(new DefaultInputMapping(), new TTMOKeyInputHandler());
    }

	/**
	 * Called when the connect button is clicked or the return key is pressed.
	 * Will attempt to connect to the server and start the rest of the client, will notify the user if unsuccessful
	 */
	public void connect()
	{
		TextField nameField = screen.findNiftyControl("name", TextField.class);
		TextField addressField = screen.findNiftyControl("address", TextField.class);
		if(Main.getInstance().initApp(nameField.getText(), addressField.getText()))
		{
			nifty.exit();
		}
		else
		{
			screen.findNiftyControl("messageLabel", Label.class).setText("Connect failed, try again");
		}
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
	 * Listener for the return key, will call connect if return key is pressed.
	 */
	class TTMOKeyInputHandler implements KeyInputHandler
	{
		public boolean keyEvent(NiftyInputEvent inputEvent)
		{
			if(inputEvent != null && inputEvent.equals(NiftyInputEvent.Activate))
			{
				connect();
				return true;
			}

			return false;
		}

	}
}
