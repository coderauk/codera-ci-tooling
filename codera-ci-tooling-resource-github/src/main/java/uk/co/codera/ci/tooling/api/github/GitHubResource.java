package uk.co.codera.ci.tooling.api.github;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.codera.ci.tooling.git.GitEventListener;

@Path("/github")
@Consumes(MediaType.APPLICATION_JSON)
public class GitHubResource {

    @SuppressWarnings("squid:S1312")
    private final Logger logger;
    private final GitPushEventAdapter gitPushEventAdapter;
    private final GitEventListener gitEventListener;

    public GitHubResource(GitPushEventAdapter gitPushEventAdapter, GitEventListener gitEventListener) {
        this(LoggerFactory.getLogger(GitHubResource.class), gitPushEventAdapter, gitEventListener);
    }

    public GitHubResource(Logger logger, GitPushEventAdapter gitPushEventAdapter, GitEventListener gitEventListener) {
        this.logger = logger;
        this.gitPushEventAdapter = gitPushEventAdapter;
        this.gitEventListener = gitEventListener;
    }

    @POST
    public void push(@HeaderParam("X-GitHub-Event") String eventType, GitHubPushEvent event) {
        this.logger.debug("Received eventType [{}] for event [{}]", eventType, event);
        if (event.isGitHubPages()) {
            this.logger.debug("Ignoring event because it is for the github pages branch");
        } else if (event.isBranch()) {
            this.gitEventListener.onPush(this.gitPushEventAdapter.from(eventType, event));
        } else {
            this.logger.debug("Ignoring event because it is not related to a branch");
        }
    }
}