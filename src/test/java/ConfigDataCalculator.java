import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ConfigDataCalculator {
	public static void main(String... args) throws UnsupportedEncodingException {
        long date1 = 1548876229 - 1548271429;

        long date2 = 1548431773 - 1547826973;

        System.out.println(date1 / 60 + " mins - access");
        System.out.println(date2 / 60 + " mins - refresh");

        System.out.println("security header: " + createSecureHeader("account", "4fbdf446-1807-4f84-9e2b-de338e4cebe2"));
		
	}

    public static String createSecureHeader(String username, String password) throws UnsupportedEncodingException {
        return "Basic " + Base64.encodeBase64String((username + ':' + password).getBytes(StandardCharsets.UTF_8));
    }

}
