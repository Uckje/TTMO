package nl.ttmo.lib.game.routing;

import nl.ttmo.lib.world.Location;

/**
 * A connection point between two map objects, consists of the locations of the two neighbouring gridsquares of each map object
 * @author Maarten Slenter
 */
public class ConnectionPoint
{
	/**
	 * The location of the first map object
	 * This is ALWAYS the Location object with the lowest hash code.
	 * This is done to ensure that hashCode always returns the same value when the same two locations are used
	 */
	Location location1;

	/**
	 * The location of the second map object
	 * This is ALWAYS the Location object with the highest hash code.
	 */
	Location location2;

	/**
	 * Instantiates this object and also makes sure the Location object with the lowest hash code is in location1
	 * @param location1 The location of the first map object
	 * @param location2 The location of the second map object
	 */
	public ConnectionPoint(Location location1, Location location2)
	{
		if(location1.nextTo(location2))
		{
			//Always put the smalles hashCode first
			if(location2.hashCode() < location1.hashCode())
			{
				this.location2 = location1;
				this.location1 = location2;
			}
			else
			{
				this.location1 = location1;
				this.location2 = location2;
			}
		}
	}

	/**
	 * Returns location1 if the supplied location matches location2 and visa versa.
	 * If it matches neither, null is returned
	 * @param location The location to check
	 * @return location1 if the supplied location matches location2 and visa versa, null if it matches neither
	 */
	public Location getOther(Location location)
	{
		if(this.location1.equals(location))
		{
			return location2;
		}
		else if(this.location2.equals(location))
		{
			return location1;
		}
		else
		{
			return null;
		}
	}

	/**
	 * @return The average X coordinate of the two locations
	 */
	public float getX()
	{
		return (location1.getX() + location2.getX())/2f;
	}

	/**
	 * @return The average Z coordinate of the two locations
	 */
	public float getZ()
	{
		return (location1.getZ() + location2.getZ())/2f;
	}

	/**
	 * Compares this object to the supplied object, to see if they're the same.
	 * This is required for using ConnectionPoint as a HashMap key
	 * @param obj The object to compare
	 * @return True if the two objects are equal, false otherwise
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

		final ConnectionPoint other = (ConnectionPoint) obj;
		if (this.location1 != other.location1 && (this.location1 == null || !this.location1.equals(other.location1)))
		{
			return false;
		}

		if (this.location2 != other.location2 && (this.location2 == null || !this.location2.equals(other.location2)))
		{
			return false;
		}

		return true;
	}

	/**
	 * Generates a hash code for this connection point
	 * This is required for using ConnectionPoint as a HashMap key
	 * @return The generated hash code
	 */
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 67 * hash + (this.location1 != null ? this.location1.hashCode() : 0);
		hash = 67 * hash + (this.location2 != null ? this.location2.hashCode() : 0);
		return hash;
	}

	/**
	 * Returns a string representation of this object, used to ease debugging
	 * @return The name of this class with the string version of the coordinates of the two location objects
	 */
	@Override
	public String toString()
	{
		return "Connection Point "+this.location1.coordsToString()+" and "+this.location2.coordsToString();
	}
}
