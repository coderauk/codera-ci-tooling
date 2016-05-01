package uk.co.codera.ci.tooling.sonar;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitPushType;
import uk.co.codera.ci.tooling.git.GitReference;

public class SonarJobDeleterTest extends BaseSonarServiceTestCase {

    private static final String SONAR_URL = "https://someserver.co.uk/sonar/";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password123";

    private static final String REPOSITORY_NAME = "our-repo";
    private static final GitReference GIT_REFERENCE = GitReference.from("refs/heads/some-feature-branch");

    private SonarJobDeleter deleter;

    @Before
    public void before() throws IOException {
        super.before();
        this.deleter = new SonarJobDeleter(this.httpClientFactory, SONAR_URL, USERNAME, PASSWORD);
    }

    @Test
    public void shouldInvokeHttpClientOnPushEvent() {
        push(aDeletePushEvent());
        verifyHttpClientIsInvoked();
    }

    @Test
    public void shouldLogSuccessfulDeletionIfResponseIsSuccess() {
        Logger logger = deleterWithMockLogger();
        whenHttpResponseIsSuccess();
        push(aDeletePushEvent());
        verify(logger).info("Successfully deleted sonar project with key [{}]", expectedSonarProjectKey());
    }

    @Test
    public void shouldLogFailureToDeleteIfResponseIndicatesProjectDidNotExist() {
        Logger logger = deleterWithMockLogger();
        whenHttpResponseIsNotFound();
        push(aDeletePushEvent());
        verify(logger)
                .info("Unable to delete sonar project with key [{}]. Most likely it did not exist or has already been deleted",
                        expectedSonarProjectKey());
    }

    @Test
    public void shouldLogFailureToDeleteForAnyOtherReason() {
        Logger logger = deleterWithMockLogger();
        whenHttpResponseIsServerError();
        push(aDeletePushEvent());
        verify(logger).warn("Unexpected http status code [{}] when trying to delete sonar project with key [{}]", 500,
                expectedSonarProjectKey());
    }

    @Override
    protected void invokeServiceExpectingFailure() {
        push(aDeletePushEvent());
    }

    private Logger deleterWithMockLogger() {
        Logger logger = mock(Logger.class);
        this.deleter = new SonarJobDeleter(logger, this.httpClientFactory, SONAR_URL, USERNAME, PASSWORD);
        return logger;
    }

    private String expectedSonarProjectKey() {
        return REPOSITORY_NAME + ":" + GIT_REFERENCE.shortBranchName();
    }

    private void verifyHttpClientIsInvoked() {
        try {
            verify(this.httpClient).execute(any());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void push(GitPushEvent.Builder event) {
        this.deleter.onPush(event.build());
    }

    private GitPushEvent.Builder aDeletePushEvent() {
        return GitPushEvent.aGitPushEvent().pushType(GitPushType.DELETE).repositoryName(REPOSITORY_NAME)
                .reference(GIT_REFERENCE);
    }
}