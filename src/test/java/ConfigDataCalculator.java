import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class ConfigDataCalculator {
	public static void main(String... args) throws UnsupportedEncodingException {
        long date1 = 1548876229 - 1548271429;

        long date2 = 1548431773 - 1547826973;

        System.out.println(date1 / 60 + " mins - access");
        System.out.println(date2 / 60 + " mins - refresh");

        System.out.println("security header: " + createSecureHeader("account", "54e7c4bd-7f1f-46f4-aea2-54bfd50d30bd"));
		
	}

    public static String createSecureHeader(String username, String password) throws UnsupportedEncodingException {
        return "Basic " + Base64.encodeBase64String((username + ':' + password).getBytes(StandardCharsets.UTF_8));
    }

}
