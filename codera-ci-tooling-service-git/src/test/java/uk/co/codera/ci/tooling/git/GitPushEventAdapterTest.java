package uk.co.codera.ci.tooling.git;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static uk.co.codera.ci.tooling.git.GitPushEvent.aGitPushEvent;

import org.junit.Before;
import org.junit.Test;

import uk.co.codera.ci.tooling.scm.ScmEvent;
import uk.co.codera.ci.tooling.scm.ScmEventType;

public class GitPushEventAdapterTest {

    private GitPushEventAdapter adapter;

    @Before
    public void before() {
        this.adapter = new GitPushEventAdapter();
    }

    @Test
    public void shouldAdaptToNonNull() {
        assertThat(adapt(anEvent()), is(notNullValue()));
    }

    @Test
    public void shouldAdaptRepositoryUrl() {
        assertThat(adapt(anEvent().repositoryUrl("git://blahblah/")).repositoryUrl(), is("git://blahblah/"));
    }

    @Test
    public void shouldAdaptRepositoryNameToProjectName() {
        assertThat(adapt(anEvent().repositoryName("repo-name")).projectName(), is("repo-name"));
    }

    @Test
    public void shouldAdaptBranchName() {
        assertThat(
                adapt(aGitPushEvent().reference(GitReference.from("refs/branches/feature/my-feature"))).branchName(),
                is("feature/my-feature"));
    }

    @Test
    public void shouldAdaptShortBranchName() {
        assertThat(adapt(aGitPushEvent().reference(GitReference.from("refs/branches/feature/my-feature")))
                .shortBranchName(), is("my-feature"));
    }

    @Test
    public void shouldAdaptGitPushTypeToEventType() {
        assertThat(adapt(anEvent().pushType(GitPushType.DELETE)).eventType(), is(ScmEventType.DELETE));
    }

    private GitPushEvent.Builder anEvent() {
        return aGitPushEvent().reference(GitReference.from("refs/heads/master"));
    }

    private ScmEvent adapt(GitPushEvent.Builder gitPushEvent) {
        return this.adapter.adapt(gitPushEvent.build());
    }
}