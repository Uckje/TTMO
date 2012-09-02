/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ttmo.engine.serverClient.gui.niftygui;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.renderer.ViewPort;
import com.jme3.niftygui.NiftyJmeDisplay;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.input.NiftyInputEvent;
import de.lessvoid.nifty.input.mapping.DefaultInputMapping;
import de.lessvoid.nifty.screen.KeyInputHandler;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import nl.ttmo.engine.serverClient.threads.Main;

/**
 *
 * @author Maarten Slenter
 */
public class AddressController implements ScreenController
{
    //NiftyJmeDisplay niftyJmeDisplay;
	Main app;
    Nifty nifty;
	Screen screen;
	InputManager inputManager;

    public AddressController(Main app, final AssetManager assetManager, final InputManager inputManager, final AudioRenderer audioRenderer, final ViewPort viewPort)
    {
		final AddressController instance = this;
		NiftyJmeDisplay niftyJmeDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, viewPort);
		nifty = niftyJmeDisplay.getNifty();
		nifty.loadStyleFile("nifty-default-styles.xml");
		nifty.loadControlFile("nifty-default-controls.xml");
		nifty.fromXml("Interface/Gui/AddressScreen.xml", "start", instance);

		viewPort.addProcessor(niftyJmeDisplay);
		inputManager.setCursorVisible(true);

		this.inputManager = inputManager;

		this.app = app;
    }

    public void bind(Nifty nifty, Screen screen)
    {
		this.nifty = nifty;
		this.screen = screen;
		screen.addKeyboardInputHandler(new DefaultInputMapping(), new TTMOKeyInputHandler());
    }

	public void connect()
	{
		TextField textField = screen.findNiftyControl("address", TextField.class);
		if(app.initApp(textField.getText()))
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

	public static String getFont()
	{
		return Main.getFont();
	}

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
