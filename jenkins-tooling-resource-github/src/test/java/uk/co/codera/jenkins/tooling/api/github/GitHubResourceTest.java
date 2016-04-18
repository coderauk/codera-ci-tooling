package uk.co.codera.jenkins.tooling.api.github;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.codera.jenkins.tooling.api.github.GitHubPushEvents.aValidPushEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

import uk.co.codera.jenkins.tooling.git.GitEventListener;
import uk.co.codera.jenkins.tooling.git.GitPushEvent;

@RunWith(MockitoJUnitRunner.class)
public class GitHubResourceTest {

	@Mock
	private GitEventListener gitEventListener;
	
	@Mock
	private GitPushEventAdapter gitPushEventAdapter;
	
	private GitHubResource resource;
	
	@Before
	public void before() {
		this.resource = new GitHubResource(gitPushEventAdapter, gitEventListener);
	}
	
	@Test
	public void shouldLogPushEvent() {
	    Logger logger = mock(Logger.class);
	    this.resource = new GitHubResource(logger, this.gitPushEventAdapter, this.gitEventListener);
	    String eventType = GitPushEventAdapter.EVENT_TYPE_CREATE;
		GitHubPushEvent event = aValidPushEvent().build();
		onPush(eventType, event);
		verify(logger).debug("Received eventType [{}] for event [{}]", eventType, event);
	}
	
	@Test
	public void shouldUseAdapterToAdaptFromBitBucketToGit() {
	    String eventType = GitPushEventAdapter.EVENT_TYPE_CREATE;
	    GitHubPushEvent gitHubPushEvent = aValidPushEvent().build();
	    GitPushEvent gitPushEvent = GitPushEvent.aGitPushEvent().build();
	    when(this.gitPushEventAdapter.from(eventType, gitHubPushEvent)).thenReturn(gitPushEvent);
	    
	    onPush(eventType, gitHubPushEvent);
	    
	    verify(this.gitEventListener).onPush(gitPushEvent);
	}
	
	private void onPush(String eventType, GitHubPushEvent event) {
	    this.resource.push(eventType, event);
	}
}