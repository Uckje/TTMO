package nl.ttmo.engine.lib.game.placementRules;

import nl.ttmo.engine.lib.world.Location;

/**
 * Interface for placement rules
 * @author Maarten Slenter
 */
public interface PlacementRules
{
	/**
	 * Checks if the placement for this type is possible at location
	 * @param location The location to check
	 * @param gameMap The game map instance
	 * @return True if the placement is possible, false otherwise
	 */
	public boolean check(Location location);
}
