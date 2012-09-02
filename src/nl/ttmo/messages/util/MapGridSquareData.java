package nl.ttmo.messages.util;

import com.jme3.network.serializing.Serializable;

/**
 * Utility class to allow data about map grid squares to be sent easily
 * @author Maarten Slenter
 */
@Serializable
public class MapGridSquareData
{
	/**
	 * The X coordinate of this map grid square
	 */
    int x;

	/**
	 * The Z coordinate of this map grid square
	 */
	int z;

	/**
	 * The grid square types this map grid square contains
	 */
    String[] types;

	/**
	 * No argument constructor, for serialization only
	 */
	public MapGridSquareData()
    {}

	/**
	 *
	 * @param x The X coordinate of this map grid square
	 * @param z The Z coordinate of this map grid square
	 * @param types The grid square types this map grid square contains
	 */
	public MapGridSquareData(int x, int z, String[] types)
    {
        this.x = x;
        this.z = z;
        this.types = types;
    }

	/**
	 *
	 * @return The X coordinate of this map grid square
	 */
	public int getX()
    {
        return x;
    }

	/**
	 *
	 * @return The Z coordinate of this map grid square
	 */
	public int getZ()
    {
        return z;
    }

	/**
	 *
	 * @return The grid square types this map grid square contains
	 */
	public String[] getTypes()
    {
        return types;
    }
}
