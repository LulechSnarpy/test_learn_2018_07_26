package club.iskyc.lulech.layout.listener;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class FunctionListener implements MouseListener {

    private Class function;

    private JLabel labelConsole;

    public FunctionListener (JLabel labelConsole, Class function) {
        this.labelConsole = labelConsole;
        this.function = function;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream old = System.out;
        System.setOut(new PrintStream(baos));
        System.out.println(function.getName());
        System.out.println(function.getCanonicalName());
        System.setOut(old);
        this.labelConsole.setText(baos.toString());
        this.labelConsole.repaint();
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
