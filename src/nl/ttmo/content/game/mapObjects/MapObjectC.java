package nl.ttmo.content.game.mapObjects;

import java.util.ArrayList;
import nl.ttmo.engine.lib.game.mapObjects.MapObject;

import nl.ttmo.engine.lib.game.world.gridSquares.GridSquare;

/**
 * Represents one city (C) type map object
 * @author Maarten Slenter
 */
public class MapObjectC extends MapObject
{
	public MapObjectC()
	{}

	public MapObjectC(int id)
	{
		this.initialize(id);
	}

	public MapObjectC(int id, GridSquare gridSquare)
	{
		this.initialize(id, gridSquare);
	}

	/**
	 * Sets this objects type to C and makes sure the sub classes are overridden
	 */
	private void initialize()
	{
		super.objectType = "C";
		this.overrideSubClasses();
	}

	/**
	 * Sets this objects type to C and makes sure the sub classes are overridden
	 */
	@Override
	public void initialize(int id)
	{
		super.initialize(id);
		this.initialize();
	}

	/**
	 * Sets this objects type to C and makes sure the sub classes are overridden
	 */
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
	protected final void overrideSubClasses()
	{
		super.score = new ScoreC();
	}

	/**
	 * Score subclass for the city (C) type map object
	 */
	public class ScoreC extends Score
	{
		/**
		 * Calculates the bonus value of the given related object
		 *
		 * For each road (R) type related object, the following formula is applied:
		 *
		 *	bonusValue = baseFactor * cityFactor * (rawThisCityValue + rawOtherCityValue)
		 *  if(bonusValue > 1)
		 *		value += baseFactor * cityFactor * (rawThisCityValue + otherCityValue)
		 *  else
		 *		value += bonusValue
		 *
		 * @param otherObject The related object to calculate the bonus value for
		 * @param baseFactor The base multiplication factor, by which the calculated score will be multiplied
		 * @return The calculated score
		 */
		@Override
		protected double getBonusValue(MapObject otherObject, double baseFactor)
		{
			double value = 0;

			if(otherObject instanceof MapObjectR)
			{
				ArrayList<MapObject> cityObjects = otherObject.relatedObjects.getRelatedObjects("C");
				for(MapObject cityObject: cityObjects)
				{
					if(cityObject.equals(MapObjectC.this))
					{
						continue;
					}

					double distance = otherObject.routing.getShortestDistance(MapObjectC.this, cityObject);
					double thisCityValue = this.calculateRawValue();
					double otherCityValue = cityObject.score.calculateRawValue();
					double bonusFactor = baseFactor * this.cityFactor(distance, thisCityValue, otherCityValue);
					double bonusValue = bonusFactor * (thisCityValue + otherCityValue);

					if(bonusValue > 1)
					{
						value += cityObject.score.calculateValue(bonusFactor) + bonusFactor * thisCityValue;
					}
					else
					{
						value += bonusValue;
					}
				}
			}

			return value;
		}

		/**
		 * Calculates the city factor, which is an indication of how well the two cities aid each other.
		 * This factor is highest at the point where one of the two cities is just a little bigger than the other.
		 *
		 * It's calculated in the following way:
		 *	factor = 0.25 * e ^ ( - (otherCityValue - thisCityValue - 300)/250)^2 - (distance/20)^2)
		 * @param distance The distance between the two city objects
		 * @param thisCityValue The raw value of this city
		 * @param otherCityValue The raw value of the other city
		 * @return The multiplication factor
		 */
		private double cityFactor(double distance, double thisCityValue, double otherCityValue)
		{
			return 0.25 * Math.exp(	- Math.pow(((otherCityValue - thisCityValue - 300)/250), 2)
									- Math.pow((distance/20), 2)
								);
		}
	}
}
