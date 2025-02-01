package club.iskyc.lulech.layout.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * TODO : Fix for jar
 * @author lulec
 * */
public class ClassScannerUtil {

    public static Tree<MarkedElement<String, Class>> getTreeClassNames() {
        List<Class> classes = getScannedClasses();
        Tree<MarkedElement<String, Class>> tree
                = new Tree<>(new MarkedElement<>("", null) , classes.size());
        MarkedElement<String, Class> p;
        MarkedElement<String, Class> leaf = null;
        for (Class clazz : classes) {
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

    public static List<Class> getScannedClasses() {
        String packageName = "club.iskyc.lulech";
        Class annotation = null;
        List<Class> classes = getScannedClasses(packageName, annotation)
                .stream().distinct().collect(Collectors.toList());
        return classes;
    }

/*
    public static Set<Class> getScannedClasses(Path path, Class annotation)
    {
        Set<Class> set = new HashSet<>();
        try {
            Stream<Path> paths = Files.list(path);
            set.addAll(paths.filter(x -> (Files.isRegularFile(x) && x.toString().endsWith(".class")))
                    .map(x -> getClass(x))
                    .filter(line -> null == annotation
                            || (null != line && line.isAnnotationPresent(annotation)))
                    .collect(Collectors.toSet()));
            paths.filter(x -> Files.isDirectory(x))
                    .forEach(x -> set.addAll(getScannedClasses(x, annotation)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return set;
        }
    }*/

    public static List<Class> getScannedClasses(String packageName, Class annotation) {
        List<Class> classes = new ArrayList<>();
        URL url = ClassLoader.getSystemResource(packageToPath(packageName));
        if (null == url) return classes;
        if (url.toString().startsWith("jar")) {
            try(JarFile reader = ((JarURLConnection) url.openConnection()).getJarFile()){
                Enumeration<JarEntry> entries = reader.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (name.contains(packageToPath(packageName)) && name.endsWith(".class")) {
                        String className = name.substring(name.lastIndexOf("/") + 1, name.length());
                        classes.add(getClass(className,
                                pathToPackage(Paths.get(name.substring(name.indexOf("!/") + 1, name.lastIndexOf("/"))))));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(url.openStream()))) {
                List<String> lines = reader.lines().collect(Collectors.toList());
                classes = (lines.stream()
                            .filter(line -> line.endsWith(".class"))
                            .map(line -> getClass(line, packageName))
                            .filter(line -> null == annotation
                                || (null != line && line.isAnnotationPresent(annotation)))
                            .collect(Collectors.toList()));
                List<Class> finalClasses = classes;
                lines.stream().filter(line -> !line.contains("."))
                            .forEach(x -> finalClasses.addAll(getScannedClasses(packageName + "." + x, annotation)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
       /* try{

        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        return classes;
    }

    private static String packageToPath(String packageName) {
        return packageName.replaceAll("\\.", "/");
    }

    private static String pathToPackage(Path path) {
        return path.toString()
                .replaceAll("\\\\",".")
                .replaceAll("/", ".");
    }

    private static Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

/*    private static Class getClass(Path path) {
        try {
            return Class.forName(pathToPackage(path.getParent()) + "."
                    + path.getFileName().toString().substring(0,
                    path.getFileName().toString().lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/
}
