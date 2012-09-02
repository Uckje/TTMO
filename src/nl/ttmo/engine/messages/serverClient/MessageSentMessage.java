package nl.ttmo.engine.messages.serverClient;

import com.jme3.network.serializing.Serializable;

import nl.ttmo.engine.messages.TTMOMessage;

/**
 * Server -> Server Client
 * A message has been sent from the server to a client
 * @author Maarten Slenter
 */
@Serializable
public class MessageSentMessage extends TTMOMessage implements MessageEventMessage
{
	String playerName;
	TTMOMessage message;

	/**
	 * No argument constructor, for serialization only
	 */
	public MessageSentMessage()
	{}

	/**
	 *
	 * @param playerName	The name of the player to which the message has been sent
	 * @param message		The message that has been sent
	 */
	public MessageSentMessage(String playerName, TTMOMessage message)
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
	 * @return "Sent" to indicate this message was sent from the server
	 */
	@Override
	public String getDirection()
	{
		return "Sent";
	}
}
