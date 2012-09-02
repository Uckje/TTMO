package nl.ttmo.engine.server.game.resources.gridSquares;

import nl.ttmo.engine.server.game.resources.util.ResourceAmountSet;

/**
 * Interface for the resource cost classes
 * @author Maarten Slenter
 */
public interface Resources
{
	/**
	 * Returns the cost of placing a grid square of this type
	 * @return The resource amount set which represents the cost of placing a grid square of this type
	 */
	public ResourceAmountSet getResourceCost();

	/**
	 * Returns the production of a grid square of this type
	 * @return The resource amount set which represents the production of a grid square of this type
	 */
	public ResourceAmountSet getResourceProduction();
}
