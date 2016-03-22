package uk.co.codera.jenkins.tooling.api.bitbucket;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import uk.co.codera.jenkins.tooling.git.GitEventListener;
import uk.co.codera.jenkins.tooling.git.GitPushEvent;

@RunWith(MockitoJUnitRunner.class)
public class BitBucketResourceTest {

	@Mock
	private Logger logger;
	
	@Mock
	private GitEventListener gitEventListener;
	
	private BitBucketResource resource;
	
	@Before
	public void before() {
		this.resource = new BitBucketResource(logger, gitEventListener);
	}
	
	@Test
	public void shouldLogPushEvent() {
		PushEvent push = new PushEvent();
		this.resource.push(push);
		verify(this.logger).debug("Received push event [{}]", push);
	}
	
	@Test
	public void shouldNotifyGitEventListenerOfPushEvent() {
		this.resource.push(new PushEvent());
		verify(this.gitEventListener).onPush(any(GitPushEvent.class));
	}
}