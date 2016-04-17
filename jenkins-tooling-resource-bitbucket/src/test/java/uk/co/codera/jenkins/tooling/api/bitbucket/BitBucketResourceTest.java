package uk.co.codera.jenkins.tooling.api.bitbucket;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
	private GitEventListener gitEventListener;
	
	@Mock
	private GitPushEventAdapter gitPushEventAdapter;
	
	private BitBucketResource resource;
	
	@Before
	public void before() {
		this.resource = new BitBucketResource(gitPushEventAdapter, gitEventListener);
	}
	
	@Test
	public void shouldLogPushEvent() {
	    Logger logger = mock(Logger.class);
	    this.resource = new BitBucketResource(logger, this.gitPushEventAdapter, this.gitEventListener);
		PushEvent push = aPushEvent();
		onPush(push);
		verify(logger).debug("Received push event [{}]", push);
	}
	
	@Test
	public void shouldNotifyGitEventListenerOfPushEvent() {
		onPush(aPushEvent());
		verify(this.gitEventListener).onPush(any(GitPushEvent.class));
	}
	
	@Test
	public void shouldUseAdapterToAdaptFromBitBucketToGit() {
	    PushEvent pushEvent = aPushEvent();
	    GitPushEvent gitPushEvent = GitPushEvent.aGitPushEvent().build();
	    when(this.gitPushEventAdapter.from(pushEvent)).thenReturn(gitPushEvent);
	    
	    onPush(pushEvent);
	    
	    verify(this.gitEventListener).onPush(gitPushEvent);
	}
	
	private PushEvent aPushEvent() {
	    return PushEvents.aValidPushEvent().build();
	}
	
	private void onPush(PushEvent event) {
	    this.resource.push(event);
	}
}