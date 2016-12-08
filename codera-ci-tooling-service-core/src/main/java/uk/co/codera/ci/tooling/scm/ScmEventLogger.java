package uk.co.codera.ci.tooling.scm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScmEventLogger implements ScmEventListener {

    @SuppressWarnings("squid:S1312")
    private final Logger logger;

    public ScmEventLogger() {
        this(LoggerFactory.getLogger(ScmEventLogger.class));
    }

    public ScmEventLogger(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void on(ScmEvent event) {
        this.logger.info("Received [{}]", event);
    }
}