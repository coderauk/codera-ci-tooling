package uk.co.codera.jenkins.tooling.git;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;
import org.mockito.Mockito;

public class GitEventBroadcasterTest {

	@Test
	public void shouldHaveNoSubscribersByDefault() {
		assertThat(new GitEventBroadcaster().numberSubscribers(), is(0));
	}

	@Test
	public void registeringListenerShouldIncreaseSubscriberCount() {
		GitEventBroadcaster broadcaster = new GitEventBroadcaster();
		broadcaster.registerListener(mock(GitEventListener.class));
		assertThat(broadcaster.numberSubscribers(), is(1));
	}

	@Test
	public void shouldNotifySubscriberOfPushEvent() {
		GitEventBroadcaster broadcaster = new GitEventBroadcaster();
		GitEventListener listener = mock(GitEventListener.class);
		broadcaster.registerListener(listener);
		GitPushEvent event = GitPushEvent.aGitPushEvent().build();
		broadcaster.onPush(event);
		Mockito.verify(listener).onPush(event);
	}
}