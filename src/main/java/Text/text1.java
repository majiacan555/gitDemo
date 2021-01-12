package Text;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.bouncycastle.jce.provider.asymmetric.ec.Signature.ecCVCDSA;

public class text1 {
	public static void main(String[] args) {
		String strNum = "15223475421364";
		String sortNum = GetSortNum(strNum);
		System.out.println("排序前："+strNum );
		System.out.println("排序后："+sortNum ); 
    } 
    private static String GetSortNum(String strNum ) {
    	String newString = "";
    	Map<Double, Integer> treeMap = new TreeMap<Double, Integer>();
		for (int index = 0; index < strNum.length(); index++) {
			char c = strNum.charAt(index); //循环得出字符串里的每个字符  
			int num = Integer.parseInt(String.valueOf(c)); //将char转为String格式 再转为int
			double copyNum = num;
			while (treeMap.containsKey(copyNum)) //如果treeMap已存在相同的key  则将key+0.000001 防止重复的key被替换
				copyNum+=0.00000001;  
			treeMap.put(copyNum, num);
		}  
			for (int num : treeMap.values())  
				newString += num;  
	    return newString;
    }
}
