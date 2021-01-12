package Text;

import java.io.IOException;
import java.util.Base64;

public class Base641 {
	public static void main(String[] args) throws IOException 
	{
	final Base64.Decoder decoder = Base64.getDecoder();
	final Base64.Encoder encoder = Base64.getEncoder();
	final String text = "×Ö´®ÎÄ×Ö";
	final byte[] textByte = text.getBytes("UTF-8");
	//±àÂë
	final String encodedText = encoder.encodeToString(textByte);
	System.out.println(encodedText);
	//½âÂë
	System.out.println(new String(decoder.decode(encodedText), "UTF-8"));
	 
	}
}
