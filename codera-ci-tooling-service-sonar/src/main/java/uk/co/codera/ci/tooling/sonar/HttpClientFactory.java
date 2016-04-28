package uk.co.codera.ci.tooling.sonar;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClientFactory {

    public CloseableHttpClient create() {
        return HttpClientBuilder.create().build();
    }
}