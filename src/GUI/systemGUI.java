package GUI;

import javax.swing.*;
import java.awt.*;

public class systemGUI extends JFrame {
    // create constructor
    public systemGUI(String title) {
        // set the title of the title bar
        super(title);

        // set the size of the GUI
        setSize(600, 600);

        // configure GUI to end process after closing
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // set layout to BorderLayout
        setLayout(new BorderLayout());

        // load GUI in the center of the screen
        setLocationRelativeTo(null);

        // prevent GUI from changing size
        setResizable(false);

        // change the background color of the GUI (assuming CommonConstants.PRIMARY_COLOR is defined)
        // getContentPane().setBackground(CommonConstants.PRIMARY_COLOR);
    }
}
