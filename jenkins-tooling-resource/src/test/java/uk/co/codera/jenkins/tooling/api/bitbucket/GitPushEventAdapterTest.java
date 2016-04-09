package uk.co.codera.jenkins.tooling.api.bitbucket;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static uk.co.codera.jenkins.tooling.api.bitbucket.PushEvent.aPushEvent;
import static uk.co.codera.jenkins.tooling.api.bitbucket.PushEvents.aValidAddRefChange;
import static uk.co.codera.jenkins.tooling.api.bitbucket.PushEvents.aValidPushEvent;

import org.junit.Before;
import org.junit.Test;

import uk.co.codera.jenkins.tooling.git.GitPushEvent;
import uk.co.codera.jenkins.tooling.git.GitPushType;
import uk.co.codera.jenkins.tooling.git.GitReference;

public class GitPushEventAdapterTest {

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
    public void shouldMapGitPushTypeFromPushEvent() {
        assertThat(from(aValidAddRefChange()).getPushType(), is(GitPushType.ADD));
    }

    @Test
    public void shouldConstructGitReferenceFromPushEvent() {
        assertThat(from(aValidAddRefChange().refId("/refs/heads/master")).getReference(),
                is(equalTo(GitReference.from("/refs/heads/master"))));
    }

    private GitPushEvent from(RefChange.Builder refChange) {
        return from(aPushEvent().with(refChange.build()));
    }

    private GitPushEvent from(PushEvent.Builder event) {
        return this.adapter.from(event.build());
    }
}