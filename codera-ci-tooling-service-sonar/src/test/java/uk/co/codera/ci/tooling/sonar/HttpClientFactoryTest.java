package uk.co.codera.ci.tooling.sonar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class HttpClientFactoryTest {

    private HttpClientFactory factory;

    @Before
    public void before() {
        this.factory = new HttpClientFactory();
    }

    @Test
    public void shouldCreateNonNullClient() {
        assertThat(this.factory.create(), is(notNullValue()));
    }
}