package uk.co.codera.ci.tooling.api.bitbucket;

import uk.co.codera.ci.tooling.api.bitbucket.dto.PushEventDto;
import uk.co.codera.ci.tooling.api.bitbucket.dto.RefChangeDto;
import uk.co.codera.ci.tooling.api.bitbucket.dto.RepositoryDto;
import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitReference;

public class PushEventDtoAdapter {

    private static final String TEMPLATE_REPOSITORY_URL = "ssh://git@%s:%d/%s/%s.git";

    private final String bitBucketServerName;
    private final int bitBucketServerPort;

    public PushEventDtoAdapter(String bitBucketServerName, int bitBucketServerPort) {
        this.bitBucketServerName = bitBucketServerName;
        this.bitBucketServerPort = bitBucketServerPort;
    }

    public GitPushEvent from(PushEventDto event) {
        RefChangeDto refChange = event.getRefChanges().get(0);
        return GitPushEvent.aGitPushEvent().pushType(refChange.getType()).reference(GitReference.from(refChange.getRefId()))
                .repositoryName(event.getRepository().getSlug()).repositoryUrl(repositoryUrl(event)).build();
    }

    private String repositoryUrl(PushEventDto event) {
        RepositoryDto repository = event.getRepository();
        return String.format(TEMPLATE_REPOSITORY_URL, this.bitBucketServerName, this.bitBucketServerPort, repository.getProject().getKey().toLowerCase(),
                repository.getSlug());
    }
}