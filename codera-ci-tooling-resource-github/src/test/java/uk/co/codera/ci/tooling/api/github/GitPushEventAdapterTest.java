package uk.co.codera.ci.tooling.api.github;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static uk.co.codera.ci.tooling.api.github.GitHubPushEvents.aValidPushEvent;
import static uk.co.codera.ci.tooling.api.github.GitHubPushEvents.aValidRepository;

import org.junit.Before;
import org.junit.Test;

import uk.co.codera.ci.tooling.api.github.GitHubPushEvent;
import uk.co.codera.ci.tooling.api.github.GitPushEventAdapter;
import uk.co.codera.ci.tooling.api.github.Repository;
import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitPushType;
import uk.co.codera.ci.tooling.git.GitReference;

public class GitPushEventAdapterTest {

    private static final String EVENT_TYPE_CREATE = "create";
    private static final String EVENT_TYPE_DELETE = "delete";

    private GitPushEventAdapter adapter;

    @Before
    public void before() {
        this.adapter = new GitPushEventAdapter();
    }

    @Test
    public void shouldReturnNonNullGitPushEventFromValidPushEvent() {
        assertThat(from(aValidPushEvent()), is(notNullValue()));
    }

    @Test
    public void shouldMapAddGitPushTypeFromEventType() {
        assertThat(from(EVENT_TYPE_CREATE).getPushType(), is(GitPushType.ADD));
    }

    @Test
    public void shouldMapDeleteGitPushTypeFromEventType() {
        assertThat(from(EVENT_TYPE_DELETE).getPushType(), is(GitPushType.DELETE));
    }

    @Test
    public void shouldMapRepositoryNameFromPushEvent() {
        assertThat(from(aValidRepository().name("boatymcboatface")).getRepositoryName(), is("boatymcboatface"));
    }

    @Test
    public void shouldMapRepositorySshUrlFromPushEvent() {
        assertThat(from(aValidRepository().sshUrl("ssh:mcSsh:/repo")).getRepositoryUrl(), is("ssh:mcSsh:/repo"));
    }

    @Test
    public void shouldConstructGitReferenceFromPushEvent() {
        assertThat(from(aValidPushEvent().ref("my-branch")).getReference(), is(equalTo(GitReference.from("refs/heads/my-branch"))));
    }

    private GitPushEvent from(Repository.Builder repository) {
        return from(aValidPushEvent().with(repository.build()));
    }

    private GitPushEvent from(GitHubPushEvent.Builder event) {
        return from(EVENT_TYPE_CREATE, event);
    }

    private GitPushEvent from(String eventType) {
        return from(eventType, aValidPushEvent());
    }

    private GitPushEvent from(String eventType, GitHubPushEvent.Builder event) {
        return this.adapter.from(eventType, event.build());
    }
}