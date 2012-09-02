package nl.ttmo.server.listeners;

import nl.ttmo.server.threads.CommandLineParser;
import nl.ttmo.server.threads.Main;

/**
 * Default command listener for the server.
 * Listens to the following commands:
 *	stop	Stops the server
 * @author Maarten Slenter
 */
public class TTMOServerCommandListener implements CommandLineParser.CommandListener
{
	public boolean callCommand(String command, int argLen, String[] arguments)
	{
		if(command.equals("stop"))
		{
			Main.getInstance().stop();
			return true;
		}

		return false;
	}
}
