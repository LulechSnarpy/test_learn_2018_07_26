package club.iskyc.lulech.elden.huffman;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LuZip {
	 
	public void compress(String source, String target) {
		int len = 0;
		byte[] b = new byte[2048];
		byte[] a = new byte[2048];
		BTTree<Byte> bt = new BTTree<Byte>();
		StringBuffer buffer = new StringBuffer();
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source))) {
			while((len = bis.read(b)) > 0) {
				for (int i = 0; i < len; i++) {
					bt.addMap(b[i]);
				}
			}
			bt.createCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(target))) {
			int i = 0;
			for(Map.Entry<Byte, String> entry : bt.getCodes().entrySet()) {
				a[i++] = entry.getKey();
				i = flush(i, bos, a);
				for (char c : entry.getValue().toCharArray()) {
					a[i++] = (byte) ((c + (-48)) & 0xFF);
					i = flush(i, bos, a);
				}
				a[i++] = (byte) -1;
				i = flush(i, bos, a);
			}
			a[i++] = (byte) -2;
			i = flush(i, bos, a);
			a[i++] = (byte) -2;
			i = flush(i, bos, a);
			for (byte v : Coder.intToByteArray(bis.available())) {
				a[i++] = v;
				i = flush(i, bos, a);
			}
			while((len = bis.read(b)) > 0) {	
				for (int k = 0; k < len; k++) { 
					buffer.append(bt.encode(b[k])); 
				}
				for ( ; buffer.length() >= 8;) {
					a[i++] = encodeString(buffer.substring(0, 8));
					buffer.delete(0, 8);
					i = flush(i, bos, a);
				}		
			}
			if (buffer.length() > 0) {
				for (;buffer.length()<8;) {
					buffer.append("0");
				}
				a[i++] = encodeString(buffer.toString());
				i = flush(i, bos, a);
			}
			if (i > 0) {
				bos.write(a, 0, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int flush(int i, OutputStream os, byte[] a) throws IOException {
		if (i == 2048) {
			os.write(a);
			return 0;
		}
		return i;
	}
	
	public void decompress(String source, String target) {
		int len = 0;
		byte[] a = new byte[2048];
		byte[] b = new byte[2048];
		byte[] c = new byte[4096];
		byte[] d = new byte[4];
		StringBuffer buffer = new StringBuffer();
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(target))) {
			BTTree<Byte> bt = new BTTree<Byte>();
			Map<String, Byte> codes = new LinkedHashMap<>();
			Byte value = null;
			String key = null;
			byte t = 0;
			boolean decode = true;
			int size = 0;
			int k = 0;
			int i = 0;
			while((len = bis.read(b)) > 0) {	
				System.arraycopy(b, 0, c, i, len);
				len += i;
				for(i = 0; i < len; ) {
					if (!decode) {
						buffer.append(decodeByte(c[i++]));
						continue;
					}
					value = c[i++];
					if (value == -2 && c[i] == -2) {
						if (len < i+5) {
							i--;
							break;
						} 
						System.arraycopy(c, ++i, d, 0, 4);
						size = Coder.byteArrayToInt(d);
						i += 4;
						decode = false;
						bt.setKeys(codes);
						continue;
					}
					for (k = i; k < len;) {
						t = c[k++];
						if (t == -1) {
							key = buffer.toString();
							codes.put(key, value);
							buffer.delete(0, k-i);
							break;
						}
						buffer.append(t);
					}
					i = k;
				}
				System.arraycopy(c, i, c, 0, len-i);
				i = len - i;
				List<Byte> list = bt.decode(buffer);
				if (size < 0) break;
				a = new byte[list.size() > size ? size : list.size()];
				for (int j = 0; j < list.size() && j < size; j++) {
					a[j] = list.get(j);
				}
				size -= list.size();
				bos.write(a);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {	
		LuZip luz= new LuZip();
		luz.compress("./source/club.iskyc.lulech.elden.huffman/input.txt", "./source/club.iskyc.lulech.elden.huffman/output.txt");
		luz.decompress("./source/club.iskyc.lulech.elden.huffman/output.txt", "./source/club.iskyc.lulech.elden.huffman/decode.txt");
//		luz.compress("./source/club.iskyc.lulech.elden.huffman/input.gif", "./source/club.iskyc.lulech.elden.huffman/output.club.iskyc.lulech.elden.zip");
//		luz.decompress("./source/club.iskyc.lulech.elden.huffman/output.club.iskyc.lulech.elden.zip", "./source/club.iskyc.lulech.elden.huffman/decode.gif");
	}
	
	public static String decodeByte(byte b) {
		StringBuffer buffer = new StringBuffer();
		int c = b;
		for (int i = 0; i <8; i++) {
			buffer.insert(0, c & 1);
			c >>= 1;
		}
		return buffer.toString();
	}
	
	
	 public static byte encodeString(String s) {
		 int b = 0;
		 for (char c : s.toCharArray()) {
			 b = (b << 1) + (c + (-48));
		 }
		 return (byte)(b & 0xFF);
	 }
}
