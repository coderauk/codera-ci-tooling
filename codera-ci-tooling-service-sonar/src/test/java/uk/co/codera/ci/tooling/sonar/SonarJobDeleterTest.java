package uk.co.codera.ci.tooling.sonar;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitPushType;
import uk.co.codera.ci.tooling.git.GitReference;

@RunWith(MockitoJUnitRunner.class)
public class SonarJobDeleterTest {

    private static final String REPOSITORY_NAME = "our-repo";
    private static final GitReference GIT_REFERENCE = GitReference.from("refs/heads/some-feature-branch");

    private SonarJobDeleter deleter;

    @Mock
    private SonarDeleteService deleteService;

    @Before
    public void before() {
        this.deleter = new SonarJobDeleter(this.deleteService);
    }

    @Test
    public void shouldInvokeDeleteServiceWithCorrectKeyOnPushEvent() {
        push(aDeletePushEvent());
        verify(this.deleteService).deleteJob(expectedSonarProjectKey());
    }

    private String expectedSonarProjectKey() {
        return REPOSITORY_NAME + ":" + GIT_REFERENCE.shortBranchName();
    }

    private void push(GitPushEvent.Builder event) {
        this.deleter.onPush(event.build());
    }

    private GitPushEvent.Builder aDeletePushEvent() {
        return GitPushEvent.aGitPushEvent().pushType(GitPushType.DELETE).repositoryName(REPOSITORY_NAME)
                .reference(GIT_REFERENCE);
    }
}