package uk.co.codera.ci.tooling.scm;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static uk.co.codera.ci.tooling.scm.ConfigurableScmEventListenerFactory.aConfigurableScmEventListenerFactory;

import org.junit.Test;

public class ConfigurableScmEventListenerFactoryTest {

    @Test
    public void shouldHaveDefaultListenerForAnyPushType() {
        assertThat(newFactory().listenerFor(ScmEventType.ADD), is(notNullValue()));
    }

    @Test
    public void defaultListenerIfNotSpecifiedShouldLogEvent() {
        assertThat(newFactory().listenerFor(ScmEventType.ADD), is(instanceOf(ScmEventLogger.class)));
    }

    @Test
    public void shouldBeAbleToOverrideDefaultListener() {
        ScmEventListener newDefaultListener = newListener();
        ConfigurableScmEventListenerFactory factory = newFactoryBuilder().defaultListener(newDefaultListener).build();
        assertThat(factory.listenerFor(ScmEventType.ADD), is(sameInstance(newDefaultListener)));
    }

    @Test
    public void shouldBeAbleToRegisterListenerForPushType() {
        ScmEventListener newListener = newListener();
        ConfigurableScmEventListenerFactory factory = newFactoryBuilder().register(ScmEventType.ADD, newListener)
                .build();
        assertThat(factory.listenerFor(ScmEventType.ADD), is(sameInstance(newListener)));
    }

    @Test
    public void shouldFindAppropriateListenerAndInvokeOnPush() {
        ScmEventListener mockListener = mock(ScmEventListener.class);
        ConfigurableScmEventListenerFactory factory = newFactoryBuilder().register(ScmEventType.ADD, mockListener)
                .build();
        ScmEvent event = ScmEvent.anScmEvent().eventType(ScmEventType.ADD).build();
        factory.on(event);
        verify(mockListener).on(event);
    }

    private ConfigurableScmEventListenerFactory newFactory() {
        return newFactoryBuilder().build();
    }

    private ConfigurableScmEventListenerFactory.Builder newFactoryBuilder() {
        return aConfigurableScmEventListenerFactory();
    }

    private ScmEventListener newListener() {
        return event -> {
        };
    }
}