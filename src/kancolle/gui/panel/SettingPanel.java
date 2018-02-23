package kancolle.gui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import kancolle.fleet.Fleets;
import kancolle.structure.ShipType;

public class SettingPanel extends JPanel implements ActionListener{

    private JPanel panel;
    private JPanel panel_1;
    private JPanel panel_2;
    private JPanel panel_3;
    private JScrollPane scrollPane;
    private JTextField textField_csvPath;
    private JButton button_openCsv;
    private JTable table;

    public JPanel addPanel(){
        panel = new JPanel();
        panel.setLayout(null);

        panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "艦娘一覧csvファイル", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(12, 10, 769, 59);
        panel.add(panel_1);
        panel_1.setLayout(null);

        textField_csvPath = new JTextField();
        textField_csvPath.setEditable(false);
        textField_csvPath.setBounds(12, 21, 642, 19);
        panel_1.add(textField_csvPath);
        textField_csvPath.setColumns(10);

        button_openCsv = new JButton("開く");
        button_openCsv.setBounds(666, 20, 91, 21);
        button_openCsv.addActionListener(this);
        panel_1.add(button_openCsv);

        panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(null, "艦隊情報", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_2.setBounds(12, 79, 430, 220);
        panel.add(panel_2);
        panel_2.setLayout(null);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 27, 307, 183);
        panel_2.add(scrollPane);

        table = new JTable();
        //        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane.setViewportView(table);

        panel_3 = new JPanel();
        panel_3.setBounds(366, 27, 280, 183);
        panel_2.add(panel_3);
        panel_3.setLayout(null);

        return panel;
    }

    private void setTableModel(){
        ShipType[] shipTypes = ShipType.values();
        String[] header = new String[] {"艦種", "艦娘数", "平均レベル", "最大レベル"};
        Object[][] objects = new Object[shipTypes.length][header.length];

        for(int i = 0; i < shipTypes.length; i++){
            objects[i][0] = shipTypes[i].typeName();
            objects[i][1] = Fleets.getCount(shipTypes[i]);
            objects[i][2] = Fleets.getAverageLevel(shipTypes[i]);
            objects[i][3] = Fleets.getMaxLevel(shipTypes[i]);
            Logger.getGlobal().info(String.format("%s (Count->%s, Avg->%s, Max->%s)", objects[i][0],
                    objects[i][1], objects[i][2], objects[i][3]));
        }

        DefaultTableModel tableModel = new DefaultTableModel(objects, header) {
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
                return columnTypes[columnIndex];
            }
        };
        table.setModel(tableModel);
        tableSetting();
    }

    private void tableSetting(){
        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setResizable(false);
        table.getColumnModel().getColumn(2).setResizable(false);
        table.getColumnModel().getColumn(3).setResizable(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );

        table.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        table.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );

        table.setAutoCreateRowSorter(true);
    }

    private void openKanmusuCsvFile(){
        JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("csv file(*.csv)", "csv");
        fc.setFileFilter(filter);
        int selected = fc.showSaveDialog(panel);
        if (selected == JFileChooser.APPROVE_OPTION) {
            Fleets.loadMyKanmusuCSVData(fc.getSelectedFile().getAbsolutePath());
            textField_csvPath.setText(fc.getSelectedFile().getAbsolutePath());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();

        if(object == button_openCsv){
            openKanmusuCsvFile();
            setTableModel();
        }
    }
}
