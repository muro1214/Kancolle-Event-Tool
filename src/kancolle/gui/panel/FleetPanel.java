package kancolle.gui.panel;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import kancolle.structure.ShipType;

public abstract class FleetPanel implements ActionListener {

    public abstract JPanel addPanel(final String title, final Point point);

    public abstract void setComboBoxType(final List<ShipType> shipTypes);

    public abstract void setComboBoxTypeValue(final int no, final String value);

    public abstract void setComboBoxNameValue(final int no, final String value);

    public abstract List<String> getFleetKanmusus();
}
