package nl.ttmo.messages.client;

import com.jme3.network.serializing.Serializable;

import java.util.ArrayList;
import java.util.Collection;

import nl.ttmo.messages.TTMOMessage;
import nl.ttmo.messages.util.MapGridSquareData;


/**
 * Server -> Client
 *
 * @author Maarten Slenter
 */
@Serializable
public class MapLoadMessage extends TTMOMessage
{
	private int[] mapSize = {0, 0};
    private ArrayList<MapGridSquareData> gridSquareDataList = new ArrayList<MapGridSquareData>();

	/**
	 * No argument constructor, for serialization only
	 */
	public MapLoadMessage()
    {}

	/**
	 * @param mapSizeX				The map size in X direction
	 * @param mapSizeZ				The map size in Z direction
	 * @param gridSquareDataList	A list with data about the current grid squares on the map
	 */
	public MapLoadMessage(int mapSizeX, int mapSizeZ, Collection<MapGridSquareData> gridSquareDataList)
    {
		this.mapSize[0] = mapSizeX;
		this.mapSize[1] = mapSizeZ;
		this.gridSquareDataList.addAll(gridSquareDataList);
    }

	/**
	 *
	 * @param mapSize The array with the map sizes (0 => x, 1 => z)
	 */
	public void setMapSize(int[] mapSize)
	{
		if(mapSize.length == 2)
		{
			this.mapSize = mapSize;
		}
	}

	/**
	 *
	 * @param mapSizeX The map size in x direction
	 */
	public void setMapSizeX(int mapSizeX)
	{
		this.mapSize[0] = mapSizeX;
	}

	/**
	 *
	 * @param mapSizeZ The map size in z direction
	 */
	public void setMapSizeZ(int mapSizeZ)
	{
		this.mapSize[1] = mapSizeZ;
	}

	/**
	 *
	 * @param gridSquareData The MapGridSquareData instance to add
	 */
	public void addGridSquare(MapGridSquareData gridSquareData)
    {
        this.gridSquareDataList.add(gridSquareData);
    }

	/**
	 *
	 * @return The map size in x direction
	 */
	public int getMapSizeX()
	{
		return mapSize[0];
	}

	/**
	 *
	 * @return The map size in z direction
	 */
	public int getMapSizeZ()
	{
		return mapSize[1];
	}

	/**
	 *
	 * @return The array with the map sizes (0 => x, 1 => z)
	 */
	public int[] getMapSize()
	{
		return mapSize;
	}

	/**
	 *
	 * @return The list with the map data
	 */
	public Collection<MapGridSquareData> getGridSquareDataList()
    {
        return this.gridSquareDataList;
    }
}
