package uk.co.codera.ci.tooling.scm;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static uk.co.codera.ci.tooling.scm.ConfigurableScmEventListener.aConfigurableScmEventListenerFactory;

import org.junit.Test;

public class ConfigurableScmEventListenerTest {

    @Test
    public void shouldHaveDefaultListenerForAnyPushType() {
        assertThat(newFactory().listenerFor(ScmEventType.ADD), is(notNullValue()));
    }

    @Test
    public void shouldBeAbleToOverrideDefaultListener() {
        ScmEventListener newDefaultListener = newListener();
        ConfigurableScmEventListener factory = newFactoryBuilder().defaultListener(newDefaultListener).build();
        assertThat(factory.listenerFor(ScmEventType.ADD), is(sameInstance(newDefaultListener)));
    }

    @Test
    public void shouldBeAbleToRegisterListenerForPushType() {
        ScmEventListener newListener = newListener();
        ConfigurableScmEventListener factory = newFactoryBuilder().register(ScmEventType.ADD, newListener).build();
        assertThat(factory.listenerFor(ScmEventType.ADD), is(sameInstance(newListener)));
    }

    @Test
    public void shouldFindAppropriateListenerAndInvokeOnPush() {
        ScmEventListener mockListener = mock(ScmEventListener.class);
        ConfigurableScmEventListener factory = newFactoryBuilder().register(ScmEventType.ADD, mockListener).build();
        ScmEvent event = ScmEvent.anScmEvent().eventType(ScmEventType.ADD).build();
        factory.on(event);
        verify(mockListener).on(event);
    }

    private ConfigurableScmEventListener newFactory() {
        return newFactoryBuilder().build();
    }

    private ConfigurableScmEventListener.Builder newFactoryBuilder() {
        return aConfigurableScmEventListenerFactory();
    }

    private ScmEventListener newListener() {
        return event -> {
        };
    }
}