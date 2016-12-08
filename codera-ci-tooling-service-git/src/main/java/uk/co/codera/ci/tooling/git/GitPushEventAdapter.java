package uk.co.codera.ci.tooling.git;

import java.util.HashMap;
import java.util.Map;

import uk.co.codera.ci.tooling.scm.ScmEvent;
import uk.co.codera.ci.tooling.scm.ScmEventType;

public class GitPushEventAdapter {

    private static final Map<GitPushType, ScmEventType> eventTypeMappings = new HashMap<>();

    static {
        eventTypeMappings.put(GitPushType.ADD, ScmEventType.ADD);
        eventTypeMappings.put(GitPushType.UPDATE, ScmEventType.UPDATE);
        eventTypeMappings.put(GitPushType.DELETE, ScmEventType.DELETE);
    }

    public ScmEvent adapt(GitPushEvent gitPushEvent) {
        return ScmEvent.anScmEvent().eventType(eventTypeMappings.get(gitPushEvent.getPushType()))
                .repositoryUrl(gitPushEvent.getRepositoryUrl()).projectName(gitPushEvent.getRepositoryName())
                .branchName(gitPushEvent.getReference().branchName())
                .shortBranchName(gitPushEvent.getReference().shortBranchName()).build();
    }
}