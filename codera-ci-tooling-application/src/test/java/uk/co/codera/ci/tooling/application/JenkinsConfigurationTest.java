package uk.co.codera.ci.tooling.application;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.codera.ci.tooling.application.data.TestJenkinsConfigurations.randomJenkinsConfiguration;

import org.junit.Test;

public class JenkinsConfigurationTest {

    @Test
    public void shouldDefaultJenkinsJobNameTemplateIfNotSet() {
        assertThat(randomJenkinsConfiguration().jobNameTemplate(null).build().getJobNameTemplate(), is("${projectName} - ${shortBranchName} - build"));
    }

    @Test
    public void willUseValueSetForJobNameTemplate() {
        assertThat(randomJenkinsConfiguration().jobNameTemplate("bob").build().getJobNameTemplate(), is("bob"));
    }
}