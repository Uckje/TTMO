package nl.ttmo.engine.serverClient.threads;

import nl.ttmo.engine.serverClient.application.TTMOServerClientApplication;
import nl.ttmo.engine.serverClient.gui.niftygui.AddressController;
import nl.ttmo.engine.serverClient.gui.niftygui.MainController;

import nl.ttmo.engine.messages.serverClient.ServerClientConnectMessage;

public class Main extends TTMOServerClientApplication
{
    NetworkThread network;
	MainController mainController;

    public static void main(String[] args)
    {
        Main app = new Main();
        app.start();
    }

    public Main()
    {
        super();
    }

    @Override
    public void initApp()
    {
        new AddressController(this, assetManager, inputManager, audioRenderer, guiViewPort);
    }

    public boolean initApp(String address)
    {
        network = new NetworkThread(address);
		if(network.isConnected())
		{
			inputManager.setCursorVisible(true);

			network.start();
			mainController = new MainController(assetManager, inputManager, audioRenderer, guiViewPort);
			network.setMainController(mainController);
			network.send(new ServerClientConnectMessage());
			return true;
		}

		return false;
    }

	@Override
	public void TTMOStop()
	{
		if(network != null)
		{
			network.exit();
		}

		if(mainController != null)
		{
			mainController.exit();
		}
	}

	public static String getFont()
	{
		return "aurulent-sans-16.fnt";
	}
}
