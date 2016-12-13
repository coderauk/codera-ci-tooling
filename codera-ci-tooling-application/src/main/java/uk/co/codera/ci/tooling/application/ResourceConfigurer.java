package uk.co.codera.ci.tooling.application;

import io.dropwizard.jersey.setup.JerseyEnvironment;
import uk.co.codera.ci.tooling.api.github.GitHubResource;
import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEventAdapter;
import uk.co.codera.ci.tooling.scm.ScmEventListener;

public class ResourceConfigurer {

    public static void configure(JerseyEnvironment jerseyEnvironment, CiToolingConfiguration configuration,
            ScmEventListener scmEventListener) {
        GitEventListener gitEventListener = new GitEventListener(new GitPushEventAdapter(), scmEventListener);
        jerseyEnvironment.register(gitHubResource(gitEventListener));
    }

    private static GitHubResource gitHubResource(GitEventListener gitEventBroadcaster) {
        return new GitHubResource(new uk.co.codera.ci.tooling.api.github.GitPushEventAdapter(), gitEventBroadcaster);
    }
}