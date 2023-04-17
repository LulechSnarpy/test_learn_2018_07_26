package club.iskyc.lulech.layout.base;

import club.iskyc.lulech.layout.util.ClassScannerUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class SMenuBar extends JMenuBar {
    private static SMenuBar sMenuBar;

    private SMenuBar() {
        super();
    }

    private void initAll() {
        JMenu jMenu = new JMenu(" Nodes");
        String labels = " Node 1, Node 2, Node 3";
        Arrays.stream(labels.split(",")).forEach(label->{
            AtomicInteger k = new AtomicInteger(1);
            JMenuItem jMenuItem = new JMenuItem(label);
            jMenuItem.addMouseListener(new MouseListener() {
                                           String message =  label +
                                                   k.getAndIncrement() + "  is on clicked";
                                           @Override
                                           public void mouseClicked(MouseEvent e) {

                                           }

                                           @Override
                                           public void mousePressed(MouseEvent e) {
                                               /*JOptionPane.showMessageDialog( jMenu, message,
                                                       "Message Pressed", JOptionPane.INFORMATION_MESSAGE);*/
                                             /*  ClassScannerUtil.getScannedClassName("", null)
                                                       .stream().forEach(System.out::println);*/
                                             ClassScannerUtil.getScannedClasses();
                                           }

                                           @Override
                                           public void mouseReleased(MouseEvent e) {

                                           }

                                           @Override
                                           public void mouseEntered(MouseEvent e) {

                                           }

                                           @Override
                                           public void mouseExited(MouseEvent e) {

                                           }
                                       }

            );
            jMenu.add(jMenuItem);
        });
        this.add(jMenu);
        setOpaque(true);
        setPreferredSize(new Dimension(200, 20));
    }

    public synchronized static JMenuBar getSToolBar() {
        if (null == sMenuBar) {
            sMenuBar = new SMenuBar();
            sMenuBar.initAll();
        }
        return sMenuBar;
    }
}
