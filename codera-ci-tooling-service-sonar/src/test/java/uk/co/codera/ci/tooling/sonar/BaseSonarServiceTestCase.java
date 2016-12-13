package uk.co.codera.ci.tooling.sonar;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@Ignore("Base class for any sonar service tests")
@RunWith(MockitoJUnitRunner.class)
public abstract class BaseSonarServiceTestCase {

    @Mock
    HttpClientFactory httpClientFactory;

    @Mock
    CloseableHttpClient httpClient;

    @Mock
    CloseableHttpResponse httpResponse;

    @Before
    public void before() throws IOException {
        when(this.httpClientFactory.create()).thenReturn(this.httpClient);
        whenHttpClientIsExecutedThenReturnMockHttpResponse();
        whenHttpResponseIsSuccess();
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailWhenNullHttpClientReturned() {
        when(this.httpClientFactory.create()).thenReturn(null);
        invokeServiceExpectingFailure();
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailWhenNullHttpResponseReturned() throws IOException {
        when(this.httpClient.execute(any())).thenReturn(null);
        invokeServiceExpectingFailure();
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailWhenNullHttpResponseReturnedAndCheckedExceptionThrownClosingHttpClient() throws IOException {
        when(this.httpClient.execute(any())).thenReturn(null);
        doThrow(new IOException()).when(this.httpClient).close();
        invokeServiceExpectingFailure();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldMapCheckedExceptionDuringHttpRequestToRuntimeException() throws IOException {
        when(this.httpClient.execute(any())).thenThrow(new IOException());
        invokeServiceExpectingFailure();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldMapCheckedExceptionDuringClosingOfHttpClientToRuntimeException() throws IOException {
        doThrow(new IOException()).when(this.httpClient).close();
        invokeServiceExpectingFailure();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldMapCheckedExceptionDuringHttpRequestAndClosingOfHttpClientToRuntimeException() throws IOException {
        when(this.httpClient.execute(any())).thenThrow(new IOException());
        doThrow(new IOException()).when(this.httpClient).close();
        invokeServiceExpectingFailure();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldMapCheckedExceptionDuringClosingOfHttpResponseToRuntimeException() throws IOException {
        doThrow(new IOException()).when(this.httpResponse).close();
        invokeServiceExpectingFailure();
    }

    @Test(expected = IllegalStateException.class)
    public void shouldMapCheckedExceptionDuringHttpRequestAndClosingOfHttpResponseToRuntimeException() throws IOException {
        when(this.httpClient.execute(any())).thenThrow(new IOException());
        doThrow(new IOException()).when(this.httpResponse).close();
        invokeServiceExpectingFailure();
    }

    protected abstract void invokeServiceExpectingFailure();

    protected void whenHttpResponseIsSuccess() {
        whenHttpResponseIsStatusCode(204);
    }

    protected void whenHttpResponseIsNotFound() {
        whenHttpResponseIsStatusCode(404);
    }

    protected void whenHttpResponseIsServerError() {
        whenHttpResponseIsStatusCode(500);
    }

    protected void whenHttpResponseIsStatusCode(int statusCode) {
        StatusLine statusLine = mock(StatusLine.class);
        when(statusLine.getStatusCode()).thenReturn(statusCode);
        when(this.httpResponse.getStatusLine()).thenReturn(statusLine);
    }

    protected void whenHttpClientIsExecutedThenReturnMockHttpResponse() {
        try {
            when(this.httpClient.execute(Mockito.any())).thenReturn(this.httpResponse);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}