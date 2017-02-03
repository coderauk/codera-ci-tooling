package uk.co.codera.ci.tooling.application;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static uk.co.codera.ci.tooling.application.SvnConfiguration.someSvnConfiguration;

import org.junit.Test;

public class SvnConfigurationTest {

    @Test
    public void shouldReturnNullWhenPortNotSet() {
        assertThat(someSvnConfiguration().build().getPort(), is(nullValue()));
    }

    @Test
    public void shouldBeAbleToSetPort() {
        assertThat(someSvnConfiguration().port(Integer.valueOf(342)).build().getPort(), is(342));
    }
}