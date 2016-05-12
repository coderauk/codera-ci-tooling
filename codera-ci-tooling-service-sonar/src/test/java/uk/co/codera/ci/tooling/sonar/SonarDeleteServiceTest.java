package uk.co.codera.ci.tooling.sonar;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

public class SonarDeleteServiceTest extends BaseSonarServiceTestCase {

    private static final String SONAR_URL = "https://someserver.co.uk/sonar/";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password123";

    private SonarDeleteService deleteService;

    @Before
    public void before() throws IOException {
        super.before();
        this.deleteService = new SonarDeleteService(this.httpClientFactory, SONAR_URL, USERNAME, PASSWORD);
    }

    @Test
    public void shouldInvokeHttpClientOnPushEvent() {
        delete();
        verifyHttpClientIsInvoked();
    }

    @Test
    public void shouldLogSuccessfulDeletionIfResponseIsSuccess() {
        Logger logger = deleterWithMockLogger();
        whenHttpResponseIsSuccess();
        delete("bob");
        verify(logger).info("Successfully deleted sonar project with key [{}]", "bob");
    }

    @Test
    public void shouldLogFailureToDeleteIfResponseIndicatesProjectDidNotExist() {
        Logger logger = deleterWithMockLogger();
        whenHttpResponseIsNotFound();
        delete("carly");
        verify(logger)
                .info("Unable to delete sonar project with key [{}]. Most likely it did not exist or has already been deleted",
                        "carly");
    }

    @Test
    public void shouldLogFailureToDeleteForAnyOtherReason() {
        Logger logger = deleterWithMockLogger();
        whenHttpResponseIsServerError();
        delete("simon");
        verify(logger).warn("Unexpected http status code [{}] when trying to delete sonar project with key [{}]", 500,
                "simon");
    }

    @Override
    protected void invokeServiceExpectingFailure() {
        delete();
    }

    private Logger deleterWithMockLogger() {
        Logger logger = mock(Logger.class);
        this.deleteService = new SonarDeleteService(logger, this.httpClientFactory, SONAR_URL, USERNAME, PASSWORD);
        return logger;
    }

    private void verifyHttpClientIsInvoked() {
        try {
            verify(this.httpClient).execute(any());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void delete() {
        delete(UUID.randomUUID().toString());
    }

    private void delete(String key) {
        this.deleteService.deleteJob(key);
    }
}