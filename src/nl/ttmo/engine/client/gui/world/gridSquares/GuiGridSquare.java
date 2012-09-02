package nl.ttmo.engine.client.gui.world.gridSquares;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

import nl.ttmo.engine.client.gui.util.AssetManagerSingleton;

import nl.ttmo.engine.lib.gui.GuiGridSquareObject;
import nl.ttmo.engine.lib.world.Location;

/**
 * Gui class for a basic (empty) grid square
 * @author Maarten Slenter
 */
public class GuiGridSquare implements GuiGridSquareObject
{
	/**
	 * The location of this grid square
	 */
    protected Location location;

	/**
	 * No argument constructor, used in ClientGuiObjectFactory
	 */
	public GuiGridSquare()
	{}

    public GuiGridSquare(Location location)
    {
		initialize(location);
    }

	/**
	 * Initializes this GuiGridSquare, used if this was instantiated through ClientGuiObjectFactory
	 * @param location The Location this grid square is at.
	 */
	public void initialize(Location location)
	{
		this.location = location;
	}

	/**
	 * Generates the Spatial that will represent this grid square
	 * @return The generated Spatial
	 */
    public Spatial generateSpatial()
    {
		Node node = new Node("gridSquare_" + location.getX() + "_" + location.getZ());

        Box box = new Box(Vector3f.ZERO, 0.5f, 0, 0.5f);
        Spatial spatial = new Geometry("gridSquare_" + location.getX() + "_" + location.getZ(), box);
        Material material = new Material(AssetManagerSingleton.get(), "Common/MatDefs/Misc/Unshaded.j3md");
		material.setColor("Color", ColorRGBA.Green);
        spatial.setMaterial(material);

		node.attachChild(spatial);

        return node;
    }
}