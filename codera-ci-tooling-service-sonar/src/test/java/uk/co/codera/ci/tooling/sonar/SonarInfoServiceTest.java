package uk.co.codera.ci.tooling.sonar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import uk.co.codera.lang.io.ClasspathResource;

public class SonarInfoServiceTest extends BaseSonarServiceTestCase {

    @Mock
    private HttpEntity httpEntity;

    private SonarInfoService service;

    @Before
    public void before() throws IOException {
        super.before();
        this.service = new SonarInfoService(this.httpClientFactory, "https://myserver.co.uk/sonar/", "user",
                "password");
        when(this.httpResponse.getEntity()).thenReturn(this.httpEntity);
        when(this.httpEntity.getContent()).thenReturn(new ClasspathResource("/sonar-configuration.json").getAsStream());
    }

    @Test
    public void shouldReturnNonNullObjectWhenInvoked() {
        assertThat(this.service.get(), is(notNullValue()));
    }

    @Test
    public void shouldMapBodyOfResponseToSonarInfo() throws IOException {
        assertThat(this.service.get().getVersion(), is("5.3"));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldConvertExceptionWhenInvokingHttpClientToRuntimeException() throws IOException {
        when(this.httpClient.execute(any())).thenThrow(new IOException());
        this.service.get();
    }

    @Override
    protected void invokeServiceExpectingFailure() {
        this.service.get();
    }
}