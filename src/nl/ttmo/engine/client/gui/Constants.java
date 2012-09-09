package nl.ttmo.engine.client.gui;

/**
 * A static class containing various gui constants
 * @author Maarten Slenter
 */
public class Constants
{
    /**
     * Map
     */
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
     * Camera
     */
        /**
         * The minimum angle (in radians) the camera is allowed to make with the horizontal plane
         */
        public final static float minAngle = 0.15f;

        /**
         * The maximum angle (in radians) the camera is allowed to make with the horizontal plane
         */
        public final static float maxAngle = 1.3f;
    
        /**
         * The lower border which the camera isn't allowed to pass
         */
        public final static float cameraBorder = 5f;
        
    /**
     * Gui
     */
        /**
         * The font used by nifty gui.
         */
        public final static String font = "aurulent-sans-16.fnt";
}
