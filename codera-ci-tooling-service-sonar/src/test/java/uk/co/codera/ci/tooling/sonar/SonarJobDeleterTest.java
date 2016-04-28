package uk.co.codera.ci.tooling.sonar;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitPushType;
import uk.co.codera.ci.tooling.git.GitReference;

@RunWith(MockitoJUnitRunner.class)
public class SonarJobDeleterTest {

    private static final String SONAR_URL = "https://someserver.co.uk/sonar/";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password123";

    private static final String REPOSITORY_NAME = "our-repo";
    private static final GitReference GIT_REFERENCE = GitReference.from("refs/heads/some-feature-branch");

    @Mock
    private HttpClientFactory httpClientFactory;

    @Mock
    private CloseableHttpClient httpClient;

    @Mock
    private CloseableHttpResponse httpResponse;

    private SonarJobDeleter deleter;

    @Before
    public void before() {
        this.deleter = new SonarJobDeleter(this.httpClientFactory, SONAR_URL, USERNAME, PASSWORD);
        when(this.httpClientFactory.create()).thenReturn(this.httpClient);
        whenHttpClientIsExecutedThenReturnMockHttpResponse();
        whenHttpResponseIsSuccess();
    }

    @Test
    public void shouldInvokeHttpClientOnPushEvent() {
        push(aDeletePushEvent());
        verifyHttpClientIsInvoked();
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailWhenNullHttpClientReturned() {
        when(this.httpClientFactory.create()).thenReturn(null);
        push(aDeletePushEvent());
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailWhenNullHttpResponseReturned() throws IOException {
        when(this.httpClient.execute(any())).thenReturn(null);
        push(aDeletePushEvent());
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailWhenNullHttpResponseReturnedAndCheckedExceptionThrownClosingHttpClient() throws IOException {
        when(this.httpClient.execute(any())).thenReturn(null);
        doThrow(new IOException()).when(this.httpClient).close();
        push(aDeletePushEvent());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldMapCheckedExceptionDuringHttpRequestToRuntimeException() throws IOException {
        when(this.httpClient.execute(any())).thenThrow(new IOException());
        push(aDeletePushEvent());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldMapCheckedExceptionDuringClosingOfHttpClientToRuntimeException() throws IOException {
        doThrow(new IOException()).when(this.httpClient).close();
        push(aDeletePushEvent());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldMapCheckedExceptionDuringHttpRequestAndClosingOfHttpClientToRuntimeException() throws IOException {
        when(this.httpClient.execute(any())).thenThrow(new IOException());
        doThrow(new IOException()).when(this.httpClient).close();
        push(aDeletePushEvent());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldMapCheckedExceptionDuringClosingOfHttpResponseToRuntimeException() throws IOException {
        doThrow(new IOException()).when(this.httpResponse).close();
        push(aDeletePushEvent());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldMapCheckedExceptionDuringHttpRequestAndClosingOfHttpResponseToRuntimeException()
            throws IOException {
        when(this.httpClient.execute(any())).thenThrow(new IOException());
        doThrow(new IOException()).when(this.httpResponse).close();
        push(aDeletePushEvent());
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

    private void whenHttpResponseIsSuccess() {
        whenHttpResponseIsStatusCode(204);
    }

    private void whenHttpResponseIsNotFound() {
        whenHttpResponseIsStatusCode(404);
    }

    private void whenHttpResponseIsServerError() {
        whenHttpResponseIsStatusCode(500);
    }

    private void whenHttpResponseIsStatusCode(int statusCode) {
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(statusCode);
        when(this.httpResponse.getStatusLine()).thenReturn(statusLine);
    }

    private void whenHttpClientIsExecutedThenReturnMockHttpResponse() {
        try {
            when(this.httpClient.execute(Mockito.any())).thenReturn(this.httpResponse);
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