package kancolle.gui.panel;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.w3c.dom.Element;

import kancolle.fleet.CombinedFleets;
import kancolle.fleet.Fleets;
import kancolle.gui.MainLoader;
import kancolle.gui.xml.EventBuilder;
import kancolle.gui.xml.EventLoader;
import kancolle.structure.FleetType;
import kancolle.structure.Kanmusu;
import kancolle.structure.ShipType;

public class EventPanel implements ActionListener {

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
    private JButton button_2;

    private JPanel panel_fleet1 = null;
    private JPanel panel_fleet2 = null;
    private boolean isBeforeCombinedFleet = false;

    private NormalFleetPanel normalFleetPanel1;
    private NormalFleetPanel normalFleetPanel2;
    private StrikingFleetPanel strikingFleetPanel;
    private Object beforeObject;

    //memo  panel.remove();

    public JPanel addPanel() {
        this.panel = new JPanel();
        this.panel.setLayout(null);

        this.panel_2 = new JPanel();
        this.panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "艦隊", TitledBorder.LEADING,
                TitledBorder.TOP, null, new Color(0, 0, 0)));
        this.panel_2.setBounds(12, 10, 423, 40);
        this.panel.add(this.panel_2);
        this.panel_2.setLayout(null);

        this.radioButton = new JRadioButton(FleetType.NORMAL.typeName());
        this.buttonGroup.add(this.radioButton);
        this.radioButton.setSelected(true);
        this.radioButton.setBounds(8, 13, 74, 21);
        this.radioButton.addActionListener(this);
        this.panel_2.add(this.radioButton);
        this.beforeObject = this.radioButton;

        this.radioButton_1 = new JRadioButton(FleetType.STRIKING_FORCE.typeName());
        this.buttonGroup.add(this.radioButton_1);
        this.radioButton_1.setBounds(86, 13, 74, 21);
        this.radioButton_1.addActionListener(this);
        this.panel_2.add(this.radioButton_1);

        this.radioButton_2 = new JRadioButton(FleetType.CARRIER_TASK_FORCE.typeName());
        this.buttonGroup.add(this.radioButton_2);
        this.radioButton_2.setBounds(164, 13, 74, 21);
        this.radioButton_2.addActionListener(this);
        this.panel_2.add(this.radioButton_2);

        this.radioButton_3 = new JRadioButton(FleetType.SURFACE_TASK_FORCE.typeName());
        this.buttonGroup.add(this.radioButton_3);
        this.radioButton_3.setBounds(242, 13, 74, 21);
        this.radioButton_3.addActionListener(this);
        this.panel_2.add(this.radioButton_3);

        this.radioButton_4 = new JRadioButton(FleetType.TRANSPORT_ESCORT.typeName());
        this.buttonGroup.add(this.radioButton_4);
        this.radioButton_4.setBounds(320, 13, 74, 21);
        this.radioButton_4.addActionListener(this);
        this.panel_2.add(this.radioButton_4);

        NormalFleetPanel normalFleetPanel = new NormalFleetPanel();
        this.panel_fleet1 = normalFleetPanel.addPanel("第一部隊", new Point(12, 60));
        normalFleetPanel.setComboBoxType(Arrays.asList(ShipType.values()));
        this.panel.add(this.panel_fleet1);

        this.panel_3 = new JPanel();
        this.panel_3.setBorder(new TitledBorder(null, "札情報", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        this.panel_3.setBounds(447, 10, 171, 47);
        this.panel.add(this.panel_3);
        this.panel_3.setLayout(null);

        textField_tag = new JTextField();
        textField_tag.setBounds(12, 18, 147, 19);
        this.panel_3.add(textField_tag);
        textField_tag.setColumns(10);

        this.panel_4 = new JPanel();
        this.panel_4.setBounds(644, 10, 142, 82);
        this.panel.add(this.panel_4);
        this.panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "フィルター",
                TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        this.panel_4.setLayout(null);

        this.label_1 = new JLabel("レベル");
        this.label_1.setBounds(12, 26, 36, 13);
        this.panel_4.add(this.label_1);

        textField_levelFilter = new JTextField("1");
        textField_levelFilter.setHorizontalAlignment(SwingConstants.CENTER);
        textField_levelFilter.setBounds(44, 23, 36, 19);
        this.panel_4.add(textField_levelFilter);
        textField_levelFilter.setColumns(10);

        this.label_2 = new JLabel("以上のみ");
        this.label_2.setBounds(84, 26, 50, 13);
        this.panel_4.add(this.label_2);

        checkBox_fastOnly = new JCheckBox("高速統一");
        checkBox_fastOnly.setBounds(8, 54, 103, 21);
        this.panel_4.add(checkBox_fastOnly);

        this.button = new JButton("保存");
        this.button.setBounds(675, 108, 91, 21);
        this.button.addActionListener(this);
        this.panel.add(this.button);

        this.button_2 = new JButton("読み込み");
        this.button_2.setBounds(675, 136, 91, 21);
        this.button_2.addActionListener(this);
        this.panel.add(this.button_2);

        return this.panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();

        if (object == this.radioButton) {
            if (Objects.equals(object, this.beforeObject)) {
                return;
            }

            loadNormalPanel();
        } else if (object == this.radioButton_1) {
            if (Objects.equals(object, this.beforeObject)) {
                return;
            }

            loadStrinkingPanel();
        } else if (object == this.radioButton_2 || object == this.radioButton_3 || object == this.radioButton_4) {
            if (Objects.equals(object, this.beforeObject)) {
                return;
            }
            if (this.isBeforeCombinedFleet) {
                Logger.getGlobal().info("艦隊情報を変更 -> " + getCurrentFleetType().typeName());
                return;
            }

            loadCombinedPanel();
        } else if (object == this.button) {
            if(this.radioButton_2.isSelected() || this.radioButton_3.isSelected() || this.radioButton_4.isSelected()){
                if(!CombinedFleets.isConditionOK1(getCurrentFleetType(), this.normalFleetPanel1.getKanmusuTypes()) ||
                        !CombinedFleets.isConditionOK2(getCurrentFleetType(), this.normalFleetPanel2.getKanmusuTypes())){
                    Logger.getGlobal().warning("連合艦隊の構築に失敗");
                    JOptionPane.showMessageDialog(MainLoader.getFrame(), "現在の艦種では連合艦隊を組めません",
                            "艦種エラー", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            setKanmusuTag();
            saveXmlFile();
        } else if (object == this.button_2) {
            loadXmlFile();
        }

        this.beforeObject = object;
    }

    private void removeFleetPanels() {
        if (!Objects.isNull(this.panel_fleet1)) {
            this.panel.remove(this.panel_fleet1);
            this.panel_fleet1 = null;
        }
        if (!Objects.isNull(this.panel_fleet2)) {
            this.panel.remove(this.panel_fleet2);
            this.panel_fleet2 = null;
        }
    }

    private void loadNormalPanel() {
        removeFleetPanels();

        this.normalFleetPanel1 = new NormalFleetPanel();
        this.panel_fleet1 = this.normalFleetPanel1.addPanel("第一部隊", new Point(12, 60));
        this.normalFleetPanel1.setComboBoxType(Arrays.asList(ShipType.values()));
        this.panel.add(this.panel_fleet1);

        this.panel.revalidate();
        this.panel.repaint();

        this.isBeforeCombinedFleet = false;
        Logger.getGlobal().info("艦隊情報を変更 -> 通常艦隊");
    }

    private void loadStrinkingPanel() {
        removeFleetPanels();

        this.strikingFleetPanel = new StrikingFleetPanel();
        this.panel_fleet1 = this.strikingFleetPanel.addPanel("第一部隊", new Point(12, 60));
        this.strikingFleetPanel.setComboBoxType(Arrays.asList(ShipType.values()));
        this.panel.add(this.panel_fleet1);

        this.panel.revalidate();
        this.panel.repaint();

        this.isBeforeCombinedFleet = false;
        Logger.getGlobal().info("艦隊情報を変更 -> 遊撃艦隊");
    }

    private void loadCombinedPanel() {
        removeFleetPanels();

        this.normalFleetPanel1 = new NormalFleetPanel();
        this.panel_fleet1 = this.normalFleetPanel1.addPanel("第一部隊", new Point(12, 60));
        this.normalFleetPanel1.setComboBoxType(Arrays.asList(ShipType.values()));
        this.panel.add(this.panel_fleet1);

        this.normalFleetPanel2 = new NormalFleetPanel();
        this.panel_fleet2 = this.normalFleetPanel2.addPanel("第二部隊", new Point(328, 60));
        this.normalFleetPanel2.setComboBoxType(Arrays.asList(ShipType.values()));
        this.panel.add(this.panel_fleet2);

        this.panel.revalidate();
        this.panel.repaint();

        this.isBeforeCombinedFleet = true;
        Logger.getGlobal().info("艦隊情報を変更 -> " + getCurrentFleetType().typeName());
    }

    public static boolean isFastOnly() {
        return checkBox_fastOnly.isSelected();
    }

    public static int getLevelFilter() {
        String text = textField_levelFilter.getText();
        try {
            int tmp = Integer.parseInt(text);
            if (tmp < 1 || tmp > 165) {
                Logger.getGlobal().warning("wrong level filter");
                return 1;
            }
            return tmp;
        } catch (NumberFormatException e) {
            Logger.getGlobal().warning("wrong level filter");
            Logger.getGlobal().warning(e.toString());
            return 1;
        }
    }

    public static String getTag() {
        return textField_tag.getText();
    }

    private FleetType getCurrentFleetType() {
        if (this.radioButton.isSelected()) {
            return FleetType.NORMAL;
        } else if (this.radioButton_1.isSelected()) {
            return FleetType.STRIKING_FORCE;
        } else if (this.radioButton_2.isSelected()) {
            return FleetType.CARRIER_TASK_FORCE;
        } else if (this.radioButton_3.isSelected()) {
            return FleetType.SURFACE_TASK_FORCE;
        } else {
            return FleetType.TRANSPORT_ESCORT;
        }
    }

    private void changeRadioButton(final FleetType fleetType) {
        if (fleetType == FleetType.NORMAL) {
            this.radioButton.setSelected(true);
        } else if (fleetType == FleetType.STRIKING_FORCE) {
            this.radioButton_1.setSelected(true);
        } else if (fleetType == FleetType.CARRIER_TASK_FORCE) {
            this.radioButton_2.setSelected(true);
        } else if (fleetType == FleetType.SURFACE_TASK_FORCE) {
            this.radioButton_3.setSelected(true);
        } else if (fleetType == FleetType.TRANSPORT_ESCORT) {
            this.radioButton_4.setSelected(true);
        }
    }

    private List<String> getAllFleetKanmusus() {
        List<String> kanmusus = new ArrayList<>();

        if (this.radioButton.isSelected()) {
            kanmusus.addAll(this.normalFleetPanel1.getFleetKanmusus());
        } else if (this.radioButton_1.isSelected()) {
            kanmusus.addAll(this.strikingFleetPanel.getFleetKanmusus());
        } else {
            kanmusus.addAll(this.normalFleetPanel1.getFleetKanmusus());
            kanmusus.addAll(this.normalFleetPanel2.getFleetKanmusus());
        }

        return kanmusus;
    }

    private static int getKanmusuId(String kanmusu) {
        return Integer.parseInt(kanmusu.split(", ")[1].replaceAll("ID#|\\)", ""));
    }

    private void setKanmusuTag() {
        List<String> kanmusus = getAllFleetKanmusus();
        String tag = getTag();

        for (String kanmusu : kanmusus) {
            if (kanmusu.equals("艦娘")) {
                continue;
            }
            int id = getKanmusuId(kanmusu);
            Fleets.setTag(id, tag);
        }
    }

    private void saveXmlFile() {
        EventBuilder eventBuilder = new EventBuilder();

        String tabName = MainLoader.getCurrentTabName();
        Element eventArea = eventBuilder.setEventAreaInfo(MainLoader.getCurrentTabNo(), tabName, getTag(),
                checkBox_fastOnly.isSelected());
        eventArea.appendChild(eventBuilder.buildFilterElement(textField_levelFilter.getText()));

        Element fleet = eventBuilder.buildFleetElement(getCurrentFleetType());

        List<String> kanmusus = getAllFleetKanmusus();
        for (int i = 0; i < kanmusus.size(); i++) {
            if (kanmusus.get(i).equals("艦娘")) {
                continue;
            }
            Kanmusu kanmusu = Fleets.getKanmusuFromId(getKanmusuId(kanmusus.get(i)));
            fleet.appendChild(eventBuilder.buildKanmusuElement(i + 1, kanmusu));
        }

        eventArea.appendChild(fleet);

        Path path = Paths.get(String.format("xml\\%s.xml", tabName));
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            Logger.getGlobal().warning(e.toString());
        }

        eventBuilder.write(path);
        Logger.getGlobal().info("save xml file : " + path.toAbsolutePath().toString());
        JOptionPane.showMessageDialog(MainLoader.getFrame(), "情報を保存しました。\n -> " +
                path.toAbsolutePath().toString());
    }

    private void loadXmlFile() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("xml file(*.xml)", "xml");
        fc.setFileFilter(filter);
        int selected = fc.showOpenDialog(this.panel);
        if (selected != JFileChooser.APPROVE_OPTION) {
            return;
        }

        EventLoader eventLoader = new EventLoader(fc.getSelectedFile().getAbsolutePath());

        MainLoader.changeTabName(eventLoader.getEventName());
        EventPanel.textField_levelFilter.setText(eventLoader.getFilterLevel());
        EventPanel.textField_tag.setText(eventLoader.getTag());
        EventPanel.checkBox_fastOnly.setSelected(eventLoader.isFastOnly());

        FleetType fleetType = eventLoader.getFleetType();
        changeRadioButton(fleetType);

        int maxKanmusu = 6;
        if (fleetType == FleetType.NORMAL) {
            loadNormalPanel();
        } else if (fleetType == FleetType.STRIKING_FORCE) {
            loadStrinkingPanel();
            maxKanmusu = 7;
        } else {
            loadCombinedPanel();
            maxKanmusu = 12;
        }

        for (int i = 1; i <= maxKanmusu; i++) {
            Kanmusu kanmusu = eventLoader.getKanmusu(i);

            if (fleetType == FleetType.NORMAL) { // 通常艦隊
                this.normalFleetPanel1.setComboBoxTypeValue(i, kanmusu.shipTypeString());
                this.normalFleetPanel1.setComboBoxNameValue(i, kanmusu.toString());
                continue;
            }
            if (fleetType == FleetType.STRIKING_FORCE) { // 遊撃艦隊
                this.strikingFleetPanel.setComboBoxTypeValue(i, kanmusu.shipTypeString());
                this.strikingFleetPanel.setComboBoxNameValue(i, kanmusu.toString());
                continue;
            }
            if (i <= 6) { // 連合第一
                this.normalFleetPanel1.setComboBoxTypeValue(i, kanmusu.shipTypeString());
                this.normalFleetPanel1.setComboBoxNameValue(i, kanmusu.toString());
                continue;
            }
            // 連合第二
            this.normalFleetPanel2.setComboBoxTypeValue(i, kanmusu.shipTypeString());
            this.normalFleetPanel2.setComboBoxNameValue(i, kanmusu.toString());
        }

        setKanmusuTag();

        Logger.getGlobal().info("load xml file : " + fc.getSelectedFile().getAbsolutePath());
        JOptionPane.showMessageDialog(MainLoader.getFrame(), "ファイルから情報を読み込みました。\n -> " +
                fc.getSelectedFile().getAbsolutePath());
    }
}
