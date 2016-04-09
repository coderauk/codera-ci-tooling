package uk.co.codera.jenkins.tooling.application;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import uk.co.codera.jenkins.tooling.api.bitbucket.BitBucketResource;
import uk.co.codera.jenkins.tooling.api.bitbucket.GitPushEventAdapter;
import uk.co.codera.jenkins.tooling.git.GitEventBroadcaster;
import uk.co.codera.jenkins.tooling.git.GitEventLogger;

public class JenkinsToolingApplication extends Application<JenkinsToolingConfiguration> {

    public static void main(String[] args) throws Exception {
        new JenkinsToolingApplication().run(args);
    }

    @Override
    public void run(JenkinsToolingConfiguration configuration, Environment environment) throws Exception {
        GitEventBroadcaster gitEventBroadcaster = new GitEventBroadcaster();
        gitEventBroadcaster.registerListener(new GitEventLogger());
        environment.jersey().register(new BitBucketResource(new GitPushEventAdapter(), gitEventBroadcaster));
    }
}