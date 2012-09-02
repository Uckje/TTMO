package nl.ttmo.engine.messages;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;
import com.jme3.network.serializing.Serializer;

import nl.ttmo.engine.messages.client.*;
import nl.ttmo.engine.messages.serverClient.*;
import nl.ttmo.engine.messages.util.*;

/**
 * The base class for all messages
 * @author Maarten Slenter
 */
@Serializable()
public abstract class TTMOMessage extends AbstractMessage
{
	/**
	 * Registers all messages and utility classes with the standard JME serializer, to make sure they can be sent without trouble
	 */
	static public void registerClasses()
    {
        Serializer.registerClasses(	ShutdownMessage.class,

									ClientConnectMessage.class,
									MapGridSquareData.class,
									MapLoadMessage.class,
									PlacementMessage.class,
									ResourceUpdateEntry.class,
									ResourceUpdateMessage.class,
									ScoreUpdateMessage.class,

									MessageRecievedMessage.class,
									MessageSentMessage.class,
									ServerClientConnectMessage.class);
    }
}
