package uk.co.codera.jenkins.tooling.jenkins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JenkinsService {

    private static final Logger logger = LoggerFactory.getLogger(JenkinsService.class);
    
    public void createJob(String jobDefinition) {
        logger.info("Create job with definition [{}]", jobDefinition);
    }
}