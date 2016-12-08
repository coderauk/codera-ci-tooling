package uk.co.codera.ci.tooling.sonar;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.ci.tooling.scm.ScmEvent;
import uk.co.codera.ci.tooling.scm.ScmEventType;
import uk.co.codera.ci.tooling.template.TemplateService;

@RunWith(MockitoJUnitRunner.class)
public class SonarJobDeleterTest {

    private static final String PROJECT_NAME = "our-project";

    private SonarJobDeleter deleter;

    @Mock
    private TemplateService templateService;

    @Mock
    private SonarDeleteService deleteService;

    @Before
    public void before() {
        this.deleter = new SonarJobDeleter(this.templateService, this.deleteService);
    }

    @Test
    public void shouldInvokeDeleteServiceWithCorrectKeyOnPushEvent() {
        push(aDeleteEvent());
        verify(this.deleteService).deleteJob(any());
    }

    @Test
    public void shouldPassPushEventToTemplateServiceToCreateJobKey() {
        ScmEvent event = aDeleteEvent().build();
        on(event);
        verify(this.templateService).create(event);
    }

    @Test
    public void shouldPassGeneratedJobKeyToDeleteService() {
        when(this.templateService.create(any(ScmEvent.class))).thenReturn("sonar:key");
        push(aDeleteEvent());
        verify(this.deleteService).deleteJob("sonar:key");
    }

    private void push(ScmEvent.Builder event) {
        on(event.build());
    }

    private void on(ScmEvent event) {
        this.deleter.on(event);
    }

    private ScmEvent.Builder aDeleteEvent() {
        return ScmEvent.anScmEvent().eventType(ScmEventType.DELETE).projectName(PROJECT_NAME);
    }
}