package kancolle.gui.panel;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import kancolle.fleet.FleetType;

public class EventPanel implements ActionListener {

    private JPanel panel;
    private JPanel panel_2;
    private JRadioButton radioButton;
    private JRadioButton radioButton_1;
    private JRadioButton radioButton_2;
    private JRadioButton radioButton_3;
    private JRadioButton radioButton_4;
    private final ButtonGroup buttonGroup = new ButtonGroup();

    private JPanel panel_fleet1 = null;
    private JPanel panel_fleet2 = null;
    private boolean isBeforeCombinedFleet = false;

    //memo  panel.remove();

    public JPanel addPanel() {
        panel = new JPanel();
        panel.setLayout(null);

        panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "艦隊", TitledBorder.LEADING,
                TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_2.setBounds(12, 10, 423, 40);
        panel.add(panel_2);
        panel_2.setLayout(null);

        radioButton = new JRadioButton(FleetType.NORMAL.typeName());
        buttonGroup.add(radioButton);
        radioButton.setSelected(true);
        radioButton.setBounds(8, 13, 74, 21);
        radioButton.addActionListener(this);
        panel_2.add(radioButton);

        radioButton_1 = new JRadioButton(FleetType.STRIKING_FORCE.typeName());
        buttonGroup.add(radioButton_1);
        radioButton_1.setBounds(86, 13, 74, 21);
        radioButton_1.addActionListener(this);
        panel_2.add(radioButton_1);

        radioButton_2 = new JRadioButton(FleetType.CARRIER_TASK_FORCE.typeName());
        buttonGroup.add(radioButton_2);
        radioButton_2.setBounds(164, 13, 74, 21);
        radioButton_2.addActionListener(this);
        panel_2.add(radioButton_2);

        radioButton_3 = new JRadioButton(FleetType.SURFACE_TASK_FORCE.typeName());
        buttonGroup.add(radioButton_3);
        radioButton_3.setBounds(242, 13, 74, 21);
        radioButton_3.addActionListener(this);
        panel_2.add(radioButton_3);

        radioButton_4 = new JRadioButton(FleetType.TRANSPORT_ESCORT.typeName());
        buttonGroup.add(radioButton_4);
        radioButton_4.setBounds(320, 13, 74, 21);
        radioButton_4.addActionListener(this);
        panel_2.add(radioButton_4);

        NormalFleetPanel normalFleetPanel = new NormalFleetPanel();
        panel_fleet1 = normalFleetPanel.addPanel("第一部隊", new Point(12, 60));
        panel.add(panel_fleet1);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();

        if (object == radioButton && radioButton.isSelected()) {
            removeFleetPanels();

            NormalFleetPanel normalFleetPanel = new NormalFleetPanel();
            panel_fleet1 = normalFleetPanel.addPanel("第一部隊", new Point(12, 60));
            panel.add(panel_fleet1);

            isBeforeCombinedFleet = false;
            Logger.getGlobal().info("艦隊情報を変更 -> 通常艦隊");
        } else if (object == radioButton_1 && radioButton_1.isSelected()) {
            removeFleetPanels();

            StrikingFleetPanel strikingFleetPanel = new StrikingFleetPanel();
            panel_fleet1 = strikingFleetPanel.addPanel("第一部隊", new Point(12, 60));
            panel.add(panel_fleet1);

            isBeforeCombinedFleet = false;
            Logger.getGlobal().info("艦隊情報を変更 -> 遊撃艦隊");
        } else if ((object == radioButton_2 || object == radioButton_3 || object == radioButton_4) &&
                (radioButton_2.isSelected() || radioButton_3.isSelected() || radioButton_4.isSelected())) {
            if (isBeforeCombinedFleet) {
                return;
            }

            removeFleetPanels();

            NormalFleetPanel normalFleetPanel1 = new NormalFleetPanel();
            panel_fleet1 = normalFleetPanel1.addPanel("第一部隊", new Point(12, 60));
            panel.add(panel_fleet1);

            NormalFleetPanel normalFleetPanel2 = new NormalFleetPanel();
            panel_fleet1 = normalFleetPanel2.addPanel("第二部隊", new Point(328, 60));
            panel.add(panel_fleet1);

            isBeforeCombinedFleet = true;
            Logger.getGlobal().info("艦隊情報を変更 -> 連合艦隊");
        }
    }

    private void removeFleetPanels() {
        if (!Objects.isNull(panel_fleet1)) {
            panel.remove(panel_fleet1);
            panel_fleet1 = null;
        }
        if (!Objects.isNull(panel_fleet2)) {
            panel.remove(panel_fleet2);
            panel_fleet2 = null;
        }
    }
}
