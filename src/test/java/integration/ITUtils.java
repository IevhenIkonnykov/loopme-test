package integration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;

import java.util.Arrays;

public class ITUtils {

    public static HttpHeaders getHeadersForUnauthenticatedUser() {

        return getHeadersFor(Base64Utils.encodeToString("username:pass".getBytes()));
    }

    public static HttpHeaders getHeadersForPublisher() {

        return getHeadersFor(Base64Utils.encodeToString("c@c.com:pass".getBytes()));
    }

    public static HttpHeaders getHeadersForOperator() {

        return getHeadersFor(Base64Utils.encodeToString("b@b.com:pass".getBytes()));
    }

    public static HttpHeaders getHeadersForAdmin() {

        return getHeadersFor(Base64Utils.encodeToString("a@a.com:pass".getBytes()));
    }

    private static HttpHeaders getBasicHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Requested-With", "XMLHttpRequest");
        headers.add("Content-Type", "application/json");
        headers.add("Cache-Control", "no-cache");
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        return headers;
    }

    private static HttpHeaders getHeadersFor(String basicAuth) {
        HttpHeaders headers = getBasicHeaders();
        headers.add("Authorization", "Basic " + basicAuth);

        return headers;
    }
}
