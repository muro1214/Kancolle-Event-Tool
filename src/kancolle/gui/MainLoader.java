package kancolle.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import kancolle.gui.panel.EventPanel;
import kancolle.gui.panel.SettingPanel;

public class MainLoader extends JFrame implements ActionListener{

    private JPanel contentPane;
    private JTabbedPane tabbedPane;
    private JMenuBar menuBar;
    private JMenu mnFile;
    private JMenu mnHelp;
    private JMenuItem mntmAbout;
    private JMenuItem mntmOpen;
    private JMenuItem mntmSave;
    private JMenuItem mntmSaveAs;
    private JSeparator separator;
    private JMenuItem mntmExit;
    private JButton button_addTab;
    private JButton button_deleteTab;
    private JButton button_renameTab;

    private static MainLoader frame;
    private static String currentTabName;
    private static int currentTabNo;

    public static void main(String[] args) {
        initializeLogger();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    frame = new MainLoader();
                    frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainLoader(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 835, 445);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
        }

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        mnFile = new JMenu("File");
        menuBar.add(mnFile);

        mntmOpen = new JMenuItem("Open");
        mnFile.add(mntmOpen);

        mntmSave = new JMenuItem("Save");
        mnFile.add(mntmSave);

        mntmSaveAs = new JMenuItem("Save As");
        mnFile.add(mntmSaveAs);

        separator = new JSeparator();
        mnFile.add(separator);

        mntmExit = new JMenuItem("Exit");
        mnFile.add(mntmExit);

        mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);

        mntmAbout = new JMenuItem("About");
        mnHelp.add(mntmAbout);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(12, 41, 798, 336);
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                currentTabNo = tabbedPane.getSelectedIndex();
                currentTabName = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
            }
        });

        SettingPanel settingPanel = new SettingPanel();
        tabbedPane.addTab("設定画面", settingPanel.addPanel());

        contentPane.add(tabbedPane);

        button_addTab = new JButton("タブ追加");
        button_addTab.setBounds(12, 10, 91, 21);
        button_addTab.addActionListener(this);
        contentPane.add(button_addTab);

        button_deleteTab = new JButton("タブ削除");
        button_deleteTab.setBounds(115, 10, 91, 21);
        button_deleteTab.addActionListener(this);
        contentPane.add(button_deleteTab);

        button_renameTab = new JButton("名前変更");
        button_renameTab.setBounds(218, 10, 91, 21);
        button_renameTab.addActionListener(this);
        contentPane.add(button_renameTab);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();

        if(object == button_addTab){
            String tabName = JOptionPane.showInputDialog(frame, "タブ名を入力してください");
            if(Objects.isNull(tabName)){
                return;
            }

            EventPanel eventPanel = new EventPanel();
            JPanel panel = eventPanel.addPanel();
            tabbedPane.addTab(tabName, panel);
            tabbedPane.setSelectedComponent(panel);

            Logger.getGlobal().info("Add tab : " + tabName);
        }else if(object == button_deleteTab){
            if(tabbedPane.getSelectedIndex() == 0){
                JOptionPane.showMessageDialog(frame, "このタブは削除できません");
                return;
            }

            int ret = JOptionPane.showConfirmDialog(frame, "このタブを削除してもいいですか？");
            if(ret != JOptionPane.YES_OPTION){
                return;
            }

            int index = tabbedPane.getSelectedIndex();
            String tabName = tabbedPane.getTitleAt(index);
            tabbedPane.remove(index);

            Logger.getGlobal().info("Delete tab : " + tabName);
        }else if(object == button_renameTab){
            if(tabbedPane.getSelectedIndex() == 0){
                JOptionPane.showMessageDialog(frame, "このタブは名前を変更できません");
                return;
            }

            String tabName = JOptionPane.showInputDialog(frame, "新しいタブ名を入力してください");
            if(Objects.isNull(tabName)){
                return;
            }

            String old = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
            tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), tabName);

            Logger.getGlobal().info("Change tabName : " + old + " -> " + tabName);
        }
    }

    public static String getCurrentTabName(){
        return currentTabName;
    }

    public static int getCurrentTabNo(){
        return currentTabNo;
    }

    private static void initializeLogger(){
        if (Objects.isNull(System.getProperty("java.util.logging.config.file")) &&
                Objects.isNull(System.getProperty("java.util.logging.config.class"))) {
            try(InputStream is = MainLoader.class.getResourceAsStream("../logging.properties")){
                if(!Objects.isNull(is)){
                    LogManager.getLogManager().readConfiguration(is);
                }
            } catch (IOException e) {
                // use default logging config.
            }
        }
    }
}
