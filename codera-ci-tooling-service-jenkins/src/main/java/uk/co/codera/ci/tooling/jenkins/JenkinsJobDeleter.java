package uk.co.codera.ci.tooling.jenkins;

import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.template.TemplateService;

public class JenkinsJobDeleter implements GitEventListener {

    private final TemplateService jobNameFactory;
    private final JenkinsService jenkinsService;

    public JenkinsJobDeleter(TemplateService jobNameFactory, JenkinsService jenkinsService) {
        this.jobNameFactory = jobNameFactory;
        this.jenkinsService = jenkinsService;
    }

    @Override
    public void onPush(GitPushEvent event) {
        String jobName = this.jobNameFactory.create(event);
        this.jenkinsService.deleteJob(jobName);
    }
}