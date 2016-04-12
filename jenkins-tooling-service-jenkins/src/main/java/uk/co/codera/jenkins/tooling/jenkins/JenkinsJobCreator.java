package uk.co.codera.jenkins.tooling.jenkins;

import uk.co.codera.jenkins.tooling.git.GitEventListener;
import uk.co.codera.jenkins.tooling.git.GitPushEvent;

public class JenkinsJobCreator implements GitEventListener {

    private final JenkinsJobFactory jobFactory;

    public JenkinsJobCreator(JenkinsJobFactory jobFactory) {
        this.jobFactory = jobFactory;
    }

    @Override
    public void onPush(GitPushEvent event) {
        this.jobFactory.create(event);
    }
}