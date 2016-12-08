package uk.co.codera.ci.tooling.jenkins;

import uk.co.codera.ci.tooling.scm.ScmEvent;
import uk.co.codera.ci.tooling.scm.ScmEventListener;
import uk.co.codera.ci.tooling.template.TemplateService;

public class JenkinsJobDeleter implements ScmEventListener {

    private final TemplateService jobNameFactory;
    private final JenkinsService jenkinsService;

    public JenkinsJobDeleter(TemplateService jobNameFactory, JenkinsService jenkinsService) {
        this.jobNameFactory = jobNameFactory;
        this.jenkinsService = jenkinsService;
    }

    @Override
    public void on(ScmEvent event) {
        String jobName = this.jobNameFactory.create(event);
        this.jenkinsService.deleteJob(jobName);
    }
}