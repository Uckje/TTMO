package nl.ttmo.messages;

import com.jme3.network.serializing.Serializable;

/**
 * Server -> Client & Server Client
 * Indicates the server is going to shut down
 * @author Maarten Slenter
 */
@Serializable
public class ShutdownMessage extends TTMOMessage
{

}
