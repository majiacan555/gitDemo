package Champollion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class CommonCache {

	public static void TextSplit(List<String> strList, String splitType, String savePath) {
		String string = "";
		try {
			for (String s : strList) {
				if ((!s.equals(" ")) && (!s.equals("  ")) && (!s.equals("        ")) && s.length() > 1) {
					string += s;
				}
			}
			FileOutputStream fop = null;
			File file = new File(savePath);
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			if (!file.exists()) {
				file.createNewFile();
			}
			fop = new FileOutputStream(file);
			String type = splitType;
			if (type.equals(".")) {
				type = "\\" + type;
			}
			String[] resultSpilt = string.split(type);
			String result = "";
			for (int i = 0; i < resultSpilt.length - 1; i++) {
				if (resultSpilt[i].length() > 1)
					result += resultSpilt[i] + splitType + "\r\n";
			}
			result += resultSpilt[resultSpilt.length - 1] + splitType;
			byte[] contentInBytes = result.getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
			System.out.println("Done");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
