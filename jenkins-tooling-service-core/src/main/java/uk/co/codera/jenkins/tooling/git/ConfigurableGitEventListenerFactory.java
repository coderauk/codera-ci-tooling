package uk.co.codera.jenkins.tooling.git;

import java.util.EnumMap;
import java.util.Map;

public class ConfigurableGitEventListenerFactory implements GitEventListener {

    public static final GitEventListener LOGGING_GIT_EVENT_LISTENER = new GitEventLogger();

    private final Map<GitPushType, GitEventListener> listeners;
    private final GitEventListener defaultListener;

    private ConfigurableGitEventListenerFactory(Builder builder) {
        this.listeners = new EnumMap<>(builder.listeners);
        this.defaultListener = builder.defaultListener;
    }
    
    @Override
    public void onPush(GitPushEvent event) {
        listenerFor(event.getPushType()).onPush(event);
    }

    public GitEventListener listenerFor(GitPushType pushType) {
        if (this.listeners.containsKey(pushType)) {
            return this.listeners.get(pushType);
        }
        return this.defaultListener;
    }

    public static Builder aConfigurableGitEventListenerFactory() {
        return new Builder();
    }

    public static class Builder {

        private final Map<GitPushType, GitEventListener> listeners;
        private GitEventListener defaultListener = LOGGING_GIT_EVENT_LISTENER;

        private Builder() {
            this.listeners = new EnumMap<>(GitPushType.class);
        }

        public Builder defaultListener(GitEventListener newDefaultListener) {
            this.defaultListener = newDefaultListener;
            return this;
        }
        
        public Builder register(GitPushType pushType, GitEventListener listener) {
            listeners.put(pushType, listener);
            return this;
        }

        public ConfigurableGitEventListenerFactory build() {
            return new ConfigurableGitEventListenerFactory(this);
        }
    }
}