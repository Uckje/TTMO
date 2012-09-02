package nl.ttmo.client.gui;

/**
 * A static class containing various gui constants
 * @author Maarten Slenter
 */
public class GuiConstants
{
	/**
	 * A value with which the position of each grid square will be multiplied.
	 * By doing this, a small gap will appear between each grid square, thus supplying the player with a simple grid.
	 */
	public final static float offsetFactor = 1.05f;

	/**
	 * An extra offset for the lines between the 5th and 6th (, 10th and 11th, etc.) grid square.
	 * This makes sure the line is in the middle of the two grid squares.
	 */
	public final static float lineOffset = -(offsetFactor - 1) / 2;

	/**
	 * The font used by nifty gui.
	 */
	public final static String font = "aurulent-sans-16.fnt";
}
