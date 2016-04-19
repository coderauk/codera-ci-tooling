package uk.co.codera.jenkins.tooling.application;

import static uk.co.codera.jenkins.tooling.git.ConfigurableGitEventListenerFactory.aConfigurableGitEventListenerFactory;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import io.dropwizard.Application;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import uk.co.codera.jenkins.tooling.api.bitbucket.BitBucketResource;
import uk.co.codera.jenkins.tooling.api.bitbucket.GitPushEventAdapter;
import uk.co.codera.jenkins.tooling.api.github.GitHubResource;
import uk.co.codera.jenkins.tooling.git.GitEventBroadcaster;
import uk.co.codera.jenkins.tooling.git.GitEventListener;
import uk.co.codera.jenkins.tooling.git.GitEventLogger;
import uk.co.codera.jenkins.tooling.git.GitPushType;
import uk.co.codera.jenkins.tooling.jenkins.JenkinsConfiguration;
import uk.co.codera.jenkins.tooling.jenkins.JenkinsJobCreator;
import uk.co.codera.jenkins.tooling.jenkins.JenkinsJobDeleter;
import uk.co.codera.jenkins.tooling.jenkins.JenkinsService;
import uk.co.codera.jenkins.tooling.jenkins.JenkinsTemplateService;
import uk.co.codera.templating.TemplateEngine;
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
        JerseyEnvironment jersey = environment.jersey();
        jersey.register(bitBucketResource(configuration, gitEventBroadcaster));
        jersey.register(new GitHubResource(new uk.co.codera.jenkins.tooling.api.github.GitPushEventAdapter(), gitEventBroadcaster));
    }

    private GitEventListener jenkinsEventListener(JenkinsToolingConfiguration configuration) {
        TemplateEngine templateEngine = new VelocityTemplateEngine();
        JenkinsTemplateService jobNameFactory = jenkinsJobNameFactory(templateEngine);
        JenkinsTemplateService jobFactory = jenkinsJobFactory(configuration, templateEngine);
        JenkinsService jenkinsService = jenkinsService(configuration);
        return aConfigurableGitEventListenerFactory()
                .register(GitPushType.ADD, jenkinsJobCreator(jobNameFactory, jobFactory, jenkinsService))
                .register(GitPushType.DELETE, jenkinsJobDeleter(jobNameFactory, jenkinsService)).build();
    }

    private GitEventListener jenkinsJobCreator(JenkinsTemplateService jobNameFactory, JenkinsTemplateService jobFactory,
            JenkinsService jenkinsService) {
        return new JenkinsJobCreator(jobNameFactory, jobFactory, jenkinsService);
    }
    
    private GitEventListener jenkinsJobDeleter(JenkinsTemplateService jobNameFactory, JenkinsService jenkinsService) {
        return new JenkinsJobDeleter(jobNameFactory, jenkinsService);
    }

    private JenkinsService jenkinsService(JenkinsToolingConfiguration configuration) {
        JenkinsConfiguration jenkinsConfiguration = JenkinsConfiguration.aJenkinsConfiguration()
                .serverUrl(configuration.getJenkinsServerName()).build();
        return new JenkinsService(jenkinsConfiguration);
    }

    private JenkinsTemplateService jenkinsJobFactory(JenkinsToolingConfiguration configuration,
            TemplateEngine templateEngine) {
        try {
            String jobTemplate = FileUtils.readFileToString(new File(configuration.getJenkinsJobTemplateFile()));
            return new JenkinsTemplateService(templateEngine, jobTemplate);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private JenkinsTemplateService jenkinsJobNameFactory(TemplateEngine templateEngine) {
        return new JenkinsTemplateService(templateEngine, "${repositoryName} - ${shortBranchName} - build");
    }

    private BitBucketResource bitBucketResource(JenkinsToolingConfiguration configuration,
            GitEventBroadcaster gitEventBroadcaster) {
        GitPushEventAdapter gitPushEventAdapter = new GitPushEventAdapter(configuration.getBitBucketServerName(),
                configuration.getBitBucketServerPort());
        return new BitBucketResource(gitPushEventAdapter, gitEventBroadcaster);
    }
}