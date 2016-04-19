package uk.co.codera.jenkins.tooling.git;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitEventLogger implements GitEventListener {

    private final Logger logger;
    
    public GitEventLogger() {
        this(LoggerFactory.getLogger(GitEventLogger.class));
    }
    
    public GitEventLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onPush(GitPushEvent event) {
        this.logger.info("Received [{}]", event);
    }
}