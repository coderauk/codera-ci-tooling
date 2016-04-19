package uk.co.codera.ci.tooling.jenkins;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitReference;
import uk.co.codera.ci.tooling.jenkins.JenkinsTemplateService;
import uk.co.codera.templating.TemplateEngine;

@RunWith(MockitoJUnitRunner.class)
public class JenkinsTemplateServiceTest {

	private static final String TEMPLATE = "This is a template";

	@Mock
	private TemplateEngine mockTemplateEngine;

	private JenkinsTemplateService service;

	@Before
	public void before() {
		this.service = new JenkinsTemplateService(this.mockTemplateEngine,
				TEMPLATE);
	}

	@Test
	public void shouldUseJobTemplateWhenProducingJobDetails() {
		create(aGitPushEvent());
		verify(this.mockTemplateEngine).merge(eq(TEMPLATE),
				anyMapOf(String.class, Object.class));
	}

	@Test
	public void shouldPassBranchNameToTemplateEngine() {
		create(aGitPushEvent().reference(
				GitReference
						.from("refs/heads/feature/AG-123-some-feature-branch")));
		assertThat(
				passedParameters().get(
						JenkinsTemplateService.PARAMETER_BRANCH_NAME),
				is("feature/AG-123-some-feature-branch"));
	}

	@Test
	public void shouldPassShortBranchNameToTemplateEngine() {
		create(aGitPushEvent().reference(
				GitReference.from("refs/heads/feature/some-feature-branch")));
		assertThat(
				passedParameters().get(
						JenkinsTemplateService.PARAMETER_SHORT_BRANCH_NAME),
				is("some-feature-branch"));
	}

	@Test
	public void shouldPassRepositoryUrlToTemplateEngine() {
		create(aGitPushEvent().repositoryUrl("ssh://repo"));
		assertThat(
				passedParameters().get(
						JenkinsTemplateService.PARAMETER_REPOSITORY_URL),
				is("ssh://repo"));
	}

	@Test
	public void shouldPassRepositoryNameToTemlpateEngine() {
		create(aGitPushEvent().repositoryName("boatymcboatface"));
		assertThat(
				passedParameters().get(
						JenkinsTemplateService.PARAMETER_REPOSITORY_NAME),
				is("boatymcboatface"));
	}

	@Test
	public void shouldReturnResultFromTemplateEngine() {
		String result = "The merged result";
		when(
				this.mockTemplateEngine.merge(any(String.class),
						anyMapOf(String.class, Object.class))).thenReturn(
				result);
		assertThat(create(aGitPushEvent()), is(result));
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private Map<String, Object> passedParameters() {
		ArgumentCaptor<Map> parametersCaptor = ArgumentCaptor
				.forClass(Map.class);
		verify(this.mockTemplateEngine).merge(any(String.class),
				parametersCaptor.capture());
		return parametersCaptor.getValue();
	}

	private GitPushEvent.Builder aGitPushEvent() {
		return GitPushEvent.aGitPushEvent().reference(
				GitReference.from("refs/heads/master"));
	}

	private String create(GitPushEvent.Builder pushEvent) {
		return this.service.create(pushEvent.build());
	}
}