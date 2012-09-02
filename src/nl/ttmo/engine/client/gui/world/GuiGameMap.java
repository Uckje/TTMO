package nl.ttmo.engine.client.gui.world;

import com.jme3.app.Application;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Line;

import nl.ttmo.engine.lib.game.world.GameMap;
import nl.ttmo.engine.lib.world.Location;
import nl.ttmo.engine.lib.game.world.MapGridSquare;

import java.util.concurrent.Callable;
import nl.ttmo.engine.client.gui.util.AssetManagerSingleton;
import nl.ttmo.engine.client.gui.GuiConstants;
import nl.ttmo.engine.client.gui.world.util.GuiGameMapUtilities;
import nl.ttmo.engine.client.threads.Main;
import nl.ttmo.engine.lib.gui.GuiGameMapObject;
import nl.ttmo.engine.lib.gui.GuiMapGridSquareObject;

/**
 * Gui object for the game map
 * @author Maarten Slenter
 */
public class GuiGameMap implements GuiGameMapObject
{
	/**
	 * The Node to which the map is attached
	 */
    final private Node mapNode = new Node("map");

	/**
	 * Generate the spatial that will represent this game map.
	 * Generates the blue lines between every 5th and 6th grid square and attaches the gui objects of all the grid squares to mapNode
	 * @return The generated Spatial
	 */
    public Spatial generateSpatial()
    {
		mapNode.attachChild(generateGridSquareNode());

		Vector3f start;
		Vector3f end;
		Line line;
		Spatial spatial;
		Material material = new Material(AssetManagerSingleton.get(), "Common/MatDefs/Misc/Unshaded.j3md");;
		material.setColor("Color", ColorRGBA.Blue);

		Box box = new Box(Vector3f.ZERO, 0.25f, 0, 0.25f);
        spatial = new Geometry("originBox", box);
        spatial.setMaterial(material);
		spatial.setLocalTranslation(0, 0.001f, 0);
		mapNode.attachChild(spatial);

		int[] mapSize = GameMap.getInstance().getMapSize();

		for(int i = 0; i <= mapSize[0]; i += 5)
		{
			start = new Vector3f(i * GuiConstants.offsetFactor + GuiConstants.lineOffset, 0.001f, 0);
			end = new Vector3f(i * GuiConstants.offsetFactor + GuiConstants.lineOffset, 0.001f, mapSize[1] * GuiConstants.offsetFactor + GuiConstants.lineOffset);
			line = new Line(start, end);
			line.setLineWidth(3);
			spatial = new Geometry("mapLine_x_"+i, line);
			spatial.setMaterial(material);
			mapNode.attachChild(spatial);
		}

		for(int i = 0; i <= mapSize[1]; i += 5)
		{
			start = new Vector3f(0, 0.001f, i * GuiConstants.offsetFactor + GuiConstants.lineOffset);
			end = new Vector3f(mapSize[0] * GuiConstants.offsetFactor + GuiConstants.lineOffset, 0.001f, i * GuiConstants.offsetFactor + GuiConstants.lineOffset);
			line = new Line(start, end);
			line.setLineWidth(3);
			spatial = new Geometry("mapLine_z_"+i, line);
			spatial.setMaterial(material);
			mapNode.attachChild(spatial);
		}

		return mapNode;
    }

	/**
	 * Updates the complete map via a Callable that is queued in the application thread (to make sure the update is done in between rendering)
	 */
    public void updateMap()
    {
        Main.getInstance().enqueue(new Callable<Void>() {
            @Override
            public Void call()
            {
				mapNode.detachChildNamed("gridSquareNode");
				mapNode.attachChild(generateGridSquareNode());
				return null;
            }
        });
    }

	/**
	 * @return The mapNode
	 */
    public Spatial getNode()
    {
		return mapNode;
    }

	/**
	 * Generates the node that has all the grid squares attached to it.
	 * @return The generated Spatial
	 */
	private Spatial generateGridSquareNode()
	{
		Node node = new Node("gridSquareNode");

		for(MapGridSquare mapGridSquare : GameMap.getInstance().getMapGridSquares())
		{
			GuiMapGridSquareObject guiMapGridSquare = mapGridSquare.getGuiObject();
			guiMapGridSquare.initialize(mapGridSquare.getLocation());
			node.attachChild(guiMapGridSquare.generateSpatial());
		}

		return node;
	}
}