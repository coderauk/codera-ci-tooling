package uk.co.codera.ci.tooling.git;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static uk.co.codera.ci.tooling.git.ConfigurableGitEventListenerFactory.aConfigurableGitEventListenerFactory;

import org.junit.Test;

import uk.co.codera.ci.tooling.git.ConfigurableGitEventListenerFactory;
import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitEventLogger;
import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitPushType;

public class ConfigurableGitEventListenerFactoryTest {

    @Test
    public void shouldHaveDefaultListenerForAnyPushType() {
        assertThat(newFactory().listenerFor(GitPushType.ADD), is(notNullValue()));
    }
    
    @Test
    public void defaultListenerIfNotSpecifiedShouldLogEvent() {
        assertThat(newFactory().listenerFor(GitPushType.ADD), is(instanceOf(GitEventLogger.class)));
    }
    
    @Test
    public void shouldBeAbleToOverrideDefaultListener() {
        GitEventListener newDefaultListener = newListener();
        ConfigurableGitEventListenerFactory factory = newFactoryBuilder().defaultListener(newDefaultListener).build();
        assertThat(factory.listenerFor(GitPushType.ADD), is(sameInstance(newDefaultListener)));
    }
    
    @Test
    public void shouldBeAbleToRegisterListenerForPushType() {
        GitEventListener newListener = newListener();
        ConfigurableGitEventListenerFactory factory = newFactoryBuilder().register(GitPushType.ADD, newListener).build();
        assertThat(factory.listenerFor(GitPushType.ADD), is(sameInstance(newListener)));
    }
    
    @Test
    public void shouldFindAppropriateListenerAndInvokeOnPush() {
        GitEventListener mockListener = mock(GitEventListener.class);
        ConfigurableGitEventListenerFactory factory = newFactoryBuilder().register(GitPushType.ADD, mockListener).build();
        GitPushEvent event = GitPushEvent.aGitPushEvent().pushType(GitPushType.ADD).build();
        factory.onPush(event);
        verify(mockListener).onPush(event);
    }
    
    private ConfigurableGitEventListenerFactory newFactory() {
        return newFactoryBuilder().build();
    }
    
    private ConfigurableGitEventListenerFactory.Builder newFactoryBuilder() {
        return aConfigurableGitEventListenerFactory();
    }
    
    private GitEventListener newListener() {
        return new GitEventListener() {
            @Override
            public void onPush(GitPushEvent event) {
            }
        };
    }
}