package uk.co.codera.ci.tooling.sonar;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

abstract class AbstractSonarService<C, R> {

    private static final AuthHeaderFactory AUTH_HEADER_FACTORY = new AuthHeaderFactory();

    private final HttpClientFactory httpClientFactory;
    private final String url;
    private final String authHeader;

    public AbstractSonarService(HttpClientFactory httpClientFactory, String url, String username, String password) {
        this.httpClientFactory = httpClientFactory;
        this.url = url;
        this.authHeader = AUTH_HEADER_FACTORY.create(username, password);
    }

    R execute(C context) {
        try {
            return executeWithPossibleCheckedException(context);
        } catch (IOException e) {
            throw new IllegalStateException("Problem executing", e);
        }
    }

    String url() {
        return this.url;
    }

    abstract HttpUriRequest createRequest(C context);

    abstract R processResponse(C context, HttpResponse response) throws IOException;

    private R executeWithPossibleCheckedException(C context) throws IOException {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = httpClient();
            return executeRequest(context, httpClient);
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
    }

    private R executeRequest(C context, CloseableHttpClient httpClient) throws IOException {
        HttpUriRequest request = createRequest(context);
        addAuthentication(request);
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
            return processResponse(context, response);
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private void addAuthentication(HttpUriRequest request) {
        request.setHeader(HttpHeaders.AUTHORIZATION, this.authHeader);
    }

    private CloseableHttpClient httpClient() {
        return this.httpClientFactory.create();
    }
}