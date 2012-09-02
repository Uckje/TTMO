package nl.ttmo.lib.game.mapObjects;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import nl.ttmo.lib.game.routing.ConnectionPoint;
import nl.ttmo.lib.game.routing.Route;
import nl.ttmo.lib.game.routing.RouteSet;
import nl.ttmo.lib.game.world.gridSquares.GridSquare;
import nl.ttmo.lib.hashMap.DoubleKey;
import nl.ttmo.lib.hashMap.MultiValueHashMap;

/**
 * Base class for map objects
 * A map object consists of a set of neighbouring grid squares that have the same type on them and is used to implement almost all of the game logic
 * @author Maarten Slenter
 */
public abstract class MapObject
{
	/**
	 * The id for this map object
	 */
    protected int objectId = 0;

	/**
	 * The type of this map object
	 */
    protected String objectType = "Base";

	/**
	 * The grid squares that belong to this object
	 */
    protected ArrayList<GridSquare> gridSquares = new ArrayList<GridSquare>();

	/**
	 * This map objects related objects object
	 */
    public RelatedObjects relatedObjects = new RelatedObjects();

	/**
	 * This map objects routing object
	 */
    public Routing routing = new Routing();

	/**
	 * This map objects score object
	 */
    public Score score = null;

	/**
	 * No argument contructor, for serialization or dynamic instantiation only
	 */
    public MapObject()
    {}

    public MapObject(int id)
    {
		this.initialize(id);
    }

    public MapObject(int id, GridSquare gridSquare)
    {
        this.initialize(id, gridSquare);
    }

	/**
	 * Initializes this map object if it has been instantiated with the no argument constructor
	 * @param id The id of this map object
	 */
    public void initialize(int id)
    {
        this.objectId = id;
    }

	/**
	 * Initializes this map object if it has been instantiated with the no argument constructor
	 * @param id The id of this map object
	 * @param gridSquare The gridquare this map object was instantiated for. Using this saves a manual call to addGridSquare
	 */
    public void initialize(int id, GridSquare gridSquare)
    {
        this.initialize(id);
        this.addGridSquare(gridSquare);
    }

	/**
	 * Overrides instances of subclasses (for example Routing or Score)
	 *
	 * This is required because of the way java handles object instances and it's member variables
	 * Example:
	 *	MapObjectR mapObjectR = ...;
	 *	MapObject mapObject = mapObjectR;
	 *
	 * When you have these two instances, both appear to be the same instance.
	 * However, mapObjectR.routing can return a different object than mapObject.routing
	 *
	 * This
	 */
    protected abstract void overrideSubClasses();

	/**
	 * Adds a grid square to this map object
	 * @param gridSquare The grid square to be added
	 */
    public final void addGridSquare(GridSquare gridSquare)
    {
        if(!this.gridSquares.contains(gridSquare))
        {
            this.gridSquares.add(gridSquare);
        }
    }

    public final int getId()                            {return this.objectId;}
    public final String getType()                       {return this.objectType;}
    public final ArrayList<GridSquare> getGridSquares()	{return this.gridSquares;}

	/**
	 * Merges a map object with this one
	 * @param mapObject The map object to be merged with
	 */
    public void mergeObject(MapObject mapObject)
    {
        for(GridSquare gridSquare: mapObject.getGridSquares())
        {
            this.addGridSquare(gridSquare);
            gridSquare.setMapObject(this);
        }

        this.relatedObjects.mergeObject(mapObject);
        this.routing.mergeObject(mapObject);
    }

	/**
	 * Class responsible for tracking related (neighbouring) objects
	 */
    public class RelatedObjects
    {
		/**
		 * Stores the related objects by their type, since usually all related objects with a certain type are required
		 */
        private MultiValueHashMap<String, MapObject> relatedObjects = new MultiValueHashMap<String, MapObject>();

		/**
		 * Adds a related object
		 * Also makes sure the other sub classes of this map object are called where required
		 * @param object The object to be added
		 * @param connectionPoint The connection point of these two objects, consisting of the grid square that this object is on and the one the related object is on
		 */
        public void addRelatedObject(MapObject object, ConnectionPoint connectionPoint)
        {
            this.relatedObjects.putMultiValue(object.getType(), object);

            MapObject.this.routing.addConnection(connectionPoint, object);
        }

		/**
		 * @return All related objects
		 */
        public Collection<MapObject> getRelatedObjects()
        {
            return this.relatedObjects.singleListValues();
        }

		/**
		 * @param type The type of the required related objects
		 * @return The related objects with the specified type
		 */
        public ArrayList<MapObject> getRelatedObjects(String type)
        {
            return this.relatedObjects.get(type);
        }

		/**
		 * Merge the related objects from another map object into this one
		 * @param mapObject The map object to merge the related objects from
		 */
        public void mergeObject(MapObject mapObject)
        {
			this.relatedObjects.putAll(mapObject.relatedObjects.relatedObjects);
        }

		/**
		 * Returns a string representation of this object, used to ease debugging
		 * @return The string representation of the MapObject instance, with (Related Objects) attached to the end
		 */
		@Override
		public String toString()
		{
			return MapObject.this.toString()+" (Related Objects)";
		}
    }

	/**
	 * Class responsible for distance calculations through this object
	 */
    public class Routing
    {
		/**
		 * A list of all the connection points with this object and other map objects
		 */
		private MultiValueHashMap<MapObject, ConnectionPoint> connections = new MultiValueHashMap<MapObject, ConnectionPoint>();

		/**
		 * A list of all possible routes for each possible pair of map objects
		 */
        private HashMap<DoubleKey<MapObject>, RouteSet> routes = new HashMap<DoubleKey<MapObject>, RouteSet>();

		/**
		 * Adds a connection to connections and calculates all the newly created routes to the old connection points
		 * @param connectionPoint The connection point of this map object
		 * @param object The map object
		 */
        public void addConnection(ConnectionPoint connectionPoint, MapObject object)
        {
			for(Map.Entry<MapObject, ArrayList<ConnectionPoint>> entry : connections.entrySet())
			{
				if(!entry.getKey().equals(object))
				{
					DoubleKey key = new DoubleKey<MapObject>(entry.getKey(), object);

					RouteSet routeSet;
					if(routes.containsKey(key))
					{
						routeSet = routes.get(key);
					}
					else
					{
						routeSet = new RouteSet();
						routes.put(key, routeSet);
					}

					for(ConnectionPoint point : entry.getValue())
					{
						routeSet.add(new Route(point, connectionPoint));
					}
				}
			}

            this.connections.putMultiValue(object, connectionPoint);
        }

		/**
		 * Returns the shortest distance between two map objects
		 * @param object1 The first map object
		 * @param object2 The second map object
		 * @return The distance between the two map objects, in a double
		 */
        public double getShortestDistance(MapObject object1, MapObject object2)
        {
			DoubleKey key = new DoubleKey(object1, object2);
            if(!routes.containsKey(key))
			{
				return 0;
			}

			return routes.get(key).getShortestDistance();
        }

		/**
		 * Merges the Routing object of the given map object with this map object
		 * It does this by calling addConnection for each connection point in the other map objects connections
		 * @param mapObject The map object to merge with
		 */
        public void mergeObject(MapObject mapObject)
        {
            for(Map.Entry<MapObject, ArrayList<ConnectionPoint>> entry : mapObject.routing.connections.entrySet())
			{
				for(ConnectionPoint connectionPoint : entry.getValue())
				{
					this.addConnection(connectionPoint, entry.getKey());
				}
			}
        }

		/**
		 * @return All connection points contained in this object
		 */
		public Set<Map.Entry<MapObject, ArrayList<ConnectionPoint>>> getConnectionPoints()
		{
			return connections.entrySet();
		}

		/**
		 * Fetches the set of routes between two map objects
		 * @param mapObject1 The first map object
		 * @param mapObject2 The second map object
		 * @return The set of possible routes between these two map objects, or an empty route set if there aren't any possible routes
		 */
		public RouteSet getRouteSet(MapObject mapObject1, MapObject mapObject2)
		{
			DoubleKey key = new DoubleKey(mapObject1, mapObject2);

			if(routes.containsKey(key))
			{
				return routes.get(key);
			}
			else
			{
				return new RouteSet();
			}
		}

		/**
		 * Fetches the set of routes between a map object and another map object with the specified type
		 * @param mapObject The map object
		 * @param otherType The type of the other map object(s)
		 * @return The route set containing all possible routes from the map object to an object with the specified type, or an empty route set if there aren't any possible routes to this type
		 */
		public RouteSet getRouteSet(MapObject mapObject, String otherType)
		{
			RouteSet routeSet = new RouteSet();

			for(MapObject otherObject : MapObject.this.relatedObjects.getRelatedObjects(otherType))
			{
				if(otherObject.equals(mapObject))
				{
					continue;
				}

				routeSet.addAll(routes.get(new DoubleKey<MapObject>(mapObject, otherObject)));
			}

			return routeSet;
		}

		/**
		 * Generates the set of routes between the given connection point and a map object with the specified type
		 * @param connectionPoint The connection point
		 * @param otherType The type of the other map object(s)
		 * @return The route set containing all possible routes from this connection point to the specified type, or an empty route set if there aren't any possible routes to this type
		 */
		public RouteSet getRouteSet(ConnectionPoint connectionPoint, String otherType)
		{
			RouteSet routeSet = new RouteSet();

			for(Map.Entry<MapObject, ArrayList<ConnectionPoint>> entry : connections.entrySet())
			{
				if(entry.getKey().getType().equals(otherType))
				{
					for(ConnectionPoint otherConnectionPoint : entry.getValue())
					{
						routeSet.add(new Route(connectionPoint, otherConnectionPoint));
					}
				}
			}

			return routeSet;
		}

		/**
		 * Returns a string representation of this object, used to ease debugging
		 * @return The string representation of the MapObject instance, with (Routing) attached to the end
		 */
		@Override
		public String toString()
		{
			return MapObject.this.toString()+" (Routing)";
		}
    }

	/**
	 * Class responsible for calculating the score of this map object
	 */
    public abstract class Score
    {
		/**
		 * Calculates the value of this map object with a base multiplication factor of 1
		 * @return The calculated value
		 */
        public double calculateValue()
        {
            return this.calculateValue(1);
        }

		/**
		 * Calculates the value of this map object
		 * @param baseFactor The base multiplication factor, by which the calculated value will be multiplied
		 * @return The calculated value
		 */
        public double calculateValue(double baseFactor)
        {
            return this.calculateRawValue(baseFactor) + this.calculateBonusValue(baseFactor);
        }

		/**
		 * Calculates the raw value of this map object with a base multiplication factor of 1
		 * The raw value is simply the value of all grid square added together, without the bonus value from related objects
		 * @return The calculated value
		 */
        public double calculateRawValue()
        {
            return this.calculateRawValue(1);
        }

		/**
		 * Calculates the raw value of this map object
		 * The raw value is simply the value of all grid square added together, without the bonus value from related objects
		 * @param baseFactor The base multiplication factor, by which the calculated value will be multiplied
		 * @return The calculated value
		 */
        public double calculateRawValue(double baseFactor)
        {
            double value = 0;

            for(GridSquare gridSquare: gridSquares)
            {
                value += gridSquare.getValue();
            }

            return baseFactor * value;
        }

		/**
		 * Calculates the bonus value of this map object, which is derived from the value of related objects
		 * @param baseFactor The base multiplicatoin factor, by which the calculated value will be multiplied
		 * @return The calculated value
		 */
        private double calculateBonusValue(double baseFactor)
        {
            double value = 0;

            for(MapObject otherObject: relatedObjects.getRelatedObjects())
            {
				value += this.getBonusValue(otherObject, baseFactor);
            }

            return value;
        }

		/**
		 * Calculates the bonus value for a given related object.
		 * This is implemented by extending classes, because the bonus value depends on both the type of the map object and the type of the related object
		 * @param otherObject The related object to calculate the bonus value for
		 * @param baseFactor The base multiplication factor, by which the calculated value will be multiplied
		 * @return The calculated value
		 */
        abstract protected double getBonusValue(MapObject otherObject, double baseFactor);

		/**
		 * Returns a string representation of this object, used to ease debugging
		 * @return The string representation of the MapObject instance, with (Score) attached to the end
		 */
		@Override
		public String toString()
		{
			return MapObject.this.toString()+" (Score)";
		}
    }

	/**
	 * Checks if this object and the object provided are the same.
	 * This is required for using MapObject as a HashMap key
	 * @param obj The map object to compare this map object with
	 * @return True if the two map objects are the same, false if not
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		final MapObject other = (MapObject) obj;

		if (this.objectId != other.objectId) {
			return false;
		}
		if ((this.objectType == null) ? (other.objectType != null) : !this.objectType.equals(other.objectType)) {
			return false;
		}
		return true;
	}

	/**
	 * Generates a hashcode for this map object.
	 * This is required for using MapObject as a HashMap key
	 * @return The generated hashcode
	 */
	@Override
	public int hashCode() {
		int hash = 3;
		hash = 59 * hash + this.objectId;
		hash = 59 * hash + (this.objectType != null ? this.objectType.hashCode() : 0);
		return hash;
	}

	/**
	 * Returns a string representation of this object, used to ease debugging
	 * @return The name of this class with this objects id at the end
	 */
	@Override
	public String toString()
	{
		return "MapObject"+this.objectType+": #"+this.objectId;
	}
}
