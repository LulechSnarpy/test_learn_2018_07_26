package club.iskyc.lulech.layout.util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
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
        List<Class> classes;
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(ClassLoader.getSystemClassLoader()
                        .getResourceAsStream(packageToPath(packageName))));
        List<String> lines = reader.lines().collect(Collectors.toList());
        classes = (lines.stream()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .filter(line -> null == annotation
                        || (null != line && line.isAnnotationPresent(annotation)))
                .collect(Collectors.toList()));
        lines.stream().filter(line -> !line.contains("."))
                .forEach(x -> classes.addAll(getScannedClasses(packageName + "." + x, annotation)));
        return classes;
    }

    private static String packageToPath(String packageName) {
        return Arrays.stream(packageName.split("\\."))
                .collect(Collectors.joining("/"));
    }

    private static String pathToPackage(Path path) {
        return Arrays.stream(path.toString().split("/"))
                .collect(Collectors.joining("."));
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
