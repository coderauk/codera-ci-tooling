package uk.co.codera.jenkins.tooling.git;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static uk.co.codera.jenkins.tooling.git.ConfigurableGitEventListenerFactory.aConfigurableGitEventListenerFactory;

import org.junit.Test;

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