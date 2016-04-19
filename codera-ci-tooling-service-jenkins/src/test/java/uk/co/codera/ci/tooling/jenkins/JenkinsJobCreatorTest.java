package uk.co.codera.ci.tooling.jenkins;

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

import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitReference;
import uk.co.codera.ci.tooling.jenkins.JenkinsJobCreator;
import uk.co.codera.ci.tooling.jenkins.JenkinsService;
import uk.co.codera.ci.tooling.jenkins.JenkinsTemplateService;

@RunWith(MockitoJUnitRunner.class)
public class JenkinsJobCreatorTest {

    @Mock
    private JenkinsTemplateService mockJobNameFactory;

    @Mock
    private JenkinsTemplateService mockJobFactory;

    @Mock
    private JenkinsService mockJenkinsService;

    private GitEventListener jobCreator;

    @Before
    public void before() {
        this.jobCreator = new JenkinsJobCreator(this.mockJobNameFactory, this.mockJobFactory, this.mockJenkinsService);
    }

    @Test
    public void shouldUseJobFactoryToCreateJobDefinition() {
        GitPushEvent event = aGitPushEvent();
        this.jobCreator.onPush(event);
        verify(this.mockJobFactory).create(event);
    }

    @Test
    public void shouldUseJobNameFactoryToCreateJobName() {
        GitPushEvent event = aGitPushEvent();
        this.jobCreator.onPush(event);
        verify(this.mockJobNameFactory).create(event);
    }

    @Test
    public void shouldPassJobDefinitionFromFactoryToJenkinsServiceToCreateJob() {
        String jobDefinition = "this is a jenkins job definition";
        when(this.mockJobFactory.create(any(GitPushEvent.class))).thenReturn(jobDefinition);
        this.jobCreator.onPush(aGitPushEvent());
        verify(this.mockJenkinsService).createJob(anyString(), eq(jobDefinition));
    }

    @Test
    public void shouldPassJobNameFromFactoryToJenkinsServiceToCreateJob() {
        String jobName = "this is a job name";
        when(this.mockJobNameFactory.create(any(GitPushEvent.class))).thenReturn(jobName);
        this.jobCreator.onPush(aGitPushEvent());
        verify(this.mockJenkinsService).createJob(eq(jobName), anyString());
    }

    private GitPushEvent aGitPushEvent() {
        return GitPushEvent.aGitPushEvent().reference(GitReference.from("refs/heads/master")).build();
    }
}