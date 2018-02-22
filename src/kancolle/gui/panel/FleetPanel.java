package kancolle.gui.panel;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import kancolle.structure.ShipType;

public abstract class FleetPanel implements ActionListener {

    public abstract JPanel addPanel(final String title, final Point point);

    public abstract void setComboBoxType(final List<ShipType> shipTypes);

    protected abstract void setComboBoxNameList(final int fleetNo, final List<String> kanmusuList);

    protected abstract void comboBoxTypeAction(final JComboBox<?> comboBox);
}
