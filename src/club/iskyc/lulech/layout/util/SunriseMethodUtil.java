package club.iskyc.lulech.layout.util;

import club.iskyc.lulech.annotation.FileChooser;
import club.iskyc.lulech.annotation.InputValue;
import club.iskyc.lulech.annotation.RunnableExampleMethod;
import club.iskyc.lulech.layout.base.SunriseFormLayer;
import club.iskyc.lulech.layout.bean.SunriseMethodInfo;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.PrintStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * This class is used to provide actions on {@code SunriseMethodInfo} bean data.
 * And  provide a series of functions for {@code java.lang.reflect.Method}
 *
 * @author lulec
 * @since 1.0
 * */
public class SunriseMethodUtil implements BaseConstantValue{
    /**
     * Default annotation. This project default use {@code RunnableExampleMethod.class} as method finding.
     * */
    public static final Class<? extends Annotation> annotation = RunnableExampleMethod.class;

    /**
     * Used for form panel constant to create form info by analyzing annotations of method parameters.
     * */
    public static void prepareForm(SunriseMethodInfo methodInfo, SunriseFormLayer container) {
        container.removeAll();
        container.inputs().clear();
        int idx = 0;
        for (Parameter parameter : methodInfo.method().getParameters()) {
            if (parameter.isAnnotationPresent(InputValue.class)) {
                JLabel label = new JLabel(parameter.getName().toUpperCase(), JLabel.TRAILING);
                JTextField field =
                        new JTextField(parameter.getAnnotation(InputValue.class).value());
                container.add(label);
                label.setLabelFor(field);
                container.add(field);
                container.inputs().add(field);
            }
            if (parameter.isAnnotationPresent(FileChooser.class)) {
                int i = idx++;
                FileChooser annotation = parameter.getAnnotation(FileChooser.class);
                JButton jButton = new JButton("choose file");
                JTextField jTextField = new JTextField(10);
                jTextField.setEditable(false);
                jButton.addActionListener(e1 -> {
                    JFileChooser fc = new JFileChooser();
                    fc.setFileSelectionMode(annotation.type());
                    if (!"".equals(annotation.fileTypes())){
                        fc.setFileFilter(new FileFilter() {
                            @Override
                            public boolean accept(File f) {
                                String[] suffixes = annotation.fileTypes().split(",");
                                for (String suffix : suffixes){
                                    if (f.getName().endsWith(suffix)) return true;
                                }
                                return false;
                            }
                            @Override
                            public String getDescription() {
                                return "";
                            }
                        });
                    }
                    if (JFileChooser.APPROVE_OPTION
                            == fc.showOpenDialog(container.getRootPane().getParent())) {
                        jTextField.setText(fc.getSelectedFile().getAbsolutePath());
                        jTextField.updateUI();
                        container.updateUI();
                    }
                });
                container.add(jButton);
                container.add(jTextField);
                container.inputs().add(jTextField);
            }
        }
    }

    /**
     * Before method ready to invoke. Get form data into params;
     * */
    public static void prepareParamForForm(SunriseMethodInfo methodInfo, SunriseFormLayer container) {
        Object[] params = new Object[methodInfo.method().getParameterCount()];
        methodInfo.setParam(params);
        int idx = 0;
        for (Parameter parameter : methodInfo.method().getParameters()) {
            params[idx] = getParamValue(parameter, container.inputs().get(idx++).getText());
        }
    }

    /**
     * Scan and get method information from scanned classes.
     * */
    public static List<SunriseMethodInfo> getSunriseMethodInfos(){
        List<Class<?>> classes = ClassScannerUtil.getScannedClasses();
        List<SunriseMethodInfo> methodInfos = new ArrayList<>();
        boolean flag = false;
        for (Class<?> clazz : classes){
            flag = false;
            Object object = null;
            for (Method method: clazz.getMethods()) {
                if(hasAnnotation(method, annotation)) {
                    // if (!flag) object = clazz.getDeclaredConstructor().newInstance();
                    SunriseMethodInfo methodInfo = new SunriseMethodInfo(method, clazz);
                    methodInfos.add(methodInfo);
                    flag = true;
                }
            }
        }
        return methodInfos;
    }

    /**
     * Check whether this method has specific annotation.
     * */
    public static boolean hasAnnotation(Method method, Class<? extends Annotation> annotation) {
        return null == annotation
                ||(null != method && method.isAnnotationPresent(annotation));
    }

    /**
     * Analysis method info list to Tree. Each node is package name, class name or method name.
     * Priority package name, then class name, last is method name and method info.
     * */
    public static Tree<MarkedElement<String, SunriseMethodInfo>> getTreeSunriseMethodInfo(
            List<SunriseMethodInfo> methodInfos) {
        Tree<MarkedElement<String, SunriseMethodInfo>> tree =
                new Tree<>(new MarkedElement<>("Project", null), methodInfos.size());
        MarkedElement<String, SunriseMethodInfo> p;
        MarkedElement<String, SunriseMethodInfo> leaf = null;
        for (SunriseMethodInfo methodInfo : methodInfos) {
            String[] names = methodInfo.clazz().getName().split(regPackageSplitter);
            String mark = null;
            p = tree.getHead();
            for (String name: names) {
                mark = null == mark ? name : mark + packageSplitter + name;
                leaf = new MarkedElement<>(mark, null);
                tree.add(leaf, p);
                p = leaf;
            }
            leaf = new MarkedElement<>(mark + packageSplitter + methodInfo.method().getName(), null);
            leaf.setData(methodInfo);
            tree.add(leaf, p);
        }
        return tree;
    }

    /**
     * Get class name + method name.
     * */
    public static String getFullPathOfMethod(SunriseMethodInfo methodInfo) {
        return methodInfo.clazz().getName() + packageSplitter + methodInfo.method().getName();
    }

    /**
     * Run method in methodInfo and catch the exception.
     * */
    public static void runMethod(SunriseMethodInfo methodInfo) {
        PrintStream old = System.out;
        synchronized (System.out) {
            try {
                System.out.println("------Method Start-------");
                methodInfo.method().invoke(methodInfo.relay(), methodInfo.getParam());
                System.out.println("------Method End---------");
            } catch (IllegalAccessException | InvocationTargetException
                     | NoSuchMethodException | InstantiationException ex) {
                ex.printStackTrace();
            } finally {
                System.setOut(old);
            }
        }
    }

    /**
     * Run method in new thread for methodInfo and catch the exception.
     * */
    public static void runMethod(SunriseMethodInfo methodInfo, ThreadPoolExecutor poolExecutor) {
        poolExecutor.execute(() -> {
            runMethod(methodInfo);
        });
    }

    /**
     * Change String value into Parameter Type.
     * @TODO
     * */
     public static Object getParamValue (Parameter p, String value) {
         return switch (p.getType().getName()) {
             case "java.lang.Integer" -> Integer.valueOf(value);
             case "java.lang.Short" -> Short.valueOf(value);
             case "java.lang.Long" -> Long.valueOf(value);
             case "java.lang.Boolean" -> Boolean.valueOf(value);
             case "java.lang.Float" -> Float.valueOf(value);
             case "java.lang.Double" -> Double.valueOf(value);
             case "java.lang.Character" -> value.chars().findFirst();
             case "java.lang.Byte" -> value.getBytes()[0];
             case "java.lang.String" -> value;
             default -> null;
         };
    }
}
