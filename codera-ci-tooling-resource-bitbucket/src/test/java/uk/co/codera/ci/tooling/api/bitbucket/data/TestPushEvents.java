package uk.co.codera.ci.tooling.api.bitbucket.data;

import uk.co.codera.ci.tooling.api.bitbucket.dto.ProjectDto;
import uk.co.codera.ci.tooling.api.bitbucket.dto.PushEventDto;
import uk.co.codera.ci.tooling.api.bitbucket.dto.RefChangeDto;
import uk.co.codera.ci.tooling.api.bitbucket.dto.RepositoryDto;
import uk.co.codera.ci.tooling.git.GitPushType;

public class TestPushEvents {

    public static final String GIT_BRANCH_REFERENCE_ID = "refs/head/master";
    public static final String GIT_TAG_REFERENCE_ID = "refs/tags/master";
    public static final String REPOSITORY_SLUG = "tooling";
    public static final String PROJECT_KEY = "tooly";

    private TestPushEvents() {
        super();
    }

    public static PushEventDto.Builder aValidPushEvent() {
        return PushEventDto.aPushEvent().with(aValidRepository().build()).with(aValidBranchAddRefChange().build());
    }

    public static PushEventDto.Builder aValidPushEventForTags() {
        return PushEventDto.aPushEvent().with(aValidRepository().build()).with(aValidTagAddRefChange().build());
    }

    public static RepositoryDto.Builder aValidRepository() {
        return RepositoryDto.aRepository().slug(REPOSITORY_SLUG).with(aValidProject().build());
    }

    public static ProjectDto.Builder aValidProject() {
        return ProjectDto.aProject().key(PROJECT_KEY);
    }

    public static RefChangeDto.Builder aValidBranchAddRefChange() {
        return RefChangeDto.aRefChange().refId(GIT_BRANCH_REFERENCE_ID).type(GitPushType.ADD);
    }

    public static RefChangeDto.Builder aValidTagAddRefChange() {
        return RefChangeDto.aRefChange().refId(GIT_TAG_REFERENCE_ID).type(GitPushType.ADD);
    }
}