package uk.co.codera.ci.tooling.api.bitbucket;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import uk.co.codera.ci.tooling.api.bitbucket.BitBucketResource;
import uk.co.codera.ci.tooling.api.bitbucket.GitPushEventAdapter;
import uk.co.codera.ci.tooling.api.bitbucket.PushEvent;
import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitPushType;
import uk.co.codera.ci.tooling.git.GitReference;

@RunWith(MockitoJUnitRunner.class)
public class BitBucketResourceTest {

    @Mock
    private GitEventListener gitEventListener;

    @Mock
    private GitPushEventAdapter gitPushEventAdapter;

    private BitBucketResource resource;

    @Before
    public void before() {
        this.resource = new BitBucketResource(gitPushEventAdapter, gitEventListener);
        Mockito.when(this.gitPushEventAdapter.from(any(PushEvent.class))).thenReturn(aGitBranchPushEvent());
    }

    @Test
    public void shouldLogPushEvent() {
        Logger logger = mock(Logger.class);
        this.resource = new BitBucketResource(logger, this.gitPushEventAdapter, this.gitEventListener);
        PushEvent push = aBranchPushEvent();
        onPush(push);
        verify(logger).debug("Received push event [{}]", push);
    }

    @Test
    public void shouldNotifyGitEventListenerOfPushEvent() {
        onPush(aBranchPushEvent());
        verify(this.gitEventListener).onPush(any(GitPushEvent.class));
    }

    @Test
    public void shouldUseAdapterToAdaptFromBitBucketToGit() {
        PushEvent pushEvent = aBranchPushEvent();
        GitPushEvent gitPushEvent = aGitBranchPushEvent();
        when(this.gitPushEventAdapter.from(pushEvent)).thenReturn(gitPushEvent);

        onPush(pushEvent);

        verify(this.gitEventListener).onPush(gitPushEvent);
    }

    @Test
    public void shouldIgnoreTagPushEvents() {
        when(this.gitPushEventAdapter.from(any(PushEvent.class))).thenReturn(aGitTagPushEvent());

        onPush(aTagPushEvent());
        verify(this.gitEventListener, never()).onPush(any(GitPushEvent.class));
    }

    @Test
    public void shouldLogTagPushEvent() {
        when(this.gitPushEventAdapter.from(any(PushEvent.class))).thenReturn(aGitTagPushEvent());

        Logger logger = mock(Logger.class);
        this.resource = new BitBucketResource(logger, this.gitPushEventAdapter, this.gitEventListener);
        PushEvent push = aBranchPushEvent();
        onPush(push);
        verify(logger).debug("Received push event [{}]", push);
        verify(logger).debug("Ignoring event because it is not related to a branch");
    }

    private PushEvent aTagPushEvent() {
        return PushEvents.aValidTagPushEvent().build();
    }

    private PushEvent aBranchPushEvent() {
        return PushEvents.aValidBranchPushEvent().build();
    }

    private void onPush(PushEvent event) {
        this.resource.push(event);
    }

    private GitPushEvent aGitBranchPushEvent() {
        return GitPushEvent.aGitPushEvent().reference(GitReference.from("refs/heads/master")).build();
    }

    private GitPushEvent aGitTagPushEvent() {
        return GitPushEvent.aGitPushEvent().reference(GitReference.from("refs/tags/master")).build();
    }
}