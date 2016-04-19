package uk.co.codera.ci.tooling.api.github;

import uk.co.codera.ci.tooling.api.github.GitHubPushEvent;
import uk.co.codera.ci.tooling.api.github.Repository;

public class GitHubPushEvents {

    public static final String REF = "a-branch";
    public static final String REPOSITORY_NAME = "myGitHubRepository";
    public static final String REPOSITORY_SSH_URL = "git@github.com:coderauk/myGitHubRepository.git";

    public static GitHubPushEvent.Builder aValidPushEvent() {
        return GitHubPushEvent.aPushEvent().ref(REF).with(aValidRepository().build());
    }

    public static Repository.Builder aValidRepository() {
        return Repository.aRepository().name(REPOSITORY_NAME).sshUrl(REPOSITORY_SSH_URL);
    }
}