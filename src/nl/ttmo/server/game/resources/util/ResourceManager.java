package nl.ttmo.server.game.resources.util;

import java.util.HashMap;

import nl.ttmo.messages.client.ResourceUpdateMessage;

import com.jme3.network.HostedConnection;

/**
 * Manages the resources for all clients
 * @author Maarten Slenter
 */
public class ResourceManager
{
	/**
	 * The ResourceManager singleton instance
	 */
	private static ResourceManager instance = new ResourceManager();

	/**
	 * The clients and their resource entries
	 */
	HashMap<HostedConnection, ClientResourceEntry> clientEntries = new HashMap<HostedConnection, ClientResourceEntry>();

	/**
	 * Adds a new client
	 * @param connection The connection to add
	 */
	public void addClient(HostedConnection connection)
	{
		clientEntries.put(connection, new ClientResourceEntry(connection));
	}

	/**
	 * Returns the client resource entry belonging to the supplied client
	 * @param connection The connection to fetch the client resource entry for
	 * @return The client resource entry that belongs to the supplied client
	 */
	public ClientResourceEntry getClientResourceEntry(HostedConnection connection)
	{
		return clientEntries.get(connection);
	}

	/**
	 * Removes a client from the list
	 * @param connection The connection to remove
	 */
	public void removeClient(HostedConnection connection)
	{
		clientEntries.remove(connection);
	}

	/**
	 * Checks if the supplied client has the specified resources
	 * @param connection The connection to check
	 * @param resourceAmounts The resource amounts to check
	 * @return True if the supplied client has the specified resources, false otherwise
	 */
	public boolean hasResources(HostedConnection connection, ResourceAmountSet resourceAmounts)
	{
		return clientEntries.get(connection).canSubstractResources(resourceAmounts);
	}

	/**
	 * Substracs the specified resource amounts from the supplied client's resources
	 * @param connection The connection to substract the resources from
	 * @param resourceAmounts The resource amounts to substract
	 */
	public void substractResources(HostedConnection connection, ResourceAmountSet resourceAmounts)
	{
		clientEntries.get(connection).substractResources(resourceAmounts);
	}

	/**
	 * Adds the specified resource amounts to the resource gains of the supplied client
	 * @param connection The connection to add the resource gains to
	 * @param resourceAmounts The resource increases to add
	 */
	public void addResourceIncreases(HostedConnection connection, ResourceAmountSet resourceAmounts)
	{
		clientEntries.get(connection).addIncrease(resourceAmounts);
	}

	/**
	 * Updates the resources for all clients and notifies them of their new resource amounts
	 */
	public void updateResources()
	{
		for(ClientResourceEntry clientEntry : clientEntries.values())
		{
			clientEntry.updateResources();
			clientEntry.getConnection().send(new ResourceUpdateMessage(clientEntry.getResourceAmounts().toHashMap()));
		}
	}

	/**
	 * @return The ResourceManager instance
	 */
	public static ResourceManager getInstance()
	{
		return instance;
	}
}
