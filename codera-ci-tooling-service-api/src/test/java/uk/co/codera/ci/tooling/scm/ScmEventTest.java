package uk.co.codera.ci.tooling.scm;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static uk.co.codera.ci.tooling.scm.ScmEvent.anScmEvent;

import org.junit.Test;

public class ScmEventTest {

    @Test
    public void shouldBuildNonNullEvent() {
        assertThat(anScmEvent().build(), is(notNullValue()));
    }

    @Test
    public void shouldBeAbleToSetEventType() {
        assertThat(anScmEvent().eventType(ScmEventType.UPDATE).build().eventType(), is(ScmEventType.UPDATE));
    }

    @Test
    public void shouldBeAbleToSetRepositoryUrl() {
        assertThat(anScmEvent().repositoryUrl("https://host/scm/location").build().repositoryUrl(),
                is("https://host/scm/location"));
    }

    @Test
    public void shouldBeAbleToSetProjectName() {
        assertThat(anScmEvent().projectName("my-project").build().projectName(), is("my-project"));
    }

    @Test
    public void shouldBeAbleToSetBranchName() {
        assertThat(anScmEvent().branchName("my-branch").build().branchName(), is("my-branch"));
    }

    @Test
    public void shouldBeAbleToSetShortBranchName() {
        assertThat(anScmEvent().shortBranchName("brnch").build().shortBranchName(), is("brnch"));
    }
}