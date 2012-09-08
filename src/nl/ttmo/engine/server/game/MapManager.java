package nl.ttmo.engine.server.game;

import com.jme3.network.HostedConnection;

import java.util.ArrayList;

import nl.ttmo.engine.lib.ObjectFactory;
import nl.ttmo.engine.lib.game.mapObjects.MapObject;
import nl.ttmo.engine.lib.game.placementRules.PlacementRules;
import nl.ttmo.engine.lib.game.routing.ConnectionPoint;
import nl.ttmo.engine.lib.game.world.GameMap;
import nl.ttmo.engine.lib.game.world.MapGridSquare;
import nl.ttmo.engine.lib.game.world.gridSquares.GridSquare;
import nl.ttmo.engine.lib.world.Location;

import nl.ttmo.engine.messages.client.MapLoadMessage;
import nl.ttmo.engine.messages.client.ScoreUpdateMessage;
import nl.ttmo.engine.messages.util.MapGridSquareData;

import nl.ttmo.engine.server.game.resources.gridSquares.Resources;
import nl.ttmo.engine.server.game.resources.util.ResourceManager;
import nl.ttmo.engine.server.gui.ServerGuiObjectFactory;
import nl.ttmo.engine.server.threads.NetworkThread;

/**
 * The class responsible for managing the map
 * @author Maarten Slenter
 */
public class MapManager
{
	/**
	 * The MapManager singleton instance
	 */
	private static MapManager instance = new MapManager();

	/**
	 * Map object id counter. Incremented by 1 every time a map object is created
	 */
	private int mapObjectIdCounter = 1;

	/**
	 * The list of map objects
	 */
	private ArrayList<MapObject> mapObjects = new ArrayList<MapObject>();

	/**
	 * Updates a grid square on the map
	 * @param connection The connection which wants to place the grid square
	 * @param network The NetworkThread instance
	 * @param location The location where the grid square is to be placed
	 * @param type The type of the grid square
	 * @return True if the placement was successful, false otherwise
	 */
	public boolean updateGridSquare(HostedConnection connection, NetworkThread network, Location location, String type)
    {
		Resources resources = (Resources) ObjectFactory.createObject("content", "game.resources.gridSquares.Resources" + type);
        if(!GameMap.getInstance().getMapGridSquare(location).hasType(type) && ((PlacementRules) ObjectFactory.createObject("content", "game.placementRules.PlacementRules" + type)).check(location) && ResourceManager.getInstance().hasResources(connection, resources.getResourceCost()))
        {
			ResourceManager.getInstance().substractResources(connection, resources.getResourceCost());
			ResourceManager.getInstance().addResourceIncreases(connection, resources.getResourceProduction());
			GridSquare gridSquare = GameMap.getInstance().updateGridSquare(location, type, new ServerGuiObjectFactory());

            this.updateMapObjects(location, gridSquare);
			this.updateScore(network);

			return true;
        }

		return false;
    }

	/**
	 * Generates a map load message.
	 * This is used to give clients the initial map lay out
	 * @return The generated map load message
	 */
    public MapLoadMessage generateMapLoadMessage()
    {
        MapLoadMessage mapLoadMessage = new MapLoadMessage();

		mapLoadMessage.setMapSize(GameMap.getInstance().getMapSize());

        for(MapGridSquare mapGridSquare : GameMap.getInstance().getMapGridSquares())
        {
            if(!mapGridSquare.isEmpty())
            {
				Location location = mapGridSquare.getLocation();
                mapLoadMessage.addGridSquare(new MapGridSquareData(location.getX(), location.getZ(), mapGridSquare.getGridSquareTypes()));
            }
        }

        return mapLoadMessage;
    }

	/**
	 * @return The MapManager instance
	 */
	public static MapManager getInstance()
	{
		return instance;
	}

	/**
	 * Updates the map objects
	 * Called when a grid square has been placed
	 * @param location The location that the grid square has been placed
	 * @param gridSquareBase The grid square that has been placed
	 */
	private void updateMapObjects(Location location, GridSquare gridSquareBase)
    {
		/*
		 * Check for neighbouring map objects that have the same type.
		 * If one is found, add this grid square to it
		 * If multiple are found, also merge the map objects into one
		 */
		for(Location.Direction direction : Location.Direction.values())
		{
			Location face = location.getFace(direction);
			if(face.isValid())
			{
				MapGridSquare mapGridSquare = GameMap.getInstance().getMapGridSquare(face);
				if(mapGridSquare.hasType(gridSquareBase.gridSquareType))
				{
					MapObject mapObject = mapGridSquare.getGridSquare(gridSquareBase.gridSquareType).getMapObject();
					if(gridSquareBase.hasMapObject())
					{
						if(!mapObject.equals(gridSquareBase.getMapObject()))
						{
							gridSquareBase.getMapObject().mergeObject(mapObject);
							mapObjects.remove(mapObject);
						}
					}
					else
					{
						gridSquareBase.setMapObject(mapObject);
						mapObject.addGridSquare(gridSquareBase);
					}
				}
			}
		}

		/*
		 * If the grid square still doesn't have a map object (i.e. no neighbouring map objects have been found), create a new map object
		 */
		if(!gridSquareBase.hasMapObject())
		{
			MapObject mapObject = (MapObject) ObjectFactory.createObject("content", "game.mapObjects.MapObject" + gridSquareBase.gridSquareType);
			mapObject.initialize(this.mapObjectIdCounter++, gridSquareBase);
			gridSquareBase.setMapObject(mapObject);
			mapObjects.add(mapObject);
		}

		/*
		 * Update the related objects of all neighbouring map objects to include the one of this grid square
		 */
		for(Location.Direction direction : Location.Direction.values())
		{
			Location face = location.getFace(direction);
			if(face.isValid())
			{
				MapGridSquare mapGridSquare = GameMap.getInstance().getMapGridSquare(face);
				for(String typeOther: mapGridSquare.getGridSquareTypes())
				{
					if(!gridSquareBase.gridSquareType.equals(typeOther))
					{
						mapGridSquare.getGridSquare(typeOther).getMapObject().relatedObjects.addRelatedObject(gridSquareBase.getMapObject(), new ConnectionPoint(face, location));
						gridSquareBase.getMapObject().relatedObjects.addRelatedObject(mapGridSquare.getGridSquare(typeOther).getMapObject(), new ConnectionPoint(location, face));
					}
				}
			}
		}
	}

	/**
	 * Update the score
	 * @param network The NetworkThread instance
	 */
	private void updateScore(NetworkThread network)
	{
		double score = 0;

		for(MapObject mapObject : mapObjects)
		{
			if(mapObject.score != null)
			{
				score += mapObject.score.calculateValue();
			}
		}

		network.send(new ScoreUpdateMessage(score));
	}
}
