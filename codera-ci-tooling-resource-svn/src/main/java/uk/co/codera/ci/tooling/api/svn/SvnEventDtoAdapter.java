package uk.co.codera.ci.tooling.api.svn;

import java.util.HashMap;
import java.util.Map;

import uk.co.codera.ci.tooling.api.svn.dto.SvnEventDto;
import uk.co.codera.ci.tooling.api.svn.dto.SvnEventType;
import uk.co.codera.ci.tooling.scm.ScmEvent;
import uk.co.codera.ci.tooling.scm.ScmEventType;

public class SvnEventDtoAdapter {

    private static final String TEMPLATE_SVN_URL = "svn://%s:%s/%s";

    private static final Map<SvnEventType, ScmEventType> eventTypeMappings = new HashMap<>();

    static {
        eventTypeMappings.put(SvnEventType.ADD, ScmEventType.ADD);
        eventTypeMappings.put(SvnEventType.UPDATE, ScmEventType.UPDATE);
        eventTypeMappings.put(SvnEventType.DELETE, ScmEventType.DELETE);
    }

    private final String host;
    private final int port;

    public SvnEventDtoAdapter(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ScmEvent from(SvnEventDto svnEvent) {
        return ScmEvent.anScmEvent().repositoryUrl(repositoryUrl(svnEvent)).eventType(eventType(svnEvent)).branchName(svnEvent.getBranch())
                .shortBranchName(svnEvent.getBranch()).projectName(svnEvent.getProject()).build();
    }

    private String repositoryUrl(SvnEventDto svnEvent) {
        return String.format(TEMPLATE_SVN_URL, this.host, this.port, svnEvent.getLocation());
    }

    private ScmEventType eventType(SvnEventDto svnEvent) {
        return eventTypeMappings.get(svnEvent.getEventType());
    }
}