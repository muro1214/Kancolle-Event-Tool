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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import kancolle.gui.panel.EventPanel;
import kancolle.gui.panel.SettingPanel;

public class MainLoader extends JFrame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 3939230639977237978L;
    private JPanel contentPane;
    JTabbedPane tabbedPane;
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

    static String currentTabName;
    static int currentTabNo;

    public static void main(String[] args) {
        initializeLogger();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainLoader frame = new MainLoader();
                    frame.setResizable(false);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainLoader() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 835, 445);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            Logger.getGlobal().info(e.toString());
        }

        this.menuBar = new JMenuBar();
        setJMenuBar(this.menuBar);

        this.mnFile = new JMenu("File");
        this.menuBar.add(this.mnFile);

        this.mntmOpen = new JMenuItem("Open");
        this.mnFile.add(this.mntmOpen);

        this.mntmSave = new JMenuItem("Save");
        this.mnFile.add(this.mntmSave);

        this.mntmSaveAs = new JMenuItem("Save As");
        this.mnFile.add(this.mntmSaveAs);

        this.separator = new JSeparator();
        this.mnFile.add(this.separator);

        this.mntmExit = new JMenuItem("Exit");
        this.mnFile.add(this.mntmExit);

        this.mnHelp = new JMenu("Help");
        this.menuBar.add(this.mnHelp);

        this.mntmAbout = new JMenuItem("About");
        this.mnHelp.add(this.mntmAbout);

        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(this.contentPane);
        this.contentPane.setLayout(null);

        this.tabbedPane = new JTabbedPane(SwingConstants.TOP);
        this.tabbedPane.setBounds(12, 41, 798, 336);
        this.tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                MainLoader.currentTabNo = MainLoader.this.tabbedPane.getSelectedIndex();
                MainLoader.currentTabName = MainLoader.this.tabbedPane.getTitleAt(MainLoader.this.tabbedPane.getSelectedIndex());
            }
        });

        SettingPanel settingPanel = new SettingPanel();
        this.tabbedPane.addTab("設定画面", settingPanel.addPanel());

        this.contentPane.add(this.tabbedPane);

        this.button_addTab = new JButton("タブ追加");
        this.button_addTab.setBounds(12, 10, 91, 21);
        this.button_addTab.addActionListener(this);
        this.contentPane.add(this.button_addTab);

        this.button_deleteTab = new JButton("タブ削除");
        this.button_deleteTab.setBounds(115, 10, 91, 21);
        this.button_deleteTab.addActionListener(this);
        this.contentPane.add(this.button_deleteTab);

        this.button_renameTab = new JButton("名前変更");
        this.button_renameTab.setBounds(218, 10, 91, 21);
        this.button_renameTab.addActionListener(this);
        this.contentPane.add(this.button_renameTab);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();

        if (object == this.button_addTab) {
            String tabName = JOptionPane.showInputDialog(this, "タブ名を入力してください");
            if (Objects.isNull(tabName)) {
                return;
            }

            EventPanel eventPanel = new EventPanel();
            JPanel panel = eventPanel.addPanel();
            this.tabbedPane.addTab(tabName, panel);
            this.tabbedPane.setSelectedComponent(panel);

            Logger.getGlobal().info("Add tab : " + tabName);
        } else if (object == this.button_deleteTab) {
            if (this.tabbedPane.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "このタブは削除できません");
                return;
            }

            int ret = JOptionPane.showConfirmDialog(this, "このタブを削除してもいいですか？");
            if (ret != JOptionPane.YES_OPTION) {
                return;
            }

            int index = this.tabbedPane.getSelectedIndex();
            String tabName = this.tabbedPane.getTitleAt(index);
            this.tabbedPane.remove(index);

            Logger.getGlobal().info("Delete tab : " + tabName);
        } else if (object == this.button_renameTab) {
            if (this.tabbedPane.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "このタブは名前を変更できません");
                return;
            }

            String tabName = JOptionPane.showInputDialog(this, "新しいタブ名を入力してください");
            if (Objects.isNull(tabName)) {
                return;
            }

            String old = this.tabbedPane.getTitleAt(this.tabbedPane.getSelectedIndex());
            this.tabbedPane.setTitleAt(this.tabbedPane.getSelectedIndex(), tabName);

            Logger.getGlobal().info("Change tabName : " + old + " -> " + tabName);
        }
    }

    public static String getCurrentTabName() {
        return MainLoader.currentTabName;
    }

    public static int getCurrentTabNo() {
        return MainLoader.currentTabNo;
    }

    private static void initializeLogger() {
        if (Objects.isNull(System.getProperty("java.util.logging.config.file")) &&
                Objects.isNull(System.getProperty("java.util.logging.config.class"))) {
            try (InputStream is = MainLoader.class.getResourceAsStream("../logging.properties")) {
                if (!Objects.isNull(is)) {
                    LogManager.getLogManager().readConfiguration(is);
                }
            } catch (IOException e) {
                // use default logging config.
                Logger.getGlobal().info(e.toString());
            }
        }
    }
}
