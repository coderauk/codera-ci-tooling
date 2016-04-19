package uk.co.codera.ci.tooling.jenkins;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.jenkins.JenkinsJobDeleter;
import uk.co.codera.ci.tooling.jenkins.JenkinsService;
import uk.co.codera.ci.tooling.jenkins.JenkinsTemplateService;

@RunWith(MockitoJUnitRunner.class)
public class JenkinsJobDeleterTest {

    @Mock
    private JenkinsTemplateService mockJobNameFactory;
    
    @Mock
    private JenkinsService mockJenkinsService;
    
    private JenkinsJobDeleter jobDeleter;
    
    @Before
    public void before() {
        this.jobDeleter = new JenkinsJobDeleter(this.mockJobNameFactory, this.mockJenkinsService);
    }
    
    @Test
    public void shouldUseJobNameFactoryToCreateJobNameToDelete() {
        GitPushEvent event = GitPushEvent.aGitPushEvent().build();
        String jobName = "job-to-delete";
        when(this.mockJobNameFactory.create(event)).thenReturn(jobName);
        
        this.jobDeleter.onPush(event);
        
        verify(this.mockJenkinsService).deleteJob(jobName);
    }
}