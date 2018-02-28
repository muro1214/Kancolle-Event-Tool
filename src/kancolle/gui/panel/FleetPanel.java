package kancolle.gui.panel;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import kancolle.structure.ShipType;

public abstract class FleetPanel extends JPanel implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = -6385115609685644561L;

    public abstract JPanel addPanel(final String title, final Point point);

    public abstract void setComboBoxType(final List<ShipType> shipTypes);

    public abstract List<String> getFleetKanmusus();

    protected abstract void setComboBoxNameList(final int fleetNo, final List<String> kanmusuList);

    protected abstract void comboBoxTypeAction(final JComboBox<?> comboBox);
}
