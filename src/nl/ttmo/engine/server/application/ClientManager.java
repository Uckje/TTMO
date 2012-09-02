package nl.ttmo.engine.server.application;

import java.util.ArrayList;

import com.jme3.network.HostedConnection;

/**
 * Manages the list of clients that are connected to the server
 * @author Maarten Slenter
 */
public class ClientManager
{
	/**
	 * The ClientManager singleton instance
	 */
	private static ClientManager instance = new ClientManager();

	/**
	 * A list of the clients that are connected to the server
	 */
	ArrayList<HostedConnection> clients = new ArrayList<HostedConnection>();

	/**
	 * Adds a client to the list
	 * @param connection The client to add
	 */
	public void addClient(HostedConnection connection)
	{
		clients.add(connection);
	}

	/**
	 * Returns the list with clients
	 * @return The list with clients
	 */
	public ArrayList<HostedConnection> getClients()
	{
		return clients;
	}

	/**
	 * Checks if the supplied client is in the list
	 * @param connection The client to check
	 * @return True if the supplied client is in the list, false otherwise
	 */
	public boolean hasClient(HostedConnection connection)
	{
		return clients.contains(connection);
	}

	/**
	 * Removes the supplied client from the list
	 * @param connection The client to remove
	 */
	public void removeClient(HostedConnection connection)
	{
		clients.remove(connection);
	}

	/**
	 * Checks if there are any clients in the list
	 * @return True if there are no clients in the list, false otherwise
	 */
	public boolean noClients()
	{
		return clients.isEmpty();
	}

	/**
	 * @return The ClientManager instance
	 */
	public static ClientManager get()
	{
		return instance;
	}
}
