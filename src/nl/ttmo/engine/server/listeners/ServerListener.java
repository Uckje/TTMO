package nl.ttmo.engine.server.listeners;

import com.jme3.network.HostedConnection;

import java.util.ArrayList;

import nl.ttmo.engine.messages.TTMOMessage;
import nl.ttmo.engine.messages.serverClient.MessageRecievedMessage;
import nl.ttmo.engine.messages.serverClient.MessageSentMessage;

/**
 * Listener for server clients (singleton)
 * @author Maarten Slenter
 */
public class ServerListener
{
	/**
	 * The list of server clients
	 */
	static ArrayList<HostedConnection> serverClients = new ArrayList<HostedConnection>();

	/**
	 * Adds a server client to the list
	 * @param connection The connection to add
	 */
	public static void addServerClient(HostedConnection connection)
	{
		serverClients.add(connection);
	}

	/**
	 * Called when a message has been sent by the server
	 * @param message The message that has been sent
	 */
	public static void messageSent(TTMOMessage message)
	{
		for(HostedConnection serverClient: serverClients)
		{
			serverClient.send(new MessageSentMessage("Broadcast", message));
		}
	}

	/**
	 * Called when a message has been received by the server
	 * @param connection The connection the message has been received from
	 * @param message The message that has been received
	 */
	public static void messageReceived(HostedConnection connection, TTMOMessage message)
	{
		for(HostedConnection serverClient: serverClients)
		{
			serverClient.send(new MessageRecievedMessage(connection.<String> getAttribute("playerName"), message));
		}
	}
}
