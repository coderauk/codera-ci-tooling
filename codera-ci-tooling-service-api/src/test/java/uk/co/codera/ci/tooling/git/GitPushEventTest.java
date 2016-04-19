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
		assertThat(aGitPushEvent().getPushType(), is(notNullValue()));
	}

	@Test
	public void shouldHaveReference() {
		assertThat(aGitPushEvent().getReference(), is(notNullValue()));
	}

	@Test
	public void shouldHaveRepositoryName() {
		assertThat(aGitPushEvent().getRepositoryName(), is(notNullValue()));
	}

	@Test
	public void shouldHaveRepositoryUrl() {
		assertThat(aGitPushEvent().getRepositoryUrl(), is(notNullValue()));
	}

	@Test
	public void toStringShouldNotBeObjectReference() {
		assertThat(aGitPushEvent().toString(), containsString("pushType="));
	}

	private GitPushEvent aGitPushEvent() {
		return GitPushEvent.aGitPushEvent().pushType(GitPushType.ADD)
				.reference(GitReference.from("refs/heads/master"))
				.repositoryName("mctooly")
				.repositoryUrl("ssh://git@server.co.uk:7999/tooly/mctooly.git")
				.build();
	}
}