package nl.ttmo.engine.lib.gui;

import com.jme3.scene.Spatial;

/**
 * Represents a gui object
 * A gui object is an object that can produce a Spatial, which can be displayed by jME
 * @author Maarten Slenter
 */
public interface GuiObject
{
	/**
	 * Generates the spatial of this gui object
	 * @return The generated spatial
	 */
	public Spatial generateSpatial();
}
