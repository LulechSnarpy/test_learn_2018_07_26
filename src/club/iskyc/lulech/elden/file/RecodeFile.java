package club.iskyc.lulech.elden.file;

import club.iskyc.lulech.annotation.FileChooser;
import club.iskyc.lulech.annotation.InputValue;
import club.iskyc.lulech.annotation.RunnableExample;
import club.iskyc.lulech.annotation.RunnableExampleMethod;

import javax.swing.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@RunnableExample
public class RecodeFile {

	public static void main(String[] args)throws Exception {
		String decode = "GB18030";
		String encode = "UTF-8";
		String pathStr = "F:\\迅雷下载\\[DBD-Raws][机动战士高达ZZ][01-47TV全集+特典映像][1080P][BDRip][HEVC-10bit][FLAC][MKV]";
		String suffix = ".ssa";
		recordFile(decode, encode, suffix, pathStr);
	}

	@RunnableExampleMethod
	public static void recordFile (
			@InputValue("GB18030")
			String decode,
		    @InputValue("UTF-8")
		    String encode,
			@InputValue(".ssa")
			String suffix,
		    @FileChooser(type = JFileChooser.DIRECTORIES_ONLY)
		    String dir) throws IOException {
		Charset decoder = Charset.forName(decode);
		Charset encoder = Charset.forName(encode);
		Path path = Paths.get(dir);
		Stream<Path> ps = Files.list(path);
		ps = ps.filter(x->x.getFileName().toString().endsWith(suffix));
		ps.forEach(x->{
			try {
				List<String> lines = Files.readAllLines(x, decoder);
				Files.write(x, lines, encoder);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
