package uk.co.codera.ci.tooling.template;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.codera.ci.tooling.scm.ScmEvent.anScmEvent;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.ci.tooling.scm.ScmEvent;
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
    public void shouldUseJobTemplateWhenProducingJobDetailsForScmEvent() {
        create(anScmEvent());
        verify(this.mockTemplateEngine).merge(eq(TEMPLATE), anyMapOf(String.class, Object.class));
    }

    @Test
    public void shouldPassBranchNameToTemplateEngineFromScmEvent() {
        create(anScmEvent().branchName("feature/AG-123-some-feature-branch"));
        assertThat(passedParameters().get(TemplateService.PARAMETER_BRANCH_NAME),
                is("feature/AG-123-some-feature-branch"));
    }

    @Test
    public void shouldPassShortBranchNameToTemplateEngineFromScmEvent() {
        create(anScmEvent().shortBranchName("some-feature-branch"));
        assertThat(passedParameters().get(TemplateService.PARAMETER_SHORT_BRANCH_NAME), is("some-feature-branch"));
    }

    @Test
    public void shouldPassRepositoryUrlToTemplateEngineFromScmEvent() {
        create(anScmEvent().repositoryUrl("ssh://repo"));
        assertThat(passedParameters().get(TemplateService.PARAMETER_REPOSITORY_URL), is("ssh://repo"));
    }

    @Test
    public void shouldPassProjectNameToTemlpateEngineFromScmEvent() {
        create(anScmEvent().projectName("boatymcboatface"));
        assertThat(passedParameters().get(TemplateService.PARAMETER_PROJECT_NAME), is("boatymcboatface"));
    }

    @Test
    public void shouldReturnResultFromTemplateEngineForScmEvent() {
        String result = whenTemplateEngineReturns("The merged result");
        assertThat(create(anScmEvent()), is(result));
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

    private String create(ScmEvent.Builder event) {
        return this.service.create(event.build());
    }
}