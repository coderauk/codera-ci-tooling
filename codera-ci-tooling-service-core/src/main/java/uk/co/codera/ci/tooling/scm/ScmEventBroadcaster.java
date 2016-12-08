package uk.co.codera.ci.tooling.scm;

import uk.co.codera.lang.Announcer;

public class ScmEventBroadcaster implements ScmEventListener {

    private final Announcer<ScmEventListener> announcer;

    public ScmEventBroadcaster() {
        this.announcer = Announcer.to(ScmEventListener.class);
    }

    public void registerListener(ScmEventListener listener) {
        this.announcer.addListener(listener);
    }

    @Override
    public void on(ScmEvent event) {
        this.announcer.announce().on(event);
    }

    public int numberSubscribers() {
        return this.announcer.numberListeners();
    }
}