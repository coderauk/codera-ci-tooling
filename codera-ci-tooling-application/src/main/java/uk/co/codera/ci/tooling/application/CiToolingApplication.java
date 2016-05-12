package uk.co.codera.ci.tooling.application;

import static uk.co.codera.ci.tooling.git.ConfigurableGitEventListenerFactory.aConfigurableGitEventListenerFactory;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import io.dropwizard.Application;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Environment;
import uk.co.codera.ci.tooling.api.bitbucket.BitBucketResource;
import uk.co.codera.ci.tooling.api.bitbucket.GitPushEventAdapter;
import uk.co.codera.ci.tooling.api.github.GitHubResource;
import uk.co.codera.ci.tooling.git.GitEventBroadcaster;
import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitEventLogger;
import uk.co.codera.ci.tooling.git.GitPushType;
import uk.co.codera.ci.tooling.jenkins.JenkinsConfiguration;
import uk.co.codera.ci.tooling.jenkins.JenkinsJobCreator;
import uk.co.codera.ci.tooling.jenkins.JenkinsJobDeleter;
import uk.co.codera.ci.tooling.jenkins.JenkinsService;
import uk.co.codera.ci.tooling.sonar.HttpClientFactory;
import uk.co.codera.ci.tooling.sonar.SonarDeleteService;
import uk.co.codera.ci.tooling.sonar.SonarJobDeleter;
import uk.co.codera.ci.tooling.template.TemplateService;
import uk.co.codera.templating.TemplateEngine;
import uk.co.codera.templating.velocity.VelocityTemplateEngine;

public class CiToolingApplication extends Application<CiToolingConfiguration> {

    public static void main(String[] args) throws Exception {
        new CiToolingApplication().run(args);
    }

    @Override
    public void run(CiToolingConfiguration configuration, Environment environment) throws Exception {
        GitEventBroadcaster gitEventBroadcaster = gitEventBroadcaster(configuration);

        JerseyEnvironment jersey = environment.jersey();

        if (configuration.isBitBucketConfigured()) {
            jersey.register(bitBucketResource(configuration.getBitBucket(), gitEventBroadcaster));
        }
        jersey.register(gitHubResource(gitEventBroadcaster));
    }

    private GitEventBroadcaster gitEventBroadcaster(CiToolingConfiguration configuration) {
        GitEventBroadcaster gitEventBroadcaster = new GitEventBroadcaster();
        gitEventBroadcaster.registerListener(new GitEventLogger());

        if (configuration.isJenkinsConfigured()) {
            gitEventBroadcaster.registerListener(jenkinsEventListener(configuration.getJenkins()));
        }

        if (configuration.isSonarConfigured()) {
            gitEventBroadcaster.registerListener(sonarEventListener(configuration.getSonar()));
        }
        return gitEventBroadcaster;
    }

    private GitEventListener sonarEventListener(SonarConfiguration configuration) {
        return aConfigurableGitEventListenerFactory().register(GitPushType.DELETE, sonarJobDeleter(configuration))
                .build();
    }

    private SonarJobDeleter sonarJobDeleter(SonarConfiguration sonarConfiguration) {
        SonarDeleteService deleteService = new SonarDeleteService(new HttpClientFactory(),
                sonarConfiguration.getSonarUrl(), sonarConfiguration.getUser(), sonarConfiguration.getPassword());
        return new SonarJobDeleter(deleteService);
    }

    private GitEventListener jenkinsEventListener(uk.co.codera.ci.tooling.application.JenkinsConfiguration configuration) {
        TemplateEngine templateEngine = new VelocityTemplateEngine();
        TemplateService jobNameFactory = jenkinsJobNameFactory(templateEngine);
        TemplateService jobFactory = jenkinsJobFactory(configuration, templateEngine);
        JenkinsService jenkinsService = jenkinsService(configuration);
        return aConfigurableGitEventListenerFactory()
                .register(GitPushType.ADD, jenkinsJobCreator(jobNameFactory, jobFactory, jenkinsService))
                .register(GitPushType.DELETE, jenkinsJobDeleter(jobNameFactory, jenkinsService)).build();
    }

    private GitEventListener jenkinsJobCreator(TemplateService jobNameFactory, TemplateService jobFactory,
            JenkinsService jenkinsService) {
        return new JenkinsJobCreator(jobNameFactory, jobFactory, jenkinsService);
    }

    private GitEventListener jenkinsJobDeleter(TemplateService jobNameFactory, JenkinsService jenkinsService) {
        return new JenkinsJobDeleter(jobNameFactory, jenkinsService);
    }

    private JenkinsService jenkinsService(uk.co.codera.ci.tooling.application.JenkinsConfiguration configuration) {
        JenkinsConfiguration jenkinsConfiguration = JenkinsConfiguration.aJenkinsConfiguration()
                .serverUrl(configuration.getJenkinsServerUrl()).build();
        return new JenkinsService(jenkinsConfiguration);
    }

    private TemplateService jenkinsJobFactory(uk.co.codera.ci.tooling.application.JenkinsConfiguration configuration,
            TemplateEngine templateEngine) {
        try {
            String jobTemplate = FileUtils.readFileToString(new File(configuration.getJenkinsJobTemplateFile()));
            return new TemplateService(templateEngine, jobTemplate);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private TemplateService jenkinsJobNameFactory(TemplateEngine templateEngine) {
        return new TemplateService(templateEngine, "${repositoryName} - ${shortBranchName} - build");
    }

    private BitBucketResource bitBucketResource(BitBucketConfiguration configuration,
            GitEventBroadcaster gitEventBroadcaster) {
        GitPushEventAdapter gitPushEventAdapter = new GitPushEventAdapter(configuration.getBitBucketServerName(),
                configuration.getBitBucketServerPort());
        return new BitBucketResource(gitPushEventAdapter, gitEventBroadcaster);
    }

    private GitHubResource gitHubResource(GitEventBroadcaster gitEventBroadcaster) {
        return new GitHubResource(new uk.co.codera.ci.tooling.api.github.GitPushEventAdapter(), gitEventBroadcaster);
    }
}