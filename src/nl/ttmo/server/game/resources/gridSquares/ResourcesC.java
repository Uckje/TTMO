package nl.ttmo.server.game.resources.gridSquares;

import nl.ttmo.server.game.resources.types.ResourceMoney;
import nl.ttmo.server.game.resources.util.ResourceAmountSet;

/**
 * All resource details related to a city (C) type grid square
 * @author Maarten Slenter
 */
public class ResourcesC implements Resources
{
	/**
	 * The cost for placing a city (C) type grid square:
	 *	Money	50
	 * @return The resource amount set containing the cost of a city (C) type grid square
	 */
	@Override
	public ResourceAmountSet getResourceCost()
	{
		ResourceAmountSet resourceAmounts = new ResourceAmountSet();
		resourceAmounts.addResource(new ResourceMoney(), 50);
		return resourceAmounts;
	}

	/**
	 * The production of a city (C) type grid square:
	 *	Money	1
	 * @return The resource amount set containing the production of a city (C) type grid square
	 */
	@Override
	public ResourceAmountSet getResourceProduction()
	{
		ResourceAmountSet resourceAmounts = new ResourceAmountSet();
		resourceAmounts.addResource(new ResourceMoney(), 1);
		return resourceAmounts;
	}
}
