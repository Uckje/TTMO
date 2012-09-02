package nl.ttmo.server.game.resources.util;

import java.util.HashMap;
import java.util.Map;

import nl.ttmo.server.game.resources.types.Resource;

/**
 * Represents a set of resources and amounts
 * @author Maarten Slenter
 */
public class ResourceAmountSet
{
	/**
	 * The hash map, linking the resources to the amounts
	 */
	HashMap<Resource, Integer> resourceAmounts;

	public ResourceAmountSet()
	{
		this.resourceAmounts = new HashMap<Resource, Integer>();
	}

	public ResourceAmountSet(HashMap<Resource, Integer> resources)
	{
		this.resourceAmounts = resources;
	}

	public HashMap<Resource, Integer> getResourceAmounts()
	{
		return resourceAmounts;
	}

	/**
	 * Adds a resource and its amount
	 * @param resource The resource to add
	 * @param amount The amount
	 */
	public void addResource(Resource resource, Integer amount)
	{
		resourceAmounts.put(resource, amount);
	}

	/**
	 * Deletes a resource and its amount
	 * @param resource The resource to delete
	 */
	public void deleteResource(Resource resource)
	{
		resourceAmounts.remove(resource);
	}

	/**
	 * Checks if it's possible to substract the supplied resource amount set from this one
	 * @param resourceAmounts The resource amount set to substract
	 * @return True if the resource amount set is substractable from this one, false otherwise
	 */
	public boolean canSubstract(ResourceAmountSet resourceAmounts)
	{
		for(Map.Entry<Resource, Integer> entry : resourceAmounts.getResourceAmounts().entrySet())
		{
			if(!this.resourceAmounts.containsKey(entry.getKey()) || this.resourceAmounts.get(entry.getKey()) < entry.getValue())
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * Substracts the supplied resource amount set from this one, if possible
	 * @param resourceAmounts The resource amount set to substract
	 */
	public void substract(ResourceAmountSet resourceAmounts)
	{
		if(canSubstract(resourceAmounts))
		{
			for(Map.Entry<Resource, Integer> entry : resourceAmounts.getResourceAmounts().entrySet())
			{
				this.resourceAmounts.put(entry.getKey(), this.resourceAmounts.get(entry.getKey()) - entry.getValue());
			}
		}
	}

	/**
	 * Adds the supplied resource amount set to this one
	 * @param resourceAmounts The resource amount set to add
	 */
	public void add(ResourceAmountSet resourceAmounts)
	{
		for(Map.Entry<Resource, Integer> entry : resourceAmounts.getResourceAmounts().entrySet())
		{
			if(this.resourceAmounts.containsKey(entry.getKey()))
			{
				this.resourceAmounts.put(entry.getKey(), this.resourceAmounts.get(entry.getKey()) + entry.getValue());
			}
			else
			{
				this.resourceAmounts.put(entry.getKey(), entry.getValue());
			}
		}
	}

	/**
	 * Converts this resource amount set to a string => integer hash map
	 * @return The generated hash map
	 */
	public HashMap<String, Integer> toHashMap()
	{
		HashMap<String, Integer> hashMap = new HashMap<String, Integer>();

		for(Map.Entry<Resource, Integer> entry : resourceAmounts.entrySet())
		{
			hashMap.put(entry.getKey().getName(), entry.getValue());
		}

		return hashMap;
	}
}
