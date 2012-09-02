package nl.ttmo.client.gui.niftygui.listBox;

import de.lessvoid.nifty.controls.ListBox.ListBoxViewConverter;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;

/**
 * Class responsible for converting data from the TTMOResourceEntry class to data usable by ListBoxViewConverter
 * @author Maarten Slenter
 */
public class ResourceListBoxViewConverter implements ListBoxViewConverter<TTMOResourceEntry>
{
	/**
	 * Display an element
	 * @param listBoxItem
	 * @param resourceEntry A TTMOResourceEntry, which contains one resource name and the amount the player has
	 */
	@Override
	public void display(Element listBoxItem, TTMOResourceEntry resourceEntry)
	{
        final TextRenderer resourceNameRenderer = listBoxItem.findElementByName("#resourceName").getRenderer(TextRenderer.class);
        final TextRenderer resourceAmountRenderer = listBoxItem.findElementByName("#resourceAmount").getRenderer(TextRenderer.class);
        if (resourceEntry != null) {
            resourceNameRenderer.setText(resourceEntry.getResourceName());
            resourceAmountRenderer.setText(resourceEntry.getResourceAmount());
        } else {
            resourceNameRenderer.setText("");
            resourceAmountRenderer.setText("");
        }
	}

	/**
	 * Calculate the width of this entry
	 * @param listBoxItem
	 * @param resourceEntry A TTMOResourceEntry, which contains one resource name and the amount the player has
	 * @return The width of this entry
	 */
	@Override
	public int getWidth(Element listBoxItem, TTMOResourceEntry resourceEntry)
	{
        final TextRenderer resourceNameRenderer = listBoxItem.findElementByName("#resourceName").getRenderer(TextRenderer.class);
        final TextRenderer resourceAmountRenderer = listBoxItem.findElementByName("#resourceAmount").getRenderer(TextRenderer.class);
        return ((resourceNameRenderer.getFont() == null) ? 0 : resourceNameRenderer.getFont().getWidth(resourceEntry.getResourceName()))
                + ((resourceAmountRenderer.getFont() == null) ? 0 : resourceAmountRenderer.getFont().getWidth(resourceEntry.getResourceAmount()));
	}
}