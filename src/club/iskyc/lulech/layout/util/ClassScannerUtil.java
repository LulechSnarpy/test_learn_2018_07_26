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
 * This is class is used to scan all the class files in current project.
 * Can filter by specific annotation.
 *
 * @author lulec
 * @since 1.0
 * */
public class ClassScannerUtil implements BaseConstantValue {

    /**
     * Default package name. Current package is <em>club.iskyc.lulech</em>
     * */
    public static final String packageName = "club.iskyc.lulech";

    /**
     * Default annotation. This project default use {@code RunnableExample} as class finding.
     * */
    public static final Class<? extends Annotation> annotation = RunnableExample.class;

    /**
     * Class file suffix.
     * */
    public static final String classFileSuffix = ".class";

    /**
     * String value of {@code jar}. Jar file url start String.
     * */
    public static final String jar =  "jar";

    /**
     * String value of {@code !/}. Jar file url outer url ends.
     * End of the jar file path.
     * */
    public static final String endJar = "!/";

    /**
     * Scan classes in {@link  club.iskyc.lulech.layout.util.Tree}.
     * */
    public static Tree<MarkedElement<String, Class<?>>> getTreeClassNames() {
        List<Class<?>> classes = getScannedClasses();
        Tree<MarkedElement<String, Class<?>>> tree
                = new Tree<>(new MarkedElement<>("", null) , classes.size());
        MarkedElement<String, Class<?>> p;
        MarkedElement<String, Class<?>> leaf = null;
        for (Class<?> clazz : classes) {
            String[] names = clazz.getName().split(regPackageSplitter);
            String mark = null;
            p = tree.getHead();
            for (String name : names) {
                mark = null == mark ? name : mark + packageSplitter + name;
                leaf = new MarkedElement<>(mark, null);
                tree.add(leaf, p);
                p = leaf;
            }
            leaf.setData(clazz);
        }
        return tree;
    }

    /**
     * Get default package and default annotation scan result.
     * */
    public static List<Class<?>> getScannedClasses() {
        return getScannedClasses(packageName, annotation)
                .stream().distinct().collect(Collectors.toList());
    }

    /**
     * Get classes from specific package with specific annotation.
     * */
    public static List<Class<?>> getScannedClasses(
            String packageName, Class<? extends Annotation> annotation) {
        List<Class<?>> classes = new ArrayList<>();
        URL url = ClassLoader.getSystemResource(packageToPath(packageName));
        if (null == url) return classes;
        if (url.toString().startsWith(jar)) {
            try(JarFile reader = ((JarURLConnection) url.openConnection()).getJarFile()){
                Enumeration<JarEntry> entries = reader.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (name.contains(packageToPath(packageName)) && name.endsWith(classFileSuffix)) {
                        String className = name.substring(name.lastIndexOf(linuxSplitter) + 1);
                        Class<?> line = getClass(className
                                , pathToPackage(Paths.get(name.substring(name.indexOf(endJar) + 1
                                        , name.lastIndexOf(linuxSplitter)))));
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
                            .filter(line -> line.endsWith(classFileSuffix))
                            .map(line -> getClass(line, packageName))
                            .filter(line -> hasAnnotation(line, annotation))
                            .collect(Collectors.toList()));
                List<Class<?>> finalClasses = classes;
                lines.stream()
                        .filter(line -> !line.contains(packageSplitter))
                        .forEach(x -> finalClasses.addAll(
                                getScannedClasses(packageName + packageSplitter + x, annotation)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return classes;
    }

    /***
     * Check whether this class has specific annotation.
     * */
    public static boolean hasAnnotation(Class<?> line, Class<? extends Annotation> annotation) {
        return null == annotation
                || (null != line && line.isAnnotationPresent(annotation));
    }

    /**
     * Change package name to path. Change the sprites {@code .} to {@code /}.
     * */
    private static String packageToPath(String packageName) {
        return packageName.replaceAll(regPackageSplitter, linuxSplitter);
    }


    /**
     * Change path to package name. Change sprites {@code /} or {@code \} to {@code .}.
     * */
    private static String pathToPackage(Path path) {
        return path.toString()
                .replaceAll(regLinuxSplitter, packageSplitter)
                .replaceAll(regWindowsSplitter, packageSplitter);
    }

    /**
     * Try get Class from full class name. Trows {@code ClassNotFoundException}.
     * */
    private static Class<?> getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + packageSplitter
                    + className.substring(0, className.lastIndexOf(packageSplitter)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
