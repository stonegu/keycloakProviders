import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ConfigDataCalculator {
	public static void main(String... args) throws UnsupportedEncodingException {
        long date1 = 1607293659 - 1607293299;

        long date2 = 1548431773 - 1547826973;

        System.out.println(date1 / 60 + " mins - access");
        System.out.println(date2 / 60 + " mins - refresh");

        System.out.println("security header: " + createSecureHeader("vanilla", "7dfc84a0-7803-4c28-87da-0574bdc96afe"));
		
	}

    public static String createSecureHeader(String username, String password) throws UnsupportedEncodingException {
        return "Basic " + Base64.encodeBase64String((username + ':' + password).getBytes(StandardCharsets.UTF_8));
    }

}
