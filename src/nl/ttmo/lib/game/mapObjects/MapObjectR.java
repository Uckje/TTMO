package nl.ttmo.lib.game.mapObjects;

import nl.ttmo.lib.game.world.gridSquares.GridSquare;

/**
 * Represents one road (R) type map object
 * @author Maarten Slenter
 */
public class MapObjectR extends MapObject
{
	public MapObjectR()
	{}

	public MapObjectR(int id)
	{
		this.initialize(id);
	}

	public MapObjectR(int id, GridSquare gridSquare)
	{
		this.initialize(id, gridSquare);
	}

	/**
	 * Sets this objects type to R and makes sure the sub classes are overridden
	 */
	private void initialize()
	{
		super.objectType = "R";
		this.overrideSubClasses();
	}

	@Override
	public void initialize(int id)
	{
		super.initialize(id);
		this.initialize();
	}

	@Override
	public void initialize(int id, GridSquare gridSquare)
	{
		super.initialize(id, gridSquare);
		this.initialize();
	}

	/**
	 * Overrides the sub classes of this map object.
	 * For further explanation on why this is required, see MapObject.overrideSubClasses
	 */
	@Override
	protected void overrideSubClasses()
	{}
}
