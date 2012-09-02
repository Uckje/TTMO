package nl.ttmo.client.gui.world.gridSquares;

import nl.ttmo.lib.world.Location;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

import nl.ttmo.client.gui.util.AssetManagerSingleton;
import nl.ttmo.client.gui.world.util.GuiGameMapUtilities;

import nl.ttmo.lib.gui.GuiGridSquareObject;

/**
 * Gui class for a road type grid square
 * @author Maarten Slenter
 */
public class GuiGridSquareR extends GuiGridSquare implements GuiGridSquareObject
{
	/**
	 * No argument constructor, used in ClientGuiObjectFactory
	 */
	public GuiGridSquareR()
	{}

    public GuiGridSquareR(Location location)
    {
		super(location);
    }

	/**
	 * Generates the Spatial that will represent this road grid square.
	 * It will consist of a brown box in the middle of the gridsquare with brown extensions to the sides of the grid square, if there is another road grid square in that direction
	 * @return The generated Spatial
	 */
    @Override
    public Spatial generateSpatial()
    {
		Node node = new Node("gridSquare_" + location.getX() + "_" + location.getZ());

		if(GuiGameMapUtilities.hasOnlyTypeAt(location, "R"))
		{
			Box box = new Box(Vector3f.ZERO, 0.5f, 0, 0.5f);
			Spatial spatial = new Geometry("gridSquare_" + location.getX() + "_" + location.getZ(), box);
			Material material = new Material(AssetManagerSingleton.get(), "Common/MatDefs/Misc/Unshaded.j3md");
			material.setColor("Color", ColorRGBA.Green);
			spatial.setMaterial(material);
			node.attachChild(spatial);
		}

		if(GuiGameMapUtilities.hasTypeInDirection(location, Location.Direction.NORTH, "R"))
		{
			Box box = new Box(Vector3f.ZERO, 0.2f, 0f, 0.1f);
			Spatial spatial = new Geometry("gridSquare_" + location.getX() + "_" + location.getZ(), box);
			Material material = new Material(AssetManagerSingleton.get(), "Common/MatDefs/Misc/Unshaded.j3md");
			material.setColor("Color", ColorRGBA.Brown);
			spatial.setMaterial(material);
			spatial.setLocalTranslation(0.3f, 0.001f, 0);
			node.attachChild(spatial);
		}

		if(GuiGameMapUtilities.hasTypeInDirection(location, Location.Direction.EAST, "R"))
		{
			Box box = new Box(Vector3f.ZERO, 0.1f, 0f, 0.2f);
			Spatial spatial = new Geometry("gridSquare_" + location.getX() + "_" + location.getZ(), box);
			Material material = new Material(AssetManagerSingleton.get(), "Common/MatDefs/Misc/Unshaded.j3md");
			material.setColor("Color", ColorRGBA.Brown);
			spatial.setMaterial(material);
			spatial.setLocalTranslation(0, 0.001f, 0.3f);
			node.attachChild(spatial);
		}

		if(GuiGameMapUtilities.hasTypeInDirection(location, Location.Direction.SOUTH, "R"))
		{
			Box box = new Box(Vector3f.ZERO, 0.2f, 0f, 0.1f);
			Spatial spatial = new Geometry("gridSquare_" + location.getX() + "_" + location.getZ(), box);
			Material material = new Material(AssetManagerSingleton.get(), "Common/MatDefs/Misc/Unshaded.j3md");
			material.setColor("Color", ColorRGBA.Brown);
			spatial.setMaterial(material);
			spatial.setLocalTranslation(-0.3f, 0.001f, 0);
			node.attachChild(spatial);
		}

		if(GuiGameMapUtilities.hasTypeInDirection(location, Location.Direction.WEST, "R"))
		{
			Box box = new Box(Vector3f.ZERO, 0.1f, 0f, 0.2f);
			Spatial spatial = new Geometry("gridSquare_" + location.getX() + "_" + location.getZ(), box);
			Material material = new Material(AssetManagerSingleton.get(), "Common/MatDefs/Misc/Unshaded.j3md");
			material.setColor("Color", ColorRGBA.Brown);
			spatial.setMaterial(material);
			spatial.setLocalTranslation(0, 0.001f, -0.3f);
			node.attachChild(spatial);
		}

		Box box = new Box(Vector3f.ZERO, 0.1f, 0f, 0.1f);
        Spatial spatial = new Geometry("gridSquare_" + location.getX() + "_" + location.getZ(), box);
        Material material = new Material(AssetManagerSingleton.get(), "Common/MatDefs/Misc/Unshaded.j3md");
		material.setColor("Color", ColorRGBA.Brown);
        spatial.setMaterial(material);
		spatial.setLocalTranslation(0, 0.001f, 0);
		node.attachChild(spatial);

        return node;
    }
}
