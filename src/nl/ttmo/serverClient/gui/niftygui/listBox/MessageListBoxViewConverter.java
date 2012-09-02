package nl.ttmo.serverClient.gui.niftygui.listBox;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import de.lessvoid.nifty.controls.ListBox.ListBoxViewConverter;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;

/**
 *
 * @author Maarten Slenter
 */
public class MessageListBoxViewConverter implements ListBoxViewConverter<TTMOMessageEntry>
{
	@Override
	public void display(Element listBoxItem, TTMOMessageEntry messageEntry)
	{
        final TextRenderer directionRenderer = listBoxItem.findElementByName("#direction").getRenderer(TextRenderer.class);
        final TextRenderer messageNameRenderer = listBoxItem.findElementByName("#messageName").getRenderer(TextRenderer.class);
        if (messageEntry != null) {
            directionRenderer.setText(messageEntry.getDirection());
            messageNameRenderer.setText(messageEntry.getMessageName());
        } else {
            directionRenderer.setText("");
            messageNameRenderer.setText("");
        }
	}

	@Override
	public int getWidth(Element listBoxItem, TTMOMessageEntry resourceEntry)
	{
        final TextRenderer directionRenderer = listBoxItem.findElementByName("#direction").getRenderer(TextRenderer.class);
        final TextRenderer messageNameRenderer = listBoxItem.findElementByName("#messageName").getRenderer(TextRenderer.class);
        return ((directionRenderer.getFont() == null) ? 0 : directionRenderer.getFont().getWidth(resourceEntry.getDirection()))
                + ((messageNameRenderer.getFont() == null) ? 0 : messageNameRenderer.getFont().getWidth(resourceEntry.getMessageName()));
	}
}