package file;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class ChangeFileNames {
    public static void main(String[] args) throws Exception{
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(jFrame)) {
            getFiles(fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    public static void getFiles(String dir) throws Exception{
        List<String> filenames =
                Files.list(Paths.get(dir))
                .filter(path -> Files.isRegularFile(path))
                .map(path -> path.toString())
                .filter(x -> x.endsWith(".mkv"))
                .map(x -> x.substring(0, x.lastIndexOf('.')))
                .collect(Collectors.toList());
        List<Path> paths = Files.list(Paths.get(dir))
                .filter(path -> Files.isRegularFile(path))
                .filter(path -> path.toString().endsWith(".sc.ass"))
                .collect(Collectors.toList());
        if (filenames.size() != paths.size()) {
            throw new Exception("文件数量异常");
        }
        for (int i = 0; i < paths.size(); i++) {
            Files.move(paths.get(i), Paths.get(filenames.get(i) + ".sc.ass"));
        }
    }
}
