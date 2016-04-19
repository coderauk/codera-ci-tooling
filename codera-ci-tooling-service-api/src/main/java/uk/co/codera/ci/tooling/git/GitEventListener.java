package uk.co.codera.ci.tooling.git;

@FunctionalInterface
public interface GitEventListener {

	void onPush(GitPushEvent event);
}