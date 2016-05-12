package uk.co.codera.ci.tooling.sonar;

import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.template.TemplateService;

public class SonarJobDeleter implements GitEventListener {

    private final TemplateService jobKeyFactory;
    private final SonarDeleteService deleteService;

    public SonarJobDeleter(TemplateService jobKeyFactory, SonarDeleteService deleteService) {
        this.jobKeyFactory = jobKeyFactory;
        this.deleteService = deleteService;
    }

    @Override
    public void onPush(GitPushEvent event) {
        String jobKey = this.jobKeyFactory.create(event);
        this.deleteService.deleteJob(jobKey);
    }
}