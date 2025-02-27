package club.iskyc.lulech.elden.file;

import club.iskyc.lulech.annotation.FileChooser;
import club.iskyc.lulech.annotation.InputValue;
import club.iskyc.lulech.annotation.RunnableExample;
import club.iskyc.lulech.annotation.RunnableExampleMethod;

import javax.swing.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RunnableExample
public class ChangeFileNames {
    public static void main(String[] args) throws Exception{
        String sourceFilesSuffix = ".mp4";
        String targetFilesSuffix = ".sc.ass";
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JFrame jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(jFrame)) {
            changeFileNamesInSameDir(sourceFilesSuffix, targetFilesSuffix, fileChooser.getSelectedFile().getAbsolutePath());
        }
    }

    @RunnableExampleMethod(label="")
    public static void changeFileNamesInSameDir(
            @InputValue(value=".mp4")
            String sourceFilesSuffix,
            @InputValue(value=".sc.ass")
            String targetFilesSuffix,
            @FileChooser(type=JFileChooser.DIRECTORIES_ONLY)
            String dir) throws Exception{
        List<String> filenames =
                Files.list(Paths.get(dir))
                .filter(Files::isRegularFile)
                .map(Path::toString)
                .filter(x -> x.endsWith(sourceFilesSuffix))
                .map(x -> x.substring(0, x.lastIndexOf('.')))
                .toList();
        List<Path> paths = Files.list(Paths.get(dir))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(targetFilesSuffix))
                .toList();
        if (filenames.size() < paths.size()) {
            throw new Exception("FileNumberException");
        }
        for (int i = 0; i < paths.size(); i++) {
            Files.move(paths.get(i), Paths.get(STR."\{filenames.get(i)}\{targetFilesSuffix}"));
        }
    }
}
