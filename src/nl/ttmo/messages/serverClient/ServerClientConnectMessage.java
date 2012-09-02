package nl.ttmo.messages.serverClient;

import nl.ttmo.messages.TTMOMessage;

import com.jme3.network.serializing.Serializable;

/**
 * ServerClient -> Server
 * Indicates this server client wants to connect to the server
 * @author Maarten Slenter
 */
@Serializable
public class ServerClientConnectMessage extends TTMOMessage
{}