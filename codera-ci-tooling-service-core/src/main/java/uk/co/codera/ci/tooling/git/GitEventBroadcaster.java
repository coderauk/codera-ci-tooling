package uk.co.codera.ci.tooling.git;

import uk.co.codera.lang.Announcer;

public class GitEventBroadcaster implements GitEventListener {

    private final Announcer<GitEventListener> announcer;

    public GitEventBroadcaster() {
        this.announcer = Announcer.to(GitEventListener.class);
    }

    public void registerListener(GitEventListener listener) {
        this.announcer.addListener(listener);
    }

    @Override
    public void onPush(GitPushEvent event) {
        this.announcer.announce().onPush(event);
    }

    public int numberSubscribers() {
        return this.announcer.numberListeners();
    }
}