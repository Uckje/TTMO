package nl.ttmo.client.threads;

import com.jme3.network.Client;
import com.jme3.network.ClientStateListener;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Callable;

import nl.ttmo.client.gui.util.ClientGuiObjectFactory;

import nl.ttmo.lib.game.world.GameMap;
import nl.ttmo.lib.world.Location;

import nl.ttmo.messages.ShutdownMessage;
import nl.ttmo.messages.TTMOMessage;
import nl.ttmo.messages.client.ScoreUpdateMessage;
import nl.ttmo.messages.client.PlacementMessage;
import nl.ttmo.messages.client.MapLoadMessage;
import nl.ttmo.messages.client.ResourceUpdateMessage;

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
	 * The address this client has connected to
	 */
    String address;

	/**
	 * The JME Client object
	 */
    Client client;

	/**
	 * Message queue for messages to be sent
	 */
    LinkedBlockingQueue<TTMOMessage> queue = new LinkedBlockingQueue<TTMOMessage>();

	/**
	 * Indicates if this thread is running
	 */
	boolean run = true;

	/**
	 * Indicates if it is connected
	 */
	boolean connected = false;

	public NetworkThread()
	{
		super("Network Thread");
	}

	/**
	 * Will try to connect to the specified address and set connected appropriately
	 * @param address The address to connect to
	 * @param app A reference to the Main object
	 * @return True if connected, false otherwise
	 */
    public boolean connect(String address)
    {
        this.address = address;

		try
		{
			client = Network.connectToServer(address, 4000);
			connected = true;
        }
        catch(IOException ex)
        {
			System.out.println(ex);
			connected = false;
        }

		return connected;
    }

	/**
	 * Called when the thread is started.
	 * Will poll the message queue for new messages and send them if there are any.
	 * Returns as soon as run is false
	 */
    @Override
    public void run()
    {
		TTMOMessage.registerClasses();

		client.addMessageListener(new TTMOMessageListener<Client>());
		client.start();

		TTMOMessage message;
		while(run)
		{
			if((message = queue.poll()) != null)
			{
				client.send(message);
			}
		}

		client.close();
    }

	/**
	 * Adds a message to the message queue to be sent as soon as possible
	 * @param message The message to be sent
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
	 * Sets run to false, so the thread will quit as soon as the while loop in run is evaluated again.
	 */
	public synchronized void exit()
	{
		run = false;
	}

	/**
	 * @return A boolean indicating if the client is connected
	 */
	public synchronized boolean isConnected()
	{
		return connected;
	}

	/**
	 * @return The NetworkThread instance
	 */
	public static NetworkThread getInstance()
	{
		return instance;
	}

	/**
	 * The message listener for the client.
	 * Will check what kind of message it is and then handle it accordingly
	 */
    class TTMOMessageListener<S> implements MessageListener<S>
    {
		/**
		 * Called when a message is received.
		 * Checks what kind of message it is and then handles it accordingly
		 * @param source The connection this was sent from
		 * @param message The message that has been received
		 */
        public void messageReceived(S source, final Message message)
        {
			if(message instanceof ShutdownMessage)
			{
				Main.getInstance().TTMOStop();
			}
			else if(message instanceof PlacementMessage)
            {
                PlacementMessage placementMessage = (PlacementMessage) message;
                GameMap.getInstance().updateGridSquare(new Location(placementMessage.getX(), placementMessage.getZ()), placementMessage.getType(), new ClientGuiObjectFactory());
            }
            else if(message instanceof MapLoadMessage)
            {
				Main.getInstance().enqueue(new Callable<Void>() {
					@Override
					public Void call() throws Exception
					{
						Main.getInstance().initializeMap((MapLoadMessage) message);
						return null;
					}
				});
            }
			else if(message instanceof ScoreUpdateMessage)
			{
				Main.getInstance().updateScore(((ScoreUpdateMessage) message).getScore());
			}
			else if(message instanceof ResourceUpdateMessage)
			{
				Main.getInstance().updateResources((ResourceUpdateMessage) message);
			}
        }
    }

	/**
	 * Client connection listener, which stops the client cleanly if the server is disconnected
	 */
	class TTMOClientStateListener implements ClientStateListener
	{
		public void clientConnected(Client c)
		{}

		public void clientDisconnected(Client c, DisconnectInfo info)
		{
			Main.getInstance().TTMOStop();
		}
	}
}
