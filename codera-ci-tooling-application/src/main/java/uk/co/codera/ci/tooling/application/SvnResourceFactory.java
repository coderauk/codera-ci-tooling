package uk.co.codera.ci.tooling.application;

import uk.co.codera.ci.tooling.api.svn.SvnEventDtoAdapter;
import uk.co.codera.ci.tooling.api.svn.SvnResource;
import uk.co.codera.ci.tooling.scm.ScmEventListener;

public class SvnResourceFactory {

    public static SvnResource create(SvnConfiguration configuration, ScmEventListener scmEventListener) {
        return new SvnResource(eventAdapter(configuration), scmEventListener);
    }

    private static SvnEventDtoAdapter eventAdapter(SvnConfiguration configuration) {
        return new SvnEventDtoAdapter(configuration.getHost(), configuration.getPort());
    }
}