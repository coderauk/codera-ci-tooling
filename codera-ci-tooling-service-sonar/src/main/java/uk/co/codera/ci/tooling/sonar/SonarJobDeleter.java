package uk.co.codera.ci.tooling.sonar;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEvent;

public class SonarJobDeleter implements GitEventListener {

    private static final String ENDPOINT_URL = "api/projects/delete?key=%s";

    @SuppressWarnings("squid:S1312")
    private final Logger logger;
    private final HttpClientFactory httpClientFactory;
    private final String urlTemplate;
    private final String authHeader;

    public SonarJobDeleter(HttpClientFactory httpClientFactory, String sonarUrl, String username, String password) {
        this(LoggerFactory.getLogger(SonarJobDeleter.class), httpClientFactory, sonarUrl, username, password);
    }

    public SonarJobDeleter(Logger logger, HttpClientFactory httpClientFactory, String sonarUrl, String username,
            String password) {
        this.logger = logger;
        this.httpClientFactory = httpClientFactory;
        this.urlTemplate = sonarUrl + ENDPOINT_URL;
        this.authHeader = AuthHeaderFactory.create(username, password);
    }

    @Override
    public void onPush(GitPushEvent event) {
        execute(event.getRepositoryName() + ":" + event.getReference().shortBranchName());
    }

    private void execute(String key) {
        try {
            executeWithPossibleCheckedException(key);
        } catch (IOException e) {
            throw new IllegalStateException("Problem executing", e);
        }
    }

    private void executeWithPossibleCheckedException(String key) throws IOException {
        CloseableHttpClient httpClient = null;
        try {
            httpClient = httpClient();
            executeRequest(key, httpClient);
        } finally {
            if (httpClient != null) {
                httpClient.close();
            }
        }
    }

    private void executeRequest(String key, CloseableHttpClient httpClient) throws IOException {
        HttpPost httpPost = httpPost(key);
        CloseableHttpResponse httpResponse = null;

        try {
            httpResponse = httpClient.execute(httpPost);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            logResult(key, statusCode);
        } finally {
            if (httpResponse != null) {
                httpResponse.close();
            }
        }
    }

    private HttpPost httpPost(String key) {
        HttpPost httpPost = new HttpPost(String.format(this.urlTemplate, key));
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, this.authHeader);
        return httpPost;
    }

    private void logResult(String key, int statusCode) {
        switch (statusCode) {
        case 204:
            logger.info("Successfully deleted sonar project with key [{}]", key);
            break;
        case 404:
            logger.info(
                    "Unable to delete sonar project with key [{}]. Most likely it did not exist or has already been deleted",
                    key);
            break;
        default:
            logger.warn("Unexpected http status code [{}] when trying to delete sonar project with key [{}]",
                    statusCode, key);
        }
    }

    private CloseableHttpClient httpClient() {
        return this.httpClientFactory.create();
    }
}