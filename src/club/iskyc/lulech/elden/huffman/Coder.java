package club.iskyc.lulech.elden.huffman;

import java.util.Arrays;

public class Coder {
	/** 
	 * int转化为byte[] 
	 * @param i 
	 * @return 
	 */  
	public static byte[] intToByteArray(int i) {  
	    byte[] result = new byte[4];  
	    result[0] = (byte) ((i >> 24) & 0xFF);  
	    result[1] = (byte) ((i >> 16) & 0xFF);  
	    result[2] = (byte) ((i >> 8) & 0xFF);  
	    result[3] = (byte) (i & 0xFF);  
	    return result;  
	}  

	/** 
	 * byte[] TO int 
	 * @param bytes 
	 * @return 
	 */  
	public static int byteArrayToInt(byte[] bytes) {  
	    int value = 0;  
	    for (int i = 0; i < 4; i++) {  
	        int shift = (4 - 1 - i) * 8;  
	        value += (bytes[i] & 0x000000FF) << shift;
	    }  
	    return value;  
	}  


	public static void main(String[] args) {  
	    byte[] b = intToByteArray(127);  
	    System.out.println(Integer.toBinaryString(127));
	    System.out.println(Arrays.toString(b));
	    int i = byteArrayToInt(b);  
	    System.out.println(i);  
	}   
}
