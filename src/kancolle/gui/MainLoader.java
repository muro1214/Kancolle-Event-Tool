package kancolle.gui;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javax.swing.JFrame;

public class MainLoader extends JFrame {

    public static void main(String[] args) {
        Logger logger = Logger.getGlobal();
        try {
            logger.addHandler(new FileHandler("Kancolle_Event_Tool_log.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
