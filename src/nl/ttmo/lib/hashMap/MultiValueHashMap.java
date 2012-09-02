package nl.ttmo.lib.hashMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * HashMap based map that allows multiple values for each key
 * @author Maarten Slenter
 */
public class MultiValueHashMap<K, V> extends HashMap<K, ArrayList<V>>
{
	/**
	 * Adds a value to a key
	 * @param key The key
	 * @param value The value
	 */
	public void putMultiValue(K key, V value)
	{
		ArrayList<V> array;

		if(!super.containsKey(key))
		{
			array = new ArrayList<V>();
			super.put(key, array);
		}
		else
		{
			array = super.get(key);
		}

		if(!array.contains(value))
		{
			array.add(value);
		}
	}

	/**
	 * Adds a set of values to a key
	 * @param key The key
	 * @param valueList The set of values
	 */
	public void put(K key, Collection<V> valueList)
	{
		ArrayList<V> array;

		if(!super.containsKey(key))
		{
			array = new ArrayList<V>();
			super.put(key, array);
		}
		else
		{
			array = super.get(key);
		}

		for(V value : valueList)
		{
			if(!array.contains(value))
			{
				array.add(value);
			}
		}
	}

	/**
	 * Fetches the list of values that belong to the specified key, or an empty list if the key doesn't exist
	 * @param key The key
	 * @return The values that belong to the key, or an emtpy set if the key doesn't exist
	 */
	@Override
	public ArrayList<V> get(Object key)
	{
		if(super.containsKey(key))
		{
			return super.get(key);
		}
		else
		{
			return new ArrayList<V>();
		}
	}

	/**
	 * Adds all the values from the supplied multi value hash map to this multi value hash map
	 * @param multiValueHashMap The multi value hash map with the new values to add
	 */
	public void putAll(MultiValueHashMap<K, V> multiValueHashMap)
	{
		for(Map.Entry<K, ArrayList<V>> entry: multiValueHashMap.entrySet())
		{
			if(super.containsKey(entry.getKey()))
			{
				super.get(entry.getKey()).addAll(entry.getValue());
			}
			else
			{
				super.put(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * Puts all values, regardless of key, in one list
	 * @return The list with all values in this multi value hash map
	 */
	public Collection<V> singleListValues()
	{
		ArrayList<V> array = new ArrayList<V>();

		for(Collection<V> valueList: super.values())
		{
			for(V value : valueList)
			{
				array.add(value);
			}
		}

		return array;
	}
}
