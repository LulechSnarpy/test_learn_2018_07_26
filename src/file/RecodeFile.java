package file;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class RecodeFile {
	public static void main(String[] args)throws Exception {
		Charset decoder = Charset.forName("GB18030");
		Charset encoder = Charset.forName("UTF-8");
		Path path = Path.of("F:\\迅雷下载\\[DBD-Raws][机动战士高达ZZ][01-47TV全集+特典映像][1080P][BDRip][HEVC-10bit][FLAC][MKV]");
		Stream<Path> ps = Files.list(path);
		ps = ps.filter(x->x.getFileName().toString().endsWith(".ssa"));
		ps.forEach(x->{
			try {
				String s =Files.readString(x, decoder);
				Files.writeString(x, s, encoder);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});		
	}
}
