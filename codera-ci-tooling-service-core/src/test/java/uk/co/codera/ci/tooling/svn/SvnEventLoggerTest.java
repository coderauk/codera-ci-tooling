package uk.co.codera.ci.tooling.svn;

import static org.mockito.Mockito.verify;
import static uk.co.codera.ci.tooling.svn.SvnCommitEvent.anSvnCommitEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

@RunWith(MockitoJUnitRunner.class)
public class SvnEventLoggerTest {

    @Mock
    private Logger mockLogger;

    private SvnEventListener listener;

    @Before
    public void before() {
        this.listener = new SvnEventLogger(this.mockLogger);
    }

    @Test
    public void shouldLogGitEventWhenInvoked() {
        SvnCommitEvent event = anSvnCommitEvent().build();
        this.listener.onCommit(event);
        verify(this.mockLogger).info("Received [{}]", event);
    }
}