package uk.co.codera.ci.tooling.jenkins;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static uk.co.codera.ci.tooling.jenkins.DeleteJobCommand.aDeleteJobCommand;
import static uk.co.codera.ci.tooling.jenkins.JenkinsConfiguration.aJenkinsConfiguration;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import uk.co.codera.ci.tooling.jenkins.DeleteJobCommand;
import uk.co.codera.ci.tooling.jenkins.JenkinsCommandLineInterfaceInvoker;
import uk.co.codera.ci.tooling.jenkins.JenkinsConfiguration;

public class DeleteJobCommandTest {

	private JenkinsConfiguration configuration;
	private String jenkinsServerUrl;

	@Before
	public void before() {
		this.jenkinsServerUrl = randomString();
		this.configuration = aJenkinsConfiguration().serverUrl(
				this.jenkinsServerUrl).build();
	}

	@Test
	public void shouldHaveJenkinsServerUrlInArguments() {
		String[] arguments = arguments(aValidDeleteJobCommand());
		assertThat(arguments[0], is("-s"));
		assertThat(arguments[1], is(this.jenkinsServerUrl));
	}

	@Test
	public void shouldHaveCorrectCommandNameInArguments() {
		String[] arguments = arguments(aValidDeleteJobCommand());
		assertThat(arguments[2], is("delete-job"));
	}

	@Test
	public void shouldHaveJobNameInArguments() {
		String[] arguments = arguments(aValidDeleteJobCommand().jobName(
				"job-name"));
		assertThat(arguments[3], is("job-name"));
	}

	@Test
	public void shouldInvokeCliInvokerWhenExecuted() {
		JenkinsCommandLineInterfaceInvoker cliInvoker = mock(JenkinsCommandLineInterfaceInvoker.class);
		aValidDeleteJobCommand().build().execute(cliInvoker);
		verify(cliInvoker).invoke(anyVararg());
	}

	private DeleteJobCommand.Builder aValidDeleteJobCommand() {
		return aDeleteJobCommand().with(this.configuration).jobName(
				randomString());
	}

	private String randomString() {
		return UUID.randomUUID().toString();
	}

	private String[] arguments(DeleteJobCommand.Builder command) {
		return command.build().getArguments();
	}
}