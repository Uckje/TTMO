/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.ttmo.serverClient.gui.niftygui;

import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.renderer.ViewPort;
import com.jme3.niftygui.NiftyJmeDisplay;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import nl.ttmo.messages.TTMOMessage;
import nl.ttmo.messages.client.ClientConnectMessage;
import nl.ttmo.messages.serverClient.MessageEventMessage;

import nl.ttmo.serverClient.gui.niftygui.listBox.TTMOConnectionEntry;
import nl.ttmo.serverClient.gui.niftygui.listBox.TTMOMessageEntry;
import nl.ttmo.serverClient.threads.Main;

/**
 *
 * @author Maarten Slenter
 */
public class MainController implements ScreenController
{
    //NiftyJmeDisplay niftyJmeDisplay;
    Nifty nifty;
	Screen screen;
	InputManager inputManager;

    public MainController(final AssetManager assetManager, final InputManager inputManager, final AudioRenderer audioRenderer, final ViewPort viewPort)
    {
		final MainController instance = this;
		NiftyJmeDisplay niftyJmeDisplay = new NiftyJmeDisplay(assetManager, inputManager, audioRenderer, viewPort);
		nifty = niftyJmeDisplay.getNifty();
		nifty.loadStyleFile("nifty-default-styles.xml");
		nifty.loadControlFile("Interface/Definitions/TTMOControlDefinitions.xml");
		nifty.fromXml("Interface/Gui/Main.xml", "start", instance);

		viewPort.addProcessor(niftyJmeDisplay);
		inputManager.setCursorVisible(true);
    }

    public void bind(Nifty nifty, Screen screen)
    {
		this.nifty = nifty;
		this.screen = screen;
    }

	public void messageRecieved(MessageEventMessage message)
	{
		TTMOMessage TTMOMessage = message.getMessage();
		ListBox<TTMOMessageEntry> messagesListBox = screen.findNiftyControl("messagesListBox", ListBox.class);
		messagesListBox.insertItem(new TTMOMessageEntry(message.getDirection(), TTMOMessage.getClass().getName()), 0);

		if(TTMOMessage instanceof ClientConnectMessage)
		{
			ClientConnectMessage clientConnectMessage = (ClientConnectMessage) TTMOMessage;
			ListBox<TTMOConnectionEntry> connectionsListBox = screen.findNiftyControl("connectionsListBox", ListBox.class);
			connectionsListBox.addItem(new TTMOConnectionEntry(clientConnectMessage.getPlayerName()));
		}
	}

    public void onStartScreen()
    {

    }

    public void onEndScreen()
    {

    }

	public void exit()
	{
		nifty.exit();
	}

	public static String getFont()
	{
		return Main.getFont();
	}
}
