package uk.co.codera.ci.tooling.sonar;

import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEvent;

public class SonarJobDeleter implements GitEventListener {

    private final SonarDeleteService deleteService;

    public SonarJobDeleter(SonarDeleteService deleteService) {
        this.deleteService = deleteService;
    }

    @Override
    public void onPush(GitPushEvent event) {
        this.deleteService.deleteJob(event.getRepositoryName() + ":" + event.getReference().shortBranchName());
    }
}