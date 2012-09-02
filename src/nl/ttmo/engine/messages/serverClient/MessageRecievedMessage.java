package nl.ttmo.engine.messages.serverClient;

import com.jme3.network.serializing.Serializable;

import nl.ttmo.engine.messages.TTMOMessage;

/**
 * Server -> Server Client
 * A message has been received from a client
 * @author Maarten Slenter
 */
@Serializable
public class MessageRecievedMessage extends TTMOMessage implements MessageEventMessage
{
	String playerName;
	TTMOMessage message;

	/**
	 * No argument constructor, for serialization only
	 */
	public MessageRecievedMessage()
	{}

	/**
	 *
	 * @param playerName Name of the player this message was received from
	 * @param message The message that was received
	 */
	public MessageRecievedMessage(String playerName, TTMOMessage message)
	{
		this.playerName = playerName;
		this.message = message;
	}

	/**
	 *
	 * @return The name of the player this message has been received from/sent to
	 */
	@Override
	public String getPlayerName()
	{
		return playerName;
	}

	/**
	 *
	 * @return The message that has been sent or received
	 */
	@Override
	public TTMOMessage getMessage()
	{
		return message;
	}

	/**
	 * Give a string representation of the direction of this message
	 * @return "Received" to indicate this message was received by the server
	 */
	@Override
	public String getDirection()
	{
		return "Recieved";
	}
}
