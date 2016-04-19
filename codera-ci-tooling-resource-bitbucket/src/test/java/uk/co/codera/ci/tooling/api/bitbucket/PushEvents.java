package uk.co.codera.ci.tooling.api.bitbucket;

import uk.co.codera.ci.tooling.api.bitbucket.Project;
import uk.co.codera.ci.tooling.api.bitbucket.PushEvent;
import uk.co.codera.ci.tooling.api.bitbucket.RefChange;
import uk.co.codera.ci.tooling.api.bitbucket.Repository;
import uk.co.codera.ci.tooling.git.GitPushType;

public class PushEvents {

    public static final String GIT_REFERENCE_ID = "refs/head/master";
    public static final String REPOSITORY_SLUG = "tooling";
    public static final String PROJECT_KEY = "tooly";

    private PushEvents() {
        super();
    }

    public static PushEvent.Builder aValidPushEvent() {
        return PushEvent.aPushEvent().with(aValidRepository().build()).with(aValidAddRefChange().build());
    }

    public static Repository.Builder aValidRepository() {
        return Repository.aRepository().slug(REPOSITORY_SLUG).with(aValidProject().build());
    }

    public static Project.Builder aValidProject() {
        return Project.aProject().key(PROJECT_KEY);
    }

    public static RefChange.Builder aValidAddRefChange() {
        return RefChange.aRefChange().refId(GIT_REFERENCE_ID).type(GitPushType.ADD);
    }
}