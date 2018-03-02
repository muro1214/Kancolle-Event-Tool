package kancolle.gui.panel;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import kancolle.fleet.Fleets;
import kancolle.structure.ShipType;

public class NormalFleetPanel extends FleetPanel {

    private JPanel panel;
    private JLabel label_1;
    private JComboBox<String> comboBox_Type_1;
    private JComboBox<String> comboBox_Name_1;
    private JLabel label_2;
    private JComboBox<String> comboBox_Type_2;
    private JComboBox<String> comboBox_Name_2;
    private JLabel label_3;
    private JComboBox<String> comboBox_Type_3;
    private JComboBox<String> comboBox_Name_3;
    private JLabel label_4;
    private JComboBox<String> comboBox_Type_4;
    private JComboBox<String> comboBox_Name_4;
    private JLabel label_5;
    private JComboBox<String> comboBox_Type_5;
    private JComboBox<String> comboBox_Name_5;
    private JLabel label_6;
    private JComboBox<String> comboBox_Type_6;
    private JComboBox<String> comboBox_Name_6;

    //memo        panel.setBounds(12, 10, 304, 206);

    @Override
    public JPanel addPanel(final String title, final Point point) {
        this.panel = new JPanel();
        this.panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), title, TitledBorder.LEADING,
                TitledBorder.TOP, null, new Color(0, 0, 0)));
        this.panel.setBounds(point.x, point.y, 304, 206);
        this.panel.setLayout(null);

        this.label_1 = new JLabel("1");
        this.label_1.setBounds(12, 24, 13, 13);
        this.panel.add(this.label_1);

        this.comboBox_Type_1 = new JComboBox<>();
        this.comboBox_Type_1.setBounds(26, 21, 100, 19);
        this.comboBox_Type_1.setModel(new DefaultComboBoxModel<>(new String[] { "艦種" }));
        this.comboBox_Type_1.addActionListener(this);
        this.panel.add(this.comboBox_Type_1);

        this.comboBox_Name_1 = new JComboBox<>();
        this.comboBox_Name_1.setBounds(138, 21, 154, 19);
        this.comboBox_Name_1.setModel(new DefaultComboBoxModel<>(new String[] { "艦娘" }));
        this.panel.add(this.comboBox_Name_1);

        this.label_2 = new JLabel("2");
        this.label_2.setBounds(12, 55, 13, 13);
        this.panel.add(this.label_2);

        this.comboBox_Type_2 = new JComboBox<>();
        this.comboBox_Type_2.setBounds(26, 52, 100, 19);
        this.comboBox_Type_2.setModel(new DefaultComboBoxModel<>(new String[] { "艦種" }));
        this.comboBox_Type_2.addActionListener(this);
        this.panel.add(this.comboBox_Type_2);

        this.comboBox_Name_2 = new JComboBox<>();
        this.comboBox_Name_2.setBounds(138, 52, 154, 19);
        this.comboBox_Name_2.setModel(new DefaultComboBoxModel<>(new String[] { "艦娘" }));
        this.panel.add(this.comboBox_Name_2);

        this.label_3 = new JLabel("3");
        this.label_3.setBounds(12, 86, 13, 13);
        this.panel.add(this.label_3);

        this.comboBox_Type_3 = new JComboBox<>();
        this.comboBox_Type_3.setBounds(26, 83, 100, 19);
        this.comboBox_Type_3.setModel(new DefaultComboBoxModel<>(new String[] { "艦種" }));
        this.comboBox_Type_3.addActionListener(this);
        this.panel.add(this.comboBox_Type_3);

        this.comboBox_Name_3 = new JComboBox<>();
        this.comboBox_Name_3.setBounds(138, 83, 154, 19);
        this.comboBox_Name_3.setModel(new DefaultComboBoxModel<>(new String[] { "艦娘" }));
        this.panel.add(this.comboBox_Name_3);

        this.label_4 = new JLabel("4");
        this.label_4.setBounds(12, 117, 13, 13);
        this.panel.add(this.label_4);

        this.comboBox_Type_4 = new JComboBox<>();
        this.comboBox_Type_4.setBounds(26, 114, 100, 19);
        this.comboBox_Type_4.setModel(new DefaultComboBoxModel<>(new String[] { "艦種" }));
        this.comboBox_Type_4.addActionListener(this);
        this.panel.add(this.comboBox_Type_4);

        this.comboBox_Name_4 = new JComboBox<>();
        this.comboBox_Name_4.setBounds(138, 114, 154, 19);
        this.comboBox_Name_4.setModel(new DefaultComboBoxModel<>(new String[] { "艦娘" }));
        this.panel.add(this.comboBox_Name_4);

        this.label_5 = new JLabel("5");
        this.label_5.setBounds(12, 148, 13, 13);
        this.panel.add(this.label_5);

        this.comboBox_Type_5 = new JComboBox<>();
        this.comboBox_Type_5.setBounds(26, 145, 100, 19);
        this.comboBox_Type_5.setModel(new DefaultComboBoxModel<>(new String[] { "艦種" }));
        this.comboBox_Type_5.addActionListener(this);
        this.panel.add(this.comboBox_Type_5);

        this.comboBox_Name_5 = new JComboBox<>();
        this.comboBox_Name_5.setBounds(138, 145, 154, 19);
        this.comboBox_Name_5.setModel(new DefaultComboBoxModel<>(new String[] { "艦娘" }));
        this.panel.add(this.comboBox_Name_5);

        this.label_6 = new JLabel("6");
        this.label_6.setBounds(12, 179, 13, 13);
        this.panel.add(this.label_6);

        this.comboBox_Type_6 = new JComboBox<>();
        this.comboBox_Type_6.setBounds(26, 176, 100, 19);
        this.comboBox_Type_6.setModel(new DefaultComboBoxModel<>(new String[] { "艦種" }));
        this.comboBox_Type_6.addActionListener(this);
        this.panel.add(this.comboBox_Type_6);

        this.comboBox_Name_6 = new JComboBox<>();
        this.comboBox_Name_6.setBounds(138, 176, 154, 19);
        this.comboBox_Name_6.setModel(new DefaultComboBoxModel<>(new String[] { "艦娘" }));
        this.panel.add(this.comboBox_Name_6);

        Logger.getGlobal().info("Added NormalFleetPanel.");

        return this.panel;
    }

    private void setComboBoxNameList(int fleetNo, List<String> kanmusuList) {
        JComboBox<String> targetComboBox = null;

        switch (fleetNo) {
        case 1:
            targetComboBox = this.comboBox_Name_1;
            break;
        case 2:
            targetComboBox = this.comboBox_Name_2;
            break;
        case 3:
            targetComboBox = this.comboBox_Name_3;
            break;
        case 4:
            targetComboBox = this.comboBox_Name_4;
            break;
        case 5:
            targetComboBox = this.comboBox_Name_5;
            break;
        case 6:
            targetComboBox = this.comboBox_Name_6;
            break;
        }

        targetComboBox.setModel(new DefaultComboBoxModel<>(kanmusuList.toArray(new String[0])));
    }

    private void comboBoxTypeAction(final JComboBox<?> comboBox) {
        ShipType shipType = ShipType.getType(comboBox.getSelectedItem().toString());
        if (Objects.isNull(shipType)) {
            return;
        }

        List<String> kanmusuList = Fleets.filterKanmusuList(shipType, EventPanel.isFastOnly(),
                EventPanel.getLevelFilter(), EventPanel.getTag());

        int fleetNo = 0;
        if (comboBox == this.comboBox_Type_1) {
            fleetNo = 1;
        } else if (comboBox == this.comboBox_Type_2) {
            fleetNo = 2;
        } else if (comboBox == this.comboBox_Type_3) {
            fleetNo = 3;
        } else if (comboBox == this.comboBox_Type_4) {
            fleetNo = 4;
        } else if (comboBox == this.comboBox_Type_5) {
            fleetNo = 5;
        } else if (comboBox == this.comboBox_Type_6) {
            fleetNo = 6;
        }

        setComboBoxNameList(fleetNo, kanmusuList);
    }

    @Override
    public void setComboBoxType(final List<ShipType> shipTypes) {
        List<String> shipTypeStrs = shipTypes.stream()
                .map(ShipType::typeName).collect(Collectors.toList());
        shipTypeStrs.add(0, "艦種");

        this.comboBox_Type_1.setModel(new DefaultComboBoxModel<>(shipTypeStrs.toArray(new String[0])));
        this.comboBox_Type_2.setModel(new DefaultComboBoxModel<>(shipTypeStrs.toArray(new String[0])));
        this.comboBox_Type_3.setModel(new DefaultComboBoxModel<>(shipTypeStrs.toArray(new String[0])));
        this.comboBox_Type_4.setModel(new DefaultComboBoxModel<>(shipTypeStrs.toArray(new String[0])));
        this.comboBox_Type_5.setModel(new DefaultComboBoxModel<>(shipTypeStrs.toArray(new String[0])));
        this.comboBox_Type_6.setModel(new DefaultComboBoxModel<>(shipTypeStrs.toArray(new String[0])));
    }

    @Override
    public List<String> getFleetKanmusus() {
        return Arrays.asList(this.comboBox_Name_1.getSelectedItem().toString(),
                this.comboBox_Name_2.getSelectedItem().toString(),
                this.comboBox_Name_3.getSelectedItem().toString(),
                this.comboBox_Name_4.getSelectedItem().toString(),
                this.comboBox_Name_5.getSelectedItem().toString(),
                this.comboBox_Name_6.getSelectedItem().toString());
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        Object object = e.getSource();

        if (object == this.comboBox_Type_1 || object == this.comboBox_Type_2 || object == this.comboBox_Type_3 ||
                object == this.comboBox_Type_4 || object == this.comboBox_Type_5 || object == this.comboBox_Type_6) {
            comboBoxTypeAction((JComboBox<?>) object);
        }
    }

    @Override
    public void setComboBoxTypeValue(final int no, final String value) {
        int realNo = no > 6 ? no - 6 : no;

        if (realNo == 1) {
            this.comboBox_Type_1.setSelectedItem(value);
        } else if (realNo == 2) {
            this.comboBox_Type_2.setSelectedItem(value);
        } else if (realNo == 3) {
            this.comboBox_Type_3.setSelectedItem(value);
        } else if (realNo == 4) {
            this.comboBox_Type_4.setSelectedItem(value);
        } else if (realNo == 5) {
            this.comboBox_Type_5.setSelectedItem(value);
        } else if (realNo == 6) {
            this.comboBox_Type_6.setSelectedItem(value);
        }
    }

    @Override
    public void setComboBoxNameValue(final int no, final String value) {
        int realNo = no > 6 ? no - 6 : no;

        if (realNo == 1) {
            this.comboBox_Name_1.setSelectedItem(value);
        } else if (realNo == 2) {
            this.comboBox_Name_2.setSelectedItem(value);
        } else if (realNo == 3) {
            this.comboBox_Name_3.setSelectedItem(value);
        } else if (realNo == 4) {
            this.comboBox_Name_4.setSelectedItem(value);
        } else if (realNo == 5) {
            this.comboBox_Name_5.setSelectedItem(value);
        } else if (realNo == 6) {
            this.comboBox_Name_6.setSelectedItem(value);
        }
    }
}
