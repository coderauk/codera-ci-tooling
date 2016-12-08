package uk.co.codera.ci.tooling.git;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.codera.ci.tooling.git.GitPushEvent.aGitPushEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.ci.tooling.scm.ScmEvent;
import uk.co.codera.ci.tooling.scm.ScmEventListener;

@RunWith(MockitoJUnitRunner.class)
public class GitEventListenerTest {

    @Mock
    private GitPushEventAdapter mockGitPushEventAdapter;

    @Mock
    private ScmEventListener mockScmEventListener;

    private GitEventListener gitEventListener;

    @Before
    public void before() {
        this.gitEventListener = new GitEventListener(this.mockGitPushEventAdapter, this.mockScmEventListener);
    }

    @Test
    public void shouldAdaptGitPushEvent() {
        GitPushEvent event = on(aGitPushEvent());
        verify(this.mockGitPushEventAdapter).adapt(event);
    }

    @Test
    public void shouldPassAdaptedEventToScmEventListener() {
        ScmEvent scmEvent = whenAdapterReturns();
        on(aGitPushEvent());
        verify(this.mockScmEventListener).on(scmEvent);
    }

    private ScmEvent whenAdapterReturns() {
        ScmEvent scmEvent = ScmEvent.anScmEvent().build();
        when(this.mockGitPushEventAdapter.adapt(any(GitPushEvent.class))).thenReturn(scmEvent);
        return scmEvent;
    }

    private GitPushEvent on(GitPushEvent.Builder event) {
        return on(event.build());
    }

    private GitPushEvent on(GitPushEvent event) {
        this.gitEventListener.on(event);
        return event;
    }
}