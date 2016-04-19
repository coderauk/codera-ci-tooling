package uk.co.codera.ci.tooling.git;

import java.util.ArrayList;
import java.util.Collection;

import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.lang.Announcer;

public class GitEventBroadcaster implements GitEventListener {

	private final Collection<GitEventListener> listeners;
	private final Announcer<GitEventListener> announcer;

	public GitEventBroadcaster() {
        this.listeners = new ArrayList<>();
        this.announcer = Announcer.to(GitEventListener.class);
    }
	public void registerListener(GitEventListener listener) {
		this.listeners.add(listener);
		this.announcer.addListener(listener);
	}

	@Override
	public void onPush(GitPushEvent event) {
		this.announcer.announce().onPush(event);
	}

	public int numberSubscribers() {
		return this.listeners.size();
	}
}