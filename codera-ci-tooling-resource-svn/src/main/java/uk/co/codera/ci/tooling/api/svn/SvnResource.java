package uk.co.codera.ci.tooling.api.svn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.codera.ci.tooling.api.svn.dto.SvnEventDto;
import uk.co.codera.ci.tooling.scm.ScmEvent;
import uk.co.codera.ci.tooling.scm.ScmEventListener;

public class SvnResource {

    private final Logger logger;
    private final SvnEventDtoAdapter svnEventAdapter;
    private final ScmEventListener scmEventListener;

    public SvnResource(SvnEventDtoAdapter svnEventAdapter, ScmEventListener scmEventListener) {
        this(LoggerFactory.getLogger(SvnResource.class), svnEventAdapter, scmEventListener);
    }

    public SvnResource(Logger logger, SvnEventDtoAdapter svnEventAdapter, ScmEventListener scmEventListener) {
        this.logger = logger;
        this.svnEventAdapter = svnEventAdapter;
        this.scmEventListener = scmEventListener;
    }

    public void on(SvnEventDto svnEvent) {
        logger.info("Received event [{}]", svnEvent);
        ScmEvent scmEvent = this.svnEventAdapter.from(svnEvent);
        this.scmEventListener.on(scmEvent);
    }
}