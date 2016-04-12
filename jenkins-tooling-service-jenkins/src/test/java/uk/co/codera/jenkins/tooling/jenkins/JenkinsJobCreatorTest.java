package uk.co.codera.jenkins.tooling.jenkins;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.jenkins.tooling.git.GitEventListener;
import uk.co.codera.jenkins.tooling.git.GitPushEvent;

@RunWith(MockitoJUnitRunner.class)
public class JenkinsJobCreatorTest {

    @Mock
    private JenkinsJobFactory mockJobFactory;
    
    private GitEventListener jobCreator;
    
    @Before
    public void before() {
        this.jobCreator = new JenkinsJobCreator(this.mockJobFactory);
    }
    
    @Test
    public void shouldUseJenkinsJobFactoryToCreateJobDefinition() {
        GitPushEvent event = aGitPushEvent();
        this.jobCreator.onPush(event);
        verify(this.mockJobFactory).create(event);
    }

    private GitPushEvent aGitPushEvent() {
        return GitPushEvent.aGitPushEvent().build();
    }
}