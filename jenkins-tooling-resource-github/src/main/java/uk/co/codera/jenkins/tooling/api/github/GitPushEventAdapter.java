package uk.co.codera.jenkins.tooling.api.github;

import java.util.HashMap;
import java.util.Map;

import uk.co.codera.jenkins.tooling.git.GitPushEvent;
import uk.co.codera.jenkins.tooling.git.GitPushType;
import uk.co.codera.jenkins.tooling.git.GitReference;

public class GitPushEventAdapter {

    private static final String GIT_REFERENCE_PREFIX = "refs/heads/";
    public static final String EVENT_TYPE_CREATE = "create";
    public static final String EVENT_TYPE_DELETE = "delete";

    private static final Map<String, GitPushType> EVENT_TYPE_MAPPINGS = new HashMap<>();

    static {
        EVENT_TYPE_MAPPINGS.put(EVENT_TYPE_CREATE, GitPushType.ADD);
        EVENT_TYPE_MAPPINGS.put(EVENT_TYPE_DELETE, GitPushType.DELETE);
    }

    public GitPushEvent from(String eventType, GitHubPushEvent event) {
        Repository repository = event.getRepository();
        return GitPushEvent.aGitPushEvent().reference(gitReference(event))
                .repositoryName(repository.getName()).repositoryUrl(repository.getSshUrl())
                .pushType(pushType(eventType)).build();
    }

    private GitReference gitReference(GitHubPushEvent event) {
        return GitReference.from(GIT_REFERENCE_PREFIX + event.getRef());
    }

    private GitPushType pushType(String eventType) {
        return EVENT_TYPE_MAPPINGS.get(eventType);
    }
}