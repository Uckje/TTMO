package nl.ttmo.server.game.resources.types;

/**
 * Base class for resource types
 * @author Maarten Slenter
 */
public abstract class Resource
{
	/**
	 * The name of the resource
	 */
	protected String name = "None";

	public String getName()
	{
		return name;
	}

	/**
	 * Compares this object to the one supplied
	 * Required for using this object as a key in HashMap
	 * @param obj The object to compare to
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

		final Resource other = (Resource) obj;
		if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
		{
			return false;
		}

		return true;
	}

	/**
	 * Generates a hash code for this object
	 * @return The generated hash code
	 */
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
		return hash;
	}


}