package uk.co.codera.jenkins.tooling.api.bitbucket;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.codera.jenkins.tooling.git.GitEventListener;
import uk.co.codera.jenkins.tooling.git.GitPushEvent;

@Path("/bitbucket")
@Consumes(MediaType.APPLICATION_JSON)
public class BitBucketResource {

    private final Logger logger;
    private final GitEventListener gitEventListener;

    public BitBucketResource(Logger logger, GitEventListener gitEventListener) {
        this.logger = logger;
        this.gitEventListener = gitEventListener;
    }

    public BitBucketResource(GitEventListener gitEventListener) {
        this(LoggerFactory.getLogger(BitBucketResource.class), gitEventListener);
    }

    @POST
    public void push(PushEvent push) {
        this.logger.debug("Received push event [{}]", push);
        this.gitEventListener.onPush(GitPushEvent.aGitPushEvent().build());
    }
}