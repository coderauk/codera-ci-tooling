package uk.co.codera.jenkins.tooling.jenkins;

import uk.co.codera.jenkins.tooling.git.GitEventListener;
import uk.co.codera.jenkins.tooling.git.GitPushEvent;

public class JenkinsJobDeleter implements GitEventListener {

    private final JenkinsTemplateService jobNameFactory;
    private final JenkinsService jenkinsService;
    
    public JenkinsJobDeleter(JenkinsTemplateService jobNameFactory, JenkinsService jenkinsService) {
        this.jobNameFactory = jobNameFactory;
        this.jenkinsService = jenkinsService;
    }
    
    @Override
    public void onPush(GitPushEvent event) {
        String jobName = this.jobNameFactory.create(event);
        this.jenkinsService.deleteJob(jobName);
    }
}