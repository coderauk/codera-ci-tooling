package uk.co.codera.ci.tooling.jenkins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JenkinsService {

    private static final Logger logger = LoggerFactory.getLogger(JenkinsService.class);

    private final JenkinsConfiguration jenkinsConfiguration;
    private final JenkinsCommandLineInterfaceInvoker cliInvoker;

    public JenkinsService(JenkinsConfiguration jenkinsConfiguration) {
        this(jenkinsConfiguration, new JenkinsCommandLineInterfaceInvoker());
    }

    public JenkinsService(JenkinsConfiguration jenkinsConfiguration, JenkinsCommandLineInterfaceInvoker cliInvoker) {
        this.jenkinsConfiguration = jenkinsConfiguration;
        this.cliInvoker = cliInvoker;
    }

    public void createJob(String jobName, String jobDefinition) {
        logger.info("Create job with definition [{}]", jobDefinition);
        execute(CreateJobCommand.aCreateJobCommand().jobName(jobName).jobDefinition(jobDefinition)
                .with(this.jenkinsConfiguration));
    }

    public void deleteJob(String jobName) {
        logger.info("Delete job with name [{}]", jobName);
        execute(DeleteJobCommand.aDeleteJobCommand().jobName(jobName).with(this.jenkinsConfiguration));
    }

    private void execute(JenkinsCommand.Builder<?, ?> command) {
        command.build().execute(this.cliInvoker);
    }
}