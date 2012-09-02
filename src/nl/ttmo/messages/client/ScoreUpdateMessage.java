package nl.ttmo.messages.client;

import com.jme3.network.serializing.Serializable;

import nl.ttmo.messages.TTMOMessage;

/**
 * Server -> Client
 * The new score for the player this is sent to
 * @author Maarten Slenter
 */
@Serializable
public class ScoreUpdateMessage extends TTMOMessage
{
	/**
	 * The new score for the player
	 */
	double score;

	/**
	 * No argument constructor, for serialization only
	 */
	public ScoreUpdateMessage()
	{}

	/**
	 *
	 * @param score The new score for the player
	 */
	public ScoreUpdateMessage(double score)
	{
		this.score = score;
	}

	/**
	 *
	 * @return The new score for the player
	 */
	public double getScore()
	{
		return score;
	}

	/**
	 *
	 * @param score The new score for this player
	 */
	public void setScore(double score)
	{
		this.score = score;
	}
}
