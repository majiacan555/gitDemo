package Globaltimes;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import LiberyUtils.LiberyCache;

public class CommonUtils {
  public static String GetWordString(String searchWord) {
		String wordString = "";
		if (searchWord.contains(" ")) {
			String[] strsString = searchWord.split(" ");
			for (int i = 0; i < strsString.length; i++) {
				if (i == strsString.length - 1)
					wordString += strsString[i];
				else
					wordString += strsString[i] + "+";
			}
		}
		return wordString;
	}
  
}
