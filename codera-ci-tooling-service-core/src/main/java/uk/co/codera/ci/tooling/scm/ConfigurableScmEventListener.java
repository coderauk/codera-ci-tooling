package uk.co.codera.ci.tooling.scm;

import java.util.EnumMap;
import java.util.Map;

public class ConfigurableScmEventListener implements ScmEventListener {

    public static final ScmEventListener LOGGING_SCM_EVENT_LISTENER = new ScmEventLogger();

    private final Map<ScmEventType, ScmEventListener> listeners;
    private final ScmEventListener defaultListener;

    private ConfigurableScmEventListener(Builder builder) {
        this.listeners = new EnumMap<>(builder.listeners);
        this.defaultListener = builder.defaultListener;
    }

    @Override
    public void on(ScmEvent event) {
        listenerFor(event.eventType()).on(event);
    }

    protected ScmEventListener listenerFor(ScmEventType eventType) {
        if (this.listeners.containsKey(eventType)) {
            return this.listeners.get(eventType);
        }
        return this.defaultListener;
    }

    public static Builder aConfigurableScmEventListenerFactory() {
        return new Builder();
    }

    public static class Builder {

        private final Map<ScmEventType, ScmEventListener> listeners;
        private ScmEventListener defaultListener = LOGGING_SCM_EVENT_LISTENER;

        private Builder() {
            this.listeners = new EnumMap<>(ScmEventType.class);
        }

        public Builder defaultListener(ScmEventListener newDefaultListener) {
            this.defaultListener = newDefaultListener;
            return this;
        }

        public Builder register(ScmEventType eventType, ScmEventListener listener) {
            listeners.put(eventType, listener);
            return this;
        }

        public ConfigurableScmEventListener build() {
            return new ConfigurableScmEventListener(this);
        }
    }
}