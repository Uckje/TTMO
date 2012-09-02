package nl.ttmo.content.game.resources.gridSquares;

import nl.ttmo.engine.server.game.resources.gridSquares.Resources;
import nl.ttmo.content.game.resources.types.ResourceMoney;
import nl.ttmo.engine.server.game.resources.util.ResourceAmountSet;

/**
 * All resource details related to a road (R) type grid square
 * @author Maarten Slenter
 */
public class ResourcesR implements Resources
{
	/**
	 * The cost for placing a road (R) type grid square:
	 *	Money	100
	 * @return The resource amount set containing the cost of a city (C) type grid square
	 */
	@Override
	public ResourceAmountSet getResourceCost()
	{
		ResourceAmountSet resourceAmounts = new ResourceAmountSet();
		resourceAmounts.addResource(new ResourceMoney(), 100);
		return resourceAmounts;
	}

	/**
	 * The production of a road (R) type grid square:
	 *	Nothing
	 * @return The resource amount set containing the production of a road (R) type grid square
	 */
	@Override
	public ResourceAmountSet getResourceProduction()
	{
		ResourceAmountSet resourceAmounts = new ResourceAmountSet();
		return resourceAmounts;
	}
}
