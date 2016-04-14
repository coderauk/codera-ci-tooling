package uk.co.codera.jenkins.tooling.jenkins;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.jenkins.tooling.git.GitEventListener;
import uk.co.codera.jenkins.tooling.git.GitPushEvent;
import uk.co.codera.jenkins.tooling.git.GitReference;

@RunWith(MockitoJUnitRunner.class)
public class JenkinsJobCreatorTest {

    @Mock
    private JenkinsJobFactory mockJobFactory;
    
    @Mock
    private JenkinsService mockJenkinsService;
    
    private GitEventListener jobCreator;
    
    @Before
    public void before() {
        this.jobCreator = new JenkinsJobCreator(this.mockJobFactory, this.mockJenkinsService);
    }
    
    @Test
    public void shouldUseJenkinsJobFactoryToCreateJobDefinition() {
        GitPushEvent event = aGitPushEvent();
        this.jobCreator.onPush(event);
        verify(this.mockJobFactory).create(event);
    }
    
    @Test
    public void shouldPassJobDefinitionFromJobFactoryToJenkinsServiceToCreateJob() {
        String jobDefinition = "this is a jenkins job definition";
        when(this.mockJobFactory.create(any(GitPushEvent.class))).thenReturn(jobDefinition);
        this.jobCreator.onPush(aGitPushEvent());
        verify(this.mockJenkinsService).createJob(anyString(), eq(jobDefinition));
    }

    private GitPushEvent aGitPushEvent() {
        return GitPushEvent.aGitPushEvent().reference(GitReference.from("refs/heads/master")).build();
    }
}