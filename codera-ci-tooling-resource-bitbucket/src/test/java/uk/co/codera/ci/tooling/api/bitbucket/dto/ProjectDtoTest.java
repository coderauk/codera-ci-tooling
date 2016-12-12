package uk.co.codera.ci.tooling.api.bitbucket.dto;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static uk.co.codera.ci.tooling.api.bitbucket.data.TestPushEvents.aValidProject;

import org.junit.Test;

public class ProjectDtoTest {

    @Test
    public void toStringShouldNotBeObjectReference() {
        assertThat(project().toString(), containsString("key="));
    }

    private ProjectDto project() {
        return aValidProject().build();
    }
}