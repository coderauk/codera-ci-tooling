package uk.co.codera.ci.tooling.jenkins;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static uk.co.codera.ci.tooling.jenkins.CreateJobCommand.aCreateJobCommand;
import static uk.co.codera.ci.tooling.jenkins.JenkinsConnectionDetails.aJenkinsConfiguration;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import uk.co.codera.ci.tooling.jenkins.CreateJobCommand;
import uk.co.codera.ci.tooling.jenkins.JenkinsCommandLineInterfaceInvoker;
import uk.co.codera.ci.tooling.jenkins.JenkinsConnectionDetails;

public class CreateJobCommandTest {

    private JenkinsConnectionDetails configuration;
    private String jenkinsServerUrl;

    @Before
    public void before() {
        this.jenkinsServerUrl = randomString();
        this.configuration = aJenkinsConfiguration().serverUrl(this.jenkinsServerUrl).build();
    }

    @Test
    public void shouldHaveJenkinsServerUrlInArguments() {
        String[] arguments = arguments(aValidCreateJobCommand());
        assertThat(arguments[0], is("-s"));
        assertThat(arguments[1], is(this.jenkinsServerUrl));
    }

    @Test
    public void shouldHaveCorrectCommandNameInArguments() {
        String[] arguments = arguments(aValidCreateJobCommand());
        assertThat(arguments[2], is("create-job"));
    }

    @Test
    public void shouldHaveJobNameInArguments() {
        String[] arguments = arguments(aValidCreateJobCommand().jobName("job-name"));
        assertThat(arguments[3], is("job-name"));
    }

    @Test
    public void shouldInvokeCliInvokerWhenExecuted() {
        JenkinsCommandLineInterfaceInvoker cliInvoker = mock(JenkinsCommandLineInterfaceInvoker.class);
        aValidCreateJobCommand().build().execute(cliInvoker);
        verify(cliInvoker).invoke(anyVararg());
    }

    private CreateJobCommand.Builder aValidCreateJobCommand() {
        return aCreateJobCommand().with(this.configuration).jobName(randomString()).jobDefinition(randomString());
    }

    private String randomString() {
        return UUID.randomUUID().toString();
    }

    private String[] arguments(CreateJobCommand.Builder command) {
        return command.build().getArguments();
    }
}