package uk.co.codera.ci.tooling.api.bitbucket;

import uk.co.codera.ci.tooling.git.GitPushEvent;
import uk.co.codera.ci.tooling.git.GitReference;

public class GitPushEventAdapter {

    private static final String TEMPLATE_REPOSITORY_URL = "ssh://git@%s:%d/%s/%s.git";

    private final String bitBucketServerName;
    private final int bitBucketServerPort;

    public GitPushEventAdapter(String bitBucketServerName, int bitBucketServerPort) {
        this.bitBucketServerName = bitBucketServerName;
        this.bitBucketServerPort = bitBucketServerPort;
    }

    public GitPushEvent from(PushEvent event) {
        RefChange refChange = event.getRefChanges().get(0);
        return GitPushEvent.aGitPushEvent().pushType(refChange.getType())
                .reference(GitReference.from(refChange.getRefId())).repositoryName(event.getRepository().getSlug())
                .repositoryUrl(repositoryUrl(event)).build();
    }

    private String repositoryUrl(PushEvent event) {
        Repository repository = event.getRepository();
        return String.format(TEMPLATE_REPOSITORY_URL, this.bitBucketServerName, this.bitBucketServerPort, repository
                .getProject().getKey().toLowerCase(), repository.getSlug());
    }
}