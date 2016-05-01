package uk.co.codera.ci.tooling.sonar;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

public class AuthHeaderFactory {

    private static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    public String create(String username, String password) {
        String unencodedAuth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(unencodedAuth.getBytes(CHARSET_UTF8));
        return "Basic " + new String(encodedAuth);
    }
}