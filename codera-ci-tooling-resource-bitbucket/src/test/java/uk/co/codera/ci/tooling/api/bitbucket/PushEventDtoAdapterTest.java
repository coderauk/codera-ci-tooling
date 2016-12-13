package uk.co.codera.ci.tooling.api.bitbucket;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static uk.co.codera.ci.tooling.api.bitbucket.data.TestPushEvents.aValidBranchAddRefChange;
import static uk.co.codera.ci.tooling.api.bitbucket.data.TestPushEvents.aValidProject;
import static uk.co.codera.ci.tooling.api.bitbucket.data.TestPushEvents.aValidPushEvent;
import static uk.co.codera.ci.tooling.api.bitbucket.data.TestPushEvents.aValidRepository;

import org.junit.Before;
import org.junit.Test;

import uk.co.codera.ci.tooling.api.bitbucket.PushEventDtoAdapter;
import uk.co.codera.ci.tooling.api.bitbucket.dto.PushEventDto;
import uk.co.codera.ci.tooling.api.bitbucket.dto.RefChangeDto;
import uk.co.codera.ci.tooling.api.bitbucket.dto.RepositoryDto;
import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitPushType;
import uk.co.codera.ci.tooling.git.GitReference;

public class PushEventDtoAdapterTest {

    private static final String BITBUCKET_SERVER_NAME = "myServer";
    private static final int BITBUCKET_SERVER_PORT = 7888;

    private PushEventDtoAdapter adapter;

    @Before
    public void before() {
        this.adapter = new PushEventDtoAdapter(BITBUCKET_SERVER_NAME, BITBUCKET_SERVER_PORT);
    }

    @Test
    public void shouldReturnNonNullGitPushEventFromValidPushEvent() {
        assertThat(from(aValidPushEvent()), is(notNullValue()));
    }

    @Test
    public void shouldMapGitPushTypeFromPushEvent() {
        assertThat(from(aValidBranchAddRefChange()).getPushType(), is(GitPushType.ADD));
    }

    @Test
    public void shouldMapRepositoryNameFromPushEvent() {
        assertThat(from(aValidRepository().slug("jeff")).getRepositoryName(), is("jeff"));
    }

    @Test
    public void shouldConstructGitReferenceFromPushEvent() {
        assertThat(from(aValidBranchAddRefChange().refId("refs/heads/master")).getReference(), is(equalTo(GitReference.from("refs/heads/master"))));
    }

    @Test
    public void shouldConstructRepositoryUrlFromBitBucketServerAndPushEventInformation() {
        assertThat(from(aValidRepository().slug("repo").with(aValidProject().key("proj").build())).getRepositoryUrl(),
                is("ssh://git@myServer:7888/proj/repo.git"));
    }

    @Test
    public void shouldMakeProjectKeyLowercaseInRepositoryUrl() {
        assertThat(from(aValidRepository().with(aValidProject().key("PROJ").build())).getRepositoryUrl(), containsString("/proj/"));
    }

    private GitPushEvent from(RepositoryDto.Builder repository) {
        return from(aValidPushEvent().with(repository.build()));
    }

    private GitPushEvent from(RefChangeDto.Builder refChange) {
        return from(aValidPushEvent().noRefChanges().with(refChange.build()));
    }

    private GitPushEvent from(PushEventDto.Builder event) {
        return this.adapter.from(event.build());
    }
}