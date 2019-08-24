import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class RealmExportUpdate {
	public static void main (String... args) {
		
		String oldRealmName = "BIZ";
		String newRealmName = "DEMO";
		
		Set<String> oldIds = new HashSet<>();
		List<String> newIds = new ArrayList<>();
		
		// read realm-export.json file for BIZ realm
		BufferedReader bufferedReader = null;
		try {
			StringBuffer sb = new StringBuffer();
			String strCurrentLine;
			bufferedReader = new BufferedReader(new FileReader("C:\\Users\\stone\\Downloads\\realm-export.json"));
			while ((strCurrentLine = bufferedReader.readLine()) != null) {
				if (StringUtils.indexOf(strCurrentLine, "id\":") > -1) {
					String[] idInArray = strCurrentLine.split("\"");
					String id = idInArray[idInArray.length - 2];
					if (!oldRealmName.equals(id)) {
						oldIds.add(id);
					}
				}
				sb.append(strCurrentLine).append("\n");
			}
			
			// generate new uuid for old uuid
			for (int i = 0; i < oldIds.size(); i++) {
				newIds.add(UUID.randomUUID().toString());
			}
			
//			System.out.println(oldIds);
//			System.out.println(newIds);
			
			
			// update old realm name & old uuid
			String originalStr = sb.toString();
			
			String newStr = originalStr.replaceAll(oldRealmName, newRealmName);
			newStr = newStr.replaceAll(oldRealmName.toLowerCase(), newRealmName.toLowerCase());
			int idx = 0;
			for (String oldId : oldIds) {
				newStr = newStr.replaceAll(oldId, newIds.get(idx));
				idx = idx + 1;
			}
			
			System.out.println(newStr);
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		
	}

}
