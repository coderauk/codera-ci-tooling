package uk.co.codera.ci.tooling.svn;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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
        assertThat(anSvnCommitEvent().svnLocation("my-project/branches/JIRA-0001").build().svnLocation(),
                is("my-project/branches/JIRA-0001"));
    }
}