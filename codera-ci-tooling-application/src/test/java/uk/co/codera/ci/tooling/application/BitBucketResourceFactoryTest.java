package uk.co.codera.ci.tooling.application;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static uk.co.codera.ci.tooling.api.bitbucket.data.TestPushEvents.aValidPushEvent;
import static uk.co.codera.ci.tooling.application.BitBucketConfiguration.someBitBucketConfiguration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.co.codera.ci.tooling.api.bitbucket.BitBucketResource;
import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEvent;

@RunWith(MockitoJUnitRunner.class)
public class BitBucketResourceFactoryTest {

    @Mock
    private GitEventListener mockGitEventListener;

    private BitBucketConfiguration bitBucketConfiguration;

    @Before
    public void before() {
        this.bitBucketConfiguration = someBitBucketConfiguration().host(randomAlphanumeric(10)).port(nextInt(80, 8081)).build();
    }

    @Test
    public void shouldConstructResourceWithPassedInGitEventListener() {
        pushEventThroughResource();
        verify(this.mockGitEventListener).on(any(GitPushEvent.class));
    }

    @Test
    public void shouldPassBitBucketConfigurationToResource() {
        pushEventThroughResource();
        String repositoryUrl = capturedGitPushEvent().getRepositoryUrl();
        assertThat(repositoryUrl, containsString(this.bitBucketConfiguration.getBitBucketServerName() + ":"));
        assertThat(repositoryUrl, containsString(":" + this.bitBucketConfiguration.getBitBucketServerPort()));
    }

    private void pushEventThroughResource() {
        create().push(aValidPushEvent().build());
    }

    private GitPushEvent capturedGitPushEvent() {
        ArgumentCaptor<GitPushEvent> captor = ArgumentCaptor.forClass(GitPushEvent.class);
        verify(this.mockGitEventListener).on(captor.capture());
        return captor.getValue();
    }

    private BitBucketResource create() {
        return BitBucketResourceFactory.create(this.bitBucketConfiguration, this.mockGitEventListener);
    }
}