package club.iskyc.lulech.layout.base;

import javax.swing.*;
import java.awt.*;

/**
 *  Main Enter for all Examples
 * @TODO haven't done well
 * */
public class Sunrise {
    // Init Basic Inter Page
    public Sunrise (String[] args) throws Exception{
        JFrame basic = new JFrame("Sunrise");
        basic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        basic.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        basic.setEnabled(true);
        basic.setSize(new Dimension(400, 400));
        basic.setBounds(100,100, 400, 400);
        /*Image iconImage = new BufferedImage(200,100, BufferedImage.TYPE_4BYTE_ABGR);
        basic.setIconImage(iconImage);*/
        JLabel jLabel = new JLabel();
        jLabel.setBackground(Color.CYAN);
        jLabel.setPreferredSize(new Dimension( 398, 358));
        jLabel.setOpaque(true);
        /*BufferedReader reader = new BufferedReader(System.console().reader());
        reader.lines();
        jPanel.add(new TextArea(reader.lines().toString()));*/
        basic.getContentPane().add(jLabel, BorderLayout.CENTER);

        basic.setJMenuBar(SMenuBar.getSToolBar(jLabel));
        basic.pack();
        basic.setVisible(true);
        //basic.dispose();
    }

    // Basic Inter Page Main Enter
    public static void main(String[] args) throws Exception{
        new Sunrise(args);
    }
}
