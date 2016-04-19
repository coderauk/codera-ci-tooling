package uk.co.codera.ci.tooling.git;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static uk.co.codera.ci.tooling.git.GitPushEvent.aGitPushEvent;

import org.junit.Before;
import org.junit.Test;

import uk.co.codera.ci.tooling.git.GitEventBroadcaster;
import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEvent;

public class GitEventBroadcasterTest {

    private GitEventBroadcaster broadcaster;

    @Before
    public void before() {
        this.broadcaster = new GitEventBroadcaster();
    }

    @Test
    public void shouldHaveNoSubscribersByDefault() {
        assertThat(this.broadcaster.numberSubscribers(), is(0));
    }

    @Test
    public void registeringListenerShouldIncreaseSubscriberCount() {
        registerNewListener();
        assertThat(broadcaster.numberSubscribers(), is(1));
    }

    @Test
    public void shouldNotifySubscriberOfPushEvent() {
        GitEventListener listener = registerNewListener();
        GitPushEvent event = aPushEvent();
        broadcaster.onPush(event);
        verify(listener).onPush(event);
    }

    @Test
    public void anExceptionInFirstListenerShouldNotStopSubsequentListenersFromBeingCalled() {
        GitEventListener firstListener = registerNewListener();
        GitEventListener secondListener = registerNewListener();

        doThrow(new IllegalStateException()).when(firstListener).onPush(any(GitPushEvent.class));

        broadcaster.onPush(aPushEvent());

        verify(secondListener).onPush(any(GitPushEvent.class));
    }

    private GitPushEvent aPushEvent() {
        return aGitPushEvent().build();
    }

    private GitEventListener registerNewListener() {
        GitEventListener listener = mock(GitEventListener.class);
        this.broadcaster.registerListener(listener);
        return listener;
    }
}