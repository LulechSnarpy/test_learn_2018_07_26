package club.iskyc.lulech.layout.util;


import club.iskyc.lulech.annotation.RunnableExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

/**
 * @author lulec
 * */
public class ClassScannerUtil {

    public static Tree<MarkedElement<String, Class<?>>> getTreeClassNames() {
        List<Class<?>> classes = getScannedClasses();
        Tree<MarkedElement<String, Class<?>>> tree
                = new Tree<>(new MarkedElement<>("", null) , classes.size());
        MarkedElement<String, Class<?>> p;
        MarkedElement<String, Class<?>> leaf = null;
        for (Class<?> clazz : classes) {
            String[] names = clazz.getName().split("\\.");
            String mark = null;
            p = tree.getHead();
            for (String name : names) {
                mark = null == mark? name : mark + "." + name;
                leaf = new MarkedElement<>(mark, null);
                tree.add(leaf, p);
                p = leaf;
            }
            leaf.setData(clazz);
        }
        return tree;
    }

    public static List<Class<?>> getScannedClasses() {
        String packageName = "club.iskyc.lulech";
        Class<? extends Annotation> annotation = RunnableExample.class;
        List<Class<?>> classes = getScannedClasses(packageName, annotation)
                .stream().distinct().collect(Collectors.toList());
        return classes;
    }

    public static List<Class<?>> getScannedClasses(String packageName, Class<? extends Annotation> annotation) {
        List<Class<?>> classes = new ArrayList<>();
        URL url = ClassLoader.getSystemResource(packageToPath(packageName));
        if (null == url) return classes;
        if (url.toString().startsWith("jar")) {
            try(JarFile reader = ((JarURLConnection) url.openConnection()).getJarFile()){
                Enumeration<JarEntry> entries = reader.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (name.contains(packageToPath(packageName)) && name.endsWith(".class")) {
                        String className = name.substring(name.lastIndexOf("/") + 1);
                        Class<?> line = getClass(className,
                                pathToPackage(Paths.get(name.substring(name.indexOf("!/") + 1, name.lastIndexOf("/")))));
                        if (!hasAnnotation(line, annotation)) continue;
                        classes.add(line);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(url.openStream()))) {
                List<String> lines = reader.lines().toList();
                classes = (lines.stream()
                            .filter(line -> line.endsWith(".class"))
                            .map(line -> getClass(line, packageName))
                            .filter(line -> hasAnnotation(line, annotation))
                            .collect(Collectors.toList()));
                List<Class<?>> finalClasses = classes;
                lines.stream().filter(line -> !line.contains("."))
                            .forEach(x -> finalClasses.addAll(getScannedClasses(packageName + "." + x, annotation)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return classes;
    }

    public static boolean hasAnnotation(Class<?> line, Class<? extends Annotation> annotation) {
        return null == annotation
                || (null != line && line.isAnnotationPresent(annotation));
    }

    private static String packageToPath(String packageName) {
        return packageName.replaceAll("\\.", "/");
    }

    private static String pathToPackage(Path path) {
        return path.toString()
                .replaceAll("\\\\",".")
                .replaceAll("/", ".");
    }

    private static Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
