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
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import uk.co.codera.ci.tooling.api.bitbucket.BitBucketResource;
import uk.co.codera.ci.tooling.api.bitbucket.PushEventDtoAdapter;
import uk.co.codera.ci.tooling.api.bitbucket.data.TestPushEvents;
import uk.co.codera.ci.tooling.api.bitbucket.dto.PushEventDto;
import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitReference;

@RunWith(MockitoJUnitRunner.class)
public class BitBucketResourceTest {

    @Mock
    private GitEventListener gitEventListener;

    @Mock
    private PushEventDtoAdapter gitPushEventAdapter;

    private BitBucketResource resource;

    @Before
    public void before() {
        this.resource = new BitBucketResource(gitPushEventAdapter, gitEventListener);
        when(this.gitPushEventAdapter.from(any(PushEventDto.class))).thenReturn(aGitPushEvent());
    }

    @Test
    public void shouldLogPushEvent() {
        Logger logger = mock(Logger.class);
        this.resource = new BitBucketResource(logger, this.gitPushEventAdapter, this.gitEventListener);
        PushEventDto push = aPushEvent();
        onPush(push);
        verify(logger).info("Received push event [{}]", push);
    }

    @Test
    public void shouldNotifyGitEventListenerOfPushEvent() {
        onPush(aPushEvent());
        verify(this.gitEventListener).on(any(GitPushEvent.class));
    }

    @Test
    public void shouldUseAdapterToAdaptFromBitBucketToGit() {
        PushEventDto pushEvent = aPushEvent();
        GitPushEvent gitPushEvent = aGitPushEvent();
        when(this.gitPushEventAdapter.from(pushEvent)).thenReturn(gitPushEvent);

        onPush(pushEvent);

        verify(this.gitEventListener).on(gitPushEvent);
    }

    @Test
    public void shouldIgnoreTagPushEvents() {
        when(this.gitPushEventAdapter.from(any(PushEventDto.class))).thenReturn(aGitPushEventForTags());

        onPush(aPushEventForTags());
        verify(this.gitEventListener, never()).on(any(GitPushEvent.class));
    }

    @Test
    public void shouldLogIgnoringTagPushEvent() {
        when(this.gitPushEventAdapter.from(any(PushEventDto.class))).thenReturn(aGitPushEventForTags());

        Logger logger = mock(Logger.class);
        this.resource = new BitBucketResource(logger, this.gitPushEventAdapter, this.gitEventListener);
        onPush(aPushEvent());
        verify(logger).info("Ignoring event because it is not related to a branch");
    }

    private PushEventDto aPushEvent() {
        return TestPushEvents.aValidPushEvent().build();
    }

    private PushEventDto aPushEventForTags() {
        return TestPushEvents.aValidPushEventForTags().build();
    }

    private void onPush(PushEventDto event) {
        this.resource.push(event);
    }

    private GitPushEvent aGitPushEvent() {
        return GitPushEvent.aGitPushEvent().reference(GitReference.from("refs/heads/master")).build();
    }

    private GitPushEvent aGitPushEventForTags() {
        return GitPushEvent.aGitPushEvent().reference(GitReference.from("refs/tags/master")).build();
    }
}