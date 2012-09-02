package nl.ttmo.engine.client.gui.util;

import com.jme3.asset.AssetManager;

/**
 * A singleton wrapper for AssetManager.
 * This makes AssetManager easily available for all gui classes that need it, instead of having to pass it through objects that don't have anything to do with it
 * @author Maarten Slenter
 */
public class AssetManagerSingleton
{
	/**
	 * The AssetManager instance
	 */
	private static AssetManager assetManager;

	/**
	 * @return The AssetManager instance
	 */
	public static AssetManager get()
	{
		return assetManager;
	}

	/**
	 * Sets the AssetManager instance
	 * @param assetManager The AssetManager instance
	 */
	public static void set(AssetManager assetManager)
	{
		AssetManagerSingleton.assetManager = assetManager;
	}
}
