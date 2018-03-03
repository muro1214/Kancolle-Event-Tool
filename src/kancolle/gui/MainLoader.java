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

    private JPanel contentPane;
    static JTabbedPane tabbedPane;
    private JMenuBar menuBar;
    private JMenu mnFile;
    private JMenu mnHelp;
    private JMenuItem mntmAbout;
    private JMenuItem mntmExit;
    private JButton button_addTab;
    private JButton button_deleteTab;
    private JButton button_renameTab;

    static String currentTabName;
    static int currentTabNo;
    static MainLoader frame;

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

    public MainLoader() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 835, 445);
        setTitle("Kancolle Event Supporter");

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

        this.mntmExit = new JMenuItem("Exit");
        this.mntmExit.addActionListener(this);
        this.mnFile.add(this.mntmExit);

        this.mnHelp = new JMenu("Help");
        this.menuBar.add(this.mnHelp);

        this.mntmAbout = new JMenuItem("About");
        this.mntmAbout.addActionListener(this);
        this.mnHelp.add(this.mntmAbout);

        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(this.contentPane);
        this.contentPane.setLayout(null);

        MainLoader.tabbedPane = new JTabbedPane(SwingConstants.TOP);
        MainLoader.tabbedPane.setBounds(12, 41, 798, 336);
        MainLoader.tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                MainLoader.currentTabNo = MainLoader.tabbedPane.getSelectedIndex();
                MainLoader.currentTabName = MainLoader.tabbedPane.getTitleAt(MainLoader.tabbedPane.getSelectedIndex());
            }
        });

        SettingPanel settingPanel = new SettingPanel();
        MainLoader.tabbedPane.addTab("設定画面", settingPanel.addPanel());

        this.contentPane.add(MainLoader.tabbedPane);

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
            if (!SettingPanel.isCsvOpened()) {
                JOptionPane.showMessageDialog(this, "先に設定タブからcsvファイルを開いてください", "メッセージ",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String tabName = JOptionPane.showInputDialog(this, "タブ名を入力してください");
            if (Objects.isNull(tabName)) {
                return;
            }

            EventPanel eventPanel = new EventPanel();
            JPanel panel = eventPanel.addPanel();
            MainLoader.tabbedPane.addTab(tabName, panel);
            MainLoader.tabbedPane.setSelectedComponent(panel);

            Logger.getGlobal().info("Add tab : " + tabName);
        } else if (object == this.button_deleteTab) {
            if (MainLoader.tabbedPane.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "このタブは削除できません", "メッセージ",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int ret = JOptionPane.showConfirmDialog(this, "このタブを削除してもいいですか？");
            if (ret != JOptionPane.YES_OPTION) {
                return;
            }

            int index = MainLoader.tabbedPane.getSelectedIndex();
            String tabName = MainLoader.tabbedPane.getTitleAt(index);
            MainLoader.tabbedPane.remove(index);

            Logger.getGlobal().info("Delete tab : " + tabName);
        } else if (object == this.button_renameTab) {
            if (MainLoader.tabbedPane.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(this, "このタブは名前を変更できません", "メッセージ",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            String tabName = JOptionPane.showInputDialog(this, "新しいタブ名を入力してください");
            if (Objects.isNull(tabName)) {
                return;
            }

            String old = MainLoader.tabbedPane.getTitleAt(MainLoader.tabbedPane.getSelectedIndex());
            changeTabName(tabName);

            Logger.getGlobal().info("Change tabName : " + old + " -> " + tabName);
        } else if (object == this.mntmExit) {
            int option = JOptionPane.showConfirmDialog(MainLoader.frame, "ツールを終了してもよろしいですか？",
                    "確認", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        } else if (object == this.mntmAbout) {
            JOptionPane.showMessageDialog(MainLoader.frame, "バージョン：0.0.1", "バージョン情報",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static String getCurrentTabName() {
        return MainLoader.currentTabName;
    }

    public static int getCurrentTabNo() {
        return MainLoader.currentTabNo;
    }

    public static MainLoader getFrame() {
        return MainLoader.frame;
    }

    public static void changeTabName(final String tabName) {
        tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), tabName);
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
