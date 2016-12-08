package uk.co.codera.ci.tooling.jenkins;

import uk.co.codera.ci.tooling.scm.ScmEvent;
import uk.co.codera.ci.tooling.scm.ScmEventListener;
import uk.co.codera.ci.tooling.template.TemplateService;

public class JenkinsJobCreator implements ScmEventListener {

    private final TemplateService jobNameFactory;
    private final TemplateService jobFactory;
    private final JenkinsService jenkinsService;

    public JenkinsJobCreator(TemplateService jobNameFactory, TemplateService jobFactory, JenkinsService jenkinsService) {
        this.jobNameFactory = jobNameFactory;
        this.jobFactory = jobFactory;
        this.jenkinsService = jenkinsService;
    }

    @Override
    public void on(ScmEvent event) {
        String jobName = this.jobNameFactory.create(event);
        String jobDefinition = this.jobFactory.create(event);
        this.jenkinsService.createJob(jobName, jobDefinition);
    }
}