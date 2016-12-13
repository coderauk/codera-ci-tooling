package uk.co.codera.ci.tooling.api.github;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.codera.ci.tooling.api.github.GitHubPushEvents.aValidPushEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEvent;

@RunWith(MockitoJUnitRunner.class)
public class GitHubResourceTest {

    @Mock
    private GitEventListener gitEventListener;

    @Mock
    private GitPushEventAdapter gitPushEventAdapter;

    private GitHubResource resource;

    @Before
    public void before() {
        this.resource = new GitHubResource(gitPushEventAdapter, gitEventListener);
    }

    @Test
    public void shouldLogPushEvent() {
        Logger logger = initResourceWithMockLogger();
        String eventType = GitPushEventAdapter.EVENT_TYPE_CREATE;
        GitHubPushEvent event = aValidPushEvent().build();
        onPush(eventType, event);
        verify(logger).info("Received eventType [{}] for event [{}]", eventType, event);
    }

    @Test
    public void shouldInvokeListenerForBranchEvent() {
        onPush(GitPushEventAdapter.EVENT_TYPE_CREATE, aValidPushEvent().refType(GitHubPushEvent.REF_TYPE_BRANCH).build());
        verify(this.gitEventListener).on(any(GitPushEvent.class));
    }

    @Test
    public void shouldNotInvokeListenerForBranchEvent() {
        onPush(GitPushEventAdapter.EVENT_TYPE_CREATE, aValidPushEvent().refType(GitHubPushEvent.REF_TYPE_TAG).build());
        verify(this.gitEventListener, never()).on(any(GitPushEvent.class));
    }

    @Test
    public void shouldNotInvokeListenerForGitHubPagesEvent() {
        onPush(GitPushEventAdapter.EVENT_TYPE_CREATE, aValidPushEvent().ref(GitHubPushEvent.REF_NAME_GIT_HUB_PAGES).build());
        verify(this.gitEventListener, never()).on(any(GitPushEvent.class));
    }

    @Test
    public void shouldLogWhenIgnoringEventBecauseItIsNotABranch() {
        Logger logger = initResourceWithMockLogger();
        onPush(GitPushEventAdapter.EVENT_TYPE_CREATE, aValidPushEvent().refType(GitHubPushEvent.REF_TYPE_TAG).build());
        verify(logger).info("Ignoring event because it is not related to a branch");
    }

    @Test
    public void shouldLogWhenIgnoringEventBecauseItIsTheGitHubPagesBranch() {
        Logger logger = initResourceWithMockLogger();
        onPush(GitPushEventAdapter.EVENT_TYPE_CREATE, aValidPushEvent().ref(GitHubPushEvent.REF_NAME_GIT_HUB_PAGES).build());
        verify(logger).info("Ignoring event because it is for the github pages branch");
    }

    @Test
    public void shouldUseAdapterToAdaptFromBitBucketToGit() {
        String eventType = GitPushEventAdapter.EVENT_TYPE_CREATE;
        GitHubPushEvent gitHubPushEvent = aValidPushEvent().build();
        GitPushEvent gitPushEvent = GitPushEvent.aGitPushEvent().build();
        when(this.gitPushEventAdapter.from(eventType, gitHubPushEvent)).thenReturn(gitPushEvent);

        onPush(eventType, gitHubPushEvent);

        verify(this.gitEventListener).on(gitPushEvent);
    }

    private void onPush(String eventType, GitHubPushEvent event) {
        this.resource.push(eventType, event);
    }

    private Logger initResourceWithMockLogger() {
        Logger logger = mock(Logger.class);
        this.resource = new GitHubResource(logger, this.gitPushEventAdapter, this.gitEventListener);
        return logger;
    }
}