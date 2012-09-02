package nl.ttmo.client.gui.world;

import com.jme3.scene.Spatial;
import com.jme3.scene.Node;

import java.util.ArrayList;

import nl.ttmo.client.gui.GuiConstants;
import nl.ttmo.client.gui.world.gridSquares.GuiGridSquare;
import nl.ttmo.lib.gui.GuiMapGridSquareObject;
import nl.ttmo.lib.gui.GuiGridSquareObject;
import nl.ttmo.lib.world.Location;

/**
 * Gui class for a map grid square
 * @author Maarten Slenter
 */
public class GuiMapGridSquare implements GuiMapGridSquareObject
{
	/**
	 * The location of this map grid square
	 */
	Location location;

	/**
	 * The gui objects of all the grid squares this map grid square contains
	 */
	ArrayList<GuiGridSquareObject> guiGridSquares = new ArrayList<GuiGridSquareObject>();

	/**
	 * No argument constructor, used in ClientGuiObjectFactory
	 */
	public GuiMapGridSquare()
	{}

	public GuiMapGridSquare(Location location)
	{
		initialize(location);
	}

	/**
	 * Initializes this GuiMapGridSquare, used if this was instantiated through ClientGuiObjectFactory
	 * @param location The Location this map grid square is at.
	 */
	public void initialize(Location location)
	{
		this.location = location;
	}

	/**
	 * Adds a GuiGridSquare to this GuiMapGridSquare
	 * @param guiGridSquare The GuiGridSquare to add
	 */
	public void addGuiGridSquare(GuiGridSquareObject guiGridSquare)
	{
		guiGridSquares.add(guiGridSquare);
		guiGridSquare.initialize(location);
	}

	/**
	 * Generates the Spatial that will represent this map grid square
	 * @return The generated Spatial
	 */
    public Spatial generateSpatial()
    {
        Node node = new Node();

		int i = 0;
		if(!guiGridSquares.isEmpty())
		{
			for(GuiGridSquareObject guiGridSquare : guiGridSquares)
			{
				Spatial spatial = guiGridSquare.generateSpatial();
				node.attachChild(spatial);
			}
		}
		else
		{
			GuiGridSquare guiGridSquare = new GuiGridSquare(location);
			Spatial spatial = guiGridSquare.generateSpatial();
			node.attachChild(spatial);
			i++;
		}

		node.setLocalTranslation(location.getX() * GuiConstants.offsetFactor + 0.5f, 0, location.getZ() * GuiConstants.offsetFactor + 0.5f);

        return node;
    }
}
