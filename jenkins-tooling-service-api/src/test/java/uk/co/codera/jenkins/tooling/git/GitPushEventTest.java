package uk.co.codera.jenkins.tooling.git;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GitPushEventTest {

    @Test
    public void shouldHavePushType() {
        assertThat(aGitPushEvent().getPushType(), is(notNullValue()));
    }

    @Test
    public void shouldHaveReference() {
        assertThat(aGitPushEvent().getReference(), is(notNullValue()));
    }

    @Test
    public void shouldHaveRepositoryUrl() {
        assertThat(aGitPushEvent().getRepositoryUrl(), is(notNullValue()));
    }

    private GitPushEvent aGitPushEvent() {
        return GitPushEvent.aGitPushEvent().pushType(GitPushType.ADD).reference(GitReference.from("/refs/heads/master"))
                .repositoryUrl("ssh://git@server.co.uk:7999/tooly/mctooly.git").build();
    }
}