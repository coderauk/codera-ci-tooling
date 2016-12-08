package uk.co.codera.ci.tooling.jenkins;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.ci.tooling.scm.ScmEvent;
import uk.co.codera.ci.tooling.template.TemplateService;

@RunWith(MockitoJUnitRunner.class)
public class JenkinsJobDeleterTest {

    @Mock
    private TemplateService mockJobNameFactory;

    @Mock
    private JenkinsService mockJenkinsService;

    private JenkinsJobDeleter jobDeleter;

    @Before
    public void before() {
        this.jobDeleter = new JenkinsJobDeleter(this.mockJobNameFactory, this.mockJenkinsService);
    }

    @Test
    public void shouldUseJobNameFactoryToCreateJobNameToDelete() {
        ScmEvent event = ScmEvent.anScmEvent().build();
        String jobName = "job-to-delete";
        when(this.mockJobNameFactory.create(event)).thenReturn(jobName);

        this.jobDeleter.on(event);
        verify(this.mockJenkinsService).deleteJob(jobName);
    }
}