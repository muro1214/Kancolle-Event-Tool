package kancolle.gui.panel;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import kancolle.fleet.FleetType;
import kancolle.fleet.Fleets;
import kancolle.structure.ShipType;

public class EventPanel extends JPanel implements ActionListener {

    private JPanel panel;
    private JPanel panel_2;
    private JPanel panel_3;
    private JPanel panel_4;
    private JLabel label_1;
    private JLabel label_2;
    private JRadioButton radioButton;
    private JRadioButton radioButton_1;
    private JRadioButton radioButton_2;
    private JRadioButton radioButton_3;
    private JRadioButton radioButton_4;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private static JTextField textField_tag;
    private static JTextField textField_levelFilter;
    private static JCheckBox checkBox_fastOnly;
    private JButton button;

    private JPanel panel_fleet1 = null;
    private JPanel panel_fleet2 = null;
    private boolean isBeforeCombinedFleet = false;

    private NormalFleetPanel normalFleetPanel1;
    private NormalFleetPanel normalFleetPanel2;
    private StrikingFleetPanel strikingFleetPanel;

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
        normalFleetPanel.setComboBoxType(Arrays.asList(ShipType.values()));
        panel.add(panel_fleet1);

        panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(null, "札情報", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_3.setBounds(447, 10, 171, 47);
        panel.add(panel_3);
        panel_3.setLayout(null);

        textField_tag = new JTextField();
        textField_tag.setBounds(12, 18, 147, 19);
        panel_3.add(textField_tag);
        textField_tag.setColumns(10);

        panel_4 = new JPanel();
        panel_4.setBounds(644, 10, 142, 82);
        panel.add(panel_4);
        panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "フィルター",
                TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        panel_4.setLayout(null);

        label_1 = new JLabel("レベル");
        label_1.setBounds(12, 26, 36, 13);
        panel_4.add(label_1);

        textField_levelFilter = new JTextField("1");
        textField_levelFilter.setHorizontalAlignment(SwingConstants.CENTER);
        textField_levelFilter.setBounds(44, 23, 36, 19);
        panel_4.add(textField_levelFilter);
        textField_levelFilter.setColumns(10);

        label_2 = new JLabel("以上のみ");
        label_2.setBounds(84, 26, 50, 13);
        panel_4.add(label_2);

        checkBox_fastOnly = new JCheckBox("高速統一");
        checkBox_fastOnly.setBounds(8, 54, 103, 21);
        panel_4.add(checkBox_fastOnly);

        button = new JButton("適用");
        button.setBounds(675, 108, 91, 21);
        button.addActionListener(this);
        panel.add(button);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();

        if (object == radioButton && radioButton.isSelected()) {
            removeFleetPanels();

            normalFleetPanel1 = new NormalFleetPanel();
            panel_fleet1 = normalFleetPanel1.addPanel("第一部隊", new Point(12, 60));
            normalFleetPanel1.setComboBoxType(Arrays.asList(ShipType.values()));
            panel.add(panel_fleet1);

            panel.revalidate();
            panel.repaint();

            isBeforeCombinedFleet = false;
            Logger.getGlobal().info("艦隊情報を変更 -> 通常艦隊");
        } else if (object == radioButton_1 && radioButton_1.isSelected()) {
            removeFleetPanels();

            strikingFleetPanel = new StrikingFleetPanel();
            panel_fleet1 = strikingFleetPanel.addPanel("第一部隊", new Point(12, 60));
            strikingFleetPanel.setComboBoxType(Arrays.asList(ShipType.values()));
            panel.add(panel_fleet1);

            panel.revalidate();
            panel.repaint();

            isBeforeCombinedFleet = false;
            Logger.getGlobal().info("艦隊情報を変更 -> 遊撃艦隊");
        } else if ((object == radioButton_2 || object == radioButton_3 || object == radioButton_4) &&
                (radioButton_2.isSelected() || radioButton_3.isSelected() || radioButton_4.isSelected())) {
            if (isBeforeCombinedFleet) {
                return;
            }

            removeFleetPanels();

            normalFleetPanel1 = new NormalFleetPanel();
            panel_fleet1 = normalFleetPanel1.addPanel("第一部隊", new Point(12, 60));
            normalFleetPanel1.setComboBoxType(Arrays.asList(ShipType.values()));
            panel.add(panel_fleet1);

            normalFleetPanel2 = new NormalFleetPanel();
            panel_fleet2 = normalFleetPanel2.addPanel("第二部隊", new Point(328, 60));
            normalFleetPanel2.setComboBoxType(Arrays.asList(ShipType.values()));
            panel.add(panel_fleet2);

            panel.revalidate();
            panel.repaint();

            isBeforeCombinedFleet = true;
            Logger.getGlobal().info("艦隊情報を変更 -> 連合艦隊");
        }else if(object == button){
            setKanmusuTag();
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

    public static boolean isFastOnly(){
        return checkBox_fastOnly.isSelected();
    }

    public static int getLevelFilter(){
        String text = textField_levelFilter.getText();
        try{
            int tmp = Integer.parseInt(text);
            if(tmp < 1 || tmp > 165){
                Logger.getGlobal().warning("wrong level filter");
                return 1;
            }else{
                return tmp;
            }
        }catch(NumberFormatException e){
            Logger.getGlobal().warning("wrong level filter");
            return 1;
        }
    }

    public static String getTag(){
        return textField_tag.getText();
    }

    public void setKanmusuTag(){
        List<String> kanmusus = new ArrayList<String>();
        String tag = getTag();

        if(radioButton.isSelected()){
            kanmusus.addAll(normalFleetPanel1.getFleetKanmusus());
        }else if(radioButton_1.isSelected()){
            kanmusus.addAll(strikingFleetPanel.getFleetKanmusus());
        }else{
            kanmusus.addAll(normalFleetPanel1.getFleetKanmusus());
            kanmusus.addAll(normalFleetPanel2.getFleetKanmusus());
        }

        for(String kanmusu : kanmusus){
            if(kanmusu.equals("艦娘")){
                continue;
            }
            int id = Integer.parseInt(kanmusu.split(", ")[1].replaceAll("ID#|\\)", ""));
            Fleets.setTag(id, tag);
        }
    }
}
