package nl.ttmo.content.gui.gridSquares;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

import nl.ttmo.engine.client.gui.util.AssetManagerSingleton;
import nl.ttmo.engine.client.gui.world.gridSquares.GuiGridSquare;
import nl.ttmo.engine.client.gui.world.util.GuiGameMapUtilities;

import nl.ttmo.engine.lib.gui.GuiGridSquareObject;
import nl.ttmo.engine.lib.world.Location;

/**
 * Gui class for a city type grid square
 * @author Maarten Slenter
 */
public class GuiGridSquareC extends GuiGridSquare implements GuiGridSquareObject
{
	/**
	 * No argument constructor, used in ClientGuiObjectFactory
	 */
	public GuiGridSquareC()
	{}

    public GuiGridSquareC(Location location)
    {
		super(location);
    }

	/**
	 * Generates the Spatial that will represent this city grid square.
	 * It will consist of a grey plane with 4 black boxes on it, which will grow according to how many other city squares are in the neighbourhood
	 * @return The generated Spatial
	 */
    @Override
    public Spatial generateSpatial()
    {
		float houseOffset = 0.25f;
		float houseHeight = (float) (0.2f * Math.floor(Math.max(9, Math.min(25, GuiGameMapUtilities.countTypeInArea(location, 3, "C"))) / 9f));
		float houseLengthWidth = 0.1f;

		Node mainNode = new Node("gridSquare_" + location.getX() + "_" + location.getZ());

		Box box = new Box(Vector3f.ZERO, 0.5f, 0, 0.5f);
        Spatial spatial = new Geometry("gridSquare_" + location.getX() + "_" + location.getZ() + "_ground", box);
        Material material = new Material(AssetManagerSingleton.get(), "Common/MatDefs/Misc/Unshaded.j3md");
		material.setColor("Color", ColorRGBA.Gray);
        spatial.setMaterial(material);
		mainNode.attachChild(spatial);

		Box houseBox1 = new Box(new Vector3f(-houseOffset, 0, -houseOffset), houseLengthWidth, houseHeight, houseLengthWidth);
		Spatial houseSpatial1 = new Geometry("gridSquare_" + location.getX() + "_" + location.getZ() + "_house1", houseBox1);
		Material houseMaterial1 = new Material(AssetManagerSingleton.get(), "Common/MatDefs/Misc/Unshaded.j3md");
		houseMaterial1.setColor("Color", ColorRGBA.Black);
		houseSpatial1.setMaterial(houseMaterial1);
		mainNode.attachChild(houseSpatial1);

		Box houseBox2 = new Box(new Vector3f(-houseOffset, 0, houseOffset), houseLengthWidth, houseHeight, houseLengthWidth);
		Spatial houseSpatial2 = new Geometry("gridSquare_" + location.getX() + "_" + location.getZ() + "_house2", houseBox2);
		Material houseMaterial2 = new Material(AssetManagerSingleton.get(), "Common/MatDefs/Misc/Unshaded.j3md");
		houseMaterial2.setColor("Color", ColorRGBA.Black);
		houseSpatial2.setMaterial(houseMaterial2);
		mainNode.attachChild(houseSpatial2);

		Box houseBox3 = new Box(new Vector3f(houseOffset, 0, -houseOffset), houseLengthWidth, houseHeight, houseLengthWidth);
		Spatial houseSpatial3 = new Geometry("gridSquare_" + location.getX() + "_" + location.getZ() + "_house3", houseBox3);
		Material houseMaterial3 = new Material(AssetManagerSingleton.get(), "Common/MatDefs/Misc/Unshaded.j3md");
		houseMaterial3.setColor("Color", ColorRGBA.Black);
		houseSpatial3.setMaterial(houseMaterial3);
		mainNode.attachChild(houseSpatial3);

		Box houseBox4 = new Box(new Vector3f(houseOffset, 0, houseOffset), houseLengthWidth, houseHeight, houseLengthWidth);
		Spatial houseSpatial4 = new Geometry("gridSquare_" + location.getX() + "_" + location.getZ() + "_house4", houseBox4);
		Material houseMaterial4 = new Material(AssetManagerSingleton.get(), "Common/MatDefs/Misc/Unshaded.j3md");
		houseMaterial4.setColor("Color", ColorRGBA.Black);
		houseSpatial4.setMaterial(houseMaterial4);
		mainNode.attachChild(houseSpatial4);

        return mainNode;
    }
}
