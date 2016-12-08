package uk.co.codera.ci.tooling.scm;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static uk.co.codera.ci.tooling.scm.ScmEvent.anScmEvent;

import org.junit.Before;
import org.junit.Test;

public class ScmEventBroadcasterTest {

    private ScmEventBroadcaster broadcaster;

    @Before
    public void before() {
        this.broadcaster = new ScmEventBroadcaster();
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
        ScmEventListener listener = registerNewListener();
        ScmEvent event = aPushEvent();
        broadcaster.on(event);
        verify(listener).on(event);
    }

    @Test
    public void anExceptionInFirstListenerShouldNotStopSubsequentListenersFromBeingCalled() {
        ScmEventListener firstListener = registerNewListener();
        ScmEventListener secondListener = registerNewListener();

        doThrow(new IllegalStateException()).when(firstListener).on(any(ScmEvent.class));

        broadcaster.on(aPushEvent());

        verify(secondListener).on(any(ScmEvent.class));
    }

    private ScmEvent aPushEvent() {
        return anScmEvent().build();
    }

    private ScmEventListener registerNewListener() {
        ScmEventListener listener = mock(ScmEventListener.class);
        this.broadcaster.registerListener(listener);
        return listener;
    }
}