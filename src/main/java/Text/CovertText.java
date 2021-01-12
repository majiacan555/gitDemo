package Text; 
import org.bouncycastle.jce.provider.asymmetric.ec.Signature.ecCVCDSA;

import com.spreada.utils.chinese.ChineseCoverter;
import com.spreada.utils.chinese.ZHConverter;  
public class CovertText {
	public static void main(String[] args) { 
	    String traditionalSrc = "ÐÐÒµ"; 
	    String a  =  ChineseCoverter.SinpleToTraditional(traditionalSrc);
	    System.out.println(a );
	}
	 
}
