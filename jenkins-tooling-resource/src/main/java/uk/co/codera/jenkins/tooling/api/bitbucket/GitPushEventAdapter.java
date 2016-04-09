package uk.co.codera.jenkins.tooling.api.bitbucket;

import uk.co.codera.jenkins.tooling.git.GitPushEvent;
import uk.co.codera.jenkins.tooling.git.GitReference;

public class GitPushEventAdapter {

    public GitPushEvent from(PushEvent pushEvent) {
        RefChange refChange = pushEvent.getRefChanges().get(0);
        return GitPushEvent.aGitPushEvent().pushType(refChange.getType())
                .reference(GitReference.from(refChange.getRefId())).build();
    }
}