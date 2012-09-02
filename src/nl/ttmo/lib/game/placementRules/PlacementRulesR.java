package nl.ttmo.lib.game.placementRules;

import nl.ttmo.lib.game.world.GameMap;
import nl.ttmo.lib.world.Location;

/**
 * Placement rules for road (R) type
 * @author Maarten Slenter
 */
public class PlacementRulesR implements PlacementRules
{
	/**
	 * Checks if the placement rules for the road (R) type are satisfied at the specified location
	 *
	 * Returns true if there is a city (C) type grid square within 5 grid squares of the specified location
	 * @param location The location to check
	 * @param map The game map instance
	 * @return The result of the check
	 */
	@Override
	public boolean check(Location location)
	{
		return(PlacementRulesUtilities.checkIfNear(location, "C", 5));
	}
}
