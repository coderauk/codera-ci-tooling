package uk.co.codera.ci.tooling.template;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.codera.ci.tooling.svn.SvnCommitEvent.anSvnCommitEvent;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitReference;
import uk.co.codera.ci.tooling.svn.SvnCommitEvent;
import uk.co.codera.ci.tooling.template.TemplateService;
import uk.co.codera.templating.TemplateEngine;

@RunWith(MockitoJUnitRunner.class)
public class TemplateServiceTest {

    private static final String TEMPLATE = "This is a template";

    @Mock
    private TemplateEngine mockTemplateEngine;

    private TemplateService service;

    @Before
    public void before() {
        this.service = new TemplateService(this.mockTemplateEngine, TEMPLATE);
    }

    @Test
    public void shouldUseJobTemplateWhenProducingJobDetailsForGitPushEvent() {
        create(aGitPushEvent());
        verify(this.mockTemplateEngine).merge(eq(TEMPLATE), anyMapOf(String.class, Object.class));
    }

    @Test
    public void shouldPassBranchNameToTemplateEngineFromGitPushEvent() {
        create(aGitPushEvent().reference(GitReference.from("refs/heads/feature/AG-123-some-feature-branch")));
        assertThat(passedParameters().get(TemplateService.PARAMETER_BRANCH_NAME),
                is("feature/AG-123-some-feature-branch"));
    }

    @Test
    public void shouldPassShortBranchNameToTemplateEngineFromGitPushEvent() {
        create(aGitPushEvent().reference(GitReference.from("refs/heads/feature/some-feature-branch")));
        assertThat(passedParameters().get(TemplateService.PARAMETER_SHORT_BRANCH_NAME), is("some-feature-branch"));
    }

    @Test
    public void shouldPassRepositoryUrlToTemplateEngineFromGitPushEvent() {
        create(aGitPushEvent().repositoryUrl("ssh://repo"));
        assertThat(passedParameters().get(TemplateService.PARAMETER_REPOSITORY_URL), is("ssh://repo"));
    }

    @Test
    public void shouldPassRepositoryNameToTemlpateEngineFromGitPushEvent() {
        create(aGitPushEvent().repositoryName("boatymcboatface"));
        assertThat(passedParameters().get(TemplateService.PARAMETER_REPOSITORY_NAME), is("boatymcboatface"));
    }

    @Test
    public void shouldReturnResultFromTemplateEngineForGitPushEvent() {
        String result = whenTemplateEngineReturns("The merged result");
        assertThat(create(aGitPushEvent()), is(result));
    }

    @Test
    public void shouldUseJobTemplateWhenProducingJobDetailsForSvnCommitEvent() {
        create(anSvnCommitEvent());
        verify(this.mockTemplateEngine).merge(eq(TEMPLATE), anyMapOf(String.class, Object.class));
    }

    @Test
    public void shouldPassBranchNameToTemplateEngineFromSvnCommitEvent() {
        create(anSvnCommitEvent().branchName("my-branch"));
        assertThat(passedParameters().get(TemplateService.PARAMETER_BRANCH_NAME), is("my-branch"));
    }

    @Test
    public void shouldPassShortBranchNameToTemplateEngineFromSvnCommitEvent() {
        create(anSvnCommitEvent().branchName("my-short-branch"));
        assertThat(passedParameters().get(TemplateService.PARAMETER_SHORT_BRANCH_NAME), is("my-short-branch"));
    }

    @Test
    public void shouldPassRepositoryUrlToTemplateEngineFromSvnCommitEvent() {
        create(anSvnCommitEvent().svnLocation("svn://host/project/trunk"));
        assertThat(passedParameters().get(TemplateService.PARAMETER_REPOSITORY_URL), is("svn://host/project/trunk"));
    }

    @Test
    public void shouldPassProjectNameToTemlpateEngineFromSvnEvent() {
        create(anSvnCommitEvent().projectName("boatymcboatface"));
        assertThat(passedParameters().get(TemplateService.PARAMETER_REPOSITORY_NAME), is("boatymcboatface"));
    }

    @Test
    public void shouldReturnResultFromTemplateEngineForSvnCommitEvent() {
        String result = whenTemplateEngineReturns("The merged result");
        assertThat(create(anSvnCommitEvent()), is(result));
    }

    private String whenTemplateEngineReturns(String result) {
        when(this.mockTemplateEngine.merge(any(String.class), anyMapOf(String.class, Object.class))).thenReturn(result);
        return result;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Map<String, Object> passedParameters() {
        ArgumentCaptor<Map> parametersCaptor = ArgumentCaptor.forClass(Map.class);
        verify(this.mockTemplateEngine).merge(any(String.class), parametersCaptor.capture());
        return parametersCaptor.getValue();
    }

    private GitPushEvent.Builder aGitPushEvent() {
        return GitPushEvent.aGitPushEvent().reference(GitReference.from("refs/heads/master"));
    }

    private String create(GitPushEvent.Builder pushEvent) {
        return this.service.create(pushEvent.build());
    }

    private String create(SvnCommitEvent.Builder commitEvent) {
        return this.service.create(commitEvent.build());
    }
}