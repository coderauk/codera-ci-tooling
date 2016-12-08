package uk.co.codera.ci.tooling.svn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SvnEventLogger implements SvnEventListener {

    @SuppressWarnings("squid:S1312")
    private final Logger logger;

    public SvnEventLogger() {
        this(LoggerFactory.getLogger(SvnEventLogger.class));
    }

    public SvnEventLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void onCommit(SvnCommitEvent event) {
        this.logger.info("Received [{}]", event);
    }
}