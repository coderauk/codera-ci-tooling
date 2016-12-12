package uk.co.codera.ci.tooling.application;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.codera.ci.tooling.application.data.TestSonarConfigurations.randomSonarConfiguration;

import org.junit.Test;

public class SonarConfigurationTest {

    @Test
    public void shouldReportWhenNoJobKeyTemplateDefined() {
        assertThat(randomSonarConfiguration().jobKeyTemplate(null).build().hasJobKeyTemplate(), is(false));
    }

    @Test
    public void shouldReportWhenJobKeyTemplateDefined() {
        assertThat(randomSonarConfiguration().jobKeyTemplate("job-key").build().hasJobKeyTemplate(), is(true));
    }
}