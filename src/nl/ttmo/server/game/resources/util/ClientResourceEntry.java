package nl.ttmo.server.game.resources.util;

import com.jme3.network.HostedConnection;

import nl.ttmo.server.game.resources.types.ResourceMoney;
import nl.ttmo.server.game.resources.types.ResourceWood;

/**
 * Represents a client and the resources that client has
 * @author Maarten Slenter
 */
public class ClientResourceEntry
{
	/**
	 * The client object
	 */
	final HostedConnection connection;

	/**
	 * The resource amount set that represents the resources this client has
	 */
	ResourceAmountSet resourceAmounts;

	/**
	 * The resource amount set that represents the resources this client gains each second
	 */
	ResourceAmountSet resourceIncreases;

	public ClientResourceEntry(HostedConnection connection)
	{
		this.connection = connection;

		resourceAmounts = new ResourceAmountSet();
		resourceAmounts.addResource(new ResourceMoney(), 500);
		resourceAmounts.addResource(new ResourceWood(), 500);

		resourceIncreases = new ResourceAmountSet();
	}

	public HostedConnection getConnection()
	{
		return connection;
	}

	/**
	 * Updates the resources this client has, i.e. adds resourceIncreases to resourceAmounts
	 */
	public void updateResources()
	{
		resourceAmounts.add(resourceIncreases);
	}

	/**
	 * Checks if the client has enough resources to substract the supplied amounts
	 * @param resourceAmounts The resource amounts to check
	 * @return True if the supplied amounts can be substracted from what this client currently has, false otherwise
	 */
	public boolean canSubstractResources(ResourceAmountSet resourceAmounts)
	{
		return this.resourceAmounts.canSubstract(resourceAmounts);
	}

	/**
	 * Adds the supplied resource amounts to what the player currently has
	 * @param resourceAmounts The resource amounts to add
	 */
	public void addAmounts(ResourceAmountSet resourceAmounts)
	{
		this.resourceAmounts.add(resourceAmounts);
	}

	/**
	 * Substracts the supplied resource amounts from what the player currently has
	 * @param resourceAmounts The resource amounts to substract
	 */
	public void substractResources(ResourceAmountSet resourceAmounts)
	{
		this.resourceAmounts.substract(resourceAmounts);
	}

	/**
	 * Adds the supplied resource amounts to what the player currently gains per second
	 * @param resourceIncreases The resource amounts to add
	 */
	public void addIncrease(ResourceAmountSet resourceIncreases)
	{
		this.resourceIncreases.add(resourceIncreases);
	}

	/**
	 * Substracts the supplied resource amounts from what the player currently gains per second
	 * @param resourceDecreases The resource amounts to substract
	 */
	public void substractIncrease(ResourceAmountSet resourceDecreases)
	{
		this.resourceIncreases.substract(resourceDecreases);
	}

	public ResourceAmountSet getResourceAmounts()
	{
		return resourceAmounts;
	}

	public ResourceAmountSet getResourceIncreases()
	{
		return resourceIncreases;
	}
}
