package uk.co.codera.ci.tooling.api.svn;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.codera.ci.tooling.api.svn.data.TestSvnEvents.aValidSvnEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import uk.co.codera.ci.tooling.api.svn.dto.SvnEventDto;
import uk.co.codera.ci.tooling.scm.ScmEvent;
import uk.co.codera.ci.tooling.scm.ScmEventListener;

@RunWith(MockitoJUnitRunner.class)
public class SvnResourceTest {

    @Mock
    private ScmEventListener scmEventListener;

    @Mock
    private SvnEventDtoAdapter svnEventAdapter;

    private SvnResource resource;

    @Before
    public void before() {
        this.resource = new SvnResource(this.svnEventAdapter, this.scmEventListener);
        when(this.svnEventAdapter.from(any(SvnEventDto.class))).thenReturn(anScmEvent());
    }

    @Test
    public void shouldLogPushEvent() {
        Logger logger = mock(Logger.class);
        this.resource = new SvnResource(logger, this.svnEventAdapter, this.scmEventListener);
        SvnEventDto event = anSvnEvent();
        on(event);
        verify(logger).info("Received event [{}]", event);
    }

    @Test
    public void shouldNotifyEventListenerOfEvent() {
        on(anSvnEvent());
        verify(this.scmEventListener).on(any(ScmEvent.class));
    }

    @Test
    public void shouldUseAdapterToAdaptFromSvnToScm() {
        SvnEventDto svnEvent = anSvnEvent();
        ScmEvent scmEvent = anScmEvent();
        when(this.svnEventAdapter.from(svnEvent)).thenReturn(scmEvent);

        on(svnEvent);

        verify(this.scmEventListener).on(scmEvent);
    }

    private SvnEventDto anSvnEvent() {
        return aValidSvnEvent().build();
    }

    private void on(SvnEventDto event) {
        this.resource.on(event);
    }

    private ScmEvent anScmEvent() {
        return ScmEvent.anScmEvent().build();
    }
}