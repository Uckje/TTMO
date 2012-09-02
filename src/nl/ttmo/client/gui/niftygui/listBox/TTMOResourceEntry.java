package nl.ttmo.client.gui.niftygui.listBox;

/**
 * A class which contains one resource name and the amount the player has
 * @author Maarten Slenter
 */
public class TTMOResourceEntry
{
	/**
	 * The name of the resource
	 */
	String resourceName;

	/**
	 * The amount of this resource the player has, converted from an integer to a string
	 */
	String resourceAmount;

	public TTMOResourceEntry(String resourceName, Integer resourceAmount)
	{
		this.resourceName = resourceName;
		this.resourceAmount = resourceAmount.toString();
	}

	public String getResourceName()
	{
		return resourceName;
	}

	public String getResourceAmount()
	{
		return resourceAmount;
	}

	public void setResourceAmount(Integer resourceAmount)
	{
		this.resourceAmount = resourceAmount.toString();
	}
}
