package nl.ttmo.server.threads;

import com.jme3.system.JmeContext;

import nl.ttmo.lib.game.world.GameMap;

import nl.ttmo.server.application.TTMOServerApplication;
import nl.ttmo.server.game.resources.util.ResourceManager;
import nl.ttmo.server.gui.ServerGuiObjectFactory;
import nl.ttmo.server.listeners.TTMOServerCommandListener;

/**
 * The Main class, which contains all the logic required for starting and stopping the game
 * @author Maarten Slenter
 */
public class Main extends TTMOServerApplication
{
	/**
	 * The singleton instance of Main
	 */
	private static Main instance = new Main();

	/**
	 * The time (in seconds) since the server as started
	 * Required for updating resources every second
	 */
	int oldTime = 0;

	/**
	 * Java bootstrapper for the application
	 * @param args Command line arguments, not used
	 */
    public static void main(String[] args)
    {
        instance.start(JmeContext.Type.Headless);
    }

	/**
	 * Called while the application is initialized, creates the map, map manager and network thread instances
	 */
    @Override
    public void initApp()
    {
		GameMap.getInstance().initialize(20, 20, new ServerGuiObjectFactory());
        NetworkThread.getInstance().start();

		CommandLineParser.get().start();
		CommandLineParser.get().addListener(new TTMOServerCommandListener());
    }

	/**
	 * Called when the application is stopped. Makes sure the network thread is shut down properly
	 */
	@Override
	public void TTMOStop()
	{
		NetworkThread.getInstance().exit();
		CommandLineParser.get().exit();
	}

	/**
	 * Called in the execution loop of the application.
	 * Updates the resources every time a second has passed
	 * @param tpf
	 */
	@Override
	public void TTMOUpdate(float tpf)
	{
		if(Math.floor(timer.getTimeInSeconds()) != oldTime)
		{
			oldTime = (int) Math.floor(timer.getTimeInSeconds());
			ResourceManager.getInstance().updateResources();
		}
	}

	/**
	 * @return The Main instance
	 */
	public static Main getInstance()
	{
		return instance;
	}
}
