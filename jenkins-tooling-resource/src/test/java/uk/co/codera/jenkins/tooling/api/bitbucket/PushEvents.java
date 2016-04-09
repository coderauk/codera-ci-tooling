package uk.co.codera.jenkins.tooling.api.bitbucket;

import uk.co.codera.jenkins.tooling.git.GitPushType;

public class PushEvents {

    public static final String REFERENCE_ID = "/refs/head/master";
    
    private PushEvents() {
        super();
    }
    
    public static PushEvent.Builder aValidPushEvent() {
        return PushEvent.aPushEvent().with(aValidAddRefChange().build());
    }
    
    private static RefChange.Builder aValidAddRefChange() {
        return RefChange.aRefChange().refId(REFERENCE_ID).type(GitPushType.ADD);
    }
}