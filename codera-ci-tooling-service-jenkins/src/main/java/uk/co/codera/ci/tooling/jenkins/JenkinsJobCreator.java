package uk.co.codera.ci.tooling.jenkins;

import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.template.TemplateService;

public class JenkinsJobCreator implements GitEventListener {

    private final TemplateService jobNameFactory;
    private final TemplateService jobFactory;
    private final JenkinsService jenkinsService;

    public JenkinsJobCreator(TemplateService jobNameFactory, TemplateService jobFactory, JenkinsService jenkinsService) {
        this.jobNameFactory = jobNameFactory;
        this.jobFactory = jobFactory;
        this.jenkinsService = jenkinsService;
    }

    @Override
    public void onPush(GitPushEvent event) {
        String jobName = this.jobNameFactory.create(event);
        String jobDefinition = this.jobFactory.create(event);
        this.jenkinsService.createJob(jobName, jobDefinition);
    }
}