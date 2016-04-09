package uk.co.codera.jenkins.tooling.git;

import static org.mockito.Mockito.verify;
import static uk.co.codera.jenkins.tooling.git.GitPushEvent.aGitPushEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

@RunWith(MockitoJUnitRunner.class)
public class GitEventLoggerTest {

    @Mock
    private Logger mockLogger;
    
    private GitEventListener listener;
    
    @Before
    public void before() {
        this.listener = new GitEventLogger(this.mockLogger);
    }
    
    @Test
    public void shouldLogGitEventWhenInvoked() {
        GitPushEvent event = aGitPushEvent().build();
        this.listener.onPush(event);
        verify(this.mockLogger).info("Received [{}]", event);
    }
}