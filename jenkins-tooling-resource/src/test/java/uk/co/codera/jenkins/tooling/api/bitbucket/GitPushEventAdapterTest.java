package uk.co.codera.jenkins.tooling.api.bitbucket;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static uk.co.codera.jenkins.tooling.api.bitbucket.PushEvents.aValidAddRefChange;
import static uk.co.codera.jenkins.tooling.api.bitbucket.PushEvents.aValidPushEvent;

import org.junit.Before;
import org.junit.Test;

import uk.co.codera.jenkins.tooling.git.GitPushEvent;
import uk.co.codera.jenkins.tooling.git.GitPushType;
import uk.co.codera.jenkins.tooling.git.GitReference;

public class GitPushEventAdapterTest {

    private static final String BITBUCKET_SERVER_NAME = "myServer";
    private static final int BITBUCKET_SERVER_PORT = 7888;

    private GitPushEventAdapter adapter;

    @Before
    public void before() {
        this.adapter = new GitPushEventAdapter(BITBUCKET_SERVER_NAME, BITBUCKET_SERVER_PORT);
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

    @Test
    public void shouldConstructRepositoryUrlFromBitBucketServerAndPushEventInformation() {
        assertThat(
                from(PushEvents.aValidRepository().slug("myRepository")
                        .with(PushEvents.aValidProject().key("myProject").build())).getRepositoryUrl(),
                is("ssh://git@myServer:7888/myProject/myRepository.git"));
    }

    private GitPushEvent from(Repository.Builder repository) {
        return from(aValidPushEvent().with(repository.build()));
    }

    private GitPushEvent from(RefChange.Builder refChange) {
        return from(aValidPushEvent().noRefChanges().with(refChange.build()));
    }

    private GitPushEvent from(PushEvent.Builder event) {
        return this.adapter.from(event.build());
    }
}