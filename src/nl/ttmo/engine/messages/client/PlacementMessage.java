package nl.ttmo.engine.messages.client;

import com.jme3.network.serializing.Serializable;

import nl.ttmo.engine.messages.TTMOMessage;

/**
 * Server <-> Client
 * Indicates a tile has been placed.
 * If from client -> server, it means a client wants to place a tile at the specified locatoin
 * If from server -> client, it means a tile has been placed at the specified location
 * @author Maarten Slenter
 */
@Serializable()
public class PlacementMessage extends TTMOMessage
{
	/**
	 * The X coordinate of this placement
	 */
    int x;

	/**
	 * The Z coordinate of this placement
	 */
	int z;

	/**
	 * The tile to be/that has been placed
	 */
    String type;

	/**
	 * No argument constructor, for serialization only
	 */
	public PlacementMessage()
    {}

	/**
	 *
	 * @param x The X coordinate of this placement
	 * @param z The Z coordinate of this placement
	 * @param type The type to be/that has been placed
	 */
	public PlacementMessage(int x, int z, String type)
    {
        this.x = x;
        this.z = z;
        this.type = type;
    }

	/**
	 *
	 * @return The X coordinate of this placement
	 */
	public int getX()
    {
        return x;
    }

	/**
	 *
	 * @return The Z coordinate of this placement
	 */
	public int getZ()
    {
        return z;
    }

	/**
	 *
	 * @return The type to be/that has been placed
	 */
	public String getType()
    {
        return type;
    }
}
