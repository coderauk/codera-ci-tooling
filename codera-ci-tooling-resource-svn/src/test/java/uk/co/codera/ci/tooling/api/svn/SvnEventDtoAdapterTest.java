package uk.co.codera.ci.tooling.api.svn;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.codera.ci.tooling.api.svn.dto.SvnEventDto.anSvnEventDto;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Test;

import uk.co.codera.ci.tooling.api.svn.dto.SvnEventDto;
import uk.co.codera.ci.tooling.api.svn.dto.SvnEventType;
import uk.co.codera.ci.tooling.scm.ScmEvent;
import uk.co.codera.ci.tooling.scm.ScmEventType;

public class SvnEventDtoAdapterTest {

    private String host;
    private Integer port;

    private SvnEventDtoAdapter adapter;

    @Before
    public void before() {
        this.host = RandomStringUtils.randomAlphanumeric(10);
        this.port = Integer.valueOf(RandomUtils.nextInt(80, 8081));

        this.adapter = new SvnEventDtoAdapter(this.host, this.port);
    }

    @Test
    public void shouldAdaptSvnUrlCorrectly() {
        String expectedRepositoryUrl = String.format("svn://%s:%s/an-svn-location", this.host, this.port);
        assertThat(from(anSvnEventDto().location("an-svn-location")).repositoryUrl(), is(expectedRepositoryUrl));
    }

    @Test
    public void shouldExcludePortFromSvnUrlIfNoneProvided() {
        String expectedRepositoryUrl = String.format("svn://%s/an-svn-location", this.host);
        this.adapter = new SvnEventDtoAdapter(this.host, null);
        assertThat(from(anSvnEventDto().location("an-svn-location")).repositoryUrl(), is(expectedRepositoryUrl));
    }

    @Test
    public void shouldAdaptEventTypeCorrectly() {
        assertThat(from(anSvnEventDto().eventType(SvnEventType.UPDATE)).eventType(), is(ScmEventType.UPDATE));
    }

    @Test
    public void shouldAdaptBranchCorrectly() {
        assertThat(from(anSvnEventDto().branch("my-branch")).branchName(), is("my-branch"));
    }

    @Test
    public void shouldAdaptBranchToShortBranchCorrectly() {
        assertThat(from(anSvnEventDto().branch("my-branch")).shortBranchName(), is("my-branch"));
    }

    @Test
    public void shouldAdaptProjectCorrectly() {
        assertThat(from(anSvnEventDto().project("my-project")).projectName(), is("my-project"));
    }

    private ScmEvent from(SvnEventDto.Builder svnEvent) {
        return this.adapter.from(svnEvent.build());
    }
}