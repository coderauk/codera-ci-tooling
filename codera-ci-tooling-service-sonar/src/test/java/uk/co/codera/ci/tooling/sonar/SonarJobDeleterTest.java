package uk.co.codera.ci.tooling.sonar;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitPushType;
import uk.co.codera.ci.tooling.git.GitReference;
import uk.co.codera.ci.tooling.template.TemplateService;

@RunWith(MockitoJUnitRunner.class)
public class SonarJobDeleterTest {

    private static final String REPOSITORY_NAME = "our-repo";
    private static final GitReference GIT_REFERENCE = GitReference.from("refs/heads/some-feature-branch");

    private SonarJobDeleter deleter;

    @Mock
    private TemplateService templateService;

    @Mock
    private SonarDeleteService deleteService;

    @Before
    public void before() {
        this.deleter = new SonarJobDeleter(this.templateService, this.deleteService);
    }

    @Test
    public void shouldInvokeDeleteServiceWithCorrectKeyOnPushEvent() {
        push(aDeletePushEvent());
        verify(this.deleteService).deleteJob(any());
    }

    @Test
    public void shouldPassPushEventToTemplateServiceToCreateJobKey() {
        GitPushEvent event = aDeletePushEvent().build();
        push(event);
        verify(this.templateService).create(event);
    }

    @Test
    public void shouldPassGeneratedJobKeyToDeleteService() {
        when(this.templateService.create(any())).thenReturn("sonar:key");
        push(aDeletePushEvent());
        verify(this.deleteService).deleteJob("sonar:key");
    }

    private void push(GitPushEvent.Builder event) {
        push(event.build());
    }

    private void push(GitPushEvent event) {
        this.deleter.onPush(event);
    }

    private GitPushEvent.Builder aDeletePushEvent() {
        return GitPushEvent.aGitPushEvent().pushType(GitPushType.DELETE).repositoryName(REPOSITORY_NAME)
                .reference(GIT_REFERENCE);
    }
}