package nl.ttmo.engine.serverClient.gui.niftygui.listBox;

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
public class ConnectionListBoxViewConverter implements ListBoxViewConverter<TTMOConnectionEntry>
{
	@Override
	public void display(Element listBoxItem, TTMOConnectionEntry connectionEntry)
	{
        final TextRenderer nameRenderer = listBoxItem.findElementByName("#name").getRenderer(TextRenderer.class);
        if (connectionEntry != null) {
            nameRenderer.setText(connectionEntry.getName());
        } else {
			nameRenderer.setText("");
        }
	}

	@Override
	public int getWidth(Element listBoxItem, TTMOConnectionEntry connectionEntry)
	{
        final TextRenderer nameRenderer = listBoxItem.findElementByName("#name").getRenderer(TextRenderer.class);
        return ((nameRenderer.getFont() == null) ? 0 : nameRenderer.getFont().getWidth(connectionEntry.getName()));
	}
}