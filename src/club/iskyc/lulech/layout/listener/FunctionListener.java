package club.iskyc.lulech.layout.listener;

import club.iskyc.lulech.annotation.FileChooser;
import club.iskyc.lulech.annotation.InputValue;
import club.iskyc.lulech.annotation.RunnableExampleMethod;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class FunctionListener implements MouseListener {

    private Class<?> function;

    private JTextArea console;

    private JPanel form;

    public FunctionListener (JTextArea console, JPanel form,Class<?> function) {
        this.form = form;
        this.console = console;
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
        System.out.println(console.getRootPane().getParent());
        for (Method method: function.getMethods()) {
            if (!method.isAnnotationPresent(RunnableExampleMethod.class)) continue;
            form.removeAll();
            Object[] param = new Object[method.getParameterCount()];
            int index = 0;
            for (Parameter parameter: method.getParameters()) {
                if (parameter.isAnnotationPresent(InputValue.class)) {
                    JLabel label = new JLabel(parameter.getName().toUpperCase(), JLabel.TRAILING);
                    JTextField field =
                            new JTextField(parameter.getAnnotation(InputValue.class).value());
                    form.add(label);
                    label.setLabelFor(field);
                    form.add(field);
                }
                if (parameter.isAnnotationPresent(FileChooser.class)) {
                    JButton jButton= new JButton("choose file");
                    JTextField jTextField = new JTextField();
                    jTextField.setEditable(false);
                    jButton.addActionListener(e1 -> {
                        console.getRootPane().getParent();
                        JFileChooser fc = new JFileChooser();
                        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        if(JFileChooser.APPROVE_OPTION
                                == fc.showOpenDialog(console.getRootPane().getParent())) {
                            jTextField.setText(fc.getSelectedFile().getAbsolutePath());
                            jTextField.updateUI();
                            form.updateUI();
                            System.out.println(fc.getSelectedFile().getAbsolutePath());
                        }
                    });
                    form.add(jButton);
                    form.add(jTextField);
                }
            }
            JLabel jLabel = new JLabel(function.getName().toUpperCase(), JLabel.TRAILING);
            JButton jButton = new JButton("run");
            jButton.addActionListener(e1 -> {
                new Thread(() -> {
                    try {
                        method.invoke(function, param);
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    } catch (InvocationTargetException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            });
            form.add(jLabel);
            jLabel.setLabelFor(jButton);
            form.add(jButton);
            form.revalidate();
      /*      try {
                method.invoke(function, param);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                ex.printStackTrace();
            }*/
        }
        this.console.setText(baos.toString());
        this.console.repaint();
        System.setOut(old);
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
