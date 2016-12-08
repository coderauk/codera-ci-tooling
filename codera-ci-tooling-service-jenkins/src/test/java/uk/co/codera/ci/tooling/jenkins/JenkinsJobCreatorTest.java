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

import uk.co.codera.ci.tooling.scm.ScmEvent;
import uk.co.codera.ci.tooling.scm.ScmEventListener;
import uk.co.codera.ci.tooling.template.TemplateService;

@RunWith(MockitoJUnitRunner.class)
public class JenkinsJobCreatorTest {

    @Mock
    private TemplateService mockJobNameFactory;

    @Mock
    private TemplateService mockJobFactory;

    @Mock
    private JenkinsService mockJenkinsService;

    private ScmEventListener jobCreator;

    @Before
    public void before() {
        this.jobCreator = new JenkinsJobCreator(this.mockJobNameFactory, this.mockJobFactory, this.mockJenkinsService);
    }

    @Test
    public void shouldUseJobFactoryToCreateJobDefinition() {
        ScmEvent event = anScmEvent();
        this.jobCreator.on(event);
        verify(this.mockJobFactory).create(event);
    }

    @Test
    public void shouldUseJobNameFactoryToCreateJobName() {
        ScmEvent event = anScmEvent();
        this.jobCreator.on(event);
        verify(this.mockJobNameFactory).create(event);
    }

    @Test
    public void shouldPassJobDefinitionFromFactoryToJenkinsServiceToCreateJob() {
        String jobDefinition = "this is a jenkins job definition";
        when(this.mockJobFactory.create(any(ScmEvent.class))).thenReturn(jobDefinition);
        this.jobCreator.on(anScmEvent());
        verify(this.mockJenkinsService).createJob(anyString(), eq(jobDefinition));
    }

    @Test
    public void shouldPassJobNameFromFactoryToJenkinsServiceToCreateJob() {
        String jobName = "this is a job name";
        when(this.mockJobNameFactory.create(any(ScmEvent.class))).thenReturn(jobName);
        this.jobCreator.on(anScmEvent());
        verify(this.mockJenkinsService).createJob(eq(jobName), anyString());
    }

    private ScmEvent anScmEvent() {
        return ScmEvent.anScmEvent().build();
    }
}