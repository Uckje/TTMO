package nl.ttmo.engine.lib.world;

import com.jme3.math.Vector3f;

/**
 * Represents a location on the map
 * @author Maarten Slenter
 */
public class Location
{
	/**
	 * The 4 possible directions
	 */
    public enum Direction
    {
		NORTH, EAST, SOUTH, WEST
    }

	/**
	 * The x coordinate of this location
	 */
    private int x;

	/**
	 * The z coordinate of this location
	 */
    private int z;

	/**
	 * The dimensions of the map
	 */
	private static int[] mapSize = {0,0};

    public Location(int x, int z)
    {
        this.x = x;
		this.z = z;
    }

    public Location(Vector3f vector)
    {
		this.x = (int) vector.getX();
		this.z = (int) vector.getZ();
    }

    public int getX()
    {
		return x;
    }

    public int getZ()
    {
		return z;
    }

	/**
	 * Returns the location that lies in the specified direction from this location
	 * @param direction The direction
	 * @return The location that lies in the specified direction
	 */
    public Location getFace(Direction direction)
    {
		switch(direction)
		{
			case NORTH:
				return new Location(this.x + 1, this.z);
			case EAST:
				return new Location(this.x, this.z + 1);
			case SOUTH:
				return new Location(this.x - 1, this.z);
			case WEST:
				return new Location(this.x, this.z - 1);
			default:
				return this;
		}
    }

	/**
	 * Checks if this location is next to the supplied location
	 * @param otherLocation The location to compare with
	 * @return True if this location is next to the supplied location, false otherwise
	 */
	public boolean nextTo(Location otherLocation)
	{
		if(this.x == otherLocation.x + 1 || this.x == otherLocation.x - 1 || this.z == otherLocation.z + 1 || this.z == otherLocation.z - 1)
		{
			return true;
		}

		return false;
	}

	/**
	 * Checks if this is a valid location
	 * @return True if this location lies within the bounds of the map, false otherwise
	 */
	public boolean isValid()
	{
		if(this.x < 0)
		{
			return false;
		}

		if(this.z < 0)
		{
			return false;
		}

		if(this.x > mapSize[0] - 1)
		{
			return false;
		}

		if(this.z > mapSize[1] - 1)
		{
			return false;
		}

		return true;
	}

	/**
	 * Sets the map dimensions
	 * @param mapSize The map dimensions
	 */
	public static void setMapSize(int[] mapSize)
	{
		Location.mapSize = mapSize;
	}

	/**
	 * Checks to see if this location object is equal to the object supplied
	 * @param obj The object to compare against
	 * @return True if the two objects are the same, false otherwise
	 */
    @Override
    public boolean equals(Object obj)
    {
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Location other = (Location) obj;
		if (this.x != other.x)
		{
			return false;
		}
		if (this.z != other.z)
		{
			return false;
		}
		return true;
    }

	/**
	 * Generates a hash code for this location object
	 * @return The generated hash code
	 */
    @Override
    public int hashCode()
    {
		int hash = 7;
		hash = 43 * hash + this.x;
		hash = 43 * hash + this.z;
		return hash;
    }

	/**
	 * Generates a string representation of this location object's coordinates
	 * @return The string representation of this location object's coordinates
	 */
	public String coordsToString()
	{
		return "("+this.x+","+this.z+")";
	}

	/**
	 * Generates a string representation of this object, used to ease
	 * @return The name of this class with the result of coordsToString attached to the end
	 */
	@Override
	public String toString()
	{
		return "Location "+this.coordsToString();
	}
}
