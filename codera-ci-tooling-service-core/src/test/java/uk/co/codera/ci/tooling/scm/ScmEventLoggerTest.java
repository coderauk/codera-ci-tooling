package uk.co.codera.ci.tooling.scm;

import static org.mockito.Mockito.verify;
import static uk.co.codera.ci.tooling.scm.ScmEvent.anScmEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

@RunWith(MockitoJUnitRunner.class)
public class ScmEventLoggerTest {

    @Mock
    private Logger mockLogger;

    private ScmEventListener listener;

    @Before
    public void before() {
        this.listener = new ScmEventLogger(this.mockLogger);
    }

    @Test
    public void shouldLogGitEventWhenInvoked() {
        ScmEvent event = anScmEvent().build();
        this.listener.on(event);
        verify(this.mockLogger).info("Received [{}]", event);
    }
}