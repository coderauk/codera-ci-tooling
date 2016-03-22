package uk.co.codera.jenkins.tooling.api.bitbucket;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;

@RunWith(MockitoJUnitRunner.class)
public class BitBucketResourceTest {

	@Mock
	private Logger logger;
	
	private BitBucketResource resource;
	
	@Before
	public void before() {
		this.resource = new BitBucketResource(logger);
	}
	
	@Test
	public void shouldLogPushEvent() {
		PushEvent push = new PushEvent();
		this.resource.push(push);
		verify(this.logger).debug("Received push event [{}]", push);
	}
}