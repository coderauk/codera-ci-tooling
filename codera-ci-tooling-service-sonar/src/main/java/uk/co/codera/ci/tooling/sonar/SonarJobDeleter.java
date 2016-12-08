package uk.co.codera.ci.tooling.sonar;

import uk.co.codera.ci.tooling.scm.ScmEvent;
import uk.co.codera.ci.tooling.scm.ScmEventListener;
import uk.co.codera.ci.tooling.template.TemplateService;

public class SonarJobDeleter implements ScmEventListener {

    private final TemplateService jobKeyFactory;
    private final SonarDeleteService deleteService;

    public SonarJobDeleter(TemplateService jobKeyFactory, SonarDeleteService deleteService) {
        this.jobKeyFactory = jobKeyFactory;
        this.deleteService = deleteService;
    }

    @Override
    public void on(ScmEvent event) {
        String jobKey = this.jobKeyFactory.create(event);
        this.deleteService.deleteJob(jobKey);
    }
}