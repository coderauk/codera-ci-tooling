package uk.co.codera.ci.tooling.sonar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SonarInfoTest {

    @Test
    public void shouldSetVersion() {
        assertThat(SonarInfo.someSonarInfo().version("5.3").build().getVersion(), is("5.3"));
    }
}