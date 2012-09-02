package nl.ttmo.client.threads;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

import nl.ttmo.client.application.TTMOClientApplication;
import nl.ttmo.client.gui.GuiConstants;
import nl.ttmo.client.gui.niftygui.ConnectController;
import nl.ttmo.client.gui.niftygui.LeftBarController;
import nl.ttmo.client.gui.niftygui.RightBarController;
import nl.ttmo.client.gui.util.AssetManagerSingleton;
import nl.ttmo.client.gui.util.ClientGuiObjectFactory;

import nl.ttmo.lib.game.world.GameMap;
import nl.ttmo.lib.gui.GuiGameMapObject;
import nl.ttmo.lib.world.Location;

import nl.ttmo.messages.client.PlacementMessage;
import nl.ttmo.messages.client.ClientConnectMessage;
import nl.ttmo.messages.client.MapLoadMessage;
import nl.ttmo.messages.client.ResourceUpdateMessage;
import nl.ttmo.messages.util.MapGridSquareData;

/**
 * The Main class, which contains all the logic required for starting and stopping the game
 * @author Maarten Slenter
 */
public class Main extends TTMOClientApplication
{
	/**
	 * The Main singleton instance
	 */
	private static Main instance = new Main();

	/**
	 * The instance of the action listener
	 */
    final TTMOActionListener actionListener = new TTMOActionListener();

	/**
	 * The instance of the nifty gui controller for the left bar
	 */
    LeftBarController leftBarController;

	/**
	 * The instance of the nifty gui controller for the right bar
	 */
    RightBarController rightBarController;

	/**
	 * Java bootstrapper for the application
	 * @param args Command line arguments, not used
	 */
    public static void main(String[] args)
    {
        instance.start();
    }

    public Main()
    {
        super();
    }

	/**
	 * Called while the application is initialized, will set the AssetManager on AssetManagerSingleton and start up the initial connect screen
	 */
    @Override
    public void initApp()
    {
		AssetManagerSingleton.set(assetManager);
        new ConnectController(inputManager, audioRenderer, guiViewPort);
    }

	/**
	 * Initializes the map, is called when a MapLoadMessage has been received
	 * @param mapLoadMessage The MapLoadMessage
	 */
	public void initializeMap(MapLoadMessage mapLoadMessage)
	{
		GameMap.getInstance().initialize(mapLoadMessage.getMapSizeX(), mapLoadMessage.getMapSizeZ(), new ClientGuiObjectFactory());
		Spatial mapNode = GameMap.getInstance().getGuiObject().generateSpatial();
		mapNode.setLocalTranslation(0, -10, 0);
		cam.setLocation(Vector3f.ZERO);
		cam.lookAtDirection(new Vector3f(5, -10, 5), Vector3f.UNIT_Y);
		rootNode.attachChild(mapNode);

		for(MapGridSquareData gridSquareData : mapLoadMessage.getGridSquareDataList())
		{
			for(String type : gridSquareData.getTypes())
			{
				GameMap.getInstance().updateGridSquare(new Location(gridSquareData.getX(), gridSquareData.getZ()), type, new ClientGuiObjectFactory());
			}
		}

		/*Spatial test = assetManager.loadModel("Models/Cube/Cube.j3o");
		test.scale(1f);
		test.setLocalTranslation(0, -10, 0);
		rootNode.attachChild(test);*/

		initializeControls();
	}

	/**
	 * Initializes the controls
	 */
	private void initializeControls()
	{
		inputManager.addMapping("place", new MouseButtonTrigger(0));
		inputManager.addListener(actionListener, "place");
	}

	/**
	 * Initializes the application, connecting to the server and initializing the gui
	 * @param playerName The name this player has chosen
	 * @param address The address the player wants to connect to
	 * @return A bool indicating if the connect was successful
	 */
    public boolean initApp(String playerName, String address)
    {
		if(NetworkThread.getInstance().connect(address))
		{
			rightBarController = new RightBarController(inputManager, audioRenderer, guiViewPort);
			leftBarController = new LeftBarController(inputManager, audioRenderer, guiViewPort);
			inputManager.setCursorVisible(true);

			NetworkThread.getInstance().start();
			NetworkThread.getInstance().send(new ClientConnectMessage(playerName));
			return true;
		}

		return false;
    }

	/**
	 * Called when the application is stopped. Makes sure the network thread and nifty gui controllers are shut down properly
	 */
	@Override
	public void TTMOStop()
	{
		NetworkThread.getInstance().exit();

		if(rightBarController != null)
		{
			rightBarController.exit();
		}

		if(leftBarController != null)
		{
			leftBarController.exit();
		}
	}

	/**
	 * Sets the current type (for grid square placement) to the given type
	 * @param type The new type
	 */
	public void setCurrentType(String type)
	{
		actionListener.setCurrentType(type);
	}

	/**
	 * Proxy method for updating the resources in the left bar
	 * @param message The ResourceUpdateMessage that has been received
	 */
	public void updateResources(ResourceUpdateMessage message)
	{
		leftBarController.updateResources(message);
	}

	/**
	 * Proxy method for updating the score in the left bar
	 * @param score The received score
	 */
	public void updateScore(double score)
	{
		leftBarController.updateScore(score);
	}

	/**
	 * @return The Main instance
	 */
	public static Main getInstance()
	{
		return instance;
	}

	/**
	 * Listener for placing new grid squares
	 */
	class TTMOActionListener implements ActionListener
	{
		String currentType = "";

		/**
		 * Called when an action is triggered (currently only clicks from grid square placements)
		 * Will calculate which grid square has been clicked and send the placement message to the server
		 */
		@Override
		public void onAction(String name, boolean isPressed, float tpf)
		{
			if(name.equals("place") && !isPressed && !currentType.isEmpty())
			{
				CollisionResults results = new CollisionResults();
				Vector2f click2d = inputManager.getCursorPosition();
				Vector3f click3d = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
				Vector3f dir = cam.getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d);
				Ray ray = new Ray(click3d, dir);
				rootNode.collideWith(ray, results);
				CollisionResult collision = results.getClosestCollision();
				if(collision != null)
				{
					Location location = new Location(collision.getGeometry().getParent().getParent().getLocalTranslation().divide(GuiConstants.offsetFactor));
					NetworkThread.getInstance().send(new PlacementMessage(location.getX(), location.getZ(), currentType));
				}
			}
		}

		/**
		 * Will set the current type that will be placed when the user clicks a grid square
		 * @param type The new type
		 */
		public void setCurrentType(String type)
		{
			currentType = type;
		}
	}
}
