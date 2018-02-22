package kancolle.gui.panel;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
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
import kancolle.structure.Kanmusu;
import kancolle.structure.ShipType;

public class StrikingFleetPanel extends FleetPanel {

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
    private JLabel label_7;
    private JComboBox<String> comboBox_Type_7;
    private JComboBox<String> comboBox_Name_7;

    //memo        panel.setBounds(12, 10, 304, 237);

    @Override
    public JPanel addPanel(final String title, final Point point) {
        panel = new JPanel();
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), title, TitledBorder.LEADING,
                TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel.setBounds(point.x, point.y, 304, 237);
        panel.setLayout(null);

        label_1 = new JLabel("1");
        label_1.setBounds(12, 24, 13, 13);
        panel.add(label_1);

        comboBox_Type_1 = new JComboBox<String>();
        comboBox_Type_1.setBounds(26, 21, 127, 19);
        comboBox_Type_1.setModel(new DefaultComboBoxModel<String>(new String[] { "艦種" }));
        comboBox_Type_1.addActionListener(this);
        panel.add(comboBox_Type_1);

        comboBox_Name_1 = new JComboBox<String>();
        comboBox_Name_1.setBounds(165, 21, 127, 19);
        comboBox_Name_1.setModel(new DefaultComboBoxModel<String>(new String[] { "艦娘" }));
        panel.add(comboBox_Name_1);

        label_2 = new JLabel("2");
        label_2.setBounds(12, 55, 13, 13);
        panel.add(label_2);

        comboBox_Type_2 = new JComboBox<String>();
        comboBox_Type_2.setBounds(26, 52, 127, 19);
        comboBox_Type_2.setModel(new DefaultComboBoxModel<String>(new String[] { "艦種" }));
        comboBox_Type_2.addActionListener(this);
        panel.add(comboBox_Type_2);

        comboBox_Name_2 = new JComboBox<String>();
        comboBox_Name_2.setBounds(165, 52, 127, 19);
        comboBox_Name_2.setModel(new DefaultComboBoxModel<String>(new String[] { "艦娘" }));
        panel.add(comboBox_Name_2);

        label_3 = new JLabel("3");
        label_3.setBounds(12, 86, 13, 13);
        panel.add(label_3);

        comboBox_Type_3 = new JComboBox<String>();
        comboBox_Type_3.setBounds(26, 83, 127, 19);
        comboBox_Type_3.setModel(new DefaultComboBoxModel<String>(new String[] { "艦種" }));
        comboBox_Type_3.addActionListener(this);
        panel.add(comboBox_Type_3);

        comboBox_Name_3 = new JComboBox<String>();
        comboBox_Name_3.setBounds(165, 83, 127, 19);
        comboBox_Name_3.setModel(new DefaultComboBoxModel<String>(new String[] { "艦娘" }));
        panel.add(comboBox_Name_3);

        label_4 = new JLabel("4");
        label_4.setBounds(12, 117, 13, 13);
        panel.add(label_4);

        comboBox_Type_4 = new JComboBox<String>();
        comboBox_Type_4.setBounds(26, 114, 127, 19);
        comboBox_Type_4.setModel(new DefaultComboBoxModel<String>(new String[] { "艦種" }));
        comboBox_Type_4.addActionListener(this);
        panel.add(comboBox_Type_4);

        comboBox_Name_4 = new JComboBox<String>();
        comboBox_Name_4.setBounds(165, 114, 127, 19);
        comboBox_Name_4.setModel(new DefaultComboBoxModel<String>(new String[] { "艦娘" }));
        panel.add(comboBox_Name_4);

        label_5 = new JLabel("5");
        label_5.setBounds(12, 148, 13, 13);
        panel.add(label_5);

        comboBox_Type_5 = new JComboBox<String>();
        comboBox_Type_5.setBounds(26, 145, 127, 19);
        comboBox_Type_5.setModel(new DefaultComboBoxModel<String>(new String[] { "艦種" }));
        comboBox_Type_5.addActionListener(this);
        panel.add(comboBox_Type_5);

        comboBox_Name_5 = new JComboBox<String>();
        comboBox_Name_5.setBounds(165, 145, 127, 19);
        comboBox_Name_5.setModel(new DefaultComboBoxModel<String>(new String[] { "艦娘" }));
        panel.add(comboBox_Name_5);

        label_6 = new JLabel("6");
        label_6.setBounds(12, 179, 13, 13);
        panel.add(label_6);

        comboBox_Type_6 = new JComboBox<String>();
        comboBox_Type_6.setBounds(26, 176, 127, 19);
        comboBox_Type_6.setModel(new DefaultComboBoxModel<String>(new String[] { "艦種" }));
        comboBox_Type_6.addActionListener(this);
        panel.add(comboBox_Type_6);

        comboBox_Name_6 = new JComboBox<String>();
        comboBox_Name_6.setBounds(165, 176, 127, 19);
        comboBox_Name_6.setModel(new DefaultComboBoxModel<String>(new String[] { "艦娘" }));
        panel.add(comboBox_Name_6);

        label_7 = new JLabel("7");
        label_7.setBounds(12, 210, 13, 13);
        panel.add(label_7);

        comboBox_Type_7 = new JComboBox<String>();
        comboBox_Type_7.setBounds(26, 207, 127, 19);
        comboBox_Type_7.setModel(new DefaultComboBoxModel<String>(new String[] { "艦種" }));
        comboBox_Type_7.addActionListener(this);
        panel.add(comboBox_Type_7);

        comboBox_Name_7 = new JComboBox<String>();
        comboBox_Name_7.setBounds(165, 207, 127, 19);
        comboBox_Name_7.setModel(new DefaultComboBoxModel<String>(new String[] { "艦娘" }));
        panel.add(comboBox_Name_7);

        Logger.getGlobal().info("Added StrikingFleetPanel.");

        return panel;
    }

    @Override
    protected void setComboBoxNameList(int fleetNo, List<String> kanmusuList) {
        JComboBox<String> targetComboBox = null;

        switch (fleetNo) {
        case 1:
            targetComboBox = comboBox_Name_1;
            break;
        case 2:
            targetComboBox = comboBox_Name_2;
            break;
        case 3:
            targetComboBox = comboBox_Name_3;
            break;
        case 4:
            targetComboBox = comboBox_Name_4;
            break;
        case 5:
            targetComboBox = comboBox_Name_5;
            break;
        case 6:
            targetComboBox = comboBox_Name_6;
            break;
        case 7:
            targetComboBox = comboBox_Name_7;
            break;
        }

        targetComboBox.setModel(new DefaultComboBoxModel<String>((String[]) kanmusuList.toArray(new String[0])));
    }

    @Override
    protected void comboBoxTypeAction(final JComboBox<?> comboBox) {
        ShipType shipType = ShipType.getType(comboBox.getSelectedItem().toString());
        if (Objects.isNull(shipType)) {
            return;
        }

        //TODO: レベルフィルターの実装
        List<String> kanmusuList = Fleets.getKanmusuList().stream()
                .filter(Kanmusu -> Kanmusu.shipType() == shipType)
                .map(Kanmusu::name)
                .collect(Collectors.toList());

        int fleetNo = 0;
        if (comboBox == comboBox_Type_1) {
            fleetNo = 1;
        } else if (comboBox == comboBox_Type_2) {
            fleetNo = 2;
        } else if (comboBox == comboBox_Type_3) {
            fleetNo = 3;
        } else if (comboBox == comboBox_Type_4) {
            fleetNo = 4;
        } else if (comboBox == comboBox_Type_5) {
            fleetNo = 5;
        } else if (comboBox == comboBox_Type_6) {
            fleetNo = 6;
        } else if (comboBox == comboBox_Type_7) {
            fleetNo = 7;
        }

        setComboBoxNameList(fleetNo, kanmusuList);
    }

    @Override
    public void setComboBoxType(final List<ShipType> shipTypes) {
        List<String> shipTypeStrs = shipTypes.stream()
                .map(ShipType::typeName).collect(Collectors.toList());
        shipTypeStrs.add(0, "艦娘");

        comboBox_Name_1.setModel(new DefaultComboBoxModel<String>((String[]) shipTypeStrs.toArray(new String[0])));
        comboBox_Name_2.setModel(new DefaultComboBoxModel<String>((String[]) shipTypeStrs.toArray(new String[0])));
        comboBox_Name_3.setModel(new DefaultComboBoxModel<String>((String[]) shipTypeStrs.toArray(new String[0])));
        comboBox_Name_4.setModel(new DefaultComboBoxModel<String>((String[]) shipTypeStrs.toArray(new String[0])));
        comboBox_Name_5.setModel(new DefaultComboBoxModel<String>((String[]) shipTypeStrs.toArray(new String[0])));
        comboBox_Name_6.setModel(new DefaultComboBoxModel<String>((String[]) shipTypeStrs.toArray(new String[0])));
        comboBox_Name_7.setModel(new DefaultComboBoxModel<String>((String[]) shipTypeStrs.toArray(new String[0])));
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        Object object = e.getSource();

        if (object == comboBox_Type_1 || object == comboBox_Type_2 || object == comboBox_Type_3 ||
                object == comboBox_Type_4 || object == comboBox_Type_5 || object == comboBox_Type_6 ||
                object == comboBox_Type_7) {
            comboBoxTypeAction((JComboBox<?>) object);
        }
    }
}
