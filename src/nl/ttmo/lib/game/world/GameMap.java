package nl.ttmo.lib.game.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import nl.ttmo.lib.ObjectFactory;
import nl.ttmo.lib.game.world.gridSquares.GridSquare;
import nl.ttmo.lib.gui.Displayable;
import nl.ttmo.lib.gui.GuiGameMapObject;
import nl.ttmo.lib.gui.GuiGridSquareObject;
import nl.ttmo.lib.gui.GuiObject;
import nl.ttmo.lib.gui.GuiObjectFactory;
import nl.ttmo.lib.world.Location;

/**
 * Represents the whole game map
 * @author Maarten Slenter
 */
public class GameMap implements Displayable
{
	/**
	 * The GameMap singleton instance
	 */
	private static GameMap instance = new GameMap();

	/**
	 * A hash map linking locations to their corresponding map grid squares
	 */
    private HashMap<Location, MapGridSquare> map = new HashMap<Location, MapGridSquare>();

	/**
	 * The gui object of the game map
	 */
	private GuiGameMapObject guiGameMap;

	/**
	 * The sizes of this map
	 * Index 0 => x, 1 => z
	 */
	private int[] mapSize = {0,0};

	private GameMap()
	{}

	/**
	 * Fills up all the locations with map grid square instances and makes sure Location knows the dimensions of the map
	 * @param rangeX The length of the map in X direction
	 * @param rangeZ The length of the map in Z direction
	 * @param guiObjectFactory The gui object factory instance
	 */
    public void initialize(int rangeX, int rangeZ, GuiObjectFactory guiObjectFactory)
    {
		mapSize[0] = rangeX;
		mapSize[1] = rangeZ;
		this.guiGameMap = (GuiGameMapObject) guiObjectFactory.create(GameMap.class);

        for(int x = 0; x <= rangeX - 1; x++)
        {
            for(int z = 0; z <= rangeZ - 1; z++)
            {
                Location location = new Location(x, z);
                map.put(location, new MapGridSquare(location, guiObjectFactory));
            }
        }

		Location.setMapSize(mapSize);
    }

    public Collection<MapGridSquare> getMapGridSquares()
    {
        return map.values();
    }

	/**
	 * Returns the map grid square at the specified location
	 * @param location The location to fetch the map grid square for
	 * @return The map grid square at the specified location
	 */
	public MapGridSquare getMapGridSquare(Location location)
	{
		return map.get(location);
	}

	@Override
	public GuiObject getGuiObject()
    {
		return guiGameMap;
    }

	/**
	 * Updates the grid square at the specified location to include the specified type
	 * Will also update the gui object, if there is one
	 * @param location The location to update
	 * @param type The type to add
	 * @param guiObjectFactory The gui object factory
	 * @return The added grid square instance
	 */
	public GridSquare updateGridSquare(Location location, String type, GuiObjectFactory guiObjectFactory)
    {
		MapGridSquare mapGridSquare = map.get(location);
		GridSquare gridSquare = (GridSquare) ObjectFactory.createObject("lib", "game.world.gridSquares.GridSquare"+type);
		gridSquare.initialize(location, (GuiGridSquareObject) guiObjectFactory.create(gridSquare.getClass()));
		mapGridSquare.addGridSquare(gridSquare);
		if(guiGameMap != null)
		{
			guiGameMap.updateMap();
		}
		return gridSquare;
	}

	/**
	 * Returns the map grid squares that are within radius of location
	 * @param location The location that serves as the center of the area
	 * @param radius The radius of the area
	 * @return The map grid squares in the area
	 */
	public ArrayList<MapGridSquare> getArea(Location location, int radius)
	{
		ArrayList<MapGridSquare> mapGridSquares = new ArrayList<MapGridSquare>();

		for(int x = location.getX() - radius + 1; x <= location.getX() + radius - 1; x++)
		{
			if(x < 0 || x > mapSize[0] - 1)
			{
				continue;
			}

			for(int z = location.getZ() - radius + 1; z <= location.getZ() + radius - 1; z++)
			{
				if(z < 0 || z > mapSize[1] - 1)
				{
					continue;
				}

				MapGridSquare mapGridSquare = this.map.get(new Location(x, z));
				if(!(mapGridSquare.isEmpty()))
				{
					mapGridSquares.add(mapGridSquare);
				}
			}
		}

		return mapGridSquares;
	}

	public int[] getMapSize()
	{
		return mapSize;
	}

	public static GameMap getInstance()
	{
		return instance;
	}
}
