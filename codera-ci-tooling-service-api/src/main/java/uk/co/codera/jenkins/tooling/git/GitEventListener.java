package uk.co.codera.jenkins.tooling.git;

@FunctionalInterface
public interface GitEventListener {

    void onPush(GitPushEvent event);
}