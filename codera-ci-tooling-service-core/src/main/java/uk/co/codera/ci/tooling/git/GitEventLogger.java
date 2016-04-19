package uk.co.codera.ci.tooling.git;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.codera.ci.tooling.git.GitEventListener;
import uk.co.codera.ci.tooling.git.GitPushEvent;

public class GitEventLogger implements GitEventListener {

    @SuppressWarnings("squid:S1312")
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