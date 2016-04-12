package uk.co.codera.jenkins.tooling.jenkins;

import uk.co.codera.jenkins.tooling.git.GitEventListener;
import uk.co.codera.jenkins.tooling.git.GitPushEvent;

public class JenkinsJobCreator implements GitEventListener {

    private final JenkinsJobFactory jobFactory;
    private final JenkinsService jenkinsService;

    public JenkinsJobCreator(JenkinsJobFactory jobFactory, JenkinsService jenkinsService) {
        this.jobFactory = jobFactory;
        this.jenkinsService = jenkinsService;
    }

    @Override
    public void onPush(GitPushEvent event) {
        String jobDefinition = this.jobFactory.create(event);
        this.jenkinsService.createJob(jobDefinition);
    }
}