package uk.co.codera.ci.tooling.svn;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static uk.co.codera.ci.tooling.svn.SvnCommitEvent.anSvnCommitEvent;

import org.junit.Test;

public class SvnCommitEventTest {

    @Test
    public void shouldBuildNonNullEvent() {
        assertThat(anSvnCommitEvent().build(), is(notNullValue()));
    }

    @Test
    public void shouldBeAbleToSetCommitType() {
        assertThat(anSvnCommitEvent().commitType(SvnCommitType.DELETE).build().commitType(), is(SvnCommitType.DELETE));
    }

    @Test
    public void shouldBeAbleToSetSvnLocation() {
        assertThat(anSvnCommitEvent().svnLocation("my-project/branches/JIRA-0001").build().svnLocation(), is("my-project/branches/JIRA-0001"));
    }

    @Test
    public void shouldBeAbleToSetBranchName() {
        assertThat(anSvnCommitEvent().branchName("JIRA-0003").build().branchName(), is("JIRA-0003"));
    }

    @Test
    public void shouldBeAbleToSetProjectName() {
        assertThat(anSvnCommitEvent().projectName("my-super-project").build().projectName(), is("my-super-project"));
    }

    @Test
    public void toStringShouldNotBeObjectReference() {
        assertThat(anSvnCommitEvent().build().toString(), containsString("projectName="));
    }
}