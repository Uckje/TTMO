package nl.ttmo.server.threads;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Command line parser for the server.
 * Reads the commands that have been inputted from the terminal and passes them to the registered listeners
 * @author Maarten Slenter
 */
public class CommandLineParser extends Thread
{
	/**
	 * The singleton instance of CommandLineParser
	 */
	static CommandLineParser instance = new CommandLineParser();

	/**
	 * The Scanner instance used to scan commands from the terminal
	 */
	Scanner scanner = new Scanner(System.in);

	/**
	 * The registered command listeners
	 */
	ArrayList<CommandListener> listeners = new ArrayList<CommandListener>();

	/**
	 * Indicates if this thread should continue running
	 */
	volatile boolean run = true;

	private CommandLineParser()
	{}

	/**
	 * @return The CommandLineParser instance
	 */
	public static CommandLineParser get()
	{
		return instance;
	}

	/**
	 * Called when the thread is started.
	 * When a command has been read, it calls each of the listeners.
	 * When a listener returns true (i.e. has successfully parsed the command), no other listeners will be called and the thread will continue scanning for the next command.
	 * Returns as soon as run is false
	 */
	@Override
	public void run()
	{
		while(run)
		{
			System.out.print(">>> ");

			if(scanner.hasNextLine())
			{
				String[] parts = scanner.nextLine().split(" ");

				for(CommandListener listener : listeners)
				{
					if(listener.callCommand(parts[0], parts.length - 1, Arrays.<String> copyOfRange(parts, 1, parts.length)))
					{
						break;
					}
				}
			}
		}
	}

	/**
	 * Adds a listener to the listeners list
	 * @param listener The listener to add
	 */
	public void addListener(CommandListener listener)
	{
		listeners.add(listener);
	}

	/**
	 * Stops this thread by setting run to false
	 */
	public void exit()
	{
		run = false;
	}

	/**
	 * Interface for command listeners
	 */
	public interface CommandListener
	{
		/**
		 * Called when a command has been entered in the terminal
		 * @param command The command that has been entered
		 * @param argLen The length of the argument list
		 * @param arguments The arguments
		 * @return True if the command has been parsed successfully, false otherwise
		 */
		public boolean callCommand(String command, int argLen, String[] arguments);
	}
}
