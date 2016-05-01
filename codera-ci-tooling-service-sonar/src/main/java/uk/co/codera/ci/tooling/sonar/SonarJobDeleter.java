package uk.co.codera.ci.tooling.sonar;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEvent;

public class SonarJobDeleter extends AbstractSonarService<String, Void> implements GitEventListener {

    private static final String ENDPOINT_URL = "api/projects/delete?key=%s";

    @SuppressWarnings("squid:S1312")
    private final Logger logger;

    public SonarJobDeleter(HttpClientFactory httpClientFactory, String sonarUrl, String username, String password) {
        this(LoggerFactory.getLogger(SonarJobDeleter.class), httpClientFactory, sonarUrl, username, password);
    }

    public SonarJobDeleter(Logger logger, HttpClientFactory httpClientFactory, String sonarUrl, String username,
            String password) {
        super(httpClientFactory, sonarUrl + ENDPOINT_URL, username, password);
        this.logger = logger;
    }

    @Override
    public void onPush(GitPushEvent event) {
        execute(event.getRepositoryName() + ":" + event.getReference().shortBranchName());
    }

    @Override
    HttpUriRequest createRequest(String key) {
        return new HttpPost(String.format(url(), key));
    }

    @Override
    Void processResponse(String key, HttpResponse response) {
        int statusCode = response.getStatusLine().getStatusCode();
        logResult(key, statusCode);
        return null;
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
}