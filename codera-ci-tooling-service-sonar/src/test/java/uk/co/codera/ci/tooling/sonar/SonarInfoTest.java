package uk.co.codera.ci.tooling.sonar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SonarInfoTest {

    @Test
    public void shouldSetVersion() {
        assertThat(build(someSonarInfo().version("5.3")).getVersion(), is("5.3"));
    }

    @Test
    public void toStringShouldNotBeObjectReference() {
        assertThat(build(someSonarInfo().version("5.4")).toString(), containsString("version=5.4"));
    }

    private SonarInfo.Builder someSonarInfo() {
        return SonarInfo.someSonarInfo();
    }

    private SonarInfo build(SonarInfo.Builder sonarInfo) {
        return sonarInfo.build();
    }
}