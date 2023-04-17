package club.iskyc.lulech.layout.util;


import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassScannerUtil {
    public static void main(String[] args) {
        getScannedClasses().stream().forEach(System.out::println);
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
        System.out.println("getScannedClasses: " + packageName);
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
        System.out.println(packageName + ":" + className);
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
