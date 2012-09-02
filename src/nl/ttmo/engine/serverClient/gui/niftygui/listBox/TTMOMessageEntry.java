package nl.ttmo.engine.serverClient.gui.niftygui.listBox;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Maarten Slenter
 */
public class TTMOMessageEntry
{
	String direction;
	String messageName;

	public TTMOMessageEntry(String direction, String messageName)
	{
		this.direction = direction;
		this.messageName = messageName;
	}

	public String getDirection()
	{
		return direction;
	}

	public String getMessageName()
	{
		return messageName;
	}
}
