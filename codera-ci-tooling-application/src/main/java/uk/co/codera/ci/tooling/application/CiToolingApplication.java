package uk.co.codera.ci.tooling.application;

import io.dropwizard.Application;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import uk.co.codera.ci.tooling.api.github.GitHubResource;
import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEventAdapter;
import uk.co.codera.ci.tooling.scm.ScmEventBroadcaster;
import uk.co.codera.ci.tooling.scm.ScmEventListener;

public class CiToolingApplication extends Application<CiToolingConfiguration> {

    public static void main(String[] args) throws Exception {
        new CiToolingApplication().run(args);
    }

    @Override
    public void run(CiToolingConfiguration configuration, Environment environment) throws Exception {
        ScmEventListener scmEventBroadcaster = scmEventBroadcaster(configuration);

        JerseyEnvironment jersey = environment.jersey();

        GitEventListener gitEventListener = new GitEventListener(new GitPushEventAdapter(), scmEventBroadcaster);
        if (configuration.isBitBucketConfigured()) {
            jersey.register(BitBucketResourceFactory.create(configuration.getBitBucket(), gitEventListener));
        }
        jersey.register(gitHubResource(gitEventListener));
    }

    private ScmEventListener scmEventBroadcaster(CiToolingConfiguration configuration) {
        ScmEventBroadcaster scmEventBroadcaster = new ScmEventBroadcaster();
        ScmEventBroadcasterConfigurer.configure(scmEventBroadcaster, configuration);
        return scmEventBroadcaster;
    }

    private GitHubResource gitHubResource(GitEventListener gitEventBroadcaster) {
        return new GitHubResource(new uk.co.codera.ci.tooling.api.github.GitPushEventAdapter(), gitEventBroadcaster);
    }
}