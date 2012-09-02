package nl.ttmo.messages.util;

import com.jme3.network.serializing.Serializable;

/**
 * Server -> Client
 * One set of a resource and the amount the player has
 * @author Maarten Slenter
 */
@Serializable
public class ResourceUpdateEntry
{
	/**
	 * The name of the resource
	 */
	String name;

	/**
	 * The amount of this resource that the player has
	 */
	Integer amount;

	/**
	 * No argument constructor, for serialization only
	 */
	public ResourceUpdateEntry()
	{}

	/**
	 *
	 * @param name		The name of this resource
	 * @param amount	The amount of this resource
	 */
	public ResourceUpdateEntry(String name, Integer amount)
	{
		this.name = name;
		this.amount = amount;
	}

	/**
	 *
	 * @return The name of the resource
	 */
	public String getName()
	{
		return name;
	}

	/**
	 *
	 * @return The amount the player has of this resource
	 */
	public Integer getAmount()
	{
		return amount;
	}
}