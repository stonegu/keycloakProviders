import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ConfigDataCalculator {
	public static void main(String... args) throws UnsupportedEncodingException {
        long date1 = 1548876229 - 1548271429;

        long date2 = 1548431773 - 1547826973;

        System.out.println(date1 / 60 + " mins - access");
        System.out.println(date2 / 60 + " mins - refresh");

        System.out.println("security header: " + createSecureHeader("account", "3367b101-5e83-4d0c-839c-6f826b8f1334"));
		
	}

    public static String createSecureHeader(String username, String password) throws UnsupportedEncodingException {
        return "Basic " + Base64.encodeBase64String((username + ':' + password).getBytes(StandardCharsets.UTF_8));
    }

}
