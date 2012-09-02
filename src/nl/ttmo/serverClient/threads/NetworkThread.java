package nl.ttmo.serverClient.threads;

import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.Client;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

import nl.ttmo.serverClient.gui.niftygui.MainController;

import nl.ttmo.messages.TTMOMessage;
import nl.ttmo.messages.serverClient.MessageEventMessage;

public class NetworkThread extends Thread
{
    String address;
    Main app;
	MainController mainController;
    
    Client client;
    LinkedBlockingQueue<TTMOMessage> queue = new LinkedBlockingQueue<TTMOMessage>();
	
	boolean run = true;
	boolean connected = false;
    
    public NetworkThread(String address)
    {
        super("Network Thread");
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
    }
    
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
	
	public synchronized void exit()
	{
		run = false;
	}
	
	public synchronized boolean isConnected()
	{
		return connected;
	}
	
	public void setMainController(MainController mainController)
	{
		this.mainController = mainController;
	}
    
    class TTMOMessageListener<S> implements MessageListener<S>
    {
        public void messageReceived(S source, final Message message) 
        {
			if(message instanceof MessageEventMessage)
			{
				mainController.messageRecieved((MessageEventMessage) message);
			}
			
			System.out.println(message);
        }
    }
}
