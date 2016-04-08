package uk.co.codera.jenkins.tooling.git;

public interface GitEventListener {

    void onPush(GitPushEvent event);
}