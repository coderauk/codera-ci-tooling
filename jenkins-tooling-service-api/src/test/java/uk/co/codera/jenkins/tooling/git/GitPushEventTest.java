package uk.co.codera.jenkins.tooling.git;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.Assert;
import org.junit.Test;

public class GitPushEventTest {

	@Test
	public void shouldHaveType() {
		Assert.assertThat(aGitPushEvent().getType(), is(notNullValue()));
	}
	
	private GitPushEvent aGitPushEvent() {
		return GitPushEvent.aGitPushEvent().type(PushType.ADD).build();
	}
}