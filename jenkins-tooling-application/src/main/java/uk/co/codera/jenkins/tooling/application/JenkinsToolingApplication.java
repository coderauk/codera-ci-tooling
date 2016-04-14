package uk.co.codera.jenkins.tooling.application;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import uk.co.codera.jenkins.tooling.api.bitbucket.BitBucketResource;
import uk.co.codera.jenkins.tooling.api.bitbucket.GitPushEventAdapter;
import uk.co.codera.jenkins.tooling.git.ConfigurableGitEventListenerFactory;
import uk.co.codera.jenkins.tooling.git.GitEventBroadcaster;
import uk.co.codera.jenkins.tooling.git.GitEventListener;
import uk.co.codera.jenkins.tooling.git.GitEventLogger;
import uk.co.codera.jenkins.tooling.git.GitPushType;
import uk.co.codera.jenkins.tooling.jenkins.JenkinsConfiguration;
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
        gitEventBroadcaster.registerListener(jenkinsEventListener(configuration));
        environment.jersey().register(bitBucketResource(configuration, gitEventBroadcaster));
    }

    private GitEventListener jenkinsEventListener(JenkinsToolingConfiguration configuration) {
        return ConfigurableGitEventListenerFactory.aConfigurableGitEventListenerFactory()
                .register(GitPushType.ADD, jenkinsJobCreator(configuration)).build();
    }

    private GitEventListener jenkinsJobCreator(JenkinsToolingConfiguration configuration) {
        try {
            String jobTemplate = FileUtils.readFileToString(new File(configuration.getJenkinsJobTemplateFile()));
            JenkinsJobFactory jobFactory = new JenkinsJobFactory(new VelocityTemplateEngine(), jobTemplate);
            JenkinsConfiguration jenkinsConfiguration = JenkinsConfiguration.aJenkinsConfiguration()
                    .serverUrl(configuration.getJenkinsServerName()).build();
            return new JenkinsJobCreator(jobFactory, new JenkinsService(jenkinsConfiguration));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private BitBucketResource bitBucketResource(JenkinsToolingConfiguration configuration,
            GitEventBroadcaster gitEventBroadcaster) {
        GitPushEventAdapter gitPushEventAdapter = new GitPushEventAdapter(configuration.getBitBucketServerName(),
                configuration.getBitBucketServerPort());
        return new BitBucketResource(gitPushEventAdapter, gitEventBroadcaster);
    }
}