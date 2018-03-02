package kancolle.gui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kancolle.fleet.Fleets;
import kancolle.structure.ShipType;

public class SettingPanel implements ActionListener {

    private JPanel panel;
    private JPanel panel_1;
    private JPanel panel_2;
    private JPanel panel_3;
    private JScrollPane scrollPane;
    private JTextField textField_csvPath;
    private JButton button_openCsv;
    private JTable table;

    private static boolean isCsvOpened = false;

    public JPanel addPanel() {
        this.panel = new JPanel();
        this.panel.setLayout(null);

        this.panel_1 = new JPanel();
        this.panel_1.setBorder(new TitledBorder(null, "艦娘一覧csvファイル", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        this.panel_1.setBounds(12, 10, 769, 59);
        this.panel.add(this.panel_1);
        this.panel_1.setLayout(null);

        this.textField_csvPath = new JTextField();
        this.textField_csvPath.setEditable(false);
        this.textField_csvPath.setBounds(12, 21, 642, 19);
        this.panel_1.add(this.textField_csvPath);
        this.textField_csvPath.setColumns(10);

        this.button_openCsv = new JButton("開く");
        this.button_openCsv.setBounds(666, 20, 91, 21);
        this.button_openCsv.addActionListener(this);
        this.panel_1.add(this.button_openCsv);

        this.panel_2 = new JPanel();
        this.panel_2.setBorder(new TitledBorder(null, "艦隊情報", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        this.panel_2.setBounds(12, 79, 430, 220);
        this.panel.add(this.panel_2);
        this.panel_2.setLayout(null);

        this.scrollPane = new JScrollPane();
        this.scrollPane.setBounds(12, 27, 307, 183);
        this.panel_2.add(this.scrollPane);

        this.table = new JTable();
        //        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        this.scrollPane.setViewportView(this.table);

        this.panel_3 = new JPanel();
        this.panel_3.setBounds(366, 27, 280, 183);
        this.panel_2.add(this.panel_3);
        this.panel_3.setLayout(null);

        return this.panel;
    }

    private void setTableModel() {
        ShipType[] shipTypes = ShipType.values();
        String[] header = new String[] { "艦種", "艦娘数", "平均レベル", "最大レベル" };
        Object[][] objects = new Object[shipTypes.length][header.length];

        for (int i = 0; i < shipTypes.length; i++) {
            objects[i][0] = shipTypes[i].typeName();
            objects[i][1] = Fleets.getCount(shipTypes[i]);
            objects[i][2] = Fleets.getAverageLevel(shipTypes[i]);
            objects[i][3] = Fleets.getMaxLevel(shipTypes[i]);
            Logger.getGlobal().info(String.format("%s (Count->%s, Avg->%s, Max->%s)", objects[i][0],
                    objects[i][1], objects[i][2], objects[i][3]));
        }

        DefaultTableModel tableModel = new DefaultTableModel(objects, header) {
            /**
             *
             */
            private static final long serialVersionUID = -2768353904463787274L;

            //編集不可にする
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @SuppressWarnings("rawtypes")
            Class[] columnTypes = new Class[] {
                    String.class, Integer.class, Integer.class, Integer.class
            };

            @SuppressWarnings({ "unchecked", "rawtypes" })
            public Class getColumnClass(int columnIndex) {
                return this.columnTypes[columnIndex];
            }
        };
        this.table.setModel(tableModel);
        tableSetting();
    }

    private void tableSetting() {
        this.table.getColumnModel().getColumn(0).setResizable(false);
        this.table.getColumnModel().getColumn(0).setPreferredWidth(100);
        this.table.getColumnModel().getColumn(1).setResizable(false);
        this.table.getColumnModel().getColumn(2).setResizable(false);
        this.table.getColumnModel().getColumn(3).setResizable(false);
        this.table.getTableHeader().setResizingAllowed(false);
        this.table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        this.table.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        this.table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        this.table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

        this.table.setAutoCreateRowSorter(true);
    }

    private void openKanmusuCsvFile() {
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv file(*.csv)", "csv");
        fc.setFileFilter(filter);
        int selected = fc.showSaveDialog(this.panel);
        if (selected == JFileChooser.APPROVE_OPTION) {
            Fleets.loadMyKanmusuCSVData(fc.getSelectedFile().getAbsolutePath());
            this.textField_csvPath.setText(fc.getSelectedFile().getAbsolutePath());
            isCsvOpened = true;
        }
    }

    public static boolean isCsvOpened() {
        return isCsvOpened;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();

        if (object == this.button_openCsv) {
            openKanmusuCsvFile();
            setTableModel();
        }
    }
}
