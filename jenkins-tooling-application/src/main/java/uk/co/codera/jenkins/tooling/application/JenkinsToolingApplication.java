package uk.co.codera.jenkins.tooling.application;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import uk.co.codera.jenkins.tooling.api.bitbucket.BitBucketResource;
import uk.co.codera.jenkins.tooling.api.bitbucket.GitPushEventAdapter;
import uk.co.codera.jenkins.tooling.git.GitEventBroadcaster;
import uk.co.codera.jenkins.tooling.git.GitEventListener;
import uk.co.codera.jenkins.tooling.git.GitEventLogger;
import uk.co.codera.jenkins.tooling.jenkins.JenkinsJobCreator;
import uk.co.codera.jenkins.tooling.jenkins.JenkinsJobFactory;
import uk.co.codera.templating.velocity.VelocityTemplateEngine;

public class JenkinsToolingApplication extends Application<JenkinsToolingConfiguration> {

    public static void main(String[] args) throws Exception {
        new JenkinsToolingApplication().run(args);
    }

    @Override
    public void run(JenkinsToolingConfiguration configuration, Environment environment) throws Exception {
        GitEventBroadcaster gitEventBroadcaster = new GitEventBroadcaster();
        gitEventBroadcaster.registerListener(new GitEventLogger());
        gitEventBroadcaster.registerListener(jenkinsJobCreator());
        environment.jersey().register(bitBucketResource(configuration, gitEventBroadcaster));
    }

    private GitEventListener jenkinsJobCreator() {
        JenkinsJobFactory jobFactory = new JenkinsJobFactory(new VelocityTemplateEngine(), "template");
        return new JenkinsJobCreator(jobFactory);
    }

    private BitBucketResource bitBucketResource(JenkinsToolingConfiguration configuration,
            GitEventBroadcaster gitEventBroadcaster) {
        GitPushEventAdapter gitPushEventAdapter = new GitPushEventAdapter(configuration.getBitBucketServerName(),
                configuration.getBitBucketServerPort());
        return new BitBucketResource(gitPushEventAdapter, gitEventBroadcaster);
    }
}