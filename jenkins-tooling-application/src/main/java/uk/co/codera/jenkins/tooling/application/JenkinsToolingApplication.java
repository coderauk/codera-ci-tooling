package uk.co.codera.jenkins.tooling.application;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import uk.co.codera.jenkins.tooling.api.bitbucket.BitBucketResource;
import uk.co.codera.jenkins.tooling.api.bitbucket.GitPushEventAdapter;
import uk.co.codera.jenkins.tooling.git.ConfigurableGitEventListenerFactory;
import uk.co.codera.jenkins.tooling.git.GitEventBroadcaster;
import uk.co.codera.jenkins.tooling.git.GitEventListener;
import uk.co.codera.jenkins.tooling.git.GitEventLogger;
import uk.co.codera.jenkins.tooling.git.GitPushType;
import uk.co.codera.jenkins.tooling.jenkins.JenkinsJobCreator;
import uk.co.codera.jenkins.tooling.jenkins.JenkinsJobFactory;
import uk.co.codera.jenkins.tooling.jenkins.JenkinsService;
import uk.co.codera.templating.velocity.VelocityTemplateEngine;

public class JenkinsToolingApplication extends Application<JenkinsToolingConfiguration> {

    public static void main(String[] args) throws Exception {
        new JenkinsToolingApplication().run(args);
    }

    @Override
    public void run(JenkinsToolingConfiguration configuration, Environment environment) throws Exception {
        GitEventBroadcaster gitEventBroadcaster = new GitEventBroadcaster();
        gitEventBroadcaster.registerListener(new GitEventLogger());
        gitEventBroadcaster.registerListener(jenkinsEventListener());
        environment.jersey().register(bitBucketResource(configuration, gitEventBroadcaster));
    }

    private GitEventListener jenkinsEventListener() {
        return ConfigurableGitEventListenerFactory.aConfigurableGitEventListenerFactory()
                .register(GitPushType.ADD, jenkinsJobCreator()).build();
    }

    private GitEventListener jenkinsJobCreator() {
        JenkinsJobFactory jobFactory = new JenkinsJobFactory(new VelocityTemplateEngine(),
                "branchName: $branchName, repositoryUrl: $repositoryUrl");
        return new JenkinsJobCreator(jobFactory, new JenkinsService());
    }

    private BitBucketResource bitBucketResource(JenkinsToolingConfiguration configuration,
            GitEventBroadcaster gitEventBroadcaster) {
        GitPushEventAdapter gitPushEventAdapter = new GitPushEventAdapter(configuration.getBitBucketServerName(),
                configuration.getBitBucketServerPort());
        return new BitBucketResource(gitPushEventAdapter, gitEventBroadcaster);
    }
}