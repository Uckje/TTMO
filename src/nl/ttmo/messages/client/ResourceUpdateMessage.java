package nl.ttmo.messages.client;

import com.jme3.network.serializing.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nl.ttmo.messages.TTMOMessage;
import nl.ttmo.messages.util.ResourceUpdateEntry;

/**
 * Server -> Client
 * The new resources amounts for the player this is sent to
 * @author Maarten Slenter
 */
@Serializable
public class ResourceUpdateMessage extends TTMOMessage
{
	ArrayList<ResourceUpdateEntry> resourcesEntryList = new ArrayList<ResourceUpdateEntry>();

	/**
	 * No argument constructor, for serialization only
	 */
	public ResourceUpdateMessage()
	{}

	/**
	 *
	 * @param resources	A hashmap with key value pairs indicating resource name and resource amount respectively
	 */
	public ResourceUpdateMessage(HashMap<String, Integer> resources)
	{
		for(Map.Entry<String, Integer> entry : resources.entrySet())
		{
			resourcesEntryList.add(new ResourceUpdateEntry(entry.getKey(), entry.getValue()));
		}
	}

	/**
	 * Generates a hashmap from the resource entries in resourceEntryList
	 * @return A hashmap with key value pairs indicating resource name and resource amount respectively
	 */
	public HashMap<String, Integer> getResources()
	{
		HashMap<String, Integer> resources = new HashMap<String, Integer>();

		for(ResourceUpdateEntry entry : resourcesEntryList)
		{
			resources.put(entry.getName(), entry.getAmount());
		}

		return resources;
	}
}
