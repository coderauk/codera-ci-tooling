package uk.co.codera.ci.tooling.application;

import static uk.co.codera.ci.tooling.scm.ConfigurableScmEventListener.aConfigurableScmEventListenerFactory;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import uk.co.codera.ci.tooling.jenkins.JenkinsConnectionDetails;
import uk.co.codera.ci.tooling.jenkins.JenkinsJobCreator;
import uk.co.codera.ci.tooling.jenkins.JenkinsJobDeleter;
import uk.co.codera.ci.tooling.jenkins.JenkinsService;
import uk.co.codera.ci.tooling.scm.ScmEventBroadcaster;
import uk.co.codera.ci.tooling.scm.ScmEventListener;
import uk.co.codera.ci.tooling.scm.ScmEventLogger;
import uk.co.codera.ci.tooling.scm.ScmEventType;
import uk.co.codera.ci.tooling.sonar.HttpClientFactory;
import uk.co.codera.ci.tooling.sonar.SonarDeleteService;
import uk.co.codera.ci.tooling.sonar.SonarJobDeleter;
import uk.co.codera.ci.tooling.template.TemplateService;
import uk.co.codera.templating.TemplateEngine;
import uk.co.codera.templating.velocity.VelocityTemplateEngine;

public class ScmEventBroadcasterConfigurer {

    private static final String DEFAULT_SONAR_JOB_KEY_TEMPLATE = "${repositoryName}:${branchName}";

    public static void configure(ScmEventBroadcaster scmEventBroadcaster, CiToolingConfiguration configuration) {
        scmEventBroadcaster.registerListener(new ScmEventLogger());

        if (configuration.isJenkinsConfigured()) {
            scmEventBroadcaster.registerListener(jenkinsEventListener(configuration.getJenkins()));
        }

        if (configuration.isSonarConfigured()) {
            scmEventBroadcaster.registerListener(sonarEventListener(configuration.getSonar()));
        }
    }

    private static ScmEventListener sonarEventListener(SonarConfiguration configuration) {
        return aConfigurableScmEventListenerFactory().register(ScmEventType.DELETE, sonarJobDeleter(configuration))
                .build();
    }

    private static SonarJobDeleter sonarJobDeleter(SonarConfiguration sonarConfiguration) {
        TemplateService jobKeyFactory = sonarJobKeyFactory(sonarConfiguration);
        SonarDeleteService deleteService = new SonarDeleteService(new HttpClientFactory(),
                sonarConfiguration.getSonarUrl(), sonarConfiguration.getUser(), sonarConfiguration.getPassword());
        return new SonarJobDeleter(jobKeyFactory, deleteService);
    }

    private static TemplateService sonarJobKeyFactory(SonarConfiguration sonarConfiguration) {
        String template = sonarConfiguration.hasJobKeyTemplate() ? sonarConfiguration.getJobKeyTemplate()
                : DEFAULT_SONAR_JOB_KEY_TEMPLATE;
        return new TemplateService(new VelocityTemplateEngine(), template);
    }

    private static ScmEventListener jenkinsEventListener(JenkinsConfiguration jenkinsConfiguration) {
        TemplateEngine templateEngine = new VelocityTemplateEngine();
        TemplateService jobNameFactory = jenkinsJobNameFactory(templateEngine);
        TemplateService jobFactory = jenkinsJobFactory(jenkinsConfiguration, templateEngine);
        JenkinsService jenkinsService = jenkinsService(jenkinsConfiguration);
        return aConfigurableScmEventListenerFactory()
                .register(ScmEventType.ADD, jenkinsJobCreator(jobNameFactory, jobFactory, jenkinsService))
                .register(ScmEventType.DELETE, jenkinsJobDeleter(jobNameFactory, jenkinsService)).build();
    }

    private static ScmEventListener jenkinsJobCreator(TemplateService jobNameFactory, TemplateService jobFactory,
            JenkinsService jenkinsService) {
        return new JenkinsJobCreator(jobNameFactory, jobFactory, jenkinsService);
    }

    private static ScmEventListener jenkinsJobDeleter(TemplateService jobNameFactory, JenkinsService jenkinsService) {
        return new JenkinsJobDeleter(jobNameFactory, jenkinsService);
    }

    private static JenkinsService jenkinsService(JenkinsConfiguration configuration) {
        JenkinsConnectionDetails jenkinsConfiguration = JenkinsConnectionDetails.aJenkinsConfiguration()
                .serverUrl(configuration.getJenkinsServerUrl()).build();
        return new JenkinsService(jenkinsConfiguration);
    }

    private static TemplateService jenkinsJobFactory(JenkinsConfiguration configuration, TemplateEngine templateEngine) {
        try {
            String jobTemplate = FileUtils.readFileToString(new File(configuration.getJenkinsJobTemplateFile()));
            return new TemplateService(templateEngine, jobTemplate);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static TemplateService jenkinsJobNameFactory(TemplateEngine templateEngine) {
        return new TemplateService(templateEngine, "${repositoryName} - ${shortBranchName} - build");
    }
}