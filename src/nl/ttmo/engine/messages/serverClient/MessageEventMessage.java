package nl.ttmo.engine.messages.serverClient;

import nl.ttmo.engine.messages.TTMOMessage;

/**
 * Server -> ServerClient
 * Indicates that either a message has been received or has been sent by the server
 * @author Maarten Slenter
 */
public interface MessageEventMessage
{
	/**
	 *
	 * @return The name of the player this message has been received from/sent to
	 */
	public String getPlayerName();

	/**
	 *
	 * @return The message that has been sent or received
	 */
	public TTMOMessage getMessage();

	/**
	 * Give a string representation of the direction of this message
	 * @return A string representing the direction of this message
	 */
	public String getDirection();
}
