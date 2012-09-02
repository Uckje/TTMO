package nl.ttmo.engine.server.threads;

import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Server;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import nl.ttmo.engine.lib.world.Location;

import nl.ttmo.engine.messages.ShutdownMessage;
import nl.ttmo.engine.messages.TTMOMessage;
import nl.ttmo.engine.messages.client.ResourceUpdateMessage;
import nl.ttmo.engine.messages.client.PlacementMessage;
import nl.ttmo.engine.messages.client.ClientConnectMessage;
import nl.ttmo.engine.messages.serverClient.ServerClientConnectMessage;

import nl.ttmo.engine.server.application.ClientManager;
import nl.ttmo.engine.server.game.MapManager;
import nl.ttmo.engine.server.game.resources.util.ResourceManager;
import nl.ttmo.engine.server.listeners.ServerListener;

/**
 * The thread that will take care of all the network related logic
 * @author Maarten Slenter
 */
public class NetworkThread extends Thread
{
	/**
	 * The NetworkThread singleton instance
	 */
	private static NetworkThread instance = new NetworkThread();

	/**
	 * The JME Server object
	 */
    Server server;

	/**
	 * Message queue for messages to be broadcast
	 */
    LinkedBlockingQueue<TTMOMessage> queue = new LinkedBlockingQueue<TTMOMessage>();

	/**
	 * Indicates if this thread is/should continue running
	 */
	volatile boolean run = true;

    private NetworkThread()
    {
        super("Network Thread");
    }

	@Override
	public void start()
	{
        Logger logger = Logger.getLogger(NetworkThread.class.getName());
		logger.getParent().setLevel(Level.WARNING);

		super.start();
	}

	/**
	 * Called when the thread is started.
	 * Tries to start the server, after which it starts polling the queue for messages.
	 * If there are any, it broadcasts the messages and notifies the server clients
	 * Returns as soon as run is false
	 */
    @Override
    public void run()
    {
        try
        {
            TTMOMessage.registerClasses();

            server = Network.createServer(4000);
            server.addMessageListener(new TTMOClientMessageListener<HostedConnection>());
            server.addMessageListener(new TTMOServerClientMessageListener<HostedConnection>());
			server.addConnectionListener(new TTMOConnectionListener());
            server.start();

            TTMOMessage message;
			while(run != false)
            {
				if((message = queue.poll()) != null)
				{
					ServerListener.messageSent(message);
					for(HostedConnection connection : ClientManager.get().getClients())
					{
						connection.send(message);
					}
				}
            }

			this.send(new ShutdownMessage());
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }

	/**
	 * Adds a message to the message queue to be broadcast as soon as possible
	 * @param message The message to be broadcast
	 */
    public synchronized void send(TTMOMessage message)
    {
        try
        {
            queue.put(message);
        }
        catch(InterruptedException ex)
        {
            System.out.println(ex);
        }
    }

	/**
	 * Checks if there are any clients connected
	 * @return True if there are clients connected, false otherwise
	 */
	public synchronized boolean hasConnections()
	{
		return !server.getConnections().isEmpty();
	}

	/**
	 * Sets run to false, to make this thread shut down as soon as possible
	 */
	public synchronized void exit()
	{
		run = false;
	}

	/**
	 * @return The NetworkThread instance
	 */
	public static NetworkThread getInstance()
	{
		return instance;
	}

	/**
	 * Listener for connections.
	 * When a connection is removed, it notifies the client and resource manager
	 */
	class TTMOConnectionListener implements ConnectionListener
	{
		public void connectionAdded(Server server, HostedConnection connection)
		{}

		/**
		 * Called when a connection is removed.
		 * Notifies the client and resource manager of the removed client.
		 * Also shuts down the server if there are no clients left and run is false. (i.e. the thread is supposed to shut down as soon as possible)
		 * @param server The server object
		 * @param connection The connection that was removed
		 */
		public void connectionRemoved(Server server, HostedConnection connection)
		{
			ClientManager.get().removeClient(connection);
			ResourceManager.getInstance().removeClient(connection);

			if(ClientManager.get().noClients() && run == false)
			{
				server.close();
			}
		}
	}

	/**
	 * The message listener of the server.
	 * Checks which message has been received and handles it accordingly.
	 * @param <S> The type of connection this message has been received from.
	 */
    class TTMOClientMessageListener<S extends HostedConnection> implements MessageListener<S>
    {
		/**
		 * Called when a message has been received.
		 * Checks what type of message it is and handles it accordingly.
		 * @param source The connection this message has been received from
		 * @param message The message that has been received
		 */
        public void messageReceived(S source, Message message)
        {
			TTMOMessage ttmoMessage = (TTMOMessage) message;
			ServerListener.messageReceived(source, ttmoMessage);
			if(ttmoMessage instanceof PlacementMessage)
            {
                PlacementMessage placementMessage = (PlacementMessage) ttmoMessage;
                if(MapManager.getInstance().updateGridSquare(source, NetworkThread.this, new Location(placementMessage.getX(), placementMessage.getZ()), placementMessage.getType()))
				{
					send(ttmoMessage);
					source.send(new ResourceUpdateMessage(ResourceManager.getInstance().getClientResourceEntry(source).getResourceAmounts().toHashMap()));
				}
            }
            else if(ttmoMessage instanceof ClientConnectMessage)
            {
				ClientConnectMessage clientConnectMessage = (ClientConnectMessage) ttmoMessage;
				source.setAttribute("playerName", clientConnectMessage.getPlayerName());
				ClientManager.get().addClient(source);
				ResourceManager.getInstance().addClient(source);
                source.send(MapManager.getInstance().generateMapLoadMessage());
            }
        }
    }

	/**
	 * The message listener that notifies the server clients of received messages
	 * @param <S> The type of the connection
	 */
	class TTMOServerClientMessageListener<S extends HostedConnection> implements MessageListener<S>
	{
		/**
		 * Called when a message has been received.
		 * Notifies all server clients of the received message
		 * @param source The connection the message has been received from
		 * @param message The message that has been received
		 */
        public void messageReceived(S source, Message message)
        {
			TTMOMessage ttmoMessage = (TTMOMessage) message;
			ServerListener.messageReceived(source, ttmoMessage);
			if(ttmoMessage instanceof ServerClientConnectMessage)
			{
				ServerListener.addServerClient(source);
			}
		}
	}
}
