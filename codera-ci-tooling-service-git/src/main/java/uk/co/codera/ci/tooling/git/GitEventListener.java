package uk.co.codera.ci.tooling.git;

import uk.co.codera.ci.tooling.scm.ScmEvent;
import uk.co.codera.ci.tooling.scm.ScmEventListener;

public class GitEventListener {

    private final GitPushEventAdapter gitPushEventAdapter;
    private final ScmEventListener scmEventListener;

    public GitEventListener(GitPushEventAdapter gitPushEventAdapter, ScmEventListener scmEventListener) {
        this.gitPushEventAdapter = gitPushEventAdapter;
        this.scmEventListener = scmEventListener;
    }

    public void on(GitPushEvent event) {
        ScmEvent scmEvent = this.gitPushEventAdapter.adapt(event);
        this.scmEventListener.on(scmEvent);
    }
}