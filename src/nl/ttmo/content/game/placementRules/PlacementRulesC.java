package nl.ttmo.content.game.placementRules;

import nl.ttmo.engine.lib.game.placementRules.PlacementRules;
import nl.ttmo.engine.lib.game.world.GameMap;
import nl.ttmo.engine.lib.world.Location;

/**
 * Placement rules for city (C) type.
 * @author Maarten Slenter
 */
public class PlacementRulesC implements PlacementRules
{
	/**
	 * Checks if the placement rules for the city (C) type are satisfied at the specified location
	 *
	 * Always returns true, there are no restrictions
	 * @param location The location to check
	 * @param map The game map instance
	 * @return Always true, there are no restrictions
	 */
	@Override
	public boolean check(Location location)
	{
		return true;
	}
}
