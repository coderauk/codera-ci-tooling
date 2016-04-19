package uk.co.codera.ci.tooling.jenkins;

import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.ci.tooling.jenkins.JenkinsCommandLineInterfaceInvoker;
import uk.co.codera.ci.tooling.jenkins.JenkinsConfiguration;
import uk.co.codera.ci.tooling.jenkins.JenkinsService;

@RunWith(MockitoJUnitRunner.class)
public class JenkinsServiceTest {

	private String serverUrl;
	private JenkinsConfiguration configuration;

	@Mock
	private JenkinsCommandLineInterfaceInvoker cliInvoker;

	private JenkinsService service;

	@Before
	public void before() {
		this.serverUrl = randomString();
		this.configuration = JenkinsConfiguration.aJenkinsConfiguration()
				.serverUrl(this.serverUrl).build();
		this.service = new JenkinsService(this.configuration, this.cliInvoker);
	}

	@Test
	public void createJobShouldInvokeJenkinsCli() {
		this.service.createJob(randomString(), randomString());
		verify(this.cliInvoker).invoke(anyVararg());
	}

	@Test
	public void deleteJobShouldInvokeJenkinsCli() {
		this.service.deleteJob(randomString());
		verify(this.cliInvoker).invoke(anyVararg());
	}

	private String randomString() {
		return UUID.randomUUID().toString();
	}
}