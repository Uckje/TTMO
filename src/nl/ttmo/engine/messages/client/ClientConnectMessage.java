package nl.ttmo.engine.messages.client;

import com.jme3.network.serializing.Serializable;

import nl.ttmo.engine.messages.TTMOMessage;

/**
 * Server -> Client
 * Indicates this client wants to connect to the server
 * @author Maarten Slenter
 */
@Serializable
public class ClientConnectMessage extends TTMOMessage
{
	/**
	 * The name the player has chosen
	 */
    String playerName;

	/**
	 * No argument constructor, for serialization only
	 */
	public ClientConnectMessage()
	{}

	/**
	 * Constructor used by the client when sending this message
	 * @param playerName
	 */
	public ClientConnectMessage(String playerName)
	{
		this.playerName = playerName;
	}

	public String getPlayerName()
	{
		return playerName;
	}
}
