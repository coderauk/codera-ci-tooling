package uk.co.codera.ci.tooling.git;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitPushType;
import uk.co.codera.ci.tooling.git.GitReference;

public class GitPushEventTest {

    @Test
    public void shouldHavePushType() {
        assertThat(aGitPushEventForBranches().getPushType(), is(notNullValue()));
    }

    @Test
    public void shouldHaveReference() {
        assertThat(aGitPushEventForBranches().getReference(), is(notNullValue()));
    }

    @Test
    public void shouldHaveRepositoryName() {
        assertThat(aGitPushEventForBranches().getRepositoryName(), is(notNullValue()));
    }

    @Test
    public void shouldHaveRepositoryUrl() {
        assertThat(aGitPushEventForBranches().getRepositoryUrl(), is(notNullValue()));
    }

    @Test
    public void toStringShouldNotBeObjectReference() {
        assertThat(aGitPushEventForBranches().toString(), containsString("pushType="));
    }

    @Test
    public void tagEventsShouldBeIdentifiedAsTag() {
        assertThat(aGitPushEventForTags().isTag(), is(true));
    }

    @Test
    public void branchEventsShouldNotBeIdentifiedAsTag() {
        assertThat(aGitPushEventForBranches().isTag(), is(false));
    }

    private GitPushEvent aGitPushEventForTags() {
        return aGitPushEventWithGitReferenceFrom("refs/tags/master");
    }

    private GitPushEvent aGitPushEventForBranches() {
        return aGitPushEventWithGitReferenceFrom("refs/heads/master");
    }

    private GitPushEvent aGitPushEventWithGitReferenceFrom(String reference) {
        return GitPushEvent.aGitPushEvent().pushType(GitPushType.ADD).reference(GitReference.from(reference)).repositoryName("mctooly")
                .repositoryUrl("ssh://git@server.co.uk:7999/tooly/mctooly.git").build();
    }
}